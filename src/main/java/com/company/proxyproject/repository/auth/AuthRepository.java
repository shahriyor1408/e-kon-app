package com.company.proxyproject.repository.auth;

import com.company.proxyproject.entity.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 17/08/23 13:56
 * proxy-project
 */

public interface AuthRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true,
            value = "select u.* from users u where u.username =:usernameOrEmail or u.email =:usernameOrEmail limit 1")
    Optional<User> findByUsernameOrEmail(@Param("usernameOrEmail") String usernameOrEmail);

    @Query(nativeQuery = true,
            value = "select case when count(u) > 0 then true else false end from users u where u.username=:username or u.email=:email")
    Boolean existsByUsernameOrEmail(String username, String email);
}
