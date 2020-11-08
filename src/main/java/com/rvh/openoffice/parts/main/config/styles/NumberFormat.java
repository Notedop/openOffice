package com.rvh.openoffice.parts.main.config.styles;

import com.rvh.openoffice.parts.main.config.Config;

import java.util.Objects;

public class NumberFormat extends Config {

    private String formatCode;

    public NumberFormat(String name, String id, String uri) {
        super(name, id, uri);
    }

    public String getFormatCode() {
        return formatCode;
    }

    public void setFormatCode(String formatCode) {
        this.formatCode = formatCode;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NumberFormat)) return false;
        NumberFormat that = (NumberFormat) o;
        return Objects.equals(formatCode, that.formatCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formatCode);
    }
}
