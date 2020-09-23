package com.rvh.openoffice.model;

import java.util.ArrayList;
import java.util.List;

public class Row {

    int cellCount;
    List<Cell> cells = new ArrayList<>();

    public Row(int cellCount) {
        this.cellCount = cellCount;
    }

    public void addCell(Cell cell) {
        cells.add(cell);
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }
}
