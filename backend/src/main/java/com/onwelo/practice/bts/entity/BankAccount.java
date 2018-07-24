package com.onwelo.practice.bts.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
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

    @Column(unique = true, name = "account_no")
    private String accountNo;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "money_amount")
    private Float moneyAmount;

    @Column(name = "money_blocked")
    private Float moneyBlocked;

    @OneToMany(mappedBy = "accountId", cascade = CascadeType.ALL)
    private List<Transfer> transfers;

    @Column(name = "is_active")
    private Boolean active = true;

    public BankAccount(String accountNo, String firstName, String lastName, Float moneyAmount, Float moneyBlocked) {
        this.accountNo = accountNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.moneyAmount = moneyAmount;
        this.moneyBlocked = moneyBlocked;
    }
}
