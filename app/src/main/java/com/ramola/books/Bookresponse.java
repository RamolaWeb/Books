package com.ramola.books;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Bookresponse {
    @SerializedName("Total")
    private String totalResult;
    @SerializedName("Page")
    private int pageNumber;
@SerializedName("Books")
    private List<Book> books;

    public Bookresponse(String totalResult, int pageNumber, List<Book> books) {
        this.totalResult = totalResult;
        this.pageNumber = pageNumber;
        this.books = books;
    }

    public String getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(String totalResult) {
        this.totalResult = totalResult;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
