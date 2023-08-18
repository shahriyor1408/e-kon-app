package com.company.proxyproject.repository.auth;

import com.company.proxyproject.constants.enums.Status;
import com.company.proxyproject.entity.auth.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 17/08/23 15:24
 * proxy-project
 */

public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query(value = "from Session s join User u on s.user.id=u.id where u.id=:id")
    Optional<Session> findSessionByUserId(@Param("id") Long id);

    @Query("from Session  s where s.user.id=:userId AND s.status=:status")
    Optional<Session> findByUserIdAndStatus(Long userId, Status status);
}
