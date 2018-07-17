package com.onwelo.practice.bts.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "transfer")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Float value;

    @ManyToOne(optional = false)
    @JoinColumn(name = "source_id", referencedColumnName = "id")
    @JsonBackReference
    private BankAccount source;

    @ManyToOne(optional = false)
    @JoinColumn(name = "destination_id", referencedColumnName = "id")
    @JsonBackReference
    private BankAccount destination;

    private java.sql.Timestamp createTime;

    @PrePersist
    protected void onCreate() {
        createTime = new Timestamp(System.currentTimeMillis());
    }
}
