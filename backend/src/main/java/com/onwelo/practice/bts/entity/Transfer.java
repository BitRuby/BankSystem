package com.onwelo.practice.bts.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.onwelo.practice.bts.utils.TransferStatus;
import com.onwelo.practice.bts.utils.TransferType;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString(exclude = "sourceAcc")
@Table(name = "transfer")
public class Transfer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "value")
    private BigDecimal value;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    @JsonBackReference
    private BankAccount accountId;

    @Column(name = "account_no")
    private String accountNo;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TransferStatus status;

    @Column(name = "transfer_type")
    @Enumerated(EnumType.STRING)
    private TransferType transferType;

    @Column(name = "create_time")
    private java.sql.Timestamp createTime;

    @Column(name = "booking_date")
    private LocalDate bookingDate;

    @PrePersist
    protected void onCreate() {
        createTime = new Timestamp(System.currentTimeMillis());
    }

    public Transfer(String title, BigDecimal value, BankAccount accountId, String accountNo, TransferType transferType) {
        this.title = title;
        this.value = value;
        this.accountId = accountId;
        this.accountNo = accountNo;
        this.transferType = transferType;
    }
}
