Feature: Pruebas de integración para Cliente

  Scenario: Crear un nuevo cliente
    Given url 'http://localhost:8080/api/clientes'
    And request
    """
    {
      "nombre": "Juan Pérez",
      "genero": "Masculino",
      "edad": 35,
      "identificacion": "1234567890",
      "direccion": "Calle Falsa 123",
      "telefono": "3001234567",
      "contraseña": "password123",
      "estado": true
    }
    """
    When method POST
    Then status 201
    And match response.nombre == "Juan Pérez"