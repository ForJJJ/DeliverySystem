insert into p_hubs (id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at, updated_by, address,
                    latitude, longitude, name)
values
    ('18f02c77-ec44-48c6-a8c2-b672e5d806af', '2024-09-09 12:52:39.120405', 1, null, null, false, null, null, '서울특별시 송파구 송파대로 55 서울복합물류', 37.474154, 127.1239062, '서울특별시 센터'),
    ('69675a20-9b0c-4e5d-927b-cada133cf3c6', '2024-09-09 12:52:39.270407', 1, null, null, false, null, null, '경기도 고양시 덕양구 권율대로 570', 37.6403771, 126.8737955, '경기 북부 센터'),
    ('1a056bf6-6c7d-4cc8-9751-50e89ba911db', '2024-09-09 12:52:39.461375', 1, null, null, false, null, null, '경기도 이천시 호법면 덕평로 257-21', 37.1896213, 127.3750501, '경기 남부 센터'),
    ('be86d571-8fb1-4c91-b7a7-ad62df4b6a9b', '2024-09-09 12:52:39.628042', 1, null, null, false, null, null, '부산광역시 동구 중앙대로 206 한국철도공사부산지역본부', 35.1176012, 129.0450579, '부산광역시 센터'),
    ('0cf39431-c46e-4b86-ad94-09622bf638f1', '2024-09-09 12:52:39.777632', 1, null, null, false, null, null, '대구광역시 북구 태평로 161 롯데백화점 대구역', 35.8758632, 128.5961385, '대구광역시 센터'),
    ('88a3f3a8-ae43-43c7-9056-ed17e0103262', '2024-09-09 12:52:39.871679', 1, null, null, false, null, null, '인천광역시 남동구 정각로 29 인천광역시청', 37.4559418, 126.7051505, '인천광역시 센터'),
    ('63661bb4-fc7d-4bb2-b9b0-fd5f2da699e5', '2024-09-09 12:52:39.990867', 1, null, null, false, null, null, '광주광역시 서구 내방로 111 광주광역시청', 35.160032, 126.851338, '광주광역시 센터'),
    ('586b88a7-22ed-4af5-b8bc-f4bd7351a902', '2024-09-09 12:52:40.286524', 1, null, null, false, null, null, '대전광역시 서구 둔산로 100 대전광역시청', 36.3505001, 127.3848334, '대전광역시 센터'),
    ('fe34aaa2-4aae-47cc-a9fd-e0745f49ec1c', '2024-09-09 12:52:40.486769', 1, null, null, false, null, null, '울산광역시 남구 중앙로 201 울산광역시청', 35.5394773, 129.3112994, '울산광역시 센터'),
    ('c6cd9293-5bfe-4fea-93c0-da51cb4e4e8b', '2024-09-09 12:52:40.712639', 1, null, null, false, null, null, '세종특별자치시 한누리대로 2130 세종특별자치시청', 36.4800121, 127.2890691, '세종특별자치시 센터'),
    ('a6894cb4-91b2-4185-bb53-cf98fa632f6e', '2024-09-09 12:52:40.835293', 1, null, null, false, null, null, '강원특별자치도 춘천시 중앙로 1 강원특별자치도청', 37.885399, 127.72975, '강원특별자치도 센터'),
    ('ea72ebef-d620-460e-905e-5ee97d88f693', '2024-09-09 12:52:41.403721', 1, null, null, false, null, null, '충청북도 청주시 상당구 상당로 82 충청북도청', 36.6360995, 127.4913605, '충청북도 센터'),
    ('c87537c9-6374-4fb1-9f74-4f5327264168', '2024-09-09 12:52:41.508904', 1, null, null, false, null, null, '충청남도 홍성군 홍북읍 충남대로 21 충청남도청', 36.659249, 126.672908, '충청남도 센터'),
    ('2bfbdbb6-27fb-461e-803a-2a832d21af67', '2024-09-09 12:52:41.672951', 1, null, null, false, null, null, '전북특별자치도 전주시 완산구 효자로 225 전북특별자치도청', 35.8203294, 127.108784, '전북특별자치도 센터'),
    ('88f8b7e0-b7bc-4082-aafe-c35458cbe3fe', '2024-09-09 12:52:41.984111', 1, null, null, false, null, null, '전라남도 무안군 삼향읍 오룡길 1 전라남도청', 34.8161102, 126.4631714, '전라남도 센터'),
    ('6e0b452d-a2db-4ab2-9758-e2e7c41c9965', '2024-09-09 12:52:42.153244', 1, null, null, false, null, null, '경상북도 안동시 풍천면 도청대로 455 경상북도청', 36.5759477, 128.5056462, '경상북도 센터'),
    ('48578d51-5faf-4251-9fe4-a61a97693d72', '2024-09-09 12:52:42.403809', 1, null, null, false, null, null, '경상남도 창원시 의창구 중앙대로 300 경상남도청', 35.2377974, 128.6919403, '경상남도 센터');


-- 서울 ↔ 경기 북부
INSERT INTO p_hub_movements (id, is_deleted, departure_hub_id, arrival_hub_id, duration, route) VALUES
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '서울특별시 센터'), (SELECT id FROM p_hubs WHERE name = '경기 북부 센터'), NULL, '서울특별시 센터-경기 북부 센터'),
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '경기 북부 센터'), (SELECT id FROM p_hubs WHERE name = '서울특별시 센터'), NULL, '경기 북부 센터-서울특별시 센터');

-- 경기 북부 ↔ 경기 남부
INSERT INTO p_hub_movements (id, is_deleted, departure_hub_id, arrival_hub_id, duration, route) VALUES
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '경기 북부 센터'), (SELECT id FROM p_hubs WHERE name = '경기 남부 센터'), NULL, '경기 북부 센터-경기 남부 센터'),
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '경기 남부 센터'), (SELECT id FROM p_hubs WHERE name = '경기 북부 센터'), NULL, '경기 남부 센터-경기 북부 센터');

-- 경기 남부 ↔ 부산광역시
INSERT INTO p_hub_movements (id, is_deleted, departure_hub_id, arrival_hub_id, duration, route) VALUES
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '경기 남부 센터'), (SELECT id FROM p_hubs WHERE name = '부산광역시 센터'), NULL, '경기 남부 센터-부산광역시 센터'),
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '부산광역시 센터'), (SELECT id FROM p_hubs WHERE name = '경기 남부 센터'), NULL, '부산광역시 센터-경기 남부 센터');

-- 부산광역시 ↔ 대구광역시
INSERT INTO p_hub_movements (id, is_deleted, departure_hub_id, arrival_hub_id, duration, route) VALUES
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '부산광역시 센터'), (SELECT id FROM p_hubs WHERE name = '대구광역시 센터'), NULL, '부산광역시 센터-대구광역시 센터'),
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '대구광역시 센터'), (SELECT id FROM p_hubs WHERE name = '부산광역시 센터'), NULL, '대구광역시 센터-부산광역시 센터');

-- 대구광역시 ↔ 인천광역시
INSERT INTO p_hub_movements (id, is_deleted, departure_hub_id, arrival_hub_id, duration, route) VALUES
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '대구광역시 센터'), (SELECT id FROM p_hubs WHERE name = '인천광역시 센터'), NULL, '대구광역시 센터-인천광역시 센터'),
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '인천광역시 센터'), (SELECT id FROM p_hubs WHERE name = '대구광역시 센터'), NULL, '인천광역시 센터-대구광역시 센터');

-- 인천광역시 ↔ 광주광역시
INSERT INTO p_hub_movements (id, is_deleted, departure_hub_id, arrival_hub_id, duration, route) VALUES
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '인천광역시 센터'), (SELECT id FROM p_hubs WHERE name = '광주광역시 센터'), NULL, '인천광역시 센터-광주광역시 센터'),
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '광주광역시 센터'), (SELECT id FROM p_hubs WHERE name = '인천광역시 센터'), NULL, '광주광역시 센터-인천광역시 센터');

-- 광주광역시 ↔ 대전광역시
INSERT INTO p_hub_movements (id, is_deleted, departure_hub_id, arrival_hub_id, duration, route) VALUES
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '광주광역시 센터'), (SELECT id FROM p_hubs WHERE name = '대전광역시 센터'), NULL, '광주광역시 센터-대전광역시 센터'),
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '대전광역시 센터'), (SELECT id FROM p_hubs WHERE name = '광주광역시 센터'), NULL, '대전광역시 센터-광주광역시 센터');

-- 대전광역시 ↔ 울산광역시
INSERT INTO p_hub_movements (id, is_deleted, departure_hub_id, arrival_hub_id, duration, route) VALUES
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '대전광역시 센터'), (SELECT id FROM p_hubs WHERE name = '울산광역시 센터'), NULL, '대전광역시 센터-울산광역시 센터'),
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '울산광역시 센터'), (SELECT id FROM p_hubs WHERE name = '대전광역시 센터'), NULL, '울산광역시 센터-대전광역시 센터');

-- 울산광역시 ↔ 세종특별자치도
INSERT INTO p_hub_movements (id, is_deleted, departure_hub_id, arrival_hub_id, duration, route) VALUES
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '울산광역시 센터'), (SELECT id FROM p_hubs WHERE name = '세종특별자치도 센터'), NULL, '울산광역시 센터-세종특별자치도 센터'),
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '세종특별자치도 센터'), (SELECT id FROM p_hubs WHERE name = '울산광역시 센터'), NULL, '세종특별자치도 센터-울산광역시 센터');

-- 세종특별자치도 ↔ 강원특별자치도
INSERT INTO p_hub_movements (id, is_deleted, departure_hub_id, arrival_hub_id, duration, route) VALUES
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '세종특별자치도 센터'), (SELECT id FROM p_hubs WHERE name = '강원특별자치도 센터'), NULL, '세종특별자치도 센터-강원특별자치도 센터'),
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '강원특별자치도 센터'), (SELECT id FROM p_hubs WHERE name = '세종특별자치도 센터'), NULL, '강원특별자치도 센터-세종특별자치도 센터');

-- 강원특별자치도 ↔ 충청북도
INSERT INTO p_hub_movements (id, is_deleted, departure_hub_id, arrival_hub_id, duration, route) VALUES
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '강원특별자치도 센터'), (SELECT id FROM p_hubs WHERE name = '충청북도 센터'), NULL, '강원특별자치도 센터-충청북도 센터'),
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '충청북도 센터'), (SELECT id FROM p_hubs WHERE name = '강원특별자치도 센터'), NULL, '충청북도 센터-강원특별자치도 센터');

-- 충청북도 ↔ 충청남도
INSERT INTO p_hub_movements (id, is_deleted, departure_hub_id, arrival_hub_id, duration, route) VALUES
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '충청북도 센터'), (SELECT id FROM p_hubs WHERE name = '충청남도 센터'), NULL, '충청북도 센터-충청남도 센터'),
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '충청남도 센터'), (SELECT id FROM p_hubs WHERE name = '충청북도 센터'), NULL, '충청남도 센터-충청북도 센터');

-- 충청남도 ↔ 전북특별자치도
INSERT INTO p_hub_movements (id, is_deleted, departure_hub_id, arrival_hub_id, duration, route) VALUES
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '충청남도 센터'), (SELECT id FROM p_hubs WHERE name = '전북특별자치도 센터'), NULL, '충청남도 센터-전북특별자치도 센터'),
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '전북특별자치도 센터'), (SELECT id FROM p_hubs WHERE name = '충청남도 센터'), NULL, '전북특별자치도 센터-충청남도 센터');

-- 전북특별자치도 ↔ 전라남도
INSERT INTO p_hub_movements (id, is_deleted, departure_hub_id, arrival_hub_id, duration, route) VALUES
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '전북특별자치도 센터'), (SELECT id FROM p_hubs WHERE name = '전라남도 센터'), NULL, '전북특별자치도 센터-전라남도 센터'),
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '전라남도 센터'), (SELECT id FROM p_hubs WHERE name = '전북특별자치도 센터'), NULL, '전라남도 센터-전북특별자치도 센터');

-- 전라남도 ↔ 경상북도
INSERT INTO p_hub_movements (id, is_deleted, departure_hub_id, arrival_hub_id, duration, route) VALUES
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '전라남도 센터'), (SELECT id FROM p_hubs WHERE name = '경상북도 센터'), NULL, '경상북도 센터-전라남도 센터'),
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '경상북도 센터'), (SELECT id FROM p_hubs WHERE name = '전라남도 센터'), NULL, '전라남도 센터-경상북도 센터');

-- 경상북도 ↔ 경상남도
INSERT INTO p_hub_movements (id, is_deleted, departure_hub_id, arrival_hub_id, duration, route) VALUES
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '경상북도 센터'), (SELECT id FROM p_hubs WHERE name = '경상남도 센터'), NULL, '경상남도 센터-경상북도 센터'),
(gen_random_uuid(), false, (SELECT id FROM p_hubs WHERE name = '경상남도 센터'), (SELECT id FROM p_hubs WHERE name = '경상북도 센터'), NULL, '경상북도 센터-경상남도 센터');
