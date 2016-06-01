package com.ramola.books;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import net.steamcrafted.loadtoast.LoadToast;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {
    private static final String ID = "id";
    private static final String TITLE ="title" ;
    private android.support.v7.widget.SearchView searchView;
private RecyclerView recyclerView;
private BookAdapter adapter;
private List<Book> list=new ArrayList<Book>();
private LoadToast loadToast;

    private boolean loading = true;
   private int totalBooks, pastVisiblesItems, visibleItemCount, totalItemCount,pageno=1,previousTotal=0,visibleThreshold=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        loadToast=new LoadToast(this);
        loadToast.setTranslationY(100);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView= (RecyclerView) findViewById(R.id.list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        searchView= (android.support.v7.widget.SearchView) findViewById(R.id.searchView);
        android.support.v7.widget.SearchView.SearchAutoComplete searchAutoComplete= (android.support.v7.widget.SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        searchView.setIconified(false);
        searchView.setQueryHint("Search The Books");
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                list.clear();
                loadToast.show();
                sendRequest(s,pageno);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
searchView.setOnCloseListener(new android.support.v7.widget.SearchView.OnCloseListener() {
    @Override
    public boolean onClose() {
        finish();
        return true;
    }
});
        adapter=new BookAdapter(this);
        final GridLayoutManager mLayoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(dy > 0)
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if (totalItemCount > previousTotal) {
                            loading = false;
                            previousTotal = totalItemCount;
                        }
                    }
                    if (pageno*10<totalBooks&&!loading && (totalItemCount - visibleItemCount)
                            <= (pastVisiblesItems + visibleThreshold)) {

                        loading = true;
                        loadToast.show();
                        sendRequest(searchView.getQuery().toString(),++pageno);
                    }
                }
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(SearchActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(SearchActivity.this,DetailBook.class);
                intent.putExtra(ID,list.get(position).getBookId());
                intent.putExtra(TITLE,list.get(position).getBookName());
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                    AspectImageView imageView= (AspectImageView) view.findViewById(R.id.imageview_item_book);
                    imageView.setTransitionName("Transition");
                    ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(SearchActivity.this,imageView,"Transition");
                    startActivity(intent,optionsCompat.toBundle());
                }
                else
                startActivity(intent);
            }
        }));
    }
    private void sendRequest(String keyword,int pageno) {
        Call<Bookresponse> call =getService().getBookResult(keyword,pageno);
        call.enqueue(new Callback<Bookresponse>() {
            @Override
            public void onResponse(Call<Bookresponse> call, Response<Bookresponse> response) {
                totalBooks=Integer.parseInt(response.body().getTotalResult());
              if(totalBooks>0){
                  for(Book book:response.body().getBooks()){
                      list.add(book);
                  }
                  loadToast.success();
                  adapter.refresh(list);
              }
                else {
                  loadToast.error();
              }
            }

            @Override
            public void onFailure(Call<Bookresponse> call, Throwable t) {
              loadToast.error();
                t.printStackTrace();
            }
        });
    }
    private BookService getService(){
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient.Builder oBuilder=new OkHttpClient.Builder();
        oBuilder.addNetworkInterceptor(loggingInterceptor);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://it-ebooks-api.info/v1/").addConverterFactory(GsonConverterFactory.create()).client(oBuilder.build()).build();
        BookService service = retrofit.create(BookService.class);
        return service;
    }
}
