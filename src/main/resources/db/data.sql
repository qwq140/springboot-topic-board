INSERT INTO USER_TB (username, password, nickname)
VALUES ('admin1234', '$2a$10$XLR6XCaCc6ZZmFYqSCG0aO5J7LHqa70LF1s8q8IacDxYeFhoF0S1m', '어드민1');

INSERT INTO USER_ROLE_TB(user_id, role)
VALUES (1, 'USER');

INSERT INTO USER_ROLE_TB(user_id, role)
VALUES (1, 'ADMIN');