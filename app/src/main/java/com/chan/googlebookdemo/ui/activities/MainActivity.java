package com.chan.googlebookdemo.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.chan.googlebookdemo.GoogleBookDemo;
import com.chan.googlebookdemo.R;
import com.chan.googlebookdemo.data.model.GoogleBook;
import com.chan.googlebookdemo.data.network.ResponseHandler;
import com.chan.googlebookdemo.data.network.RestCaller;
import com.chan.googlebookdemo.interfaces.BookItemClickListener;
import com.chan.googlebookdemo.ui.adapters.BookAdapter;
import com.chan.googlebookdemo.utils.Loading;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ResponseHandler, View.OnClickListener, BookItemClickListener {

    SearchView searchView;
    RecyclerView bookListView;
    BookAdapter bookAdapter;
    Button btnLoadMore;
    ArrayList<GoogleBook> books;
    private int page_num = 0;
    private boolean isLoading = false;
    private final static  int BOOK_REQUEST_CODE = 11111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        setUpAdapter();

        setListener();

        loadBook();
    }

    private void initUI() {
        searchView = findViewById(R.id.searchView);
        bookListView = findViewById(R.id.bookListView);
        btnLoadMore = findViewById(R.id.btnLoadMore);
    }

    private void setListener() {
        btnLoadMore.setOnClickListener(this);
    }

    private void setUpAdapter() {
        books = new ArrayList<>();
        bookAdapter = new BookAdapter(this, books, this);
        bookListView.setLayoutManager(new GridLayoutManager(this,2));
        bookListView.setAdapter(bookAdapter);
    }

    private void loadBook() {
        if(isLoading == false){
            isLoading = true;
            Loading.show(this,false,"Loading...");

            new RestCaller(this, GoogleBookDemo.getRestClient().get_books("book", page_num, 10), BOOK_REQUEST_CODE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLoadMore:
                loadMoreAction();
                break;
        }
    }

    @Override
    public void onSuccess(Call call, Response response, int reqCode) {
        isLoading = false;
        Loading.cancel();
        if(reqCode == BOOK_REQUEST_CODE){
            JsonObject result = (JsonObject) response.body();
            if(result.has("items")){
                JsonArray items = result.get("items").getAsJsonArray();
                for(int i = 0 ; i < items.size() ; i++){
                    JsonObject bookJsonObject = items.get(i).getAsJsonObject();
                    GoogleBook googleBook =  getDataFromJson(bookJsonObject);
                    books.add(googleBook);
                }
                bookAdapter.setBooks(books);
            }
        }
    }

    @Override
    public void onApiCrash(Call call, Throwable t, int reqCode) {
        isLoading = false;
        Loading.cancel();
    }

    public GoogleBook getDataFromJson(JsonObject jsonObject) {
        GoogleBook googleBook = new GoogleBook();
        if(jsonObject.has("id")){
            String id = jsonObject.get("id").getAsString();
            googleBook.setId(id);
        }
        if(jsonObject.has("volumeInfo")) {
            JsonObject volumeInfo = jsonObject.get("volumeInfo").getAsJsonObject();
            if(volumeInfo.has("title")){
                String title = volumeInfo.get("title").getAsString();
                googleBook.setTitle(title);
            }
            if(volumeInfo.has("description")){
                String desc = volumeInfo.get("description").getAsString();
                googleBook.setDesc(desc);
            }
            if(volumeInfo.has("imageLinks")) {
                JsonObject imageLinks = volumeInfo.get("imageLinks").getAsJsonObject();
                if(imageLinks.has("thumbnail")){
                    String thumbnail = imageLinks.get("thumbnail").getAsString();
                    googleBook.setBook_image(thumbnail);
                }
            }
        }

        return googleBook;
    }

    private void loadMoreAction() {
        page_num = page_num + 1;
        loadBook();
    }

    @Override
    public void onItemClick(GoogleBook googleBook) {
        Intent intent = new Intent(this, DetailBookActivity.class);
        intent.putExtra("googleBook", googleBook);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
}