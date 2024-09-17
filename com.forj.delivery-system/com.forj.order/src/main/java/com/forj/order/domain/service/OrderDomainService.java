package com.forj.order.domain.service;

import com.forj.common.security.SecurityUtil;
import com.forj.order.domain.enums.OrderStatusEnum;
import com.forj.order.domain.model.Order;
import com.forj.order.domain.repostiory.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class OrderDomainService {

    private final OrderRepository orderRepository;

    // 주문 생성
    @Transactional
    public Order create(
            UUID requestCompanyId,
            UUID receivingCompanyId,
            UUID productId,
            Integer quantity
    ){
        Order order = Order.create(
                requestCompanyId,
                receivingCompanyId,
                productId,
                quantity
        );
        log.info("[Order : OrderDomainService] 주문 생성");
        return orderRepository.save(order);
    }

    // 주문 단건 조회
    @Transactional(readOnly = true)
    public Order findOrderById(
            UUID orderId
    ){
        Order order = getOrder(orderId);
        log.info("[Order : OrderDomainService] 주문 단건 조회");
        return order;
    }

    // 주문 전체 조회
    @Transactional(readOnly = true)
    public Page<Order> findAllOrder(
            Pageable pageable
    ){
        log.info("[Order : OrderDomainService] 주문 전체 조회");
        return orderRepository.findAll(pageable);
    }

    // 주문 내용 수정
    @Transactional
    public Order update(
            UUID orderId,
            UUID receivingCompanyId,
            UUID productId,
            Integer quantity
    ){
        Order order = getOrder(orderId);
        log.info("[Order : OrderDomainService] 주문 내용 수정");

        if (!(order.getStatus() == OrderStatusEnum.PROGRESS)){
            log.info("[Order : OrderDomainService] 주문 수정 불가 현재 상태 : {}",order.getStatus());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"해당 주문건은 수정할 수 없습니다.");
        }
        order.update(
                receivingCompanyId,
                productId,
                quantity
        );

        return orderRepository.save(order);
    }

    // 주문 내역 삭제
    @Transactional
    public void delete(
            UUID orderId
    ){
        Order order = getOrder(orderId);
        log.info("[Order : OrderDomainService] 주문 내용 삭제");

        if (!(order.getStatus() == OrderStatusEnum.COMPLETED)){
            log.info("[Order : OrderDomainService] 주문 삭제 불가 현재 상태 : {}",order.getStatus());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"해당 주문건은 진행중이거나 이미 취소가 되었습니다.");
        }

        order.delete(SecurityUtil.getCurrentUserId());
    }

    // 주문 취소 요청
    @Transactional
    public Order cancel(
            UUID orderId
    ){
        Order order = getOrder(orderId);
        log.info("[Order : OrderDomainService] 주문 취소 요청");

        if (!order.getStatus().equals(OrderStatusEnum.PROGRESS)){
            log.info("[Order : OrderDomainService] 주문 취소 불가 현재 상태 : {}",order.getStatus());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"해당 주문 건은 이미 출고가 되었습니다.");
        }

        order.cancelOrder();

        return orderRepository.save(order);
    }

    // 배송쪽에서 배송id받아서 업데이트 하기
    @Transactional
    public void updateDeliveryId(
            UUID orderId,
            UUID deliveryId
    ){
        Order order = getOrder(orderId);

        log.info("[Order : OrderDomainService] 배송id 업데이트");

        order.updateDeliveryId(deliveryId);

        orderRepository.save(order);

        log.info("[Order : OrderDomainService] 배송id 업데이트 완료");
    }

    // 조회
    private Order getOrder(
            UUID orderId
    ){
        return orderRepository.findById(orderId)
                .filter(o -> !o.getIsdelete())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 주문은 찾을 수가 없습니다."));
    }

}
