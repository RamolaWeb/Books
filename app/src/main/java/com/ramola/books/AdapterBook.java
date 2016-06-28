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

public class AdapterBook extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Book> list = new ArrayList<>();
    private Context context;
    private int Oldsize;
    public static final int FOOTER_VIEW = 1;
    public static final int NORMAL_VIEW = 2;
    private View view;

    public AdapterBook(Context context) {
        this.context = context;
    }

    public void refresh(List<Book> list) {
        Oldsize = list.size();
        this.list = list;
        notifyItemRangeInserted(Oldsize, list.size());
    }

    @Override
    public int getItemViewType(int position) {
       return position>=list.size()?FOOTER_VIEW:NORMAL_VIEW;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTER_VIEW&&list.size()!=0) {
             view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress, parent, false);
            return new footerView(view);
        }
         view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new normalView(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
     if (holder instanceof normalView&&getItemViewType(position)!=FOOTER_VIEW) {
           normalView n= (normalView) holder;
            if (!list.get(position).getBookImage().isEmpty()) {
                Glide.with(context).load(list.get(position).getBookImage()).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.drawable.booksstackofthree).into(n.imageView);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size()+1;
    }

    public static class normalView extends RecyclerView.ViewHolder {
        AspectImageView imageView;

        public normalView(View itemView) {
            super(itemView);
            imageView = (AspectImageView) itemView.findViewById(R.id.imageview_item_book);
        }
    }

    public static class footerView extends RecyclerView.ViewHolder {
        public footerView(View itemView) {
            super(itemView);
        }
    }

    public void removeItem(Book item) {
        int indexOfItem = list.indexOf(item);
        if (indexOfItem != -1) {
            list.remove(indexOfItem);
            notifyItemRemoved(indexOfItem);
        }
    }
}
