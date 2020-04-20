package com.bardab.budgettracker.model;
import javax.persistence.*;
import java.time.LocalDate;



@Entity
@Table (name="Transaction",uniqueConstraints = {@UniqueConstraint(columnNames = "ID")})
public class Transaction {


    public Transaction() {
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID", nullable = false,unique = true,length = 11)
    private Long id;

    @Column (name="TYPE")
    private String type;

    @Column (name="transaction_date" ,nullable = false)
    private LocalDate transactionDate;

    @Column (name="VALUE")
    private Double value;

    @Column (name="DESCRIPTION")
    private String description;

    @Column (name = "month_code" ,nullable = false)
    private String monthCode;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDateAndMonthCode(LocalDate transactionDate) {
        this.transactionDate=transactionDate;
        this.monthCode = MonthCode.createMonthCodeFromLocalDate(transactionDate);
    }

    public String getMonthCode() {
        return monthCode;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        if(value>=0){
        this.value = value;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && obj instanceof Transaction) {
            Transaction other = (Transaction) obj;
            return this.toStringWithoutID().equals(other.toStringWithoutID());
        }
        return false;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", transactionDate=" + transactionDate +
                ", value=" + value +
                ", description='" + description + '\'' +
                '}';
    }
    public String toStringWithoutID(){
        return "Transaction{" +
                " type='" + type + '\'' +
                ", transactionDate=" + transactionDate +
                ", value=" + value +
                ", description='" + description + '\'' +
                '}';
    }


}
