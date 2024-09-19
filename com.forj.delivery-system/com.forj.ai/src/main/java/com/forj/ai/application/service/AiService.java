package com.forj.ai.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.forj.ai.application.dto.response.AiHistoryResponseDto;
import com.forj.ai.application.dto.response.SlackMessageResponseDto;
import com.forj.ai.domain.model.Ai;
import com.forj.ai.domain.model.RequestType;
import com.forj.ai.domain.repository.AiRepository;
import com.forj.ai.infrastructure.dto.DeliveryAgentDto;
import com.forj.ai.infrastructure.dto.DeliveryDto;
import com.forj.ai.infrastructure.dto.HubDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiService {

    private final RestTemplate restTemplate;

    private final DeliveryAgentServiceClient deliveryAgentServiceClient;

    private final DeliveryServiceClient deliveryServiceClient;

    private final HubServiceClient hubServiceClient;

    private final AiRepository aiRepository;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Value("${openWeather.api.key}")
    private String openWeatherApiKey;

    // 배송담당자(업체)에게 보낼 정보 요청하기
    public List<SlackMessageResponseDto> getInfoForCompanyDeliveryAgents() {
        // 업체 배송 담당자들의 목록 가져오기
        List<DeliveryAgentDto> deliveryAgents = deliveryAgentServiceClient.getAllDeliveryAgentsByCompanyRole();

        // 각 담당자에 대한 요약 메시지 요청
        return deliveryAgents.stream()
                .map(this::getInfoForCompanyDeliveryAgent)
                .collect(Collectors.toList());
    }

    // 배송담당자(허브)에게 보낼 정보 요청하기
    public String requestForHubDeliveryAgent(Long deliveryAgentId) {
        // 현재 시간과 24시간 전 시간 계산
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneDayAgo = now.minusHours(24);

        // deliveryAgentId로 hubId 가져오기
        DeliveryAgentDto deliveryAgentDto = deliveryAgentServiceClient.getHubIdByDeliveryAgentId(deliveryAgentId);
        UUID hubId = deliveryAgentDto.hubId();

        // 24시간 전부터 지금까지의 배송 건수
        Long deliveries = deliveryServiceClient.getDeliveriesByAgentIdAndTimeRange(deliveryAgentId, hubId, oneDayAgo, now);

        // 요약 요청 메시지 생성
        String summaryRequest = createSummaryRequestForHub(deliveries);

        // gemini한테 요약 요청
        String aiResponse = summarizeWithGemini(summaryRequest, geminiApiKey);

        Ai aiHistory = Ai.createRequestHistory(deliveryAgentId,
                RequestType.FOR_HUB_DELIVERY_AGENT,
                summaryRequest,
                aiResponse);

        aiRepository.save(aiHistory);

        return aiResponse;
    }

    // ai 요청기록 조회
    public AiHistoryResponseDto getAiHistory(UUID aiId) {
        Ai ai = aiRepository.findByAiId(aiId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "기록이 없습니다."));
        return AiHistoryResponseDto.fromEntity(ai);
    }


    // -------------------------------------------------------------------------------------------------------------

    // 업체 배송 담당자(개별) 정보 생성
    private SlackMessageResponseDto getInfoForCompanyDeliveryAgent(DeliveryAgentDto deliveryAgentDto) {
        // 배송 담당자의 허브 정보와 위치 정보 가져오기
        HubDto hubDto = hubServiceClient.getLonLatByHubId(deliveryAgentDto.hubId(), "true");

        // 허브 위치 기반 날씨 정보 가져오기
        double longitude = hubDto.longitude();
        double latitude = hubDto.latitude();
        String weatherData = getWeather(longitude, latitude);

        // 배송 담당자의 PENDING 상태 배송 정보 가져오기
        List<DeliveryDto> deliveries = deliveryServiceClient.getPendingDeliveries();

        // 요약 메시지 생성
        String summaryRequest = createSummaryRequestForCompany(deliveries, weatherData);
        String aiResponse = summarizeWithGemini(summaryRequest, geminiApiKey);

        // AI 응답 저장
        Ai aiHistory = Ai.createRequestHistory(deliveryAgentDto.deliveryAgentId(), RequestType.FOR_COMPANY_DELIVERY_AGENT, summaryRequest, aiResponse);
        aiRepository.save(aiHistory);

        // SlackMessageRequestDto로 반환
        return new SlackMessageResponseDto(deliveryAgentDto.deliveryAgentId(), aiResponse);
    }

    // 날씨 정보 가져오기
    private String getWeather(double longitude, double latitude) {
        String uri = UriComponentsBuilder.fromUriString("http://api.openweathermap.org/data/2.5/forecast")
                .queryParam("appid", openWeatherApiKey)
                .queryParam("lon", longitude)
                .queryParam("lat", latitude)
                .queryParam("lang", "kr")
                .queryParam("units", "metric")
                .build()
                .toUriString();

        // 날씨 데이터 JSON 응답을 받아옴
        String response = restTemplate.getForObject(uri, String.class);

        // JSON 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode weatherList = root.path("list");

            // 날씨 설명을 모은 문자열
            StringBuilder weatherDescription = new StringBuilder();
            for (JsonNode weatherNode : weatherList) {
                JsonNode weather = weatherNode.path("weather").get(0);
                String description = weather.path("description").asText();
                weatherDescription.append(description).append(", ");
            }

            // 결과에서 마지막 쉼표 제거
            if (!weatherDescription.isEmpty()) {
                weatherDescription.setLength(weatherDescription.length() - 2);
            }

            return weatherDescription.toString();

        } catch (Exception e) {
            log.error("Error parsing weather data: ", e);
            return "날씨 정보를 가져오는 데 오류가 발생했습니다.";
        }
    }

    // gemini에게 보낼 요청 내용(업체배송담당자)
    private String createSummaryRequestForCompany(List<DeliveryDto> deliveries, String weatherData) {
        int deliveryCount = deliveries.size();
        String endAddresses = deliveries.stream()
                .map(DeliveryDto::endAddress)
                .distinct()
                .collect(Collectors.joining(", "));

        return String.format("%s 날씨랑 배송 건수: %d건, 배송지 주소: %s 이 내용들을 간단하게 요약해줘", weatherData, deliveryCount, endAddresses);
    }

    // gemini에게 보낼 요청 내용(허브배송담당자)
    private String createSummaryRequestForHub(long deliveryCount) {
        return String.format("%d건의 주문이 있다는 말과 함께 응원 한마디를 남겨줘", deliveryCount);
    }

    // gemini에게 요청
    private String summarizeWithGemini(String summaryRequest, String geminiApiKey) {
        String geminiApiUrl = UriComponentsBuilder.fromUriString("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent")
                .queryParam("key", geminiApiKey)
                .build()
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();

        // 요청 본문을 Gemini API 문서에 맞게 수정
        ObjectNode contentNode = objectMapper.createObjectNode();
        contentNode.put("text", summaryRequest);

        ObjectNode partsNode = objectMapper.createObjectNode();
        partsNode.set("parts", objectMapper.createArrayNode().add(contentNode));

        ObjectNode contentsNode = objectMapper.createObjectNode();
        contentsNode.set("contents", objectMapper.createArrayNode().add(partsNode));

        String requestBody = contentsNode.toString();

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        String response = restTemplate.postForObject(geminiApiUrl, requestEntity, String.class);

        try {
            // 응답을 JSON으로 파싱하여 필요한 "text"만 추출
            JsonNode root = objectMapper.readTree(response);
            JsonNode textNode = root.path("candidates").get(0)
                    .path("content").path("parts").get(0)
                    .path("text");

            return textNode.asText();

        } catch (Exception e) {
            log.error("Error parsing AI response: ", e);
            return "AI 응답을 처리하는 데 오류가 발생했습니다.";
        }
    }

}
