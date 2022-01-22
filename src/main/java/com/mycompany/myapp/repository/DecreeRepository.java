package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Decree;
import java.util.List;
import java.util.Optional;
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
        "WHERE decree.id = rel_decree__employee.decree_id\n" +
        "and employee.id = rel_decree__employee.employee_id\n" +
        " and decreeyear=2021\n" +
        "group by employee.name\n" +
        "order by dayCount desc"
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
}
