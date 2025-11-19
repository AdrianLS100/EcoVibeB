package com.upc.ecovibeb.security.repositories;

import com.upc.ecovibeb.security.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.huellaTotalKgCO2e IS NOT NULL ORDER BY u.huellaTotalKgCO2e ASC")
    Page<User> findUsersForRanking(Pageable pageable);

    Optional<User> findByResetToken(String resetToken);

    List<User> findAllByFamiliaId(Long familiaId);

    List<User> findAllByInstitucionId(Long institucionId);
}