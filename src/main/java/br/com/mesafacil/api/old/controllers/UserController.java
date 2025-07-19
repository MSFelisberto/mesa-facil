package br.com.mesafacil.api.old.controllers;

import br.com.mesafacil.api.old.dtos.*;
import br.com.mesafacil.api.old.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Usuários", description = "API de gerenciamento de usuários")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista paginada de usuários ativos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuários encontrados com sucesso"),
            @ApiResponse(responseCode = "403", description = "Sem permissão para acessar este recurso", content = @Content)
    })
    @GetMapping
    public ResponseEntity<Page<ListUserDTO>> findAll(
            @Parameter(description = "Parâmetros de paginação")
            @PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var users = this.userService.findAllUsers(paginacao);
        return ResponseEntity.ok(users);
    }


    @Operation(summary = "Buscar usuário por ID", description = "Retorna um único usuário pelo seu ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Sem permissão para acessar este recurso", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ListUserDTO> findUser(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable("id") Long id) {
        return this.userService.findUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @Operation(summary = "Criar novo usuário", description = "Cria um novo usuário no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content),
            @ApiResponse(responseCode = "403", description = "Sem permissão para acessar este recurso", content = @Content)
    })
    @PostMapping
    @Transactional
    public ResponseEntity<ListUserDTO> saveUser(
            @Parameter(description = "Dados para criação do usuário", required = true,
                    schema = @Schema(implementation = CreateUserDTO.class))
            @RequestBody @Valid CreateUserDTO createUserDTO,
            UriComponentsBuilder uriBuilder) {
        var savedUser = this.userService.saveUser(createUserDTO);
        URI location = uriBuilder.path("/api/v1/users/{id}")
                .buildAndExpand(savedUser.id()).toUri();
        return ResponseEntity.created(location).body(savedUser);
    }


    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Sem permissão para acessar este recurso", content = @Content)
    })
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ListUserDTO> updateUser(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable Long id,
            @Parameter(description = "Dados para atualização do usuário", required = true,
                    schema = @Schema(implementation = UpdateUserDataDTO.class))
            @RequestBody @Valid UpdateUserDataDTO userData) {
        if (!id.equals(userData.id())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        var updatedUser = this.userService.updateUserDetails(userData);
        return ResponseEntity.ok(updatedUser);
    }


    @Operation(summary = "Alterar senha do usuário", description = "Altera a senha de um usuário existente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Sem permissão para acessar este recurso", content = @Content)
    })
    @PatchMapping("/{id}/password")
    @Transactional
    public ResponseEntity<Void> changeUserPassword(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable Long id,
            @Parameter(description = "Nova senha do usuário", required = true,
                    schema = @Schema(implementation = ChangeUserPasswordDTO.class))
            @RequestBody @Valid ChangeUserPasswordDTO userData) {

        this.userService.changeUserPassword(id, userData);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Excluir usuário", description = "Marca um usuário como inativo (exclusão lógica)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Sem permissão para acessar este recurso", content = @Content)
    })
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable("id") Long id) {
        boolean deleted = this.userService.deleteUser(id);
        return deleted ? ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }

    @Operation(summary = "Autenticar usuário", description = "Valida as credenciais de um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autenticação processada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Parameter(description = "Credenciais do usuário", required = true,
                    schema = @Schema(implementation = LoginRequestDTO.class))
            @RequestBody @Valid LoginRequestDTO loginRequestDTO) {

        LoginResponseDTO response = userService.authenticate(loginRequestDTO);
        return ResponseEntity.ok(response);
    }

}