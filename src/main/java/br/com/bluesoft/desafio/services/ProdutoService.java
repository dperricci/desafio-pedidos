package br.com.bluesoft.desafio.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bluesoft.desafio.entity.Produto;
import br.com.bluesoft.desafio.repository.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    public List<Produto> listaProdutos() {
        List<Produto> lista = new ArrayList<>();
        repository.findAll().forEach(lista::add);
        return lista;
    }

    public Produto recuperaNomeProdutoPorId(String gtin) {
        return repository.findByGtin(gtin);
    }

}
