package org.mifosplatform.infrastructure.dataimport.data.datatable.column;

public abstract class ColumnAbstract {

    final String name;
    final String type;
    final Boolean mandatory;

    protected ColumnAbstract(final String name, final String type, final Boolean mandatory) {
        this.name = name;
        this.type = type;
        this.mandatory = mandatory;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public Boolean isMandatory() {
        return this.mandatory;
    }
}
