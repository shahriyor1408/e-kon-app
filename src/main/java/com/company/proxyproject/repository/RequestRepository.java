package com.company.proxyproject.repository;

import com.company.proxyproject.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 18/08/23 15:32
 * proxy-project
 */

public interface RequestRepository extends JpaRepository<Request, Long> {

}
