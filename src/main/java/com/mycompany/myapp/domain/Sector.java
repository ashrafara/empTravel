package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Sector.
 */
@Entity
@Table(name = "sector")
public class Sector implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "sector")
    @JsonIgnoreProperties(value = { "sector", "degree", "decrees" }, allowSetters = true)
    private Set<Employee> employees = new HashSet<>();

    @OneToMany(mappedBy = "sector")
    @JsonIgnoreProperties(value = { "employees", "parents", "sector" }, allowSetters = true)
    private Set<Sector> parents = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "employees", "parents", "sector" }, allowSetters = true)
    private Sector sector;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sector id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Sector name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return this.phone;
    }

    public Sector phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return this.address;
    }

    public Sector address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Employee> getEmployees() {
        return this.employees;
    }

    public void setEmployees(Set<Employee> employees) {
        if (this.employees != null) {
            this.employees.forEach(i -> i.setSector(null));
        }
        if (employees != null) {
            employees.forEach(i -> i.setSector(this));
        }
        this.employees = employees;
    }

    public Sector employees(Set<Employee> employees) {
        this.setEmployees(employees);
        return this;
    }

    public Sector addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.setSector(this);
        return this;
    }

    public Sector removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.setSector(null);
        return this;
    }

    public Set<Sector> getParents() {
        return this.parents;
    }

    public void setParents(Set<Sector> sectors) {
        if (this.parents != null) {
            this.parents.forEach(i -> i.setSector(null));
        }
        if (sectors != null) {
            sectors.forEach(i -> i.setSector(this));
        }
        this.parents = sectors;
    }

    public Sector parents(Set<Sector> sectors) {
        this.setParents(sectors);
        return this;
    }

    public Sector addParent(Sector sector) {
        this.parents.add(sector);
        sector.setSector(this);
        return this;
    }

    public Sector removeParent(Sector sector) {
        this.parents.remove(sector);
        sector.setSector(null);
        return this;
    }

    public Sector getSector() {
        return this.sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public Sector sector(Sector sector) {
        this.setSector(sector);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sector)) {
            return false;
        }
        return id != null && id.equals(((Sector) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sector{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
