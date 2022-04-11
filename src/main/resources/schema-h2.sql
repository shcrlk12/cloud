DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users (
                       seq           bigint NOT NULL AUTO_INCREMENT, -- 사용자 PK
                       name          varchar(10) NOT NULL,           -- 사용자명
                       email         varchar(50) NOT NULL,           -- 로그인 이메일
                       passwd        varchar(80) NOT NULL,           -- 로그인 비밀번호
                       login_count   int NOT NULL DEFAULT 0,         -- 로그인 횟수. 로그인시 마다 1 증가
                       last_login_at datetime DEFAULT NULL,          -- 최종 로그인 일자
                       create_at     datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    PRIMARY KEY (seq)
    );