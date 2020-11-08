package com.rvh.openoffice.parts.main.config.styles;

import com.rvh.openoffice.parts.main.config.Config;

import java.util.Objects;

public class CellBorders extends Config {

    private boolean diagonalUp;
    private boolean diagonalDown;
    private boolean outline;

    private Border left;
    private Border right;
    private Border top;
    private Border bottom;
    private Border diagonal;
    private Border vertical;
    private Border horizontal;

    public CellBorders(String name, String id, String uri) {
        super(name, id, uri);
    }

    public boolean isDiagonalUp() {
        return diagonalUp;
    }

    public void setDiagonalUp(boolean diagonalUp) {
        this.diagonalUp = diagonalUp;
    }

    public boolean isDiagonalDown() {
        return diagonalDown;
    }

    public void setDiagonalDown(boolean diagonalDown) {
        this.diagonalDown = diagonalDown;
    }

    public boolean isOutline() {
        return outline;
    }

    public void setOutline(boolean outline) {
        this.outline = outline;
    }

    public Border getLeft() {
        return left;
    }

    public void setLeft(Border left) {
        this.left = left;
    }

    public Border getRight() {
        return right;
    }

    public void setRight(Border right) {
        this.right = right;
    }

    public Border getTop() {
        return top;
    }

    public void setTop(Border top) {
        this.top = top;
    }

    public Border getBottom() {
        return bottom;
    }

    public void setBottom(Border bottom) {
        this.bottom = bottom;
    }

    public Border getDiagonal() {
        return diagonal;
    }

    public void setDiagonal(Border diagonal) {
        this.diagonal = diagonal;
    }

    public Border getVertical() {
        return vertical;
    }

    public void setVertical(Border vertical) {
        this.vertical = vertical;
    }

    public Border getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(Border horizontal) {
        this.horizontal = horizontal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CellBorders)) return false;
        CellBorders that = (CellBorders) o;
        return diagonalUp == that.diagonalUp &&
                diagonalDown == that.diagonalDown &&
                outline == that.outline &&
                Objects.equals(left, that.left) &&
                Objects.equals(right, that.right) &&
                Objects.equals(top, that.top) &&
                Objects.equals(bottom, that.bottom) &&
                Objects.equals(diagonal, that.diagonal) &&
                Objects.equals(vertical, that.vertical) &&
                Objects.equals(horizontal, that.horizontal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(diagonalUp, diagonalDown, outline, left, right, top, bottom, diagonal, vertical, horizontal);
    }
}
