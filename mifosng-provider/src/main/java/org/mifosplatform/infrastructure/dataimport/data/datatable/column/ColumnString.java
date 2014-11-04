package org.mifosplatform.infrastructure.dataimport.data.datatable.column;

public class ColumnString extends ColumnAbstract {

    private final String length;

    public ColumnString(final String name, final String type, final Boolean mandatory, final String length) {

        super(name, type, mandatory);
        this.length = length;
    }

    public String getLenth() {
        return this.length;
    }

}
