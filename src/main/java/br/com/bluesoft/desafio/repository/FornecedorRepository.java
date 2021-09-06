package br.com.bluesoft.desafio.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.bluesoft.desafio.entity.Fornecedor;

public interface FornecedorRepository extends CrudRepository<Fornecedor, String> {

    Fornecedor findByCnpj(String cnpj);
}
