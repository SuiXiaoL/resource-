package com.example.addressbooksystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.addressbooksystem.R;
import com.example.addressbooksystem.dao.PeoDao;

public class AddActivity extends AppCompatActivity {

    private Toolbar toolbarAdd;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
        toolbarAdd.setOnClickListener(v -> {
            finish();
        });
        final TextView name = (TextView) findViewById(R.id.add_name);
        final TextView phone = (TextView) findViewById(R.id.add_phone);
        final RadioButton man = (RadioButton) findViewById(R.id.add_man);
        RadioButton radioButton = (RadioButton) findViewById(R.id.add_woman);
        man.setChecked(true);
        final TextView bz = (TextView) findViewById(R.id.add_bz);
        ((Button) findViewById(R.id.add_button)).setOnClickListener(v -> {
                String nameT = name.getText().toString().trim();
                String phoneT = phone.getText().toString().trim();
                String bzT = bz.getText().toString().trim();
                if (nameT.isEmpty()) {
                    Toast.makeText(AddActivity.this, "请输入姓名",  Toast.LENGTH_SHORT).show();
                } else if (phoneT.isEmpty()) {
                    Toast.makeText(AddActivity.this, "请输入手机号",  Toast.LENGTH_SHORT).show();
                } else if (bzT.isEmpty()) {
                    Toast.makeText(AddActivity.this, "请输入备注",  Toast.LENGTH_SHORT).show();
                } else {
                    String sex = "女";
                    if (man.isChecked()) {
                        sex = "男";
                    }
                    PeoDao.savePeo(nameT, phoneT, sex, bzT);
                    Toast.makeText(AddActivity.this, "添加成功",  Toast.LENGTH_SHORT).show();
                }
        });
        initView();
    }

    private void initView() {
        toolbarAdd = (Toolbar) findViewById(R.id.toolbar_add);
    }
}