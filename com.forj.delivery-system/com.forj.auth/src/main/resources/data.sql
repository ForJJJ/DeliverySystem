insert into p_users (user_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at, updated_by, password, role, slack_id, username)

values
    (1, now(), 1, null, null, false, now(), 1, '$2a$10$oo07QIR5WqYueEfyZ/6bW.qdlAr975f8Bx6EEbVM5KDI0xG4s8mkS', 'MASTER', 'U07L9V8KWLU', 'master1'),
    (2, now(), 2, null, null, false, now(), 2, '$2a$10$oo07QIR5WqYueEfyZ/6bW.qdlAr975f8Bx6EEbVM5KDI0xG4s8mkS', 'MASTER', 'U07L9V8KWLU', 'master2'),
    (3, now(), 3, null, null, false, now(), 3, '$2a$10$oo07QIR5WqYueEfyZ/6bW.qdlAr975f8Bx6EEbVM5KDI0xG4s8mkS', 'HUBMASTER', 'U07L9V8KWLU', 'hubmaster1'),
    (4, now(), 4, null, null, false, now(), 4, '$2a$10$oo07QIR5WqYueEfyZ/6bW.qdlAr975f8Bx6EEbVM5KDI0xG4s8mkS', 'HUBMASTER', 'U07L9V8KWLU', 'hubmaster2'),
    (5, now(), 5, null, null, false, now(), 5, '$2a$10$oo07QIR5WqYueEfyZ/6bW.qdlAr975f8Bx6EEbVM5KDI0xG4s8mkS', 'DELIVERYAGENT', 'U07L9V8KWLU', 'deliagent1'),
    (6, now(), 6, null, null, false, now(), 6, '$2a$10$oo07QIR5WqYueEfyZ/6bW.qdlAr975f8Bx6EEbVM5KDI0xG4s8mkS', 'DELIVERYAGENT', 'U07L9V8KWLU', 'deliagent2'),
    (7, now(), 7, null, null, false, now(), 7, '$2a$10$oo07QIR5WqYueEfyZ/6bW.qdlAr975f8Bx6EEbVM5KDI0xG4s8mkS', 'DELIVERYAGENT', 'U07L9V8KWLU', 'compagent1'),
    (8, now(), 8, null, null, false, now(), 8, '$2a$10$oo07QIR5WqYueEfyZ/6bW.qdlAr975f8Bx6EEbVM5KDI0xG4s8mkS', 'DELIVERYAGENT', 'U07L9V8KWLU', 'compagent2'),
    (9, now(), 9, null, null, false, now(), 9, '$2a$10$oo07QIR5WqYueEfyZ/6bW.qdlAr975f8Bx6EEbVM5KDI0xG4s8mkS', 'DELIVERYAGENT', 'U07L9V8KWLU', 'hubagent1'),
    (10, now(), 10, null, null, false, now(), 10, '$2a$10$oo07QIR5WqYueEfyZ/6bW.qdlAr975f8Bx6EEbVM5KDI0xG4s8mkS', 'DELIVERYAGENT', 'U07L9V8KWLU', 'hubagent2'),
    (11, now(), 11, null, null, false, now(), 11, '$2a$10$oo07QIR5WqYueEfyZ/6bW.qdlAr975f8Bx6EEbVM5KDI0xG4s8mkS', 'HUBCOMPANY', 'U07L9V8KWLU', 'hubcomp1'),
    (12, now(), 12, null, null, false, now(), 12, '$2a$10$oo07QIR5WqYueEfyZ/6bW.qdlAr975f8Bx6EEbVM5KDI0xG4s8mkS', 'HUBCOMPANY', 'U07L9V8KWLU', 'hubcomp2');
