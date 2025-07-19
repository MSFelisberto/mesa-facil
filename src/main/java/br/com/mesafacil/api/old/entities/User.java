package br.com.mesafacil.api.old.entities;

import br.com.mesafacil.api.old.dtos.ChangeUserPasswordDTO;
import br.com.mesafacil.api.old.dtos.CreateUserDTO;
import br.com.mesafacil.api.old.dtos.EnderecoDTO;
import br.com.mesafacil.api.old.dtos.UpdateUserDataDTO;
import br.com.mesafacil.api.old.enums.ETipoUsuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "usuario")
@Table(name = "usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String login;
    private String senha;
    private LocalDate dataUltimaAtualizacao;

    @Enumerated(EnumType.STRING)
    private ETipoUsuario tipoUsuario;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    private boolean ativo;


    public User(CreateUserDTO userData) {
        this.ativo = true;
        this.nome = userData.nome();
        this.email = userData.email();
        this.login = userData.login();
        this.senha = userData.senha();
        this.dataUltimaAtualizacao = LocalDate.now();
        this.tipoUsuario = userData.tipoUsuario();
        this.endereco = new Endereco(userData.endereco());
    }


    public void atualizarInformacoes(UpdateUserDataDTO data) {
        atualizarNome(data.nome());
        atualizarEmail(data.email());
        atualizarEndereco(data.endereco());
        this.dataUltimaAtualizacao = LocalDate.now();
    }

    public void changeUserPassword(ChangeUserPasswordDTO userData) {
        if (userData.senha() != null) {
            this.senha = userData.senha();
        }
        this.dataUltimaAtualizacao = LocalDate.now();
    }

    public void deleteUser() {
        this.ativo = false;
    }


    private void atualizarNome(String nome) {
        if(nome != null) {
            this.nome = nome;
        }
    }

    private void atualizarEmail(String email) {
        if(email != null) {
            this.email = email;
        }
    }

    private void atualizarEndereco(EnderecoDTO endereco) {
        if (endereco != null && this.endereco != null) {
            this.endereco.atualizarInformacoesEndereco(endereco);
        }
    }



}
