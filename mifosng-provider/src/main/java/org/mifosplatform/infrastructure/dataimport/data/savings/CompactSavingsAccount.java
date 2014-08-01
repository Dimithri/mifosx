package org.mifosplatform.infrastructure.dataimport.data.savings;

import java.util.Comparator;
import java.util.Locale;

import org.mifosplatform.infrastructure.dataimport.data.Status;
import org.mifosplatform.portfolio.savings.data.SavingsAccountData;

public class CompactSavingsAccount {

    private final String accountNo;

    private final String clientId;

    private final String clientName;

    private final String savingsProductName;

    private final Double minRequiredOpeningBalance;

    private final SavingsTimeline timeline;

    private final Status status;

    public CompactSavingsAccount(String accountNo, String clientId, String clientName, String savingsProductName,
            Double minRequiredOpeningBalance, SavingsTimeline timeline, Status status) {
        this.accountNo = accountNo;
        this.clientId = clientId;
        this.clientName = clientName;
        this.savingsProductName = savingsProductName;
        this.minRequiredOpeningBalance = minRequiredOpeningBalance;
        this.timeline = timeline;
        this.status = status;
    }

    public CompactSavingsAccount(final SavingsAccountData savingsAccountData) {

        this.accountNo = savingsAccountData.getAccountNo();
        this.clientId = savingsAccountData.clientId().toString();
        this.clientName = savingsAccountData.getClientName();
        this.savingsProductName = savingsAccountData.getSavingsProductName();
        this.minRequiredOpeningBalance = savingsAccountData.getMinRequiredOpeningBalance().doubleValue();
        this.timeline = new SavingsTimeline(savingsAccountData.getTimeline());
        this.status = new Status(savingsAccountData.getStatus().isActive());
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientId() {
        return clientId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public String getSavingsProductName() {
        return savingsProductName;
    }

    public Double getMinRequiredOpeningBalance() {
        return minRequiredOpeningBalance;
    }

    public Boolean isActive() {
        return this.status.isActive();
    }

    public SavingsTimeline getTimeline() {
        return timeline;
    }

    public static final Comparator<CompactSavingsAccount> ClientNameComparator = new Comparator<CompactSavingsAccount>() {

        @Override
        public int compare(CompactSavingsAccount savings1, CompactSavingsAccount savings2) {
            String clientOfSavings1 = savings1.getClientName().toUpperCase(Locale.ENGLISH);
            String clientOfSavings2 = savings2.getClientName().toUpperCase(Locale.ENGLISH);
            return clientOfSavings1.compareTo(clientOfSavings2);
        }
    };
}