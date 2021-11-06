package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "jobposition")
    private String jobposition;

    @Column(name = "phone")
    private String phone;

    @Column(name = "departement")
    private String departement;

    @ManyToOne
    @JsonIgnoreProperties(value = { "employees", "parents", "sector" }, allowSetters = true)
    private Sector sector;

    @ManyToOne
    @JsonIgnoreProperties(value = { "employees" }, allowSetters = true)
    private Degree degree;

    @ManyToMany(mappedBy = "employees")
    @JsonIgnoreProperties(value = { "employees", "decreeissue" }, allowSetters = true)
    private Set<Decree> decrees = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Employee id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Employee name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobposition() {
        return this.jobposition;
    }

    public Employee jobposition(String jobposition) {
        this.setJobposition(jobposition);
        return this;
    }

    public void setJobposition(String jobposition) {
        this.jobposition = jobposition;
    }

    public String getPhone() {
        return this.phone;
    }

    public Employee phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartement() {
        return this.departement;
    }

    public Employee departement(String departement) {
        this.setDepartement(departement);
        return this;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public Sector getSector() {
        return this.sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public Employee sector(Sector sector) {
        this.setSector(sector);
        return this;
    }

    public Degree getDegree() {
        return this.degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public Employee degree(Degree degree) {
        this.setDegree(degree);
        return this;
    }

    public Set<Decree> getDecrees() {
        return this.decrees;
    }

    public void setDecrees(Set<Decree> decrees) {
        if (this.decrees != null) {
            this.decrees.forEach(i -> i.removeEmployee(this));
        }
        if (decrees != null) {
            decrees.forEach(i -> i.addEmployee(this));
        }
        this.decrees = decrees;
    }

    public Employee decrees(Set<Decree> decrees) {
        this.setDecrees(decrees);
        return this;
    }

    public Employee addDecree(Decree decree) {
        this.decrees.add(decree);
        decree.getEmployees().add(this);
        return this;
    }

    public Employee removeDecree(Decree decree) {
        this.decrees.remove(decree);
        decree.getEmployees().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", jobposition='" + getJobposition() + "'" +
            ", phone='" + getPhone() + "'" +
            ", departement='" + getDepartement() + "'" +
            "}";
    }
}
