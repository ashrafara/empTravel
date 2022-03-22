package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Decree;
import com.mycompany.myapp.domain.Employee;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Query(
        nativeQuery = true,
        value = "select decree.decreenum, decree.decreeyear, decree.countrty, decree.dectype, decree.daynum, employee.name\n" +
        "from decree, rel_decree__employee, employee\n" +
        "WHERE decree.id = rel_decree__employee.decree_id\n" +
        "and employee.id = rel_decree__employee.employee_id\n" +
        " and decreeyear=2021\n" +
        "order by employee.name"
    )
    List<Object[]> findAllreport();

    @Query(
        nativeQuery = true,
        value = "select sum(decree.daynum) as dayCount, employee.name\n" +
        "from decree, rel_decree__employee, employee\n" +
        "wHERE decree.id = rel_decree__employee.decree_id\n" +
        "and employee.id = rel_decree__employee.employee_id\n" +
        "and decreeyear=2021\n" +
        "group by employee.name\n" +
        "order by dayCount desc;"
    )
    List<Object[]> findAllDayCount();

    @Query(
        nativeQuery = true,
        value = "select Count(decree.id) as decreeCount, employee.name \n" +
        "from decree, rel_decree__employee, employee \n" +
        "WHERE decree.id = rel_decree__employee.decree_id \n" +
        "and employee.id = rel_decree__employee.employee_id \n" +
        " and decreeyear=2021\n" +
        "group by employee.name \n" +
        "order by decreeCount desc "
    )
    List<Object[]> findAllDecreecount();

    @Query(
        nativeQuery = true,
        value = "select Count(decree.id) as decreeCount, decree.countrty \n" +
        "from decree \n" +
        "WHERE decreeyear=2021\n" +
        "group by decree.countrty \n" +
        "order by decreeCount desc "
    )
    List<Object[]> findAllCountrycount();

    @Query(
        nativeQuery = true,
        value = "select sum(decree.daynum) as dayCount, decree.countrty\n" +
        "from decree, rel_decree__employee, employee\n" +
        "WHERE decree.id = rel_decree__employee.decree_id\n" +
        "and employee.id = rel_decree__employee.employee_id\n" +
        "and decreeyear=2021\n" +
        "group by decree.countrty\n" +
        "order by dayCount desc;\n"
    )
    List<Object[]> findAllCountryday();

    @Query(
        nativeQuery = true,
        value = " select sum(decree.daynum) as dayCount,Count(decree.id) as decreeCount, employee.name\n" +
        "from decree, rel_decree__employee, employee\n" +
        "wHERE decree.id = rel_decree__employee.decree_id\n" +
        "and employee.id = rel_decree__employee.employee_id\n" +
        "and decreeyear=2021\n" +
        "group by employee.name\n" +
        "order by dayCount desc;\n"
    )
    List<Object[]> findAllCountCountrydayemployee();

    @Query(
        nativeQuery = true,
        value = " select  decree.id, decree.daynum, employee.name as 'employeeename',employee.departement ,employee.jobposition," +
        "country.name as 'countryname', degree.name as 'degreename'," +
        " decree.decreenum, zone.name as 'zonename', zone.amount_1, zone.amount_2, zone.amount_3\n" +
        "        from decree, rel_decree__employee, employee, country, degree, zone\n" +
        "        wHERE decree.id = rel_decree__employee.decree_id\n" +
        "        and employee.id = rel_decree__employee.employee_id\n" +
        "        AND country.id = decree.country_id \n" +
        "        AND degree.id = employee.degree_id \n" +
        "        AND zone.id = country.zone_id \n" +
        "        and decreeyear=2021\n" +
        "        ORDER BY employee.name;"
    )
    List<Object[]> findAllEmployeeReport();

    Page<Decree> findAllByCountryNameContainsOrPurposeContains(String country_name, String purpose, Pageable pageable);

    Page<Decree> findAllByDecreenumEqualsOrDecreeyearEquals(@NotNull Integer decreenum, @NotNull Integer decreeyear, Pageable pageable);
}
