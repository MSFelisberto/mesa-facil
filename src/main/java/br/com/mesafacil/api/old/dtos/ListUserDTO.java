package br.com.mesafacil.api.old.dtos;

import br.com.mesafacil.api.old.entities.User;
import br.com.mesafacil.api.old.enums.ETipoUsuario;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de um usuário para listagem")
public record ListUserDTO(
        @Schema(description = "ID do usuário", example = "1")
        Long id,

        @Schema(description = "Nome do usuário", example = "João da Silva")
        String nome,

        @Schema(description = "Email do usuário", example = "joao@example.com")
        String email,

        @Schema(description = "Login do usuário", example = "joaosilva")
        String login,

        @Schema(description = "Tipo de usuário", example = "USUARIO_COMUM")
        ETipoUsuario tipoUsuario) {

    public ListUserDTO(User user){
        this(user.getId(), user.getNome(), user.getEmail(), user.getLogin(), user.getTipoUsuario());
    }
}