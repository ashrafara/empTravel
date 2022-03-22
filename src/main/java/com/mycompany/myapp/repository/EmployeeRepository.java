package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Decree;
import com.mycompany.myapp.domain.Employee;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Employee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findAllByNameContains(Pageable pageable, String query);

    @Query(
        nativeQuery = true,
        value = "SELECT employee.id, employee.name as 'empname', employee.jobposition, employee.departement, " +
        "sector.name as 'secname', degree.name as 'degname'\n" +
        "            FROM employee LEFT JOIN\n" +
        "            sector on employee.sector_id= sector.id LEFT JOIN\n" +
        "            degree on employee.degree_id= degree.id\n" +
        "            ORDER BY employee.name;"
    )
    List<Object[]> findCountEmployee();

    @Query("select employee from Employee employee left join fetch employee.decrees where employee.id =:id")
    Optional<Employee> findOneWithEagerRelationships(@Param("id") Long id);
}
