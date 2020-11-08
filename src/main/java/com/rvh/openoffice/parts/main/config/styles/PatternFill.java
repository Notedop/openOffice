package com.rvh.openoffice.parts.main.config.styles;

import com.rvh.openoffice.parts.main.enums.styles.PatternType;

import java.util.Objects;

public class PatternFill  {

    private PatternType patternType;
    private Color foreGroundColor;
    private Color backGroundColor;

    public PatternType getPatternType() {
        return patternType;
    }

    public void setPatternType(PatternType patternType) {
        this.patternType = patternType;
    }

    public Color getForeGroundColor() {
        return foreGroundColor;
    }

    public void setForeGroundColor(Color foreGroundColor) {
        this.foreGroundColor = foreGroundColor;
    }

    public Color getBackGroundColor() {
        return backGroundColor;
    }

    public void setBackGroundColor(Color backGroundColor) {
        this.backGroundColor = backGroundColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PatternFill)) return false;
        PatternFill that = (PatternFill) o;
        return patternType == that.patternType &&
                Objects.equals(foreGroundColor, that.foreGroundColor) &&
                Objects.equals(backGroundColor, that.backGroundColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patternType, foreGroundColor, backGroundColor);
    }
}
