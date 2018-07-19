package com.onwelo.practice.bts.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.onwelo.practice.bts.utils.TransferStatus;
import com.onwelo.practice.bts.utils.TransferType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@ToString(exclude = "sourceAcc")
@Table(name = "transfer")
public class Transfer {
    @Id
    @Getter @Setter
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    @Column(name = "title")
    private String title;

    @Getter @Setter
    @Column(name = "value")
    private Float value;

    @Getter @Setter
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    @JsonBackReference
    private BankAccount accountId;

    @Getter @Setter
    @Column(name = "account_no")
    private String accountNo;

    @Getter @Setter
    @Column(name = "status")
    private TransferStatus status;

    @Getter @Setter
    @Column(name = "transfer_type")
    private TransferType transferType;

    @Getter @Setter
    @Column(name = "create_time")
    private java.sql.Timestamp createTime;

    @Getter @Setter
    @Column(name = "booking_date")
    private LocalDate bookingDate;

    @PrePersist
    protected void onCreate() {
        createTime = new Timestamp(System.currentTimeMillis());
    }

    public Transfer() {
    }

    public Transfer(String title, Float value, BankAccount accountId, String accountNo, TransferType transferType) {
        this.title = title;
        this.value = value;
        this.accountId = accountId;
        this.accountNo = accountNo;
        this.transferType = transferType;
    }
}
