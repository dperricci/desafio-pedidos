package br.com.bluesoft.desafio.controller.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrecoDto {

    @JsonProperty("preco")
    private BigDecimal valor;
    @JsonProperty("quantidade_minima")
    private Integer qtdeMinima;

}
