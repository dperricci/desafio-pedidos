package br.com.bluesoft.desafio.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Preco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer qtdeMinima;
    private BigDecimal valor;
    @ManyToOne
    private Fornecedor fornecedor;

    public Preco(Integer qtdeMinima, BigDecimal precoUnitario) {
        this.qtdeMinima = qtdeMinima;
        this.valor = precoUnitario;
    }
}
