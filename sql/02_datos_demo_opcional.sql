-- Datos de ejemplo OPCIONALES (los IDs se relacionan entre bases: cliente 1 -> equipo 1 -> orden 1).
-- Si cargas estos datos, la coleccion de Postman sigue funcionando (usa documentos/codigos aleatorios).

USE bd_cliente;
INSERT INTO cliente (documento, nombres, apellidos, correo, telefono) VALUES
('70123456', 'Piero', 'Guevara Garrido', 'piero@example.com', '999111222'),
('71234567', 'Lucia', 'Ramos Soto', 'lucia@example.com', '988777666');

USE bd_equipo;
INSERT INTO equipo (codigo, tipo, marca, modelo, id_cliente) VALUES
('EQ-0001', 'Laptop', 'Lenovo', 'ThinkPad E14', 1),
('EQ-0002', 'Impresora', 'Epson', 'L3250', 2);

USE bd_orden_servicio;
INSERT INTO orden_servicio (id_equipo, fecha_ingreso, descripcion, costo_estimado, estado) VALUES
(1, CURDATE(), 'No enciende, revisar fuente de poder', 120.00, 'RECIBIDO');
