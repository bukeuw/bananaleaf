package com.ato.bananaleaf.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employees")
@EntityListeners(value = EmployeeDateListener.class)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Date dob;

    private String education;

    private String job;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Department department;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    public Employee() {}

    public Employee(Long id, String name, Date dob, String education, Department department) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.education = education;
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}

class EmployeeDateListener {
    @PrePersist
    public void setCreatedAt(Employee employee) {
        employee.setCreatedAt(new Date());
        employee.setUpdatedAt(new Date());
    }

    @PreUpdate
    public void setUpdatedAt(Employee employee) {
        employee.setUpdatedAt(new Date());
    }
}