package com.example.weibinhuang.address_list.Button;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.weibinhuang.address_list.R;

public class Group_Button extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group__button);
        //hide system title
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }
}
