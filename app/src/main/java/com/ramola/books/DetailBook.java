package com.ramola.books;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailBook extends AppCompatActivity {
    private static final String TITLE = "title";
    private static final String ID = "id";
    private static final String ADDRESS = "url";
    private static final String PACKAGE_NAME ="com.android.chrome" ;
    private AspectImageView imageView;
    private TextView title, description, author, year, publisher, page;
    private String url;
    private ProgressBar progressBar;
    private LinearLayout layout;

    private CustomTabsClient c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        imageView = (AspectImageView) findViewById(R.id.image_detail_book);
        title = (TextView) findViewById(R.id.title_detail_book);
        description = (TextView) findViewById(R.id.description_detail_book);
        author = (TextView) findViewById(R.id.author_detail_book);
        year = (TextView) findViewById(R.id.year_detail_book);
        publisher = (TextView) findViewById(R.id.publisher_detail_book);
        page = (TextView) findViewById(R.id.page_detail_book);
        description.setTypeface(TypeFaceManager.getRegular());
        author.setTypeface(TypeFaceManager.getRegular());
        year.setTypeface(TypeFaceManager.getRegular());
        publisher.setTypeface(TypeFaceManager.getRegular());
        page.setTypeface(TypeFaceManager.getRegular());
        if (intent.hasExtra(ID)) {
            getSupportActionBar().setTitle(intent.getStringExtra(TITLE));
            sendRequest(intent.getLongExtra(ID, 0l));
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (url != null) {
                    CustomTabsIntent.Builder builder=new CustomTabsIntent.Builder();
                    CustomTabsIntent customTabsIntent=builder.build();
                    customTabsIntent.launchUrl(DetailBook.this, Uri.parse(url));
                }
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.progressbar_detail);
        layout = (LinearLayout) findViewById(R.id.layout_detail_book);
    }


    @Override
    protected void onStart() {
        super.onStart();
        /*
        CustomTabsClient.bindCustomTabsService(this, PACKAGE_NAME, new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
                c=customTabsClient;
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
             c=null;
            }
        });

        if(c!=null) c.warmup(0);
        */
    }

    private BookService getService() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient.Builder oBuilder = new OkHttpClient.Builder();
        oBuilder.addNetworkInterceptor(loggingInterceptor);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://it-ebooks-api.info/v1/").addConverterFactory(GsonConverterFactory.create()).client(oBuilder.build()).build();
        BookService service = retrofit.create(BookService.class);
        return service;
    }

    private void sendRequest(long id) {
        Call<BookDetail> call = getService().getBookDetail(id);
        call.enqueue(new Callback<BookDetail>() {
            @Override
            public void onResponse(Call<BookDetail> call, Response<BookDetail> response) {
                progressBar.setVisibility(View.GONE);
                layout.setVisibility(View.VISIBLE);
                BookDetail bookDetail = response.body();
                if (!bookDetail.getError().equalsIgnoreCase("Book not found!")) {
                    url = bookDetail.getDownload();
                    title.setText(bookDetail.getTitle());
                    description.setText(bookDetail.getDescription());
                    author.setText(bookDetail.getAuthor());
                    page.setText(bookDetail.getPage());
                    publisher.setText(bookDetail.getPublisher());
                    year.setText(bookDetail.getYear());
                    Glide.with(DetailBook.this).load(bookDetail.getImage()).placeholder(R.drawable.booksstackofthree).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
                } else {

                }
            }

            @Override
            public void onFailure(Call<BookDetail> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
