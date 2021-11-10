package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A DecreeIssue.
 */
@Entity
@Table(name = "decree_issue")
public class DecreeIssue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "decreeissue")
    @JsonIgnoreProperties(value = { "employees", "decreeissue", "sponsor", "proponent" }, allowSetters = true)
    private Set<Decree> decreees = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DecreeIssue id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public DecreeIssue name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Decree> getDecreees() {
        return this.decreees;
    }

    public void setDecreees(Set<Decree> decrees) {
        if (this.decreees != null) {
            this.decreees.forEach(i -> i.setDecreeissue(null));
        }
        if (decrees != null) {
            decrees.forEach(i -> i.setDecreeissue(this));
        }
        this.decreees = decrees;
    }

    public DecreeIssue decreees(Set<Decree> decrees) {
        this.setDecreees(decrees);
        return this;
    }

    public DecreeIssue addDecreee(Decree decree) {
        this.decreees.add(decree);
        decree.setDecreeissue(this);
        return this;
    }

    public DecreeIssue removeDecreee(Decree decree) {
        this.decreees.remove(decree);
        decree.setDecreeissue(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DecreeIssue)) {
            return false;
        }
        return id != null && id.equals(((DecreeIssue) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DecreeIssue{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
