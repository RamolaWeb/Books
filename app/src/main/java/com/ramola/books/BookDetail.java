package com.ramola.books;


import com.google.gson.annotations.SerializedName;

public class BookDetail {
    @SerializedName("Error")
    private String error;
    @SerializedName("Title")
    private String title;
    @SerializedName("Description")
    private String description;
    @SerializedName("Author")
    private String author;
    @SerializedName("Year")
    private String year;
    @SerializedName("Page")
    private String page;
    @SerializedName("Publisher")
    private String publisher;
    @SerializedName("Image")
    private String image;
@SerializedName("Download")
private String download;
    public BookDetail(String error,String title, String description, String author, String year, String page, String publisher, String image,String download) {
        this.error=error;
        this.title = title;
        this.description = description;
        this.author = author;
        this.year = year;
        this.page = page;
        this.publisher = publisher;
        this.image = image;
        this.download=download;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }
}
