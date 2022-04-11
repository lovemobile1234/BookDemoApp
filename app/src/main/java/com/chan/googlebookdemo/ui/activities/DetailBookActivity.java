package com.chan.googlebookdemo.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chan.googlebookdemo.R;
import com.chan.googlebookdemo.data.model.GoogleBook;

public class DetailBookActivity extends AppCompatActivity implements View.OnClickListener{

    GoogleBook googleBook;
    ImageView imgBack, imgBook;
    TextView txtTitle, txtDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_book);

        initUI();

        setListener();

        loadData();
    }

    private void initUI () {
        imgBack = findViewById(R.id.imgBack);
        imgBook = findViewById(R.id.imgBook);
        txtTitle = findViewById(R.id.txtTitle);
        txtDesc = findViewById(R.id.txtDesc);
    }

    private void setListener() {
        imgBack.setOnClickListener(this);
    }

    private void loadData() {
        googleBook = (GoogleBook)getIntent().getSerializableExtra("googleBook");
        txtTitle.setText(googleBook.getTitle());
        txtDesc.setText(googleBook.getDesc());
        Glide.with(this).load(googleBook.getBook_image()).centerInside().into(imgBook);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgBack:
                back_Action();
                break;
        }
    }

    private void back_Action() {
        finish();
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
    }
}