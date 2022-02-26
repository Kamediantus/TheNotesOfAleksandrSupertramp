package ru.rodichev.webBlog.entity;

import java.util.*;

import javax.persistence.*;

@Table(name = "orders")
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cardNumber;
    private Long consumerId;
    private Long productId;
    private Long storeId;
    private Date creationDate;
    // булева, определяющая был ли товар куплен в период "не первой свежести". У каждого товара такой период свой
    private Boolean isBuyInWarningSpoilPeriod;

    public Order() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Long consumerId) {
        this.consumerId = consumerId;
    }

    public Boolean getBuyInWarningSpoilPeriod() {
        return isBuyInWarningSpoilPeriod;
    }

    public void setBuyInWarningSpoilPeriod(Boolean buyInWarningSpoilPeriod) {
        isBuyInWarningSpoilPeriod = buyInWarningSpoilPeriod;
    }
}
