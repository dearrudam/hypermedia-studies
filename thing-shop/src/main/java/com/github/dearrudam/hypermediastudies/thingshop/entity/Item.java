package com.github.dearrudam.hypermediastudies.thingshop.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.money.MonetaryAmount;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class Item extends PanacheEntity {

    public String name;
    public Money amount;

    public Item() {

    }

    public Item(String name, Money amount) {
        this.name = name;
        this.amount = amount;
    }

    public void setAmount(MonetaryAmount monetaryAmount) {
        this.amount = new Money(monetaryAmount.getCurrency().getCurrencyCode(),
                BigDecimal
                        .valueOf(monetaryAmount.getNumber().longValueExact(),
                                monetaryAmount.getNumber().getScale()));
    }


}
