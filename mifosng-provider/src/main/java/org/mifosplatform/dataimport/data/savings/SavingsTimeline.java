package org.mifosplatform.dataimport.data.savings;

import java.util.ArrayList;

import org.joda.time.DateTimeFieldType;
import org.joda.time.LocalDate;
import org.mifosplatform.portfolio.savings.data.SavingsAccountApplicationTimelineData;

public class SavingsTimeline {

    private final ArrayList<Integer> activatedOnDate;

    public SavingsTimeline(ArrayList<Integer> activatedOnDate) {
        this.activatedOnDate = activatedOnDate;
    }

    public SavingsTimeline(SavingsAccountApplicationTimelineData timeline) {
        
        LocalDate activatedOnDate = timeline.getActivatedOnDate();
        
        this.activatedOnDate = new ArrayList<Integer>();
        this.activatedOnDate.add(activatedOnDate.getYear());
        this.activatedOnDate.add(activatedOnDate.get(DateTimeFieldType.monthOfYear()));
        this.activatedOnDate.add(activatedOnDate.getDayOfMonth());
    }

    public ArrayList<Integer> getActivatedOnDate() {
        return this.activatedOnDate;
    }
}
