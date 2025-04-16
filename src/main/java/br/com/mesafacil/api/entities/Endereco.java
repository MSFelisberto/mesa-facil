package br.com.mesafacil.api.entities;

import br.com.mesafacil.api.dtos.EnderecoDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "endereco")
@Table(name = "enderecos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String logradouro;
    private String numero;
    private String cep;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;

    public Endereco(EnderecoDTO endereco) {
        this.logradouro = endereco.logradouro();
        this.numero = endereco.numero();
        this.cep = endereco.cep();
        this.complemento = endereco.complemento();
        this.bairro = endereco.bairro();
        this.cidade = endereco.cidade();
        this.uf = endereco.uf();

    }


    public void atualizarInformacoesEndereco(EnderecoDTO endereco) {
        atualizarLogradouro(endereco.logradouro());
        atualizarNumero(endereco.numero());
        atualizarCep(endereco.cep());
        atualizarComplemento(endereco.complemento());
        atualizarBairro(endereco.bairro());
        atualizarCidade(endereco.cidade());
        atualizarUf(endereco.uf());
    }

    private void atualizarLogradouro(String logradouro) {
        if (logradouro != null) {
            this.logradouro = logradouro;
        }
    }

    private void atualizarNumero(String numero) {
        if (numero != null) {
            this.numero = numero;
        }
    }

    private void atualizarCep(String cep) {
        if (cep != null) {
            this.cep = cep;
        }
    }

    private void atualizarComplemento(String complemento) {
        if (complemento != null) {
            this.complemento = complemento;
        }
    }

    private void atualizarBairro(String bairro) {
        if (bairro != null) {
            this.bairro = bairro;
        }
    }

    private void atualizarCidade(String cidade) {
        if (cidade != null) {
            this.cidade = cidade;
        }
    }

    private void atualizarUf(String uf) {
        if (uf != null) {
            this.uf = uf;
        }
    }

}
