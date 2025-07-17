package com.example.demo.entity;

import java.io.Serializable;

import com.example.demo.dto.UserDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "address")
@Getter
@Setter
public class AddressEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;
    @Column(length = 30, nullable = false)
    private String addressId;
    @Column(length = 30, nullable = false)
    private String country;
    @Column(length = 30, nullable = false)
    private String city;
    @Column(length = 100, nullable = false)
    private String streetName;
    @Column(length = 30, nullable = true)
    private String postalCode;
    @Column(length = 10, nullable = false)
    private String type;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userDetails;

}
