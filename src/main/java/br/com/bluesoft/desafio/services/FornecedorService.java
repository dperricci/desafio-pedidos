package br.com.bluesoft.desafio.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.bluesoft.desafio.controller.dto.FornecedorDto;
import br.com.bluesoft.desafio.controller.dto.PrecoDto;
import br.com.bluesoft.desafio.entity.Fornecedor;
import br.com.bluesoft.desafio.exception.FornecedorException;
import br.com.bluesoft.desafio.repository.FornecedorRepository;

@Service
public class FornecedorService {

    private static final String URI = "https://egf1amcv33.execute-api.us-east-1.amazonaws.com/dev/produto/";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FornecedorRepository fornecedorRepo;

    private ModelMapper modelMapper = new ModelMapper();

    public FornecedorDto recuperaMelhorFornecedor(String gtin, Integer qtdePedido) {

        AtomicReference<BigDecimal> valor = new AtomicReference<>(BigDecimal.ZERO);
        AtomicReference<PrecoDto> menorPreco = new AtomicReference<>(new PrecoDto());

        FornecedorDto melhorFornecedor = new FornecedorDto();
        List<FornecedorDto> fornecedores = buscaFornecedorPorProdutoId(gtin);

        fornecedores.stream().forEach(f -> {
            Stream<PrecoDto> precos = f.getPrecos().stream().filter(p -> qtdePedido >= p.getQtdeMinima());
            precos.filter(i -> valor.get().compareTo(BigDecimal.ZERO) == 0 || valor.get().compareTo(i.getValor()) > 0)
                .forEach(s -> {
                    valor.set(s.getValor());
                    menorPreco.set(s);
                });
            melhorFornecedor.setCnpj(f.getCnpj());
            melhorFornecedor.setNome(f.getNome());
        });

        melhorFornecedor.getPrecos().add(menorPreco.get());
        return melhorFornecedor;
    }

    public void salvaFornecedores(FornecedorDto fornecedoreDto) {
        fornecedorRepo.save(modelMapper.map(fornecedoreDto, Fornecedor.class));
    }

    public List<FornecedorDto> buscaFornecedorPorProdutoId(String id) {
        ObjectMapper mapper = new ObjectMapper();
        String json = restTemplate.getForObject(URI + id, String.class);
        List<FornecedorDto> fornecedores = new ArrayList<>();
        try {
            fornecedores = mapper.readValue(json, new TypeReference<List<FornecedorDto>>() {
            });
        } catch (JsonProcessingException e) {
            return Collections.emptyList();
        }
        return fornecedores;
    }

}
