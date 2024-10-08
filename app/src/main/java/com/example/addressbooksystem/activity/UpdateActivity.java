package com.example.addressbooksystem.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.addressbooksystem.R;
import com.example.addressbooksystem.bean.PeoBean;
import com.example.addressbooksystem.dao.PeoDao;

public class UpdateActivity extends AppCompatActivity {

    private Toolbar toolbarUp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        initView();
        toolbarUp.setOnClickListener(v -> {
            finish();
        });
        final String id = getIntent().getStringExtra("id");
        PeoBean peo = PeoDao.getOnePeo(id);
        final TextView name = (TextView) findViewById(R.id.up_name);
        final TextView phone = (TextView) findViewById(R.id.up_phone);
        final RadioButton man = (RadioButton) findViewById(R.id.man);
        RadioButton woman = (RadioButton) findViewById(R.id.woman);
        final TextView bz = (TextView) findViewById(R.id.up_bz);
        name.setText(peo.getName());
        phone.setText(peo.getNum());
        if (peo.getSex().equals("男")) {
            man.setChecked(true);
        } else {
            woman.setChecked(true);
        }
        bz.setText(peo.getRemark());
        ((Button) findViewById(R.id.up_button)).setOnClickListener(v -> {
                String nameT = name.getText().toString().trim();
                String phoneT = phone.getText().toString().trim();
                String bzT = bz.getText().toString().trim();

                if (nameT.isEmpty()) {
                    Toast.makeText(UpdateActivity.this, "请输入姓名", Toast.LENGTH_SHORT).show();
                } else if (phoneT.isEmpty()) {
                    Toast.makeText(UpdateActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                } else if (bzT.isEmpty()) {
                    Toast.makeText(UpdateActivity.this, "请输入备注", Toast.LENGTH_SHORT).show();
                } else {
                    String sex = "女";
                    if (man.isChecked()) {
                        sex = "男";
                    }
                    PeoDao.updatePeo(nameT, phoneT, sex, bzT, id);
                    Toast.makeText(UpdateActivity.this, "更改成功", Toast.LENGTH_SHORT).show();
                }
        });

        initView();
    }

    private void initView() {
        toolbarUp = (Toolbar) findViewById(R.id.toolbar_up);
    }
}