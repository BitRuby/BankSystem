package com.onwelo.practice.bts.entity;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.onwelo.practice.bts.utils.MoneySerializer;
import com.onwelo.practice.bts.utils.TransferStatus;
import com.onwelo.practice.bts.utils.TransferType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString(exclude = "accountId")
@Table(name = "transfer")
@Where(clause = "is_active=1")
public class Transfer {
    @Id
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
    private TransferStatus status;

    @Column(name = "transfer_type", length = 8)
    @Enumerated(EnumType.STRING)
    private TransferType transferType;

    @Column(name = "create_time")
    private java.sql.Timestamp createTime;

    @Column(name = "booking_date")
    private LocalDate bookingDate;

    @Column(name = "is_active")
    private Boolean active = true;

    @PrePersist
    protected void onCreate() {
        if (createTime == null) {
            createTime = new Timestamp(System.currentTimeMillis());
        }
    }

    public Transfer(String title, BigDecimal value, BankAccount accountId, String accountNo, TransferType transferType) {
        this.title = title;
        this.value = value;
        this.accountId = accountId;
        this.accountNo = accountNo.replace(" ", "");
        this.transferType = transferType;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo.replace(" ", "");
    }
}
