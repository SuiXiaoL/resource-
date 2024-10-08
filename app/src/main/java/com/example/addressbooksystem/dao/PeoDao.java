package com.example.addressbooksystem.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.addressbooksystem.bean.PeoBean;
import com.example.addressbooksystem.until.DBUntil;
import com.hankcs.hanlp.HanLP;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PeoDao {

    public static SQLiteDatabase db = DBUntil.db;
    static {
        db.execSQL("PRAGMA encoding='UTF-8';");
    }
    public static List<PeoBean> getAllPeo() {
        List<PeoBean> list = new ArrayList<>();
        Cursor res = db.rawQuery("select * from d_peo", null);
        while (res.moveToNext()) {
            PeoBean peoBean = new PeoBean(res.getString(0), res.getString(1), res.getString(2), res.getString(3), "");
            String first = res.getString(1).substring(0, 1);
            if (first.matches("^[a-zA-Z]*")) {
                peoBean.setBeginZ(first.toUpperCase());
            } else if (Pattern.compile("[\\u4e00-\\u9fa5]+").matcher(first).find()) {
                peoBean.setBeginZ(HanLP.convertToPinyinString(first, " ", false).substring(0, 1).toUpperCase());
            } else {
                peoBean.setBeginZ("#");
            }
            list.add(peoBean);
        }
        return list;
    }

    public static void delPeo(String id) {
        db.execSQL("DELETE FROM  d_peo where s_id=?", new String[]{id});
    }

    public static PeoBean getOnePeo(String id) {
        Cursor res = db.rawQuery("select * from d_peo where s_id=?", new String[]{id});
        PeoBean peoBean = null;
        while (res.moveToNext()) {
            peoBean = new PeoBean(res.getString(0), res.getString(1), res.getString(2), res.getString(3), res.getString(4));
        }
        return peoBean;
    }

    public static void updatePeo(String... id) {
        db.execSQL("update  d_peo set s_name=?,s_phone=?,s_sex=?,s_remark=? where s_id=?", id);
    }

    public static void savePeo(String... id) {
        db.execSQL("INSERT INTO d_peo (s_name,s_phone,s_sex,s_remark) VALUES(?,?,?,?)", id);
    }

    public static List<PeoBean> getAllPeo(String id) {
        List<PeoBean> list = new ArrayList<>();
        Cursor res = db.rawQuery("SELECT * FROM d_peo WHERE s_phone LIKE '%" + id + "%'  OR s_name LIKE '%" + id + "%'", null);
        while (res.moveToNext()) {
            PeoBean peoBean = new PeoBean(res.getString(0), res.getString(1), res.getString(2), res.getString(3), "");
            String first = res.getString(1).substring(0, 1);
            if (first.matches("^[a-zA-Z]*")) {
                peoBean.setBeginZ(first.toUpperCase());
            } else if (Pattern.compile("[\\u4e00-\\u9fa5]+").matcher(first).find()) {
                peoBean.setBeginZ(HanLP.convertToPinyinString(first, " ", false).substring(0, 1).toUpperCase());
            } else {
                peoBean.setBeginZ("#");
            }
            list.add(peoBean);
        }
        return list;
    }
}