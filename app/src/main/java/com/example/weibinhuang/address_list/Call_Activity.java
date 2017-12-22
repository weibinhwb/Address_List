package com.example.weibinhuang.address_list;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.example.weibinhuang.address_list.MainActivity.number;
public class Call_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_);
        //hide system title
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Button callButton = (Button) findViewById(R.id.call_button);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Call_Activity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Call_Activity.this,new String[] {Manifest.permission.CALL_PHONE
                    }, 1);
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + number[1].toString()));
                    startActivity(intent);
                }

            }
        });

        Button delete_button = (Button) findViewById(R.id.delete_button);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.delete_button:
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Call_Activity.this);
                    dialog.setTitle("You don't love me?");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("Sorry, I click Wrong!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.setPositiveButton("Sorry, I click Wrong!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();
                    break;
                    default:
                        break;
                }

            }
        });
    }
}
