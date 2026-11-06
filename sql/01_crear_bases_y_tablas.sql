-- Evaluacion 03 - Mantenimiento de equipos
-- Una base de datos por microservicio (sin FK entre bases).
-- En dev Hibernate crea las tablas solo (ddl-auto: update); este script es la evidencia
-- y el que se usa en prod (ddl-auto: none).

CREATE DATABASE IF NOT EXISTS bd_cliente CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS bd_equipo CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS bd_orden_servicio CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- ---------- cliente-service ----------
USE bd_cliente;
CREATE TABLE IF NOT EXISTS cliente (
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    documento  VARCHAR(20)  NOT NULL,
    nombres    VARCHAR(80)  NOT NULL,
    apellidos  VARCHAR(80)  NOT NULL,
    correo     VARCHAR(100) NOT NULL,
    telefono   VARCHAR(20)  NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_cliente_documento UNIQUE (documento)
) ENGINE=InnoDB;

-- ---------- equipo-service ----------
USE bd_equipo;
CREATE TABLE IF NOT EXISTS equipo (
    id          BIGINT      NOT NULL AUTO_INCREMENT,
    codigo      VARCHAR(30) NOT NULL,
    tipo        VARCHAR(50) NOT NULL,
    marca       VARCHAR(50) NOT NULL,
    modelo      VARCHAR(50) NOT NULL,
    id_cliente  BIGINT      NOT NULL,   -- referencia por ID a bd_cliente.cliente (sin FK)
    PRIMARY KEY (id),
    CONSTRAINT uk_equipo_codigo UNIQUE (codigo)
) ENGINE=InnoDB;

-- ---------- orden-servicio-service ----------
USE bd_orden_servicio;
CREATE TABLE IF NOT EXISTS orden_servicio (
    id              BIGINT         NOT NULL AUTO_INCREMENT,
    id_equipo       BIGINT         NOT NULL,   -- referencia por ID a bd_equipo.equipo (sin FK)
    fecha_ingreso   DATE           NOT NULL,
    descripcion     VARCHAR(500)   NOT NULL,
    costo_estimado  DECIMAL(10,2)  NOT NULL,
    estado          VARCHAR(20)    NOT NULL,   -- RECIBIDO | EN_REPARACION | LISTO | ENTREGADO
    PRIMARY KEY (id)
) ENGINE=InnoDB;
