package com.example.demo.entity;

import java.io.Serializable;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "authorities")
@Getter
@Setter
public class AuthorityEntity implements Serializable {

    
    public AuthorityEntity() {
        super();
    }

    public AuthorityEntity(String name) {
        this.name = name;
    }
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(nullable = false, length = 20)
    private String name;
    
    @ManyToMany(mappedBy = "authorities")
    private Collection<RoleEntity>roles;

}
