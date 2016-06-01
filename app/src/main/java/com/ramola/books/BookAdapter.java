package com.ramola.books;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.viewHolder> {
    private List<Book> list=new ArrayList<>();
    private Context context;
private int Oldsize;
    public BookAdapter(Context context) {
        this.context = context;
    }
public void refresh(List<Book> list){
    Oldsize=list.size();
    this.list=list;
    notifyItemRangeInserted(Oldsize,list.size());
}
    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book,parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
if(!list.get(position).getBookImage().isEmpty()&&list.get(position).getBookImage().length()!=0){
    Glide.with(context).load(list.get(position).getBookImage()).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.drawable.booksstackofthree).into(holder.imageView);
}
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        AspectImageView imageView;
        public viewHolder(View itemView) {
            super(itemView);
            imageView= (AspectImageView) itemView.findViewById(R.id.imageview_item_book);
        }
    }
}
