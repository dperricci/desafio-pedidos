package br.com.bluesoft.desafio.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String cnpj;
    private String nome;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Preco> precos = new HashSet<>();

    public Fornecedor(String cnpj, String nome, Set<Preco> precos) {
        this.cnpj = cnpj;
        this.nome = nome;
        this.precos = precos;
    }
}
