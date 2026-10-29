-- Datos iniciales del sistema
-- Se cargan registros base para habilitar la aplicación en un entorno de desarrollo.

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
VALUES ('Carlos Mendoza', '987654321', 'carlos@gmail.com', 'CLI-00001', '71234561', TRUE),
('María Torres', '986123456', 'maria@gmail.com', 'CLI-00002', '72345612', TRUE),
('Luis Ramírez', '985234567', 'luis@gmail.com', 'CLI-00003', '73456123', TRUE);

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

INSERT INTO marca (nombre) VALUES ('Nissan');
INSERT INTO modelo (nombre, id_marca) VALUES ('Elantra', 2);
INSERT INTO modelo (nombre, id_marca) VALUES ('Tucson', 2);
INSERT INTO modelo (nombre, id_marca) VALUES ('Sportage', 3);
INSERT INTO modelo (nombre, id_marca) VALUES ('Seltos', 3);
INSERT INTO modelo (nombre, id_marca) VALUES ('Sentra', 4);
INSERT INTO modelo (nombre, id_marca) VALUES ('Versa', 4);

INSERT INTO unidad_medida (nombre, abreviatura)
VALUES ('Unidad', 'UND');
INSERT INTO unidad_medida (nombre, abreviatura)
VALUES ('Litro', 'L');
INSERT INTO unidad_medida (nombre, abreviatura)
VALUES ('Juego', 'JGO');
INSERT INTO unidad_medida (nombre, abreviatura)
VALUES ('Par', 'PAR');

INSERT INTO categoria (nombre, descripcion, activo)
VALUES ('Mantenimiento', 'Servicios de mantenimiento preventivo', TRUE);
INSERT INTO categoria (nombre, descripcion, activo)
VALUES ('Diagnóstico', 'Evaluación y detección de fallas', TRUE);
INSERT INTO categoria (nombre, descripcion, activo)
VALUES ('Frenos', 'Reparación y mantenimiento del sistema de frenos', TRUE);
INSERT INTO categoria (nombre, descripcion, activo)
VALUES ('Electricidad', 'Revisión del sistema eléctrico del vehículo', TRUE);
INSERT INTO categoria (nombre, descripcion, activo)
VALUES ('Motor', 'Reparación y revisión del motor', TRUE);

INSERT INTO cliente
(nombre, telefono, correo, cod, dni, activo)
VALUES
    ('Ana Flores', '984123456', 'ana.flores@gmail.com', 'CLI-00004', '74561234', TRUE),
    ('Jorge Castillo', '983234567', 'jorge.castillo@gmail.com', 'CLI-00005', '75612345', TRUE),
    ('Lucía Vargas', '982345678', 'lucia.vargas@gmail.com', 'CLI-00006', '76723456', TRUE),
    ('Pedro Salazar', '981456789', 'pedro.salazar@gmail.com', 'CLI-00007', '77834567', TRUE),
    ('Rosa Medina', '980567123', 'rosa.medina@gmail.com', 'CLI-00008', '78945678', TRUE);


INSERT INTO empleado
(nombre, correo, cod, usuario, contrasenia, activo, id_rol)
VALUES
    ('Carlos Rojas', 'c.rojas@2gautomotriz.com',
     'EMP-00002', 'crojas', '{noop}123456', TRUE, 2),

    ('Miguel Paredes', 'm.paredes@2gautomotriz.com',
     'EMP-00003', 'mparedes', '{noop}123456', TRUE, 2),

    ('Andrea Campos', 'a.campos@2gautomotriz.com',
     'EMP-00004', 'acampos', '{noop}123456', TRUE, 3),

    ('Diego Herrera', 'd.herrera@2gautomotriz.com',
     'EMP-00005', 'dherrera', '{noop}123456', TRUE, 4);

INSERT INTO articulo
(nombre, descripcion, precio, stock, cod, stock_min, activo, id_unidad)
VALUES
    ('Aceite sintético 5W-30', 'Aceite para motor de vehículos', 85.00, 25, 'ART-00001', 5, TRUE, 2),

    ('Filtro de aceite', 'Filtro para sistema de lubricación', 28.00, 18, 'ART-00002', 5, TRUE, 1),

    ('Filtro de aire', 'Filtro para admisión de aire del motor', 35.00, 12, 'ART-00003', 4, TRUE, 1),

    ('Pastillas de freno', 'Juego de pastillas para freno delantero', 120.00, 3, 'ART-00004', 5, TRUE, 3),

    ('Bujías', 'Juego de bujías para motor', 65.00, 8, 'ART-00005', 4, TRUE, 3),

    ('Refrigerante', 'Líquido refrigerante para motor', 32.00, 2, 'ART-00006', 5, TRUE, 2),

    ('Líquido de frenos', 'Líquido para sistema hidráulico de frenos', 25.00, 10, 'ART-00007', 3, TRUE, 2),

    ('Batería 12V', 'Batería para sistema eléctrico', 380.00, 4, 'ART-00008', 2, TRUE, 1),

    ('Foco H4', 'Foco para faros delanteros', 22.00, 15, 'ART-00009', 5, TRUE, 1),

    ('Correa auxiliar', 'Correa para accesorios del motor', 75.00, 3, 'ART-00010', 4, TRUE, 1),

    ('Limpiaparabrisas', 'Par de escobillas limpiaparabrisas', 48.00, 9, 'ART-00011', 3, TRUE, 4),

    ('Terminal de batería', 'Terminal metálico para batería', 18.00, 1, 'ART-00012', 3, TRUE, 1);

INSERT INTO servicio
(nombre, descripcion, cod, precio, activo, id_categoria)
VALUES
    ('Cambio de aceite', 'Cambio de aceite y revisión de niveles', 'SER-00001', 80.00, TRUE, 1),

    ('Mantenimiento preventivo', 'Inspección general y mantenimiento periódico', 'SER-00002', 180.00, TRUE, 1),

    ('Diagnóstico computarizado', 'Lectura de códigos y diagnóstico electrónico', 'SER-00003', 100.00, TRUE, 2),

    ('Revisión de frenos', 'Inspección del sistema de frenos', 'SER-00004', 70.00, TRUE, 3),

    ('Cambio de pastillas', 'Reemplazo de pastillas de freno', 'SER-00005', 90.00, TRUE, 3),

    ('Revisión eléctrica', 'Evaluación del sistema eléctrico', 'SER-00006', 85.00, TRUE, 4),

    ('Cambio de batería', 'Instalación y comprobación de batería', 'SER-00007', 45.00, TRUE, 4),

    ('Afinamiento de motor', 'Revisión de componentes y funcionamiento del motor', 'SER-00008', 220.00, TRUE, 5);

INSERT INTO vehiculo
(placa, anio_fabricacion, kilometros, cod, activo, id_modelo, id_cliente)
VALUES
    ('GHA-123', 2021, 35000, 'VEH-00005', TRUE, 5, 4),
    ('HJB-234', 2020, 58000, 'VEH-00006', TRUE, 6, 5),
    ('KLM-345', 2022, 24000, 'VEH-00007', TRUE, 7, 6),
    ('NPQ-456', 2018, 82000, 'VEH-00008', TRUE, 8, 7),
    ('RST-567', 2023, 12000, 'VEH-00009', TRUE, 9, 8);

INSERT INTO cita
(fecha, hora, motivo, estado, cod, id_cliente, id_contacto, id_vehiculo)
VALUES
    (DATE '2026-10-22', TIME '09:00:00',
     'Mantenimiento preventivo', 'ATENDIDA', 'CIT-00001', 1, 1, 1),

    (DATE '2026-10-23', TIME '10:30:00',
     'Cambio de aceite y filtro', 'ATENDIDA', 'CIT-00002', 2, 2, 3),

    (DATE '2026-10-24', TIME '11:00:00',
     'Revisión del sistema de frenos', 'CONFIRMADA', 'CIT-00003', 3, 1, 4),

    (DATE '2026-10-25', TIME '09:30:00',
     'Diagnóstico de ruido del motor', 'PENDIENTE', 'CIT-00004', 4, 2, 5),

    (DATE '2026-10-26', TIME '14:00:00',
     'Revisión eléctrica', 'CONFIRMADA', 'CIT-00005', 5, 3, 6),

    (DATE '2026-10-27', TIME '08:30:00',
     'Cambio de batería', 'PENDIENTE', 'CIT-00006', 6, 1, 7),

    (DATE '2026-10-28', TIME '15:00:00',
     'Mantenimiento preventivo', 'PENDIENTE', 'CIT-00007', 7, 2, 8),

    (DATE '2026-10-29', TIME '10:00:00',
     'Inspección general del vehículo', 'CANCELADA', 'CIT-00008', 8, 3, 9);

INSERT INTO orden_servicio
(fecha_hora_ingreso, fecha_hora_salida, diagnostico, monto_total, cod, estado, id_vehiculo)
VALUES
    (TIMESTAMP '2026-10-22 09:15:00', TIMESTAMP '2026-10-22 12:00:00',
     'Mantenimiento preventivo completado', 285.00, 'ORD-00004', 'FINALIZADA', 1),

    (TIMESTAMP '2026-10-23 10:45:00', NULL,
     'Desgaste de pastillas de freno', 280.00, 'ORD-00005', 'EN_PROCESO', 4),

    (TIMESTAMP '2026-10-24 08:30:00', NULL,
     'Falla en el sistema de encendido', 0.00, 'ORD-00006', 'PENDIENTE', 5),

    (TIMESTAMP '2026-10-24 11:00:00', TIMESTAMP '2026-10-24 14:30:00',
     'Cambio de batería y comprobación eléctrica', 425.00, 'ORD-00007', 'FINALIZADA', 7),

    (TIMESTAMP '2026-10-25 09:00:00', NULL,
     'Evaluación del sistema de refrigeración', 0.00, 'ORD-00008', 'PENDIENTE', 9);

INSERT INTO detalle_orden_articulo
(id_orden, id_articulo, cantidad, precio_apli)
VALUES
    (1, 1, 1, 85.00),
    (1, 2, 1, 28.00),
    (2, 1, 1, 85.00),
    (2, 2, 1, 28.00),
    (3, 4, 1, 120.00),
    (4, 1, 1, 85.00),
    (4, 2, 1, 28.00),
    (5, 4, 1, 120.00),
    (7, 8, 1, 380.00),
    (8, 6, 1, 32.00);

INSERT INTO detalle_orden_servicio
(id_orden, id_servicio, precio_apli)
VALUES
    (1, 3, 100.00),
    (2, 1, 80.00),
    (3, 4, 70.00),
    (4, 2, 180.00),
    (5, 5, 90.00),
    (6, 3, 100.00),
    (7, 7, 45.00),
    (8, 2, 180.00);

INSERT INTO detalle_orden_empleado
(id_orden, id_empleado, descripcion, horas_traba)
VALUES
    (1, 2, 'Inspección general del vehículo', 2.00),
    (2, 2, 'Cambio de aceite y filtro', 1.50),
    (3, 3, 'Inspección del sistema de frenos', 2.00),
    (4, 2, 'Mantenimiento preventivo', 3.00),
    (5, 3, 'Reemplazo de pastillas de freno', 2.50),
    (6, 3, 'Diagnóstico del sistema de encendido', 1.00),
    (7, 3, 'Cambio de batería y pruebas eléctricas', 1.50),
    (8, 2, 'Revisión del sistema de refrigeración', 1.00);

INSERT INTO comprobante_pago
(codigo, tipo_comprobante, medio_pago, estado_pago,
 fecha_emision, subtotal, igv, total, id_orden)
VALUES
    ('CP-00001', 'BOLETA', 'EFECTIVO', 'PAGADO',
     TIMESTAMP '2026-10-19 15:10:00', 186.44, 33.56, 220.00, 2),

    ('CP-00002', 'FACTURA', 'TARJETA', 'PAGADO',
     TIMESTAMP '2026-10-22 12:10:00', 241.53, 43.47, 285.00, 4),

    ('CP-00003', 'BOLETA', 'TRANSFERENCIA', 'PAGADO',
     TIMESTAMP '2026-10-24 14:40:00', 360.17, 64.83, 425.00, 7);

INSERT INTO movimiento_almacen
(tipo, fecha, cod, motivo, cantidad, id_orden, id_articulo)
VALUES
    ('SALIDA', DATE '2026-10-19', 'MOV-00001',
     'Cambio de aceite y filtro', 1, 2, 1),

    ('SALIDA', DATE '2026-10-19', 'MOV-00002',
     'Reemplazo de filtro de aceite', 1, 2, 2),

    ('SALIDA', DATE '2026-10-21', 'MOV-00003',
     'Revisión del sistema de frenos', 1, 3, 4),

    ('SALIDA', DATE '2026-10-22', 'MOV-00004',
     'Mantenimiento preventivo', 1, 4, 1),

    ('SALIDA', DATE '2026-10-22', 'MOV-00005',
     'Reemplazo de filtro', 1, 4, 2),

    ('SALIDA', DATE '2026-10-24', 'MOV-00006',
     'Cambio de batería', 1, 7, 8),

    ('ENTRADA', DATE '2026-10-24', 'MOV-00007',
     'Reposición de refrigerante', 10, NULL, 6),

    ('AJUSTE', DATE '2026-10-25', 'MOV-00008',
     'Regularización del inventario de terminales', 1, NULL, 12);