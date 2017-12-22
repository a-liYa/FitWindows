package com.aliya.fitsys;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends Activity {

    ViewGroup rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootView = (ViewGroup) findViewById(R.id.root_view);
    }

    public void onClick(View view) {
        Log.e("TAG", "onClick");
        View inflate = LayoutInflater.from(this).inflate(R.layout.layout_item, rootView, false);
        rootView.addView(inflate);
    }

}
