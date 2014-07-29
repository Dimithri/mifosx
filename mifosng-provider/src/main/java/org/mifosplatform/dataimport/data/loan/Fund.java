package org.mifosplatform.dataimport.data.loan;

import org.mifosplatform.portfolio.fund.data.FundData;

public class Fund {

    private final Integer id;

    private final String name;

    public Fund(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Fund(FundData aFundData) {
        this.id = aFundData.getId().intValue();
        this.name = aFundData.getName();
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
