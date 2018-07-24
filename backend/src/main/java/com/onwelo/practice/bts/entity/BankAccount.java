package com.onwelo.practice.bts.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.glassfish.jersey.internal.util.collection.Views;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString(exclude = "transfers")
@Table(name = "bank_account")
@Where(clause = "is_active=1")
public class BankAccount {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, name = "account_no", length = 26)
    private String accountNo;

    @Column(name = "first_name", length = 15)
    private String firstName;

    @Column(name = "last_name", length = 25)
    private String lastName;

    @Column(name = "money_amount")
    private BigDecimal moneyAmount;

    @Column(name = "money_blocked")
    private BigDecimal moneyBlocked;

    @OneToMany(mappedBy = "accountId", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Transfer> transfers;

    @Column(name = "is_active")
    private Boolean active = true;

    public BankAccount(String accountNo, String firstName, String lastName, BigDecimal moneyAmount, BigDecimal moneyBlocked) {
        this.accountNo = accountNo.replace(" ", "");
        this.firstName = firstName;
        this.lastName = lastName;
        this.moneyAmount = moneyAmount;
        this.moneyBlocked = moneyBlocked;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo.replace(" ", "");
    }
}
