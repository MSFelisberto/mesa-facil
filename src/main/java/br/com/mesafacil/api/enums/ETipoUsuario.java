package br.com.mesafacil.api.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tipos de usuário do sistema")
public enum ETipoUsuario {
    @Schema(description = "Proprietário do estabelecimento")
    ADMINISTRADOR_RESTAURANTE,

    @Schema(description = "Cliente comum")
    USUARIO_COMUM
}