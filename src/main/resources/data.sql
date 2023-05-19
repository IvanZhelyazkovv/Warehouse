INSERT INTO users (email, name, password, firm)
VALUES ('admin@admin.com', 'admin admin', '$2a$10$.tPlRC9Afvh42Ly.6J2p/OHnTR2Wgs7yR18y.2D5nAzAJt5XMX0EC', '');
INSERT INTO roles (name) value ('ROLE_ADMIN');
INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1);
INSERT INTO warehouse (cashbox, name) VALUES (200000, 'demo');