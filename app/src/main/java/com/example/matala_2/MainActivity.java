package com.example.matala_2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
     Button btn_brthsday,btn_save;
     EditText et_name;
     EditText et_family_name;
     EditText et_city;
    RadioGroup radioGroup;
    RadioButton rb_male, rb_female;


    private String name;
     private  String familyName;
     private String city;
     private String birthday;
     DatePickerDialog.OnDateSetListener setListener;
    SharedPreferences sp;       //file
    SharedPreferences.Editor editor;     //sharedpreference editortool
    final String KEY_NAME = "key_name";
    final String KEY_FAMILYNAME = "key_familyname";
    final String KEY_CITY = "key_city";
    final String KEY_BIRTHDAY = "key_birthday";
    final String KEY_MALE = "key_male";
    final String KEY_FEMALE = "key_female";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        et_name = findViewById(R.id.et_name);
        et_family_name = findViewById(R.id.et_family_name);
        et_city = findViewById(R.id.et_city);
        btn_brthsday = findViewById(R.id.btn_brthsday);
        radioGroup = findViewById(R.id.radiogroup);
        rb_male = findViewById(R.id.rb_male);
        rb_female = findViewById(R.id.rb_female);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);



        sp = getSharedPreferences("users_sp_file", Context.MODE_PRIVATE);
        //sex = sp.getInt("sex", 3);
        editor = sp.edit();

        btn_brthsday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this, setListener, year, month, day);
                datePickerDialog.getWindow();
                datePickerDialog.show();


            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                btn_brthsday.setText(date);
            }
        };

        updateUserData();
         loadUserData();

    }

    @Override
    public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Save");
            builder.setMessage("Want to keep all your data?");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    saveUserData();

                    Toast.makeText(MainActivity.this, "data saved", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
    }
    public void saveUserData(){

        name = et_name.getText().toString();
        familyName = et_family_name.getText().toString();
         city = et_city.getText().toString();
         birthday = btn_brthsday.getText().toString();

        editor.putString(KEY_NAME, name);
        editor.putString(KEY_FAMILYNAME, familyName);
        editor.putString(KEY_CITY, city);
        editor.putString(KEY_BIRTHDAY, birthday);
        editor.putBoolean(KEY_MALE, rb_male.isChecked());
        editor.putBoolean(KEY_FEMALE, rb_female.isChecked());
        editor.commit();


    }
    public void loadUserData(){

        if(!sp.contains(KEY_NAME))
            Toast.makeText(this,
                    "no user saved!",
                    Toast.LENGTH_SHORT).show();
        else{
            et_name.setText(sp.getString(KEY_NAME, ""));
            et_family_name.setText(sp.getString(KEY_FAMILYNAME, ""));
            et_city.setText(sp.getString(KEY_CITY, ""));
            btn_brthsday.setText(sp.getString(KEY_BIRTHDAY, ""));
            if((sp.contains(KEY_MALE)) && (sp.contains(KEY_FEMALE))){
                rb_male.setChecked(sp.getBoolean(KEY_MALE, false));
                rb_female.setChecked(sp.getBoolean(KEY_FEMALE, false));
            }
        }
    }



    public void updateUserData(){
         et_name.setText(name);
         et_family_name.setText(familyName);
         et_city.setText(city);
         btn_brthsday.setText(birthday);

    }
    }






