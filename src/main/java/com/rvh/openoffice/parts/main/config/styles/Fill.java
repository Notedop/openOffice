package com.rvh.openoffice.parts.main.config.styles;

import com.rvh.openoffice.parts.main.config.Config;

import java.util.Objects;

public class Fill extends Config {

    private PatternFill patternFill;
    private GradientFill gradientFill;

    public Fill(String name, String id, String uri) {
        super(name, id, uri);
    }

    public PatternFill getPatternFill() {
        return patternFill;
    }

    public void setPatternFill(PatternFill patternFill) {
        this.patternFill = patternFill;
    }

    public GradientFill getGradientFill() {
        return gradientFill;
    }

    public void setGradientFill(GradientFill gradientFill) {
        this.gradientFill = gradientFill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fill)) return false;
        Fill fill = (Fill) o;
        return Objects.equals(patternFill, fill.patternFill) &&
                Objects.equals(gradientFill, fill.gradientFill);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patternFill, gradientFill);
    }
}
