-- Tabla de clientes
CREATE TABLE clientes (
    cliente_id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    genero VARCHAR(10),
    edad INTEGER,
    identificacion VARCHAR(50) UNIQUE NOT NULL,
    direccion VARCHAR(200),
    telefono VARCHAR(50),
    contrasena VARCHAR(255) NOT NULL,
    estado BOOLEAN DEFAULT true
);

-- Tabla de cuentas
CREATE TABLE cuentas (
    cuenta_id SERIAL PRIMARY KEY,
    numero_cuenta VARCHAR(30) UNIQUE NOT NULL,
    tipo_cuenta VARCHAR(50) NOT NULL,
    saldo_inicial NUMERIC(15, 2) NOT NULL,
    estado BOOLEAN DEFAULT true,
    cliente_id INTEGER NOT NULL,
    CONSTRAINT fk_cliente
        FOREIGN KEY (cliente_id)
        REFERENCES clientes (cliente_id)
        ON DELETE RESTRICT
);

-- Tabla de movimientos
CREATE TABLE movimientos (
    movimiento_id SERIAL PRIMARY KEY,
    fecha DATE NOT NULL DEFAULT CURRENT_DATE,
    tipo_movimiento VARCHAR(50) NOT NULL,
    valor NUMERIC(15, 2) NOT NULL,
    saldo NUMERIC(15, 2) NOT NULL,
    cuenta_id INTEGER NOT NULL,
    CONSTRAINT fk_cuenta
        FOREIGN KEY (cuenta_id)
        REFERENCES cuentas (cuenta_id)
        ON DELETE RESTRICT
);