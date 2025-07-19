package br.com.mesafacil.api.old.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta da tentativa de autenticação")
public record LoginResponseDTO(
        @Schema(description = "Indica se o login foi bem-sucedido", example = "true")
        boolean sucesso,

        @Schema(description = "Mensagem sobre o resultado da autenticação", example = "Login realizado com sucesso")
        String mensagem
) {
}