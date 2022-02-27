package ru.rodichev.webBlog.entity;

import java.util.*;
import java.util.concurrent.*;

import javax.persistence.*;

import org.springframework.beans.factory.annotation.*;
import ru.rodichev.webBlog.repo.*;

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
    private int count;
    // булева, определяющая был ли товар куплен в период "не первой свежести". У каждого товара такой период свой
    private Boolean isBuyInWarningSpoilPeriod;
    private Boolean isFinished;
    @Transient
    private Product product;

    public Order(Long cardNumber, Long consumerId, Long productId, Long storeId, Date creationDate, int count, Product product, ProductLot lot) {
        this.cardNumber = cardNumber;
        this.consumerId = consumerId;
        this.productId = productId;
        this.storeId = storeId;
        this.creationDate = creationDate;
        this.isBuyInWarningSpoilPeriod = isExpirationDateIsComingToEnd(lot, creationDate);
        this.count = count;
        this.isFinished = true;
    }

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    // метод определяющий был ли товар куплен в период, когда срок годности подходит к концу
    // k - коэффициент, определяющий через сколько времени от производства товар считается "почти протухшим"
    public boolean isExpirationDateIsComingToEnd(ProductLot lot, Date dateOfDeal) {
        double k = 0.3;
        long diffInHours  = TimeUnit.HOURS.convert(Math.abs(lot.getDateOfProduction().getTime() - dateOfDeal.getTime()), TimeUnit.MILLISECONDS);
        if (diffInHours > lot.getShelLife() * 0.3) {
            return true;
        }
        return false;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }
}
