package com.devsu.cuentas.infrastructure.entryPoints.rest.controller;

import com.devsu.cuentas.domain.model.movimiento.Movimiento;
import com.devsu.cuentas.domain.useCase.movimiento.MovimientoCRUDUseCase;
import com.devsu.cuentas.infrastructure.entryPoints.rest.dto.request.MovimientoRequest;
import com.devsu.cuentas.infrastructure.entryPoints.rest.dto.request.MovimientoUpdateRequest;
import com.devsu.cuentas.infrastructure.entryPoints.rest.mapper.MovimientoMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    private final MovimientoCRUDUseCase movimientoUseCase;

    public MovimientoController(MovimientoCRUDUseCase movimientoUseCase) {
        this.movimientoUseCase = movimientoUseCase;
    }

    @PostMapping
    public ResponseEntity<Movimiento> crearMovimiento(@RequestBody @Valid MovimientoRequest movimientoRequest) {
        Movimiento movimiento = MovimientoMapper.toDomain(movimientoRequest);
        Movimiento movimientoCreado = movimientoUseCase.crearMovimiento(movimiento);
        return ResponseEntity.created(URI.create("/movimientos/" + movimientoCreado.getMovimientoId())).body(movimientoCreado);
    }

    @GetMapping
    public ResponseEntity<List<Movimiento>> obtenerTodosLosMovimientos() {
        return ResponseEntity.ok(movimientoUseCase.obtenerTodosLosMovimientos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movimiento> obtenerMovimientoPorId(@PathVariable Long id) {
        return movimientoUseCase.obtenerMovimientoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cuenta/{cuentaId}")
    public ResponseEntity<List<Movimiento>> obtenerMovimientosPorCuenta(@PathVariable Long cuentaId) {
        List<Movimiento> movimientos = movimientoUseCase.obtenerMovimientosPorCuentaId(cuentaId);
        return ResponseEntity.ok(movimientos);
    }

    @PostMapping("/{id}/revertir")
    public ResponseEntity<Movimiento> revertirMovimiento(@PathVariable Long id) {
        Movimiento movimientoRevertido = movimientoUseCase.revertirMovimiento(id);
        return ResponseEntity.ok(movimientoRevertido);
    }
}
