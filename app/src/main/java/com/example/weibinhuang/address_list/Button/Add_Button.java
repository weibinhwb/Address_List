package com.example.weibinhuang.address_list.Button;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weibinhuang.address_list.MainActivity;
import com.example.weibinhuang.address_list.R;

import java.util.ArrayList;
import java.util.List;

public class Add_Button extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__button);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }

        Button back_button = (Button) findViewById(R.id.back);
        Button edit_button = (Button) findViewById(R.id.yes);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Add_Button.this, MainActivity.class);
                startActivity(intent);
            }
        });
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText name_1 = (EditText) findViewById(R.id.name);
                EditText phone_1 = (EditText) findViewById(R.id.phone);
                EditText email_1 = (EditText) findViewById(R.id.email);

                String name_2 = name_1.getText().toString();
                String phone_2 = phone_1.getText().toString();
                String email_2 = email_1.getText().toString();
                if ((name_2.equals("")) && (phone_2.equals("")) && (email_2.equals(""))){
                    Toast.makeText(Add_Button.this,"Sorry,Add Nothing",Toast.LENGTH_SHORT).show();
                    onRestart();
                }else {
                    addContact(name_2,phone_2,email_2);
                    Intent intent = new Intent(Add_Button.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    public void addContact(String name,String phoneNumber,String email){
        ContentValues values = new ContentValues();
        Uri rawContactUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI,values);
        long rawContactId = ContentUris.parseId(rawContactUri);
        values.clear();

        values.put(ContactsContract.Data.RAW_CONTACT_ID,rawContactId);
        values.put(ContactsContract.Data.MIMETYPE,ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,name);
        getContentResolver().insert(ContactsContract.Data.CONTENT_URI,values);
        values.clear();

        values.put(ContactsContract.Data.RAW_CONTACT_ID,rawContactId);
        values.put(ContactsContract.Data.MIMETYPE,ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER,phoneNumber);
        values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE);
        getContentResolver().insert(ContactsContract.Data.CONTENT_URI,values);
        values.clear();

        values.put(ContactsContract.Data.RAW_CONTACT_ID,rawContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.Email.DATA,email);
        values.put(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK);
        getContentResolver().insert(ContactsContract.Data.CONTENT_URI,values);
        values.clear();

        Toast.makeText(this,"Add Successfully",Toast.LENGTH_SHORT).show();
    }
}
