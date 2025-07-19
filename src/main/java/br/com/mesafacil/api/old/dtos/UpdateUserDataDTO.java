package br.com.mesafacil.api.old.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados para atualização de um usuário")
public record UpdateUserDataDTO(
        @Schema(description = "ID do usuário a ser atualizado", example = "1", required = true)
        @NotNull(message = "O ID do usuário é obrigatório")
        Long id,

        @Schema(description = "Nome atualizado do usuário", example = "João Silva Atualizado")
        String nome,

        @Schema(description = "Email atualizado do usuário", example = "joao.atualizado@example.com")
        @Email(message = "Formato de email inválido")
        String email,

        @Schema(description = "Dados atualizados de endereço")
        @Valid
        EnderecoDTO endereco
) {
}