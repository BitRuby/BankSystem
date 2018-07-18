package com.onwelo.practice.bts.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@ToString(exclude = "transfers")
@Table(name = "bank_account")
@Where(clause = "is_active=1")
public class BankAccount {
    @Id
    @Getter @Setter
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    @Column(unique = true, name = "account_no")
    private String accountNo;

    @Getter @Setter
    @Column(name = "first_name")
    private String firstName;

    @Getter @Setter
    @Column(name = "last_name")
    private String lastName;

    @Getter @Setter
    @Column(name = "money_amount")
    private Float moneyAmount;

    @Getter @Setter
    @Column(name = "money_blocked")
    private Float moneyBlocked;

    @Getter @Setter
    @OneToMany(mappedBy = "sourceAcc", cascade = CascadeType.ALL)
    private List<Transfer> transfers;

    @Getter @Setter
    @Column(name = "is_active")
    private Boolean active = true;

    public BankAccount() {
    }

    public BankAccount(String accountNo, String firstName, String lastName, Float moneyAmount, Float moneyBlocked) {
        this.accountNo = accountNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.moneyAmount = moneyAmount;
        this.moneyBlocked = moneyBlocked;
    }
}
