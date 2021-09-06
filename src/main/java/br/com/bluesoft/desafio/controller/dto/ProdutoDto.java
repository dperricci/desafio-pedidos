package br.com.bluesoft.desafio.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.bluesoft.desafio.entity.Produto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ProdutoDto {

    private String gtin;
    private String nome;
    private Integer quantidade;

    public ProdutoDto() {
    }

    public ProdutoDto(String gtin, String nome, Integer quantidade) {
        this.gtin = gtin;
        this.nome = nome;
        this.quantidade = quantidade;
    }

    @Builder
    private ProdutoDto(Produto produto) {
        this.gtin = produto.getGtin();
        this.nome = produto.getNome();
    }

    public static List<ProdutoDto> toListOfDto(List<Produto> produtos) {
        return produtos.stream().map(ProdutoDto::new).collect(Collectors.toList());
    }
}
