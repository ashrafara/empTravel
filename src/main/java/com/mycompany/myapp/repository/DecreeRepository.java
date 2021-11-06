package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Decree;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Decree entity.
 */
@Repository
public interface DecreeRepository extends JpaRepository<Decree, Long> {
    @Query(
        value = "select distinct decree from Decree decree left join fetch decree.employees",
        countQuery = "select count(distinct decree) from Decree decree"
    )
    Page<Decree> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct decree from Decree decree left join fetch decree.employees")
    List<Decree> findAllWithEagerRelationships();

    @Query("select decree from Decree decree left join fetch decree.employees where decree.id =:id")
    Optional<Decree> findOneWithEagerRelationships(@Param("id") Long id);
}
