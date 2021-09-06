package br.com.bluesoft.desafio;

import static org.junit.Assert.assertFalse;

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
import br.com.bluesoft.desafio.entity.ItemPedido;
import br.com.bluesoft.desafio.entity.Pedido;
import br.com.bluesoft.desafio.entity.Preco;
import br.com.bluesoft.desafio.entity.Produto;
import br.com.bluesoft.desafio.repository.FornecedorRepository;
import br.com.bluesoft.desafio.repository.PedidoRepository;
import br.com.bluesoft.desafio.repository.ProdutoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PedidoRepositoryTest {

    @Autowired
    private PedidoRepository pedidoRepo;
    @Autowired
    private ProdutoRepository produtoRepo;
    @Autowired
    private FornecedorRepository fornecedorRepo;

    @Test
    @Transactional
    public void criaPedido() {
        pedidoRepo.save(createPedido());
        List<Pedido> pedidos = (List<Pedido>) pedidoRepo.findAll();
        assertFalse(pedidos.isEmpty());
    }

    private Pedido createPedido() {
        Produto p1 = produtoRepo.findByGtin("7894900011517");
        Produto p2 = produtoRepo.findByGtin("7891910000197");

        Fornecedor fornecedor = createFornecedor("56.918.868/0001-20");
        fornecedorRepo.save(fornecedor);

        Set<ItemPedido> itens = new HashSet<>();
        itens.add(new ItemPedido(p1, 2, BigDecimal.valueOf(10), BigDecimal.valueOf(20)));
        itens.add(new ItemPedido(p2, 3, BigDecimal.valueOf(10), BigDecimal.valueOf(30)));

        Pedido pedido = new Pedido();
        pedido.setFornecedor(createFornecedor("56.918.868/0001-20"));
        pedido.setItens(itens);
        return pedido;
    }

    private Fornecedor createFornecedor(String id) {
        Set<Preco> precos = new HashSet<>();
        precos.add(new Preco(3, BigDecimal.valueOf(2.99)));
        precos.add(new Preco(1, BigDecimal.valueOf(1.99)));

        return new Fornecedor(id, "Fornecedor 1", precos);
    }
}
