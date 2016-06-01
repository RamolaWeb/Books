package com.ramola.books;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BookService {
    @GET("search/{keyword}/page/{pageno}")
     Call<Bookresponse> getBookResult(@Path("keyword") String Keyword,@Path("pageno") int pageno);
@GET("book/{id}")
    Call<BookDetail> getBookDetail(@Path("id") long id);

}
