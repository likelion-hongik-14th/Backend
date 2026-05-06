-- 1. 사용자 데이터 (유저 생성 시 Cart도 생성되게 로직을 짜셨다면 User만 넣어도 됩니다)
INSERT INTO users (name, email, password) VALUES ('김철수', 'chul@test.com', '1234');
INSERT INTO users (name, email, password) VALUES ('이영희', 'young@test.com', '1234');

-- 2. 상품 데이터
INSERT INTO product (name, price, stock) VALUES ('기계식 키보드', 120000, 50);
INSERT INTO product (name, price, stock) VALUES ('로지텍 마우스', 55000, 100);
INSERT INTO product (name, price, stock) VALUES ('4K 모니터', 450000, 20);

-- 3. 장바구니 데이터 (User 생성 시 자동 생성 안 되게 되어있을 경우를 대비)
-- User ID 1번과 2번에게 각각 장바구니 하나씩 할당