package com.aliya.fitsys;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends Activity {

    FrameLayout fitFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        fitFrame = findViewById(R.id.fit_frame);
//        fitFrame.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                fitFrame
//            }
//        });
    }

}
