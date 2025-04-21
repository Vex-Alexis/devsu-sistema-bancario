package com.devsu.cuentas.infrastructure.entryPoints.rest.controller;

import com.devsu.cuentas.application.useCase.cuenta.CuentaUseCase;
import com.devsu.cuentas.application.useCase.reporte.ReporteUseCase;
import com.devsu.cuentas.domain.model.cuenta.Cuenta;
import com.devsu.cuentas.domain.model.reporte.EstadoDeCuenta;
import com.devsu.cuentas.infrastructure.entryPoints.rest.dto.request.CuentaRequest;
import com.devsu.cuentas.infrastructure.entryPoints.rest.dto.request.CuentaUpdateRequest;
import com.devsu.cuentas.infrastructure.entryPoints.rest.mapper.CuentaMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    private final CuentaUseCase cuentaUseCase;
    private final ReporteUseCase reporteUseCase;

    public CuentaController(CuentaUseCase cuentaUseCase, ReporteUseCase reporteUseCase) {
        this.cuentaUseCase = cuentaUseCase;
        this.reporteUseCase = reporteUseCase;
    }

    @PostMapping
    public ResponseEntity<Cuenta> crearCuenta(@RequestBody CuentaRequest cuentaRequest) {
        Cuenta cuenta = CuentaMapper.toDomain(cuentaRequest);
        Cuenta cuentaCreada = cuentaUseCase.crearCuenta(cuenta);
        return ResponseEntity.status(HttpStatus.CREATED).body(cuentaCreada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> consultarCuentaPorId(@PathVariable Long id) {
        Optional<Cuenta> cuenta = cuentaUseCase.consultarCuentaPorId(id);
        return cuenta.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/numero-cuenta/{numeroCuenta}")
    public ResponseEntity<?> obtenerPorNumeroCuenta(@PathVariable String numeroCuenta) {
        Cuenta cuenta = cuentaUseCase.consultarCuentaPorNumeroCuenta(numeroCuenta);
        return ResponseEntity.status(HttpStatus.OK).body(cuenta);
    }

    @GetMapping
    public ResponseEntity<List<Cuenta>> listarCuentas() {
        return ResponseEntity.ok(cuentaUseCase.listarCuentas());
    }

    @PutMapping("/{numeroCuenta}")
    public ResponseEntity<Cuenta> actualizarCuenta(
            @PathVariable String numeroCuenta,
            @RequestBody CuentaUpdateRequest cuentaUpdateRequest) {
        Cuenta cuenta = CuentaMapper.toDomain(cuentaUpdateRequest);
        Cuenta cuentaActualizada = cuentaUseCase.actualizarCuenta(numeroCuenta, cuenta);
        return ResponseEntity.status(HttpStatus.CREATED).body(cuentaActualizada);
    }

    @GetMapping("/reportes")
    public ResponseEntity<EstadoDeCuenta> generarReporte(
            @RequestParam String identificacionCliente,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta
    ) {
        EstadoDeCuenta reporte = reporteUseCase.generarEstadoDeCuenta(identificacionCliente, desde, hasta);
        return ResponseEntity.ok(reporte);
    }
}
