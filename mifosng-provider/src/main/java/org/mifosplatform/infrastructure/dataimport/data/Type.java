package org.mifosplatform.infrastructure.dataimport.data;

import org.mifosplatform.infrastructure.core.data.EnumOptionData;

public class Type {

    private final Integer id;

    private final String code;

    private final String value;

    public Type(Integer id, String code, String value) {
        this.id = id;
        this.code = code;
        this.value = value;
    }

    public Type(final EnumOptionData type) {
        this.id = type.getId().intValue();
        this.code = type.getCode();
        this.value = type.getValue();
    }

    public Integer getId() {
        return this.id;
    }

    public String getCode() {
        return this.code;
    }

    public String getValue() {
        return this.value;
    }

}
