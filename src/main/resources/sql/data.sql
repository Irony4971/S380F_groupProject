INSERT INTO users VALUES ('t', '{noop}t', 'Full Name', 'email@example.com', '1234567890');
INSERT INTO user_roles(username,role)
VALUES ('t',  'ROLE_TEACHER');
INSERT INTO user_roles(username, role)
VALUES ('t', 'ROLE_STUDENT');

INSERT INTO users VALUES ('m', '{noop}m', 'Jason Wong', '12345@example.com', '88887777');
INSERT INTO user_roles(username,role)
VALUES  ('m', 'ROLE_TEACHER');

INSERT INTO users VALUES ('n', '{noop}n', 'Double Happy', '54321@example.com', '77776666');
INSERT INTO user_roles(username,role)
VALUES  ('n', 'ROLE_STUDENT');