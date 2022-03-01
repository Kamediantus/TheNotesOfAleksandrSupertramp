package ru.rodichev.webBlog.entity;


import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "t_lot")
public class ProductLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private Long storeId;
    private Date dateOfProduction;
    private int shelLife;
    private int count;
    private Boolean active;

    public ProductLot(){}

    public ProductLot(Long productId, Long storeIdId, Date dateOfProduction, int shelLife) {
        this.productId = productId;
        this.storeId = storeIdId;
        this.dateOfProduction = dateOfProduction;
        this.shelLife = shelLife;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Date getDateOfProduction() {
        return dateOfProduction;
    }

    public void setDateOfProduction(Date dateOfProduction) {
        this.dateOfProduction = dateOfProduction;
    }

    public int getShelLife() {
        return shelLife;
    }

    public void setShelLife(int shelLife) {
        this.shelLife = shelLife;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
