package org.mifosplatform.dataimport.data;

import org.mifosplatform.organisation.monetary.data.CurrencyData;

public class Currency {

    private final String code;

    private final String name;

    private final Integer decimalPlaces;

    private final Integer inMultiplesOf;

    private final String displaySymbol;

    public Currency(String code, String name, Integer decimalPlaces, Integer inMultiplesOf, String displaySymbol) {
        this.code = code;
        this.name = name;
        this.decimalPlaces = decimalPlaces;
        this.inMultiplesOf = inMultiplesOf;
        this.displaySymbol = displaySymbol;
    }

    public Currency(final CurrencyData currencyData) {
        this.code = currencyData.code();
        this.name = currencyData.getName();
        this.decimalPlaces = currencyData.decimalPlaces();
        this.inMultiplesOf = currencyData.currencyInMultiplesOf();
        this.displaySymbol = currencyData.getDisplaySymbol();
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public Integer getDecimalPlaces() {
        return this.decimalPlaces;
    }

    public Integer getInMultiplesOf() {
        return this.inMultiplesOf;
    }

    public String getDisplaySymbol() {
        return this.displaySymbol;
    }
}
