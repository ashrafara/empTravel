package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.DecType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * A Decree.
 */
@Entity
@Table(name = "decree")
public class Decree implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "decreenum", nullable = false)
    private Integer decreenum;

    @NotNull
    @Column(name = "decreeyear", nullable = false)
    private Integer decreeyear;

    @Column(name = "purpose")
    private String purpose;

    @Enumerated(EnumType.STRING)
    @Column(name = "dectype")
    private DecType dectype;

    @NotNull
    @Column(name = "daynum", nullable = false)
    private Integer daynum;

    @Column(name = "city")
    private String city;

    @NotNull
    @Column(name = "countrty", nullable = false)
    private String countrty;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "area")
    private String area;

    @Column(name = "cost")
    private String cost;

    @Column(name = "decreecost")
    private String decreecost;

    @Column(name = "image_url")
    private String imageUrl;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @ManyToMany
    @JoinTable(
        name = "rel_decree__employee",
        joinColumns = @JoinColumn(name = "decree_id"),
        inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    @JsonIgnoreProperties(value = { "sector", "degree", "decrees" }, allowSetters = true)
    private Set<Employee> employees = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "decreees" }, allowSetters = true)
    private DecreeIssue decreeissue;

    @ManyToOne
    @JsonIgnoreProperties(value = { "decreees" }, allowSetters = true)
    private DecreeIssue sponsor;

    @ManyToOne
    @JsonIgnoreProperties(value = { "decreees" }, allowSetters = true)
    private DecreeIssue proponent;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Decree id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDecreenum() {
        return this.decreenum;
    }

    public Decree decreenum(Integer decreenum) {
        this.setDecreenum(decreenum);
        return this;
    }

    public void setDecreenum(Integer decreenum) {
        this.decreenum = decreenum;
    }

    public Integer getDecreeyear() {
        return this.decreeyear;
    }

    public Decree decreeyear(Integer decreeyear) {
        this.setDecreeyear(decreeyear);
        return this;
    }

    public void setDecreeyear(Integer decreeyear) {
        this.decreeyear = decreeyear;
    }

    public String getPurpose() {
        return this.purpose;
    }

    public Decree purpose(String purpose) {
        this.setPurpose(purpose);
        return this;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public DecType getDectype() {
        return this.dectype;
    }

    public Decree dectype(DecType dectype) {
        this.setDectype(dectype);
        return this;
    }

    public void setDectype(DecType dectype) {
        this.dectype = dectype;
    }

    public Integer getDaynum() {
        return this.daynum;
    }

    public Decree daynum(Integer daynum) {
        this.setDaynum(daynum);
        return this;
    }

    public void setDaynum(Integer daynum) {
        this.daynum = daynum;
    }

    public String getCity() {
        return this.city;
    }

    public Decree city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountrty() {
        return this.countrty;
    }

    public Decree countrty(String countrty) {
        this.setCountrty(countrty);
        return this;
    }

    public void setCountrty(String countrty) {
        this.countrty = countrty;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public Decree startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public Decree endDate(LocalDate endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getArea() {
        return this.area;
    }

    public Decree area(String area) {
        this.setArea(area);
        return this;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCost() {
        return this.cost;
    }

    public Decree cost(String cost) {
        this.setCost(cost);
        return this;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDecreecost() {
        return this.decreecost;
    }

    public Decree decreecost(String decreecost) {
        this.setDecreecost(decreecost);
        return this;
    }

    public void setDecreecost(String decreecost) {
        this.decreecost = decreecost;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public Decree imageUrl(String imageUrl) {
        this.setImageUrl(imageUrl);
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public byte[] getImage() {
        return this.image;
    }

    public Decree image(byte[] image) {
        this.setImage(image);
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return this.imageContentType;
    }

    public Decree imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Set<Employee> getEmployees() {
        return this.employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Decree employees(Set<Employee> employees) {
        this.setEmployees(employees);
        return this;
    }

    public Decree addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.getDecrees().add(this);
        return this;
    }

    public Decree removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.getDecrees().remove(this);
        return this;
    }

    public DecreeIssue getDecreeissue() {
        return this.decreeissue;
    }

    public void setDecreeissue(DecreeIssue decreeIssue) {
        this.decreeissue = decreeIssue;
    }

    public Decree decreeissue(DecreeIssue decreeIssue) {
        this.setDecreeissue(decreeIssue);
        return this;
    }

    public DecreeIssue getSponsor() {
        return this.sponsor;
    }

    public void setSponsor(DecreeIssue decreeIssue) {
        this.sponsor = decreeIssue;
    }

    public Decree sponsor(DecreeIssue decreeIssue) {
        this.setSponsor(decreeIssue);
        return this;
    }

    public DecreeIssue getProponent() {
        return this.proponent;
    }

    public void setProponent(DecreeIssue decreeIssue) {
        this.proponent = decreeIssue;
    }

    public Decree proponent(DecreeIssue decreeIssue) {
        this.setProponent(decreeIssue);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Decree)) {
            return false;
        }
        return id != null && id.equals(((Decree) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Decree{" +
            "id=" + getId() +
            ", decreenum=" + getDecreenum() +
            ", decreeyear=" + getDecreeyear() +
            ", purpose='" + getPurpose() + "'" +
            ", dectype='" + getDectype() + "'" +
            ", daynum=" + getDaynum() +
            ", city='" + getCity() + "'" +
            ", countrty='" + getCountrty() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", area='" + getArea() + "'" +
            ", cost='" + getCost() + "'" +
            ", decreecost='" + getDecreecost() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            "}";
    }
}
