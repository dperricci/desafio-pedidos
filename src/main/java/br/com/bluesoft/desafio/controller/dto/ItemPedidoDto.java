package br.com.bluesoft.desafio.controller.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemPedidoDto {

    private ProdutoDto produto;
    private Integer quantidade;
    private BigDecimal preco;
    private BigDecimal total;

    public ItemPedidoDto() {
        // nothing
    }

    public ItemPedidoDto(ProdutoDto produto, Integer quantidade, BigDecimal preco, BigDecimal total) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.preco = preco;
        this.total = total;
    }
}
