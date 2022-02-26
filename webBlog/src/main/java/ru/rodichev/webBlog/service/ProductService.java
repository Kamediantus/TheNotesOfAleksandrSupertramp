package ru.rodichev.webBlog.service;

import java.util.*;
import java.util.concurrent.*;

import ru.rodichev.webBlog.entity.*;

public class ProductService {
    private Product product;

    public ProductService(Product product) {
        this.product = product;
    }

    public boolean isExpirationDateIsComingToEnd() {
        Date currDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.product.getDateOfProduction());
        calendar.add(Calendar.HOUR, this.product.getShelLife());
        long diffInMillies  = Math.abs(this.product.getDateOfProduction().getTime() - currDate.getTime());
        long diff = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        if (diff < this.product.getShelLifeWarning()) {
            return true;
        }
        return false;
    }
}
