package br.com.bluesoft.desafio;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.bluesoft.desafio.entity.Fornecedor;
import br.com.bluesoft.desafio.entity.Preco;
import br.com.bluesoft.desafio.repository.FornecedorRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FornecedorRepositoryTest {

    @Autowired
    private FornecedorRepository fornecedorRepo;

    @Test
    @Transactional
    public void criaFornecedor() {
        List<Fornecedor> fornecedores = new ArrayList<>();
        fornecedores.add(createFornecedor("0001"));
        fornecedores.add(createFornecedor("0002"));

        fornecedorRepo.saveAll(fornecedores);
        List<Fornecedor> result = (List<Fornecedor>) fornecedorRepo.findAll();
        assertTrue(result.size() == 2);
    }

    private Fornecedor createFornecedor(String id) {
        Set<Preco> precos = new HashSet<>();
        precos.add(new Preco(3, BigDecimal.valueOf(2.99)));
        precos.add(new Preco(1, BigDecimal.valueOf(1.99)));

        return new Fornecedor(id, "Fornecedor 1", precos);
    }
}
