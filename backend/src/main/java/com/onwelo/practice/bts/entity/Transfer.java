package com.onwelo.practice.bts.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.onwelo.practice.bts.utils.Currency;
import com.onwelo.practice.bts.utils.MoneySerializer;
import com.onwelo.practice.bts.utils.TransferStatus;
import com.onwelo.practice.bts.utils.TransferType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Where;
import org.jvnet.hk2.annotations.Optional;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString(exclude = "accountId")
@Table(name = "transfer")
@Where(clause = "is_active=1")
public class Transfer {
    @Id
    @Optional
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "value")
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal value;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private BankAccount accountId;

    @Column(name = "account_no", length = 26)
    private String accountNo;

    @Column(name = "status", length = 8)
    @Enumerated(EnumType.STRING)
    private TransferStatus status = TransferStatus.PENDING;

    @Column(name = "transfer_type", length = 8)
    @Enumerated(EnumType.STRING)
    private TransferType transferType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "booking_date")
    private LocalDateTime bookingDate;

    @Column(name = "currency", length = 3)
    @Enumerated(EnumType.STRING)
    private Currency currency = Currency.PLN;

    @Column(name = "is_active")
    private Boolean active = true;

    @PrePersist
    protected void onCreate() {
        if (createTime == null) {
            createTime = LocalDateTime.now();
        }
    }

    public Transfer(String title, BigDecimal value, BankAccount accountId, String accountNo, TransferType transferType) {
        this.title = title;
        this.value = value;
        this.accountId = accountId;
        this.accountNo = accountNo.replace(" ", "");
        this.transferType = transferType;
    }

    public Transfer(String title, BigDecimal value, BankAccount accountId, String accountNo, TransferStatus status, TransferType transferType, LocalDateTime bookingDate, Currency currency) {
        this.title = title;
        this.value = value;
        this.accountId = accountId;
        this.accountNo = accountNo;
        this.status = status;
        this.transferType = transferType;
        this.bookingDate = bookingDate;
        this.currency = currency;
    }

    public Transfer(String title, BigDecimal value, BankAccount accountId, String accountNo, TransferStatus status, TransferType transferType, LocalDateTime createTime, LocalDateTime bookingDate, Currency currency) {
        this.title = title;
        this.value = value;
        this.accountId = accountId;
        this.accountNo = accountNo;
        this.status = status;
        this.transferType = transferType;
        this.createTime = createTime;
        this.bookingDate = bookingDate;
        this.currency = currency;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo.replace(" ", "");
    }
}
