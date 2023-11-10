INSERT INTO Permission (Code, CreatedAt, UpdatedAt) VALUES ('READ_INFO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), ('CREATE_INFO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), ('UPDATE_INFO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), ('DELETE_INFO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),('READ_USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),('CREATE_USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), ('UPDATE_USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), ('DELETE_USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), ('READ_ROLE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), ('CREATE_ROLE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), ('UPDATE_ROLE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), ('DELETE_ROLE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), ('READ_PRODUCT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), ('CREATE_PRODUCT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), ('UPDATE_PRODUCT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), ('DELETE_PRODUCT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), ('READ_SUPLIER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), ('CREATE_SUPLIER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), ('UPDATE_SUPLIER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), ('DELETE_SUPLIER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); 

INSERT INTO Role (Name, Code, Description, CreatedAt, UpdatedAt) VALUES ('ADMIN', 'ADMIN', 'Quản trị viên',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), ('SALER', 'SALER', 'Người bán',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), ('CUSTOMER', 'CUSTOMER', 'Khách hàng',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO RoleDetail (RoleId, PermissionId) VALUES (1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), (1, 9), (1, 10), (1, 11), (1, 12), (1, 13), (1, 14), (1, 15), (1, 16), (1, 17), (1, 18), (1, 19), (1, 20),  (2, 1), (2, 2), (2, 3), (2, 4), (2, 13), (2, 14), (2, 15), (2, 16),  (3, 1), (3, 2), (3, 3), (3, 4); 



INSERT INTO User (FullName, UserName, Password, Email, RoleId, CreatedAt, UpdatedAt) VALUES ('Admin', 'admin', '$2a$10$jiuninilsrqO/7zEw8W/pu65CtDrQdpyo9lO4kAiqMGzrq0unWyNS', 'phuthinh53.it@gmail.com', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)