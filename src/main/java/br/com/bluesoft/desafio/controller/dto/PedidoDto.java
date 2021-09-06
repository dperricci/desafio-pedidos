package br.com.bluesoft.desafio.controller.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class PedidoDto {

    private Long id;
    private FornecedorDto fornecedor;
    private List<ItemPedidoDto> itens = new ArrayList<>();

    public PedidoDto() {
        // nothing
    }
}
