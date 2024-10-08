package com.example.addressbooksystem.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.addressbooksystem.MainActivity;
import com.example.addressbooksystem.R;
import com.example.addressbooksystem.bean.PeoBean;
import com.example.addressbooksystem.dao.PeoDao;

public class DetailsActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        final String id = getIntent().getStringExtra("id");
        final TextView num = (TextView) findViewById(R.id.de_phone);
        ((Button) findViewById(R.id.de_da_phone)).setOnClickListener(v -> {
                if (ContextCompat.checkSelfPermission(DetailsActivity.this, "android.permission.CALL_PHONE") == 0) {
                    Log.d("AAA", "有权限");
                    DetailsActivity.this.makePhoneCall(num.getText().toString().trim());
                    return;
                }
                ActivityCompat.requestPermissions(DetailsActivity.this, new String[]{"android.permission.CALL_PHONE"}, 1);
                Log.d("AAA", "无权限");
        });
        ((Button) findViewById(R.id.de_da_message)).setOnClickListener(v -> {
                Intent intent = new Intent("android.intent.action.SENDTO");
                intent.setData(Uri.parse("smsto:" + Uri.encode(num.getText().toString().trim())));
                DetailsActivity.this.startActivity(intent);
        });
        ((Button) findViewById(R.id.de_back)).setOnClickListener(v -> {
                DetailsActivity.this.startActivity(new Intent(DetailsActivity.this, MainActivity.class));
        });
        ((Button) findViewById(R.id.de_del)).setOnClickListener(v -> {
                PeoDao.delPeo(id);
                Toast.makeText(DetailsActivity.this, "删除成功",  Toast.LENGTH_SHORT).show();
                DetailsActivity.this.startActivity(new Intent(DetailsActivity.this, MainActivity.class));
        });
        ((Button) findViewById(R.id.de_up)).setOnClickListener(v -> {
                Intent intent = new Intent(DetailsActivity.this, UpdateActivity.class);
                intent.putExtra("id", id);
                DetailsActivity.this.startActivity(intent);
        });
        PeoBean peo = PeoDao.getOnePeo(id);
        ImageView tx = (ImageView) findViewById(R.id.de_tx);
        if (peo.getSex().equals("男")) {
            tx.setImageResource(R.drawable.peo_man);
        } else {
            tx.setImageResource(R.drawable.peo_woman);
        }
        ((TextView) findViewById(R.id.de_name)).setText(peo.getName());
        ((TextView) findViewById(R.id.de_phone)).setText(peo.getNum());
        ((TextView) findViewById(R.id.de_sex)).setText("性别:" + peo.getSex());
        ((TextView) findViewById(R.id.de_remark)).setText(peo.getRemark());
    }

    private void makePhoneCall(String num) {
        Intent callIntent = new Intent("android.intent.action.CALL");
        callIntent.setData(Uri.parse("tel:" + num));
        startActivity(callIntent);
    }
}