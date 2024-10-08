package com.example.addressbooksystem;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.addressbooksystem.activity.AddActivity;
import com.example.addressbooksystem.adapter.PeoAdapter;
import com.example.addressbooksystem.bean.PeoBean;
import com.example.addressbooksystem.dao.PeoDao;
import com.example.addressbooksystem.until.DBUntil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBUntil.db = new DBUntil(this).getWritableDatabase();
        List<PeoBean> result = PeoDao.getAllPeo();
        final ListView listView = (ListView) findViewById(R.id.book_list);
        if (result.size() == 0) {
            listView.setAdapter((ListAdapter) null);
        } else {
            result.sort(new Comparator<PeoBean>() {
                public int compare(PeoBean peoBean, PeoBean t1) {
                    if (peoBean.getBeginZ().equals("#") || t1.getBeginZ().equals("#")) {
                        return 1;
                    }
                    return peoBean.getBeginZ().compareTo(t1.getBeginZ());
                }
            });
            listView.setAdapter((ListAdapter) new PeoAdapter(this, result));
        }
        ((FloatingActionButton) findViewById(R.id.add)).setOnClickListener(v -> {
                MainActivity.this.startActivity(new Intent(MainActivity.this, AddActivity.class));
        });
        final EditText id = (EditText) findViewById(R.id.search_id);
        id.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                List<PeoBean> temp;
                listView.setAdapter((ListAdapter) null);
                String title = id.getText().toString();
                if (title.isEmpty()) {
                    temp = PeoDao.getAllPeo();
                } else {
                    temp = PeoDao.getAllPeo(title);
                }
                temp.sort(new Comparator<PeoBean>() {
                    public int compare(PeoBean peoBean, PeoBean t1) {
                        if (peoBean.getBeginZ().equals("#") || t1.getBeginZ().equals("#")) {
                            return 1;
                        }
                        return peoBean.getBeginZ().compareTo(t1.getBeginZ());
                    }
                });
                listView.setAdapter((ListAdapter) new PeoAdapter(MainActivity.this, temp));
                return false;
            }
        });
        id.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void afterTextChanged(Editable editable) {
                List<PeoBean> temp;
                listView.setAdapter((ListAdapter) null);
                String title = id.getText().toString();
                if (title.isEmpty()) {
                    temp = PeoDao.getAllPeo();
                } else {
                    temp = PeoDao.getAllPeo(title);
                }
                temp.sort(new Comparator<PeoBean>() {
                    public int compare(PeoBean peoBean, PeoBean t1) {
                        if (peoBean.getBeginZ().equals("#") || t1.getBeginZ().equals("#")) {
                            return 1;
                        }
                        return peoBean.getBeginZ().compareTo(t1.getBeginZ());
                    }
                });
                listView.setAdapter((ListAdapter) new PeoAdapter(MainActivity.this, temp));
            }
        });
    }
}