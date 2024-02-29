CREATE TABLE permission
(
    id     INT AUTO_INCREMENT PRIMARY KEY,
    name   VARCHAR(255),
    uri    VARCHAR(255),
    method VARCHAR(255)
);

insert into permission (uri, name, method)
values ('/api/v1/users/all', 'VIEW_ALL_USERS', 'GET'),
       ('/api/v1/users', 'GET_USERS_BY_ID', 'GET_BY'),
       ('/api/v1/roles/all', 'VIEW_ALL_ROLES', 'GET'),
       ('/api/v1/roles', 'GET_ROLES_BY_ID', 'GET_BY'),
       ('/api/v1/roles/create', 'CREATE_ROLES', 'POST'),
       ('/api/v1/roles/update', 'UPDATE_ROLES', 'PUT'),
       ('/api/v1/permissions/all', 'VIEW_ALL_PERMISSIONS', 'GET'),
       ('/api/v1/permissions', 'GET_PERMISSIONS_BY_ID', 'GET_BY'),
       ('/api/v1/permissions/create', 'CREATE_PERMISSIONS', 'POST'),
       ('/api/v1/permissions/update', 'UPDATE_PERMISSIONS', 'PUT');


CREATE TABLE role
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);
insert into role (name)
values ('ADMIN'),
       ('USER');

CREATE TABLE role_permission
(
    role_id       INT,
    permission_id INT,
    FOREIGN KEY (role_id) REFERENCES role (id),
    FOREIGN KEY (permission_id) REFERENCES permission (id),
    PRIMARY KEY (role_id, permission_id)
);

CREATE TABLE user
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(255) UNIQUE,
    password   VARCHAR(255),
    email      VARCHAR(255)
);
insert into user (email, password, username)
values ('admin@gmail.com', '$2a$12$CexShZfaHvNlwY5z0MDK3eclUsJFVXhyHcOct.Vfd3TDUGwhghQqG', 'admin');

CREATE TABLE users_roles
(
    user_id INT,
    role_id INT,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (role_id) REFERENCES role (id),
    PRIMARY KEY (user_id, role_id)
);

insert into users_roles(user_id, role_id)
values (1, 1);

insert into role_permission(role_id, permission_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (1, 8),
       (1, 9),
       (2, 10),
       (2, 1),
       (2, 2),
       (2, 3),
       (2, 4),
       (2, 7),
       (2, 8);