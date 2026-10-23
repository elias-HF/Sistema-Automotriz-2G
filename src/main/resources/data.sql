INSERT INTO rol (nombre, descripcion) SELECT 'ADMINISTRADOR', 'Acceso completo al sistema' WHERE NOT EXISTS (
    SELECT 1 FROM rol WHERE nombre = 'ADMINISTRADOR'
);

INSERT INTO rol (nombre, descripcion) SELECT 'MECANICO', 'Gestión de reparaciones y servicios' WHERE NOT EXISTS (
    SELECT 1 FROM rol WHERE nombre = 'MECANICO'
);

INSERT INTO rol (nombre, descripcion) SELECT 'RECEPCION', 'Gestión de clientes, vehículos y citas' WHERE NOT EXISTS (
    SELECT 1 FROM rol WHERE nombre = 'RECEPCION'
);

INSERT INTO rol (nombre, descripcion) SELECT 'ALMACEN', 'Gestión de repuestos e inventario' WHERE NOT EXISTS (
    SELECT 1 FROM rol WHERE nombre = 'ALMACEN'
);


INSERT INTO marca (nombre) VALUES ('Toyota');
INSERT INTO marca (nombre) VALUES ('Hyundai');
INSERT INTO marca (nombre) VALUES ('Kia');

INSERT INTO modelo (nombre, id_marca) VALUES ('Corolla', 1);
INSERT INTO modelo (nombre, id_marca) VALUES ('Yaris', 1);
INSERT INTO modelo (nombre, id_marca) VALUES ('Accent', 2);
INSERT INTO modelo (nombre, id_marca) VALUES ('Rio', 3);

INSERT INTO cliente (nombre, telefono, correo, cod, dni, activo)
VALUES ('Carlos Mendoza', '987654321', 'carlos@gmail.com', 'CLI-00001', '71234561', TRUE);
INSERT INTO cliente (nombre, telefono, correo, cod, dni, activo)
VALUES ('María Torres', '986123456', 'maria@gmail.com', 'CLI-00002', '72345612', TRUE);
INSERT INTO cliente (nombre, telefono, correo, cod, dni, activo)
VALUES ('Luis Ramírez', '985234567', 'luis@gmail.com', 'CLI-00003', '73456123', TRUE);

INSERT INTO medio_contacto (nombre) VALUES ('Teléfono');
INSERT INTO medio_contacto (nombre) VALUES ('WhatsApp');
INSERT INTO medio_contacto (nombre) VALUES ('Presencial');

INSERT INTO vehiculo (placa, anio_fabricacion, kilometros, cod, activo, id_modelo, id_cliente)
VALUES ('ABC-123', 2020, 45000.00, 'VEH-00001', TRUE, 1, 1);
INSERT INTO vehiculo (placa, anio_fabricacion, kilometros, cod, activo, id_modelo, id_cliente)
VALUES ('BCD-234', 2022, 28000.00, 'VEH-00002', TRUE, 2, 1);
INSERT INTO vehiculo (placa, anio_fabricacion, kilometros, cod, activo, id_modelo, id_cliente)
VALUES ('CDE-345', 2019, 62000.00, 'VEH-00003', TRUE, 3, 2);
INSERT INTO vehiculo (placa, anio_fabricacion, kilometros, cod, activo, id_modelo, id_cliente)
VALUES ('DEF-456', 2023, 15000.00, 'VEH-00004', TRUE, 4, 3);

INSERT INTO orden_servicio (fecha_hora_ingreso, fecha_hora_salida, diagnostico, monto_total, cod, estado, id_vehiculo)
VALUES (TIMESTAMP '2026-10-20 09:00:00',NULL,'Revisión general del vehículo',150.00,'ORD-00001','PENDIENTE',1);
INSERT INTO orden_servicio (fecha_hora_ingreso, fecha_hora_salida, diagnostico, monto_total, cod, estado, id_vehiculo)
VALUES (TIMESTAMP '2026-10-19 10:30:00',TIMESTAMP '2026-10-19 15:00:00','Cambio de aceite y filtro',220.00,'ORD-00002','FINALIZADA',2);
INSERT INTO orden_servicio (fecha_hora_ingreso, fecha_hora_salida, diagnostico, monto_total, cod, estado, id_vehiculo)
VALUES (TIMESTAMP '2026-10-21 08:30:00',NULL,'Revisión del sistema de frenos',350.00,'ORD-00003','EN_PROCESO',3);