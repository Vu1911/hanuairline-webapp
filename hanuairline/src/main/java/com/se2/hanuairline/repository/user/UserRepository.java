package com.se2.hanuairline.repository.user;

import com.se2.hanuairline.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    List<User> findByIdIn(List<Long> userIds);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Page<User> findAll (Pageable pageable);

    Page<User> findById (Long id, Pageable pageable);

    Page<User> findByEmailContainingAndUsernameContainingAndNameContaining(String email, String username, String name, Pageable pageable);
}
