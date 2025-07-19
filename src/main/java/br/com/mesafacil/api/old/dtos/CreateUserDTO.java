package br.com.mesafacil.api.old.dtos;


import br.com.mesafacil.api.old.enums.ETipoUsuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para criação de um novo usuário")
public record CreateUserDTO(
        @Schema(description = "Nome completo do usuário", example = "João da Silva")
        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @Schema(description = "Email do usuário", example = "joao@example.com")
        @NotBlank(message = "O email é obrigatório")
        @Email(message = "Formato de email inválido")
        String email,

        @Schema(description = "Login do usuário", example = "joaosilva")
        @NotBlank(message = "O login é obrigatório")
        String login,

        @Schema(description = "Senha do usuário (mínimo 6 caracteres)", example = "senha123")
        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
        String senha,

        @Schema(description = "Tipo de usuário", example = "USUARIO_COMUM")
        @NotNull(message = "O tipo de usuário é obrigatório")
        ETipoUsuario tipoUsuario,

        @Schema(description = "Dados de endereço do usuário")
        @NotNull(message = "O endereço é obrigatório")
        @Valid
        EnderecoDTO endereco
) {
}