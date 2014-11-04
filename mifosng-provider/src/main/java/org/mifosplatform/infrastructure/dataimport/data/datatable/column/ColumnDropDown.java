package org.mifosplatform.infrastructure.dataimport.data.datatable.column;

public class ColumnDropDown extends ColumnAbstract {

    private final String code;

    public ColumnDropDown(final String name, final String type, final Boolean mandatory, final String code) {

        super(name, type, mandatory);
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

}
