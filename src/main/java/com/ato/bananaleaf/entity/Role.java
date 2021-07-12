package com.ato.bananaleaf.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "roles")
@EntityListeners(value = RoleDateListener.class)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

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

    public Role() {}

    public Role(String name) {
        this.name = name;
    }
}

class RoleDateListener {
    @PrePersist
    public void setCreatedAt(Role role) {
        role.setCreatedAt(new Date());
        role.setUpdatedAt(new Date());
    }

    @PostUpdate
    public void setUpdatedAt(Role role) {
        role.setUpdatedAt(new Date());
    }
}
