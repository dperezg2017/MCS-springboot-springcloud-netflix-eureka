INSERT INTO usuarios(username,password,enabled,nombre,apellido,email) VALUES ('deyviz','12345',1,'Deyviz','Perez','dperezg2017@gmail.com');
INSERT INTO usuarios(username,password,enabled,nombre,apellido,email) VALUES ('eliodoro','12345',1,'Eliodoro','Chavez','dperezc2017@hotmail.com');

INSERT INTO roles(nombre) VALUES ('ROLE_USER');
INSERT INTO roles(nombre) VALUES ('ROLE_ADMIN');

INSERT INTO usuarios_roles(usuario_id,role_id) VALUES (1,1);
INSERT INTO usuarios_roles(usuario_id,role_id) VALUES (2,2);
INSERT INTO usuarios_roles(usuario_id,role_id) VALUES (2,1);