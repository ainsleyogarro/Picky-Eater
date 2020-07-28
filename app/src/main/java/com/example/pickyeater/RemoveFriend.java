package com.example.pickyeater;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.PopupWindow;

import androidx.annotation.Nullable;

public class RemoveFriend extends Activity {
    PopupWindow popUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //getActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.removefriend_popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = (int) (dm.widthPixels * .8);
        int length = (int) (dm.heightPixels * .6);

        getWindow().setLayout(width, length);
    }
}
