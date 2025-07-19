package br.com.mesafacil.api.old.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados para autenticação do usuário")
public record LoginRequestDTO(
        @Schema(description = "Login do usuário", example = "joaosilva")
        @NotBlank(message = "O login é obrigatório")
        String login,

        @Schema(description = "Senha do usuário", example = "senha123")
        @NotBlank(message = "A senha é obrigatória")
        String senha
) {
}