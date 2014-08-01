package org.mifosplatform.infrastructure.dataimport.data;

public class Status {

	private Boolean active;
	
	public Status(Boolean active){
		this.active = active;
	}
	
	public Boolean isActive() {
		return this.active;
	}
}
