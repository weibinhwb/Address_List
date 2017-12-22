package com.example.weibinhuang.address_list;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.weibinhuang.address_list.Button.Add_Button;
import com.example.weibinhuang.address_list.Button.Group_Button;
import com.example.weibinhuang.address_list.Button.Setting_Button;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    List<String> addressList = new ArrayList<>();
    public static String[] number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //hide system title
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        //initial Button
        Button add_button = (Button) findViewById(R.id.add_button);
        Button group_button = (Button) findViewById(R.id.group_button);
        Button setting_button = (Button) findViewById(R.id.setting_button);
        //add listener
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Add_Button.class);
                startActivity(intent);
            }
        });
        //group listener
        group_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Group_Button.class);
                startActivity(intent);
            }
        });
        //setting listener
         setting_button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(MainActivity.this,Setting_Button.class);
                 startActivity(intent);
             }
         });

        ListView addressView = (ListView) findViewById(R.id.address_View);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
                addressList);
        addressView.setAdapter(adapter);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_CONTACTS
            }, 1);
        } else {
            readAddressList ();
        }
        addressView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //get phone number
                number = addressList.get(position).split("\n");
                Toast.makeText(MainActivity.this,addressList.get(position),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Call_Activity.class);
                startActivity(intent);
            }

        });
    }
    private void readAddressList (){
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.
            Phone.CONTENT_URI,null,null,null,null);
            if (cursor != null) {
                while (cursor.moveToNext()){
                    //get name
                    String displayName = cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    //get number
                    String number = cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER));
                    addressList.add(displayName + "\n" + number);
                    //follow Chinese character
                    Collections.sort(addressList, new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            Comparator<Object> com = Collator.getInstance(Locale.CHINA);
                            return com.compare(o1,o2);
                        }
                    });
                }
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (cursor != null){
                cursor.close();
            }
        }
    }


    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on {@link #requestPermissions(String[], int)}.
     * <p>
     * <strong>Note:</strong> It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     * </p>
     *
     * @param requestCode  The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link PackageManager#PERMISSION_GRANTED}
     *                     or {@link PackageManager#PERMISSION_DENIED}. Never null.
     * @see #requestPermissions(String[], int)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    readAddressList();
                } else {
                    Toast.makeText(this,"You denied the Permission",Toast.LENGTH_SHORT
                    ).show();
                }
                break;
            default:
        }
    }
}