package com.onwelo.practice.bts.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "bank")
public class Bank {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, name = "sort_code", length = 8)
    private String sortCode;

    @Column(name = "name")
    private String name;

    @Column(name = "department")
    private String department;

    @Column(name = "post_code", length = 6)
    private String postCode;

    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;

    public Bank(String name, String department, String postCode, String city, String address, String sortCode) {
        this.name = name;
        this.department = department;
        this.postCode = postCode;
        this.city = city;
        this.address = address;
        this.sortCode = sortCode;
    }
}
