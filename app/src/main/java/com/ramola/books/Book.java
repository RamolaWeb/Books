package com.ramola.books;


import com.google.gson.annotations.SerializedName;

public class Book {
    @SerializedName("ID")
    private long bookId;
    @SerializedName("Title")
    private String bookName;
    @SerializedName("Description")
    private String bookDescription;
    @SerializedName("Image")
    private String bookImage;

    public Book(long bookId, String bookName, String bookDescription, String bookImage) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookDescription = bookDescription;
        this.bookImage = bookImage;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }
}
