-- User 테이블에 더미 사용자 추가
INSERT INTO user (serial_id, email, nickname, password, refresh_token, is_login, created_at, updated_at) VALUES
                                                                                                             (1001, 'john@example.com', 'JohnDoe', 'password123', 'refreshToken1', TRUE, NOW(), NOW()),
                                                                                                             (1002, 'jane@example.com', 'JaneDoe', 'password456', 'refreshToken2', FALSE, NOW(), NOW());

-- News 테이블에 더미 뉴스 추가
INSERT INTO news (user_id, title, content, url, created_at) VALUES
                                                                (1, '수면장애를 해결하고 싶다면, 식물성 멜라토닌 함유 멜라피스', '식물성 멜라토닌 함유 멜라피스는 외부 물질과 합성 성분을 담지 않고 100% 식물성 원료로 설계되었습니다. 불면증을 겪고 계신 분들이라면 멜라피스를 통해 편안하고 깊은 휴식을 취해보시길 바랍니다.', 'https://www.example.com/news1', NOW()),
                                                                (2, '엔큐엔에이, 타트체리 함량 ↑ 마그멜라퓨어-드림 출시', '엔큐엔에이 관계자는 이 제품이 생체이용률이 높은 해수 유래 마그네슘을 사용했다고 전했습니다.', 'https://www.example.com/news2', NOW()),
                                                                (1, '잠 못 드는 현대인…3조 원 국내 수면 시장 급성장', '타트체리, L-테아닌, 피스타치오 추출물 등 식물성 멜라토닌 원료를 활용한 건강기능식품이 인기를 끌고 있습니다.', 'https://www.example.com/news3', NOW());

-- UserNews 테이블에 사용자-뉴스 저장 정보 추가
INSERT INTO user_news (news_id,user_id ) VALUES
                                             (1, 1),
                                             (1, 2),
                                             (2, 1),
                                             (3, 2);