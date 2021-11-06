package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DecreeIssue;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DecreeIssue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DecreeIssueRepository extends JpaRepository<DecreeIssue, Long> {}
