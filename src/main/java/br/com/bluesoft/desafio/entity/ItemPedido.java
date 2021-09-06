package br.com.bluesoft.desafio.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Pedido pedido;
    @ManyToOne
    private Produto produto;
    private Integer quantidade;
    private BigDecimal preco;
    private BigDecimal total;

    public ItemPedido(Produto produto, Integer quantidade, BigDecimal preco, BigDecimal total) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.preco = preco;
        this.total = total;
    }
}
