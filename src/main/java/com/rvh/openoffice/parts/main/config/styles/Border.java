package com.rvh.openoffice.parts.main.config.styles;

import com.rvh.openoffice.parts.main.enums.styles.BorderStyle;

import java.util.Objects;

public class Border  {

    private Color color;
    private BorderStyle style;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public BorderStyle getStyle() {
        return style;
    }

    public void setStyle(BorderStyle style) {
        this.style = style;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Border)) return false;

        Border border = (Border) o;

        if (!Objects.equals(color, border.color)) return false;
        return style == border.style;
    }

    @Override
    public int hashCode() {
        int result = color != null ? color.hashCode() : 0;
        result = 31 * result + (style != null ? style.hashCode() : 0);
        return result;
    }
}
