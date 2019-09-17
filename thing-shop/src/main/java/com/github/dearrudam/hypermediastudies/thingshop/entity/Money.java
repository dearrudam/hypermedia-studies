package com.github.dearrudam.hypermediastudies.thingshop.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.money.MonetaryAmount;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Money implements Serializable {

    public String currency;
    public BigDecimal amount;

    public Money() {
    }

    public Money(String currency, BigDecimal amount) {
        this.currency = currency;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(currency, money.currency) &&
                Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, amount);
    }

}