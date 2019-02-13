package com.example.jimaras.comiccharatacters.features.characters.model;

import android.databinding.ObservableField;

import java.util.List;

public class Data {
    private int offset;
    private int total; // TOTAL CHARACTERS 1491
    private List<Results> results;

    public Data(int offset, int total, List<Results> results) {
        this.offset = offset;
        this.total = total;
        this.results = results;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }
}
