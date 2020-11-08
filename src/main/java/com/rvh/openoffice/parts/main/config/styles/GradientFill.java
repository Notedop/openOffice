package com.rvh.openoffice.parts.main.config.styles;

import com.rvh.openoffice.parts.main.enums.styles.GradientType;

import java.util.Objects;

public class GradientFill {

    private GradientType gradientType;
    private double degree;
    private double left;
    private double right;
    private double top;
    private double bottom;

    public GradientType getGradientType() {
        return gradientType;
    }

    public void setGradientType(GradientType gradientType) {
        this.gradientType = gradientType;
    }

    public double getDegree() {
        return degree;
    }

    public void setDegree(double degree) {
        this.degree = degree;
    }

    public double getLeft() {
        return left;
    }

    public void setLeft(double left) {
        this.left = left;
    }

    public double getRight() {
        return right;
    }

    public void setRight(double right) {
        this.right = right;
    }

    public double getTop() {
        return top;
    }

    public void setTop(double top) {
        this.top = top;
    }

    public double getBottom() {
        return bottom;
    }

    public void setBottom(double bottom) {
        this.bottom = bottom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GradientFill)) return false;
        GradientFill that = (GradientFill) o;
        return Double.compare(that.degree, degree) == 0 &&
                Double.compare(that.left, left) == 0 &&
                Double.compare(that.right, right) == 0 &&
                Double.compare(that.top, top) == 0 &&
                Double.compare(that.bottom, bottom) == 0 &&
                gradientType == that.gradientType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gradientType, degree, left, right, top, bottom);
    }
}
