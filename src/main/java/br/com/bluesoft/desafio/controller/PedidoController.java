package br.com.bluesoft.desafio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bluesoft.desafio.controller.dto.PedidoDto;
import br.com.bluesoft.desafio.services.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @GetMapping
    public List<PedidoDto> findAll() {
        return service.listaPedidos();
    }

    @PostMapping("/novo/")
    public ResponseEntity<List<PedidoDto>> create(@RequestBody String json) {
        List<PedidoDto> pedidos = service.criaPedido(json);
        return ResponseEntity.ok(pedidos);
    }

}
