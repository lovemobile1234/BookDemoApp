package com.chan.googlebookdemo.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chan.googlebookdemo.R;
import com.chan.googlebookdemo.data.model.GoogleBook;
import com.chan.googlebookdemo.interfaces.BookItemClickListener;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder>{

    ArrayList<GoogleBook> books;
    Context context;
    BookItemClickListener bookItemClickListener;
    public BookAdapter(Context context, ArrayList<GoogleBook> books,BookItemClickListener bookItemClickListener) {
        this.context = context;
        this.books = books;
        this.bookItemClickListener = bookItemClickListener;
    }

    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book,parent,false);
        BookAdapter.ViewHolder viewHolder = new BookAdapter.ViewHolder(v);
        return viewHolder;
    }

    public void setBooks(ArrayList<GoogleBook> books){
        this.books = books;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(BookAdapter.ViewHolder holder, int position) {
        GoogleBook googleBook = books.get(position);
        holder.txtBook.setText(googleBook.getTitle());
        Glide.with(context).load(googleBook.getBook_image()).centerCrop().into(holder.imgBook);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookItemClickListener.onItemClick(googleBook);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView txtBook;
        protected ImageView imgBook;
        public ViewHolder(View itemView) {
            super(itemView);
            txtBook = itemView.findViewById(R.id.txtBookName);
            imgBook = itemView.findViewById(R.id.imgBook);
        }
    }
}
