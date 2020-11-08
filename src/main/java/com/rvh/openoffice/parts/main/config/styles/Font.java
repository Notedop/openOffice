package com.rvh.openoffice.parts.main.config.styles;

import com.rvh.openoffice.parts.main.config.Config;
import com.rvh.openoffice.parts.main.enums.styles.FontScheme;
import com.rvh.openoffice.parts.main.enums.styles.UnderLineValue;
import com.rvh.openoffice.parts.main.enums.styles.VerticalAlignRun;

import java.util.Objects;

public class Font extends Config {

    private int charSet;
    private int family;
    private boolean bold;
    private boolean italic;
    private boolean strikeThrough;
    private boolean outLine;
    private boolean shadow;
    private boolean condense;
    private boolean extend;
    private Color color;
    private double fontSize;
    private UnderLineValue underLine;
    private VerticalAlignRun verticalAlignRun;
    private FontScheme fontScheme;

    public Font(String name, String id, String uri) {
        super(name, id, uri);
    }

    public int getCharSet() {
        return charSet;
    }

    public void setCharSet(int charSet) {
        this.charSet = charSet;
    }

    public int getFamily() {
        return family;
    }

    public void setFamily(int family) {
        this.family = family;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public boolean isItalic() {
        return italic;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public boolean isStrikeThrough() {
        return strikeThrough;
    }

    public void setStrikeThrough(boolean strikeThrough) {
        this.strikeThrough = strikeThrough;
    }

    public boolean isOutLine() {
        return outLine;
    }

    public void setOutLine(boolean outLine) {
        this.outLine = outLine;
    }

    public boolean isShadow() {
        return shadow;
    }

    public void setShadow(boolean shadow) {
        this.shadow = shadow;
    }

    public boolean isCondense() {
        return condense;
    }

    public void setCondense(boolean condense) {
        this.condense = condense;
    }

    public boolean isExtend() {
        return extend;
    }

    public void setExtend(boolean extend) {
        this.extend = extend;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getFontSize() {
        return fontSize;
    }

    public void setFontSize(double fontSize) {
        this.fontSize = fontSize;
    }

    public UnderLineValue getUnderLine() {
        return underLine;
    }

    public void setUnderLine(UnderLineValue underLine) {
        this.underLine = underLine;
    }

    public VerticalAlignRun getVerticalAlignRun() {
        return verticalAlignRun;
    }

    public void setVerticalAlignRun(VerticalAlignRun verticalAlignRun) {
        this.verticalAlignRun = verticalAlignRun;
    }

    public FontScheme getFontScheme() {
        return fontScheme;
    }

    public void setFontScheme(FontScheme fontScheme) {
        this.fontScheme = fontScheme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Font)) return false;
        Font font = (Font) o;
        return charSet == font.charSet &&
                family == font.family &&
                bold == font.bold &&
                italic == font.italic &&
                strikeThrough == font.strikeThrough &&
                outLine == font.outLine &&
                shadow == font.shadow &&
                condense == font.condense &&
                extend == font.extend &&
                Double.compare(font.fontSize, fontSize) == 0 &&
                Objects.equals(color, font.color) &&
                underLine == font.underLine &&
                verticalAlignRun == font.verticalAlignRun &&
                fontScheme == font.fontScheme;
    }

    @Override
    public int hashCode() {
        return Objects.hash(charSet, family, bold, italic, strikeThrough, outLine, shadow, condense, extend, color, fontSize, underLine, verticalAlignRun, fontScheme);
    }
}
