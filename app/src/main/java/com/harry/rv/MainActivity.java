package com.harry.rv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.harry.rv.view.RingView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RingView rv1=(RingView) findViewById(R.id.rv1);
        RingView rv2=(RingView) findViewById(R.id.rv2);
        RingView rv3=(RingView) findViewById(R.id.rv3);
        rv1.setProgress(80);
        rv2.setProgress(130);
        rv3.setProgress(220);
    }
}
