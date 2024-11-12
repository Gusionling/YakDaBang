-- User 테이블 생성
CREATE TABLE user (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      serial_id BIGINT NOT NULL UNIQUE,
                      email VARCHAR(255) NOT NULL,
                      nickname VARCHAR(255) NOT NULL,
                      password VARCHAR(255),
                      refresh_token VARCHAR(255),
                      is_login TINYINT(1),
                      created_at DATETIME(6) NOT NULL,
                      updated_at DATETIME(6)
);

-- News 테이블 생성
CREATE TABLE news (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      user_id BIGINT,
                      title VARCHAR(255) NOT NULL,
                      content TEXT,
                      url VARCHAR(255),
                      created_at DATETIME(6),
                      FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE SET NULL
);

-- UserNews 테이블 생성 (user 와 news 간 다대다 관계 매핑 테이블)
CREATE TABLE user_news (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           user_id BIGINT NOT NULL,
                           news_id BIGINT NOT NULL,
                           FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
                           FOREIGN KEY (news_id) REFERENCES news(id) ON DELETE CASCADE
);