-- INSERT INTO 'calendars' table
INSERT INTO calendars (cal_idx, c_theme) VALUES
(1, 'LIGHT'),
(2, 'LIGHT'),
(3, 'LIGHT'),
(4, 'LIGHT'),
(5, 'LIGHT');

-- INSERT INTO 'user' table
INSERT INTO user (u_idx, u_email, u_password, u_nickname, u_joinday, cal_idx, provider) VALUES
(1, 'aaa@naver.com', '$2a$10$026yHEvmvZ8iIwmdXgTo6eASStzX3HQMBMVTUnca2.jfEWWTJAVCW', '다람이', '2024-01-01 09:00:00', 1, 'LOCAL'),
(2, 'bbb@naver.com', '$2a$10$026yHEvmvZ8iIwmdXgTo6eASStzX3HQMBMVTUnca2.jfEWWTJAVCW', '포미언니', '2024-01-02 10:00:00', 2, 'LOCAL'),
(3, 'ccc@naver.com', '$2a$10$026yHEvmvZ8iIwmdXgTo6eASStzX3HQMBMVTUnca2.jfEWWTJAVCW', '루카쿠', '2024-01-03 11:00:00', 3, 'LOCAL'),
(4, 'ddd@naver.com', '$2a$10$026yHEvmvZ8iIwmdXgTo6eASStzX3HQMBMVTUnca2.jfEWWTJAVCW', '현빈', '2024-01-04 12:00:00', 4, 'LOCAL'),
(5, 'eee@naver.com', '$2a$10$026yHEvmvZ8iIwmdXgTo6eASStzX3HQMBMVTUnca2.jfEWWTJAVCW', '민지', '2024-01-05 13:00:00', 5, 'LOCAL');