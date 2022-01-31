package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A Zone.
 */
@Entity
@Table(name = "zone")
public class Zone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "amount_1")
    private Integer amount1;

    @Column(name = "amount_2")
    private Integer amount2;

    @Column(name = "amount_3")
    private Integer amount3;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Zone id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Zone name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount1() {
        return this.amount1;
    }

    public Zone amount1(Integer amount1) {
        this.setAmount1(amount1);
        return this;
    }

    public void setAmount1(Integer amount1) {
        this.amount1 = amount1;
    }

    public Integer getAmount2() {
        return this.amount2;
    }

    public Zone amount2(Integer amount2) {
        this.setAmount2(amount2);
        return this;
    }

    public void setAmount2(Integer amount2) {
        this.amount2 = amount2;
    }

    public Integer getAmount3() {
        return this.amount3;
    }

    public Zone amount3(Integer amount3) {
        this.setAmount3(amount3);
        return this;
    }

    public void setAmount3(Integer amount3) {
        this.amount3 = amount3;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Zone)) {
            return false;
        }
        return id != null && id.equals(((Zone) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Zone{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", amount1=" + getAmount1() +
            ", amount2=" + getAmount2() +
            ", amount3=" + getAmount3() +
            "}";
    }
}
