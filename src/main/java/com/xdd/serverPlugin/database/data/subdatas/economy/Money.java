package com.xdd.serverPlugin.database.data.subdatas.economy;

import com.xdd.serverPlugin.Utils.Format;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
public class Money {

    private BigDecimal money;

    public Money(double money) {
        this.money = BigDecimal.valueOf(money).setScale(2, RoundingMode.HALF_UP);
    }

    public void addMoney(double amountToAdd){
        money = money.add(BigDecimal.valueOf(amountToAdd).setScale(2, RoundingMode.HALF_UP));
    }

    public void subtractMoney(double amountToSubtract){
        money = money.subtract(BigDecimal.valueOf(amountToSubtract).setScale(2, RoundingMode.HALF_UP));
    }

    public boolean canMakePayment(double amountToPay){
        return money.compareTo(BigDecimal.valueOf(amountToPay).setScale(2, RoundingMode.HALF_UP)) >= 0;
    }

    public double getDouble(){
        return money.setScale(2,RoundingMode.HALF_UP).doubleValue();
    }

    public void setMoney(double newValue){
        money = BigDecimal.valueOf(newValue).setScale(2, RoundingMode.HALF_UP);
    }
    public String formated(){
        return Format.formatMoney(money.doubleValue()) + "$";
    }
    public String smartFormated(){
        if(money.doubleValue() < 1000) {
            return formated();
        } else return Format.formatMoneyWithK(money.doubleValue());

    }
}
