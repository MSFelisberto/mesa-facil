package br.com.mesafacil.api.services;

import br.com.mesafacil.api.dtos.*;
import br.com.mesafacil.api.entities.User;
import br.com.mesafacil.api.repositories.UserRepository;
import br.com.mesafacil.api.services.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Serviço responsável por operações relacionadas a usuários
 */
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Busca todos os usuários ativos
     * @param paginacao Parâmetros de paginação
     * @return Página de usuários
     */
    public Page<ListUserDTO> findAllUsers(Pageable paginacao) {
        logger.info("Buscando todos os usuários ativos com paginação: {}", paginacao);
        return userRepository.findAllByAtivoTrue(paginacao).map(ListUserDTO::new);
    }

    /**
     * Busca um usuário pelo ID
     * @param id ID do usuário
     * @return Usuário encontrado ou vazio
     */
    public Optional<ListUserDTO> findUserById(Long id) {
        logger.info("Buscando usuário pelo ID: {}", id);
        return userRepository.findByIdAndAtivoTrue(id).map(ListUserDTO::new);
    }

    /**
     * Salva um novo usuário
     * @param createUserDTO Dados do usuário
     * @return Usuário criado
     */
    public ListUserDTO saveUser(CreateUserDTO createUserDTO) {
        logger.info("Criando novo usuário com login: {}", createUserDTO.login());
        User user = new User(createUserDTO);
        user = userRepository.save(user);
        logger.info("Usuário criado com ID: {}", user.getId());
        return new ListUserDTO(user);
    }

    /**
     * Atualiza os dados de um usuário
     * @param updateUserDTO Dados atualizados
     * @return Usuário atualizado
     * @throws ResourceNotFoundException se o usuário não for encontrado
     */
    public ListUserDTO updateUserDetails(UpdateUserDataDTO updateUserDTO) {
        logger.info("Atualizando dados do usuário com ID: {}", updateUserDTO.id());
        User user = getUserById(updateUserDTO.id());
        user.atualizarInformacoes(updateUserDTO);
        userRepository.save(user);
        logger.info("Dados do usuário atualizados com sucesso");
        return new ListUserDTO(user);
    }

    /**
     * Marca um usuário como inativo (exclusão lógica)
     * @param id ID do usuário
     * @return true se o usuário foi excluído, false caso contrário
     */
    public boolean deleteUser(Long id) {
        logger.info("Tentando excluir usuário com ID: {}", id);
        return userRepository.findByIdAndAtivoTrue(id)
                .map(user -> {
                    user.deleteUser();
                    userRepository.save(user);
                    logger.info("Usuário com ID: {} excluído com sucesso", id);
                    return true;
                })
                .orElse(false);
    }

    /**
     * Altera a senha de um usuário
     * @param id ID do usuário
     * @param userData Dados com nova senha
     * @throws ResourceNotFoundException se o usuário não for encontrado
     */
    public void changeUserPassword(Long id, ChangeUserPasswordDTO userData) {
        logger.info("Alterando senha do usuário com ID: {}", id);
        User user = getUserById(id);
        user.changeUserPassword(userData);
        userRepository.save(user);
        logger.info("Senha do usuário alterada com sucesso");
    }


    /**
     * Autentica um usuário verificando login e senha
     * @param loginRequestDTO Dados de login
     * @return Resultado da autenticação
     */
    public LoginResponseDTO authenticate(LoginRequestDTO loginRequestDTO) {
        logger.info("Tentativa de login para o usuário: {}", loginRequestDTO.login());

        Optional<User> userOptional = userRepository.findByLoginAndAtivoTrue(loginRequestDTO.login());

        if (userOptional.isEmpty()) {
            logger.warn("Tentativa de login falhou: login não encontrado - {}", loginRequestDTO.login());
            return new LoginResponseDTO(false, "Login ou Senha incorretos");
        }

        User user = userOptional.get();

        if (!user.getSenha().equals(loginRequestDTO.senha())) {
            logger.warn("Tentativa de login falhou: senha incorreta para usuário - {}", loginRequestDTO.login());
            return new LoginResponseDTO(false, "Login ou Senha incorretos");
        }

        logger.info("Login realizado com sucesso para o usuário: {}", loginRequestDTO.login());
        return new LoginResponseDTO(true, "Login realizado com sucesso");
    }


    /**
     * Método auxiliar para buscar um usuário pelo ID
     * @param id ID do usuário
     * @return Usuário encontrado
     * @throws ResourceNotFoundException se o usuário não for encontrado
     */
    private User getUserById(Long id) {
        return userRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> {
                    logger.error("Usuário não encontrado com ID: {}", id);
                    return new ResourceNotFoundException("Usuário não encontrado com ID: " + id);
                });
    }
}