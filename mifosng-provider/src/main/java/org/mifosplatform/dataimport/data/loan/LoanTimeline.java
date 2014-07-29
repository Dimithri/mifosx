package org.mifosplatform.dataimport.data.loan;

import java.util.ArrayList;

import org.joda.time.DateTimeFieldType;
import org.joda.time.LocalDate;
import org.mifosplatform.portfolio.loanaccount.data.LoanApplicationTimelineData;

public class LoanTimeline {

    private final ArrayList<Integer> actualDisbursementDate;

    public LoanTimeline(ArrayList<Integer> actualDisbursementDate) {
        this.actualDisbursementDate = actualDisbursementDate;
    }

    public LoanTimeline(LoanApplicationTimelineData timeline) {

        LocalDate actualDisbursementDate = timeline.getActualDisbursementDate();
        
        this.actualDisbursementDate = new ArrayList<Integer>();
        this.actualDisbursementDate.add(actualDisbursementDate.getYear());
        this.actualDisbursementDate.add(actualDisbursementDate.get(DateTimeFieldType.monthOfYear()));
        this.actualDisbursementDate.add(actualDisbursementDate.getDayOfMonth());
    }

    public ArrayList<Integer> getActualDisbursementDate() {
        return this.actualDisbursementDate;
    }

}
