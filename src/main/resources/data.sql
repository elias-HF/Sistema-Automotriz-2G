INSERT INTO rol (nombre, descripcion) SELECT 'ADMINISTRADOR', 'Acceso a la administración del sistema' WHERE NOT EXISTS (
    SELECT 1 FROM rol WHERE nombre = 'ADMINISTRADOR'
);

INSERT INTO rol (nombre, descripcion) SELECT 'MECANICO', 'Acceso a las funciones del mecánico' WHERE NOT EXISTS (
    SELECT 1 FROM rol WHERE nombre = 'MECANICO'
);

INSERT INTO rol (nombre, descripcion) SELECT 'RECEPCIONISTA', 'Acceso a las funciones de recepción' WHERE NOT EXISTS (
    SELECT 1 FROM rol WHERE nombre = 'RECEPCION'
);

INSERT INTO rol (nombre, descripcion) SELECT 'ALMACEN', 'Acceso a la gestión de almacén' WHERE NOT EXISTS (
    SELECT 1 FROM rol WHERE nombre = 'ALMACEN'
);