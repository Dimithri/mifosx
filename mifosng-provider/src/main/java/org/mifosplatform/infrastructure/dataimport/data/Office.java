package org.mifosplatform.infrastructure.dataimport.data;

import java.util.ArrayList;

import org.joda.time.DateTimeFieldType;
import org.joda.time.LocalDate;
import org.mifosplatform.organisation.office.data.OfficeData;

public class Office {
    
    private final Integer id;
    
    private final String name;
    
    private final String externalId;
    
    private final ArrayList<Integer> openingDate;
    
    private final String parentName;
	
    private final String hierarchy;

    public Office(Integer id, String name, String externalId, ArrayList<Integer> openingDate, String parentName, String hierarchy ) {
        this.id = id;
        this.name = name;
        this.parentName = parentName;
        this.externalId = externalId;
        this.openingDate = openingDate;
        this.hierarchy = hierarchy;
    }
    
    public Office(OfficeData officeData){
    	
    	//TODO
    	//need to control the overflow
    	this.id = officeData.getId() != null ? officeData.getId().intValue() : null;
        this.name = officeData.name();
        this.parentName = officeData.getParentName();
        this.externalId = officeData.getExternalId();
        
        //TODO
        //Convert the openingDate field to LocalDate type
        LocalDate openingDate = officeData.getOpeningDate();
        
        this.openingDate = new ArrayList<Integer>();
        this.openingDate.add(openingDate.getYear());
        this.openingDate.add(openingDate.get(DateTimeFieldType.monthOfYear()));
        this.openingDate.add(openingDate.getDayOfMonth());
        
        this.hierarchy = officeData.getHierarchy();
    }
    
    @Override
	public String toString() {
	   return "OfficeObject [id=" + id + ", name=" + name + ", externalId=" + externalId + ", openingDate=" + openingDate + ", parentName=" + parentName + "]";
	}
    
    public Integer getId() {
    	return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getParentName() {
        return this.parentName;
    }

    public String getExternalId() {
        return this.externalId;
    }

    public ArrayList<Integer> getOpeningDate() {
        return this.openingDate;
    }
    
    public String getHierarchy() {
        return this.hierarchy;
    }

}
