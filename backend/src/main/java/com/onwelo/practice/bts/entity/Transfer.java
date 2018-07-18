package com.onwelo.practice.bts.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "transfer")
public class Transfer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "value")
    private Float value;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "source_acc_id")
    @JsonBackReference
    private BankAccount sourceAcc;

    @Column(name = "account_no")
    private String accountNo;

    @Column(name = "status")
    private String status;

    @Column(name = "transfer_type")
    private String transferType;

    @Column(name = "create_time")
    private java.sql.Timestamp createTime;

    @Column(name = "booking_date")
    private LocalDate bookingDate;

    @PrePersist
    protected void onCreate() {
        createTime = new Timestamp(System.currentTimeMillis());
    }

    public Transfer() {
    }

    public Transfer(String title, Float value, BankAccount sourceAcc, String accountNo, String transferType) {
        this.title = title;
        this.value = value;
        this.sourceAcc = sourceAcc;
        this.accountNo = accountNo;
        this.transferType = transferType;

        if (transferType.equals("inner")) {
            this.status = "realized";
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public BankAccount getSourceAcc() {
        return sourceAcc;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public void setSource(BankAccount sourceAcc) {
        this.sourceAcc = sourceAcc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", value=" + value +
                ", sourceAcc=" + sourceAcc.getId() +
                ", accountNo='" + accountNo + '\'' +
                ", status='" + status + '\'' +
                ", transferType='" + transferType + '\'' +
                ", createTime=" + createTime +
                ", bookingDate=" + bookingDate +
                '}';
    }
}
