package com.beilin.activity;

import android.view.View;
import android.widget.TextView;

import com.beilin.R;
import com.dd.CircularProgressButton;

public class MainActivity extends BaseActivity {
    private TextView tv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        final CircularProgressButton circularButton1 = (CircularProgressButton) findViewById(R.id.circularButton1);
        circularButton1.setIndeterminateProgressMode(true);
        circularButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circularButton1.getProgress() == 0) {
                    circularButton1.setProgress(50);
                } else if (circularButton1.getProgress() == 100) {
                    circularButton1.setProgress(0);
                } else {
                    circularButton1.setProgress(100);
                }
            }
        });
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

    }
}
