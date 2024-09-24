insert into p_delivery_agents (delivery_agent_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at, updated_by, agent_role, hub_id)

values
    (7, now(), 7, null, null, false, now(), now(), 'HUBDELIVERY', '461f1084-2b08-48a7-aa15-6ceafffb7b41'),
    (8, now(), 8, null, null, false, now(), now(), 'HUBDELIVERY', '0869eaa9-2d07-48e2-b6fc-1efb6bb7b223'),
    (9, now(), 9, null, null, false, now(), now(), 'COMPANYDELIVERY', 'd2b0698d-3ad7-47ae-b86a-f13e5c41e81d'),
    (10, now(), 10, null, null, false, now(), now(), 'COMPANYDELIVERY', '7687dde2-5dc9-4ff7-95ff-955a27703581');
