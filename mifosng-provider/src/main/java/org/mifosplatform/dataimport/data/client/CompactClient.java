package org.mifosplatform.dataimport.data.client;

import java.util.ArrayList;

import org.joda.time.DateTimeFieldType;
import org.joda.time.LocalDate;
import org.mifosplatform.portfolio.client.data.ClientData;

public class CompactClient {

    private final Integer id;

    private final String displayName;

    private final String officeName;

    private final ArrayList<Integer> activationDate;

    private final Boolean active;

    public CompactClient(Integer id, String displayName, String officeName, ArrayList<Integer> activationDate, Boolean active) {
        this.id = id;
        this.displayName = displayName;
        this.activationDate = activationDate;
        this.officeName = officeName;
        this.active = active;
    }
    
    public CompactClient(ClientData clientData) {
        this.id = clientData.id().intValue();
        this.displayName = clientData.displayName();    
        this.officeName = clientData.officeName();
        this.active = clientData.isActive();
        
        this.activationDate = new ArrayList<Integer>();
        if(this.active){
            LocalDate activationDate = clientData.getActivationDate();

            this.activationDate.add(activationDate.getYear());
            this.activationDate.add(activationDate.get(DateTimeFieldType.monthOfYear()));
            this.activationDate.add(activationDate.getDayOfMonth());
        }
    }

    public Integer getId() {
        return this.id;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public ArrayList<Integer> getActivationDate() {
        return this.activationDate;
    }

    public String getOfficeName() {
        return this.officeName;
    }

    public Boolean isActive() {
        return this.active;
    }
}
