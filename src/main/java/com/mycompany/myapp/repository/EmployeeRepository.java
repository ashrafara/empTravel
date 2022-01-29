package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Employee;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Employee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findAllByNameContains(Pageable pageable, String query);

    @Query(nativeQuery = true, value = "SELECT * FROM `employee`\n" + "ORDER by employee.name;")
    List<Object[]> findAllEmployee();
}
