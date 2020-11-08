package com.rvh.openoffice.parts.main.config.styles;

import java.util.Objects;

public class Color {

    private boolean auto;
    private int indexed;
    private String rgb;
    private int theme;
    private int tint;

    public boolean isAuto() {
        return auto;
    }

    public void setAuto(boolean auto) {
        this.auto = auto;
    }

    public int getIndexed() {
        return indexed;
    }

    public void setIndexed(int indexed) {
        this.indexed = indexed;
    }

    public String getRgb() {
        return rgb;
    }

    public void setRgb(String rgb) {
        this.rgb = rgb;
    }

    public int getTheme() {
        return theme;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }

    public int getTint() {
        return tint;
    }

    public void setTint(int tint) {
        this.tint = tint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Color)) return false;

        Color color = (Color) o;

        if (auto != color.auto) return false;
        if (indexed != color.indexed) return false;
        if (theme != color.theme) return false;
        if (tint != color.tint) return false;
        return Objects.equals(rgb, color.rgb);
    }

    @Override
    public int hashCode() {
        int result = (auto ? 1 : 0);
        result = 31 * result + indexed;
        result = 31 * result + (rgb != null ? rgb.hashCode() : 0);
        result = 31 * result + theme;
        result = 31 * result + tint;
        return result;
    }
}
