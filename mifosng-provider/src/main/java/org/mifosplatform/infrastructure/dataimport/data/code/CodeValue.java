package org.mifosplatform.infrastructure.dataimport.data.code;

public class CodeValue {

    private final transient Integer rowIndex;

    private final transient String status;

    private final String name;

    private final transient Long codeId;

    public CodeValue(final String name, final Long codeId, final Integer rowIndex, final String status) {

        this.name = name;
        this.codeId = codeId;
        this.rowIndex = rowIndex;
        this.status = status;
    }

    public Integer getRowIndex() {
        return this.rowIndex;
    }

    public Long getCodeId() {
        return this.codeId;
    }

    public String getStatus() {
        return this.status;
    }

    public String getName() {
        return this.name;
    }
}
