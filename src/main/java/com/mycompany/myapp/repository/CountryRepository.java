package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Country;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Country entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    List<Country> findAllByOrderByNameAsc();
}
