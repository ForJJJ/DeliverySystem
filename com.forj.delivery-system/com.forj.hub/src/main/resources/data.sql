insert into p_hubs (id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at, updated_by, address,
                    latitude, longitude, name)
values
    (gen_random_uuid(), now(), 1, null, null, false, null, null, '서울특별시 송파구 송파대로 55 서울복합물류', 37.474154, 127.1239062, '서울특별시 센터'),
    (gen_random_uuid(), now(), 1, null, null, false, null, null, '경기도 고양시 덕양구 권율대로 570', 37.6403771, 126.8737955, '경기 북부 센터'),
    (gen_random_uuid(), now(), 1, null, null, false, null, null, '경기도 이천시 호법면 덕평로 257-21', 37.1896213, 127.3750501, '경기 남부 센터'),
    (gen_random_uuid(), now(), 1, null, null, false, null, null, '부산광역시 동구 중앙대로 206 한국철도공사부산지역본부', 35.1176012, 129.0450579, '부산광역시 센터'),
    (gen_random_uuid(), now(), 1, null, null, false, null, null, '대구광역시 북구 태평로 161 롯데백화점 대구역', 35.8758632, 128.5961385, '대구광역시 센터'),
    (gen_random_uuid(), now(), 1, null, null, false, null, null, '인천광역시 남동구 정각로 29 인천광역시청', 37.4559418, 126.7051505, '인천광역시 센터'),
    (gen_random_uuid(), now(), 1, null, null, false, null, null, '광주광역시 서구 내방로 111 광주광역시청', 35.160032, 126.851338, '광주광역시 센터'),
    (gen_random_uuid(), now(), 1, null, null, false, null, null, '대전광역시 서구 둔산로 100 대전광역시청', 36.3505001, 127.3848334, '대전광역시 센터'),
    (gen_random_uuid(), now(), 1, null, null, false, null, null, '울산광역시 남구 중앙로 201 울산광역시청', 35.5394773, 129.3112994, '울산광역시 센터'),
    (gen_random_uuid(), now(), 1, null, null, false, null, null, '세종특별자치시 한누리대로 2130 세종특별자치시청', 36.4800121, 127.2890691, '세종특별자치시 센터'),
    (gen_random_uuid(), now(), 1, null, null, false, null, null, '강원특별자치도 춘천시 중앙로 1 강원특별자치도청', 37.885399, 127.72975, '강원특별자치도 센터'),
    (gen_random_uuid(), now(), 1, null, null, false, null, null, '충청북도 청주시 상당구 상당로 82 충청북도청', 36.6360995, 127.4913605, '충청북도 센터'),
    (gen_random_uuid(), now(), 1, null, null, false, null, null, '충청남도 홍성군 홍북읍 충남대로 21 충청남도청', 36.659249, 126.672908, '충청남도 센터'),
    (gen_random_uuid(), now(), 1, null, null, false, null, null, '전북특별자치도 전주시 완산구 효자로 225 전북특별자치도청', 35.8203294, 127.108784, '전북특별자치도 센터'),
    (gen_random_uuid(), now(), 1, null, null, false, null, null, '전라남도 무안군 삼향읍 오룡길 1 전라남도청', 34.8161102, 126.4631714, '전라남도 센터'),
    (gen_random_uuid(), now(), 1, null, null, false, null, null, '경상북도 안동시 풍천면 도청대로 455 경상북도청', 36.5759477, 128.5056462, '경상북도 센터'),
    (gen_random_uuid(), now(), 1, null, null, false, null, null, '경상남도 창원시 의창구 중앙대로 300 경상남도청', 35.2377974, 128.6919403, '경상남도 센터');


-- 서울 ↔ 경기 북부
INSERT INTO p_hub_movements (id, is_deleted, departure_hub_id, arrival_hub_id, duration, route) VALUES
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '서울특별시 센터'), (SELECT id FROM p_hubs WHERE name = '경기 북부 센터'), NULL, '서울특별시 센터-경기 북부 센터');

-- 경기 북부 ↔ 경기 남부
INSERT INTO p_hub_movements (id, is_deleted, departure_hub_id, arrival_hub_id, duration, route) VALUES
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '경기 북부 센터'), (SELECT id FROM p_hubs WHERE name = '경기 남부 센터'), NULL, '경기 북부 센터-경기 남부 센터');

-- 경기 남부 ↔ 부산광역시
INSERT INTO p_hub_movements (id, is_deleted, departure_hub_id, arrival_hub_id, duration, route) VALUES
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '경기 남부 센터'), (SELECT id FROM p_hubs WHERE name = '부산광역시 센터'), NULL, '경기 남부 센터-부산광역시 센터');

-- 부산광역시 ↔ 대구광역시
INSERT INTO p_hub_movements (id, is_deleted, departure_hub_id, arrival_hub_id, duration, route) VALUES
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '부산광역시 센터'), (SELECT id FROM p_hubs WHERE name = '대구광역시 센터'), NULL, '부산광역시 센터-대구광역시 센터');

-- 대구광역시 ↔ 인천광역시
INSERT INTO p_hub_movements (id, is_deleted, departure_hub_id, arrival_hub_id, duration, route) VALUES
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '대구광역시 센터'), (SELECT id FROM p_hubs WHERE name = '인천광역시 센터'), NULL, '대구광역시 센터-인천광역시 센터');

-- 인천광역시 ↔ 광주광역시
INSERT INTO p_hub_movements (id, is_deleted, departure_hub_id, arrival_hub_id, duration, route) VALUES
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '인천광역시 센터'), (SELECT id FROM p_hubs WHERE name = '광주광역시 센터'), NULL, '인천광역시 센터-광주광역시 센터');

-- 광주광역시 ↔ 대전광역시
INSERT INTO p_hub_movements (id, is_deleted, departure_hub_id, arrival_hub_id, duration, route) VALUES
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '광주광역시 센터'), (SELECT id FROM p_hubs WHERE name = '대전광역시 센터'), NULL, '광주광역시 센터-대전광역시 센터');

-- 대전광역시 ↔ 울산광역시
INSERT INTO p_hub_movements (id, is_deleted, departure_hub_id, arrival_hub_id, duration, route) VALUES
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '대전광역시 센터'), (SELECT id FROM p_hubs WHERE name = '울산광역시 센터'), NULL, '대전광역시 센터-울산광역시 센터');

-- 울산광역시 ↔ 세종특별자치시
INSERT INTO p_hub_movements (id, is_deleted, departure_hub_id, arrival_hub_id, duration, route) VALUES
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '울산광역시 센터'), (SELECT id FROM p_hubs WHERE name = '세종특별자치시 센터'), NULL, '울산광역시 센터-세종특별자치시 센터');

-- 세종특별자치시 ↔ 강원특별자치도
INSERT INTO p_hub_movements (id, is_deleted, departure_hub_id, arrival_hub_id, duration, route) VALUES
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '세종특별자치시 센터'), (SELECT id FROM p_hubs WHERE name = '강원특별자치도 센터'), NULL, '세종특별자치시 센터-강원특별자치도 센터');

-- 강원특별자치도 ↔ 충청북도
INSERT INTO p_hub_movements (id, is_deleted, departure_hub_id, arrival_hub_id, duration, route) VALUES
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '강원특별자치도 센터'), (SELECT id FROM p_hubs WHERE name = '충청북도 센터'), NULL, '강원특별자치도 센터-충청북도 센터');

-- 충청북도 ↔ 충청남도
INSERT INTO p_hub_movements (id, is_deleted, departure_hub_id, arrival_hub_id, duration, route) VALUES
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '충청북도 센터'), (SELECT id FROM p_hubs WHERE name = '충청남도 센터'), NULL, '충청북도 센터-충청남도 센터');

-- 충청남도 ↔ 전북특별자치도
INSERT INTO p_hub_movements (id, is_deleted, departure_hub_id, arrival_hub_id, duration, route) VALUES
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '충청남도 센터'), (SELECT id FROM p_hubs WHERE name = '전북특별자치도 센터'), NULL, '충청남도 센터-전북특별자치도 센터');

-- 전북특별자치도 ↔ 전라남도
INSERT INTO p_hub_movements (id, is_deleted, departure_hub_id, arrival_hub_id, duration, route) VALUES
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '전북특별자치도 센터'), (SELECT id FROM p_hubs WHERE name = '전라남도 센터'), NULL, '전북특별자치도 센터-전라남도 센터');

-- 전라남도 ↔ 경상북도
INSERT INTO p_hub_movements (id, is_deleted, departure_hub_id, arrival_hub_id, duration, route) VALUES
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '전라남도 센터'), (SELECT id FROM p_hubs WHERE name = '경상북도 센터'), NULL, '경상북도 센터-전라남도 센터');

-- 경상북도 ↔ 경상남도
INSERT INTO p_hub_movements (id, is_deleted, departure_hub_id, arrival_hub_id, duration, route) VALUES
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '경상북도 센터'), (SELECT id FROM p_hubs WHERE name = '경상남도 센터'), NULL, '경상남도 센터-경상북도 센터');
