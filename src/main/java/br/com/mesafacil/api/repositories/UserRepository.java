package br.com.mesafacil.api.repositories;

import br.com.mesafacil.api.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAllByAtivoTrue(Pageable pageable);

    Optional<User> findByIdAndAtivoTrue(Long id);

    Optional<User> findByLoginAndAtivoTrue(String login);
}
