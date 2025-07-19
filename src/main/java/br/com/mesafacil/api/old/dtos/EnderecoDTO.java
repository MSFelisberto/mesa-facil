package br.com.mesafacil.api.old.dtos;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * DTO para transferência de dados de endereço entre camadas
 */
@Schema(description = "Dados de endereço do usuário")
public record EnderecoDTO(
        @Schema(description = "Nome da rua ou avenida", example = "Avenida Paulista")
        @NotBlank(message = "O logradouro é obrigatório")
        String logradouro,

        @Schema(description = "Número do endereço", example = "1000")
        @NotBlank(message = "O número é obrigatório")
        String numero,

        @Schema(description = "CEP no formato 8 dígitos sem hífen", example = "01310100")
        @NotBlank(message = "O CEP é obrigatório")
        @Pattern(regexp = "\\d{8}", message = "O CEP deve conter 8 dígitos numéricos")
        String cep,

        @Schema(description = "Complemento do endereço", example = "Conjunto 101", required = false)
        String complemento,

        @Schema(description = "Bairro", example = "Bela Vista")
        @NotBlank(message = "O bairro é obrigatório")
        String bairro,

        @Schema(description = "Cidade", example = "São Paulo")
        @NotBlank(message = "A cidade é obrigatória")
        String cidade,

        @Schema(description = "Unidade Federativa (Estado)", example = "SP")
        @NotBlank(message = "A UF é obrigatória")
        String uf
) {
}