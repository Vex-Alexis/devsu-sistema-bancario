-- Insertar clientes
INSERT INTO clientes (nombre, genero, edad, identificacion, direccion, telefono, contrasena)
VALUES
  ('Juan Pérez', 'Masculino', 35, '1234567890', 'Cra 12 #34-56', '3001234567', 'contrasena123'),
  ('Ana Gómez', 'Femenino', 28, '9876543210', 'Av 6 #45-67', '3017654321', 'secreta456'),
  ('Carlos Mendoza', 'Masculino', 42, '1122334455', 'Calle 7 #89-10', '3023344556', 'passcarlos'),
  ('Laura Torres', 'Femenino', 31, '5566778899', 'Carrera 9 #11-22', '3045566778', 'laurapass');

-- Insertar cuentas
INSERT INTO cuentas (numero_cuenta, tipo_cuenta, saldo_inicial, estado, cliente_id)
VALUES
  ('1002003001', 'Ahorros', 9500.00, TRUE, 1),
  ('2003004002', 'Corriente', 22000.00, TRUE, 2),
  ('3004005003', 'Ahorros', 3500.0, TRUE, 3),
  ('4005006004', 'Corriente', 0.0, FALSE, 4), -- Cuenta inactiva
  ('5006007005', 'Ahorros', 1500.0, TRUE, 1);     -- Segunda cuenta del cliente 1

-- Insertar movimientos
INSERT INTO movimientos (tipo_movimiento, valor, saldo, cuenta_id, fecha)
VALUES
  -- Cuenta 1 (Juan Pérez)
  ('Depósito inicial', 10000.00, 10000.00, 1, '2024-08-01'),
  ('Retiro en cajero', -2000.00, 8000.00, 1, '2024-08-03'),
  ('Depósito transferencia', 3000.00, 11000.00, 1, '2024-08-05'),
  ('Pago de servicios', -1500.00, 9500.00, 1, '2024-08-08'),

  -- Cuenta 2 (Ana Gómez)
  ('Depósito inicial', 25000.00, 25000.00, 2, '2024-08-02'),
  ('Compra supermercado', -5000.00, 20000.00, 2, '2024-08-04'),
  ('Pago suscripción', -2000.00, 18000.00, 2, '2024-08-06'),
  ('Abono cuenta', 4000.00, 22000.00, 2, '2024-08-09'),

  -- Cuenta 3 (Carlos Mendoza)
  ('Depósito apertura', 5000.00, 5000.00, 3, '2024-08-03'),
  ('Retiro en ventanilla', -1500.00, 3500.00, 3, '2024-08-06'),

  -- Cuenta 4 (Laura Torres - inactiva)
  -- No se registran movimientos por ser una cuenta inactiva

  -- Cuenta 5 (Segunda cuenta de Juan Pérez)
  ('Transferencia recibida', 2000.00, 2000.00, 5, '2024-08-07'),
  ('Retiro app', -500.00, 1500.00, 5, '2024-08-10');


