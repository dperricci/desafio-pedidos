package br.com.bluesoft.desafio.controller.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FornecedorDto {

    private String cnpj;
    private List<PrecoDto> precos = new ArrayList<>();
    private String nome;

    public FornecedorDto() {
        // nothing
    }

}
