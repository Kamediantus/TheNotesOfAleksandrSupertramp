package ru.rodichev.webBlog.entity;

import java.util.*;
import java.util.concurrent.*;

import javax.persistence.*;

@Table(name = "products")
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long storeId;
    private String title;
    private String description;
    // срок годности в часах
    private int shelLife;
    // значение в часах, определяющее за сколько до истечения срока годности начислять скидку
    private int shelLifeWarning;
    private double price;
    // дата изготовления. В базе не храним, эти данные хранятся в таблице поставок.
    @Transient
    private Date dateOfProduction;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public int getShelLifeWarning() {
        return shelLifeWarning;
    }

    public void setShelLifeWarning(int shelLifeWarning) {
        this.shelLifeWarning = shelLifeWarning;
    }

    public Date getDateOfProduction() {
        return dateOfProduction;
    }

    public void setDateOfProduction(Date dateOfProduction) {
        this.dateOfProduction = dateOfProduction;
    }

    public Product(String title, String description, Double price, Long storeId) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.storeId = storeId;
    }

    public Product() {}
}

