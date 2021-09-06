package br.com.bluesoft.desafio.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.bluesoft.desafio.controller.dto.FornecedorDto;
import br.com.bluesoft.desafio.controller.dto.ItemPedidoDto;
import br.com.bluesoft.desafio.controller.dto.PedidoDto;
import br.com.bluesoft.desafio.controller.dto.ProdutoDto;
import br.com.bluesoft.desafio.entity.Pedido;
import br.com.bluesoft.desafio.exception.FornecedorException;
import br.com.bluesoft.desafio.repository.PedidoRepository;

@Service
public class PedidoService {

    private ObjectMapper mapper = new ObjectMapper();
    private ModelMapper modelMapper = new ModelMapper();

    private List<PedidoDto> pedidos = new ArrayList<>();
    private Map<ProdutoDto, FornecedorDto> forncedorMap = new HashMap<>();

    @Autowired
    private FornecedorService fornecedorService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private PedidoRepository pedidoRepo;

    public List<PedidoDto> listaPedidos() {
        return ((List<Pedido>) pedidoRepo.findAll())//
            .stream()//
            .map(pedido -> modelMapper.map(pedido, PedidoDto.class))//
            .collect(Collectors.toList());
    }

    public List<PedidoDto> criaPedido(String jsonString) {
        pedidos.clear();
        forncedorMap.clear();

        // Recuperar a lista de Produtos
        List<ProdutoDto> produtos = recuperaListaProdutos(jsonString);

        // Para cada produto fazer a requisição para recuperar os melhores fornecedores
        produtos.stream().filter(p -> p.getQuantidade() > 0).forEach(p -> {
            FornecedorDto fornecedor = fornecedorService.recuperaMelhorFornecedor(p.getGtin(), p.getQuantidade());
            fornecedor.getPrecos().stream().filter(preco -> preco.getValor() != null).findAny()
                .orElseThrow(() -> new FornecedorException(
                    "Nenhum fornecedor encontrado para a quantidade solicitada do produto " + p.getNome()));
            forncedorMap.put(p, fornecedor);
        });

        Map<String, List<FornecedorDto>> groupByGrades = forncedorMap.values().stream()
            .collect(Collectors.groupingBy(FornecedorDto::getNome));

        groupByGrades.values().stream().forEach(listaFornecedores -> {
            PedidoDto pedido = new PedidoDto();
            pedido.setFornecedor(listaFornecedores.get(0));
            List<ItemPedidoDto> itens = new ArrayList<>();
            listaFornecedores.stream().forEach(fornecedor -> {
                ProdutoDto produto = forncedorMap.entrySet().stream().filter(g -> g.getValue().equals(fornecedor))
                    .findFirst().map(Map.Entry::getKey).orElse(null);
                itens.add(new ItemPedidoDto(produto, produto.getQuantidade(), getValorUnitario(fornecedor),
                    getValorTotal(produto.getQuantidade(), getValorUnitario(fornecedor))));
            });
            pedido.setItens(itens);
            pedidos.add(pedido);
        });

        salvaPedidos(pedidos);

        return ((List<Pedido>) pedidoRepo.findAll())//
            .stream()//
            .map(pedido -> modelMapper.map(pedido, PedidoDto.class))//
            .collect(Collectors.toList());
    }

    public void salvaPedidos(List<PedidoDto> pedidos) {
        List<Pedido> entities = pedidos//
            .stream()//
            .map(pedido -> modelMapper.map(pedido, Pedido.class))//
            .collect(Collectors.toList());

        pedidoRepo.saveAll(entities);
    }

    private BigDecimal getValorTotal(Integer qtdeProdutos, BigDecimal valorUnitario) {
        return valorUnitario.multiply(BigDecimal.valueOf(qtdeProdutos));
    }

    private BigDecimal getValorUnitario(FornecedorDto fornecedor) {
        return fornecedor.getPrecos().get(0).getValor();
    }

    private List<ProdutoDto> recuperaListaProdutos(String jsonString) {
        List<ProdutoDto> produtos = convertJsonToObject(jsonString);
        produtos.stream().forEach(p -> p.setNome(produtoService.recuperaNomeProdutoPorId(p.getGtin()).getNome()));
        return produtos;
    }

    public List<ProdutoDto> convertJsonToObject(String jsonString) {
        try {
            return mapper.readValue(jsonString, new TypeReference<List<ProdutoDto>>() {
            });
        } catch (JsonProcessingException e) {
            return Collections.emptyList();
        }
    }

}
