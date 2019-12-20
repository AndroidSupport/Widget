package com.uniquext.android.sample.widget;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.uniquext.widget.loopplayer.TextSwitchView;
import com.uniquext.widget.loopplayer.TextTest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        TextSwitchView textSwitchView = findViewById(R.id.switcher);
//        textSwitchView.setResources(autoRes);
//        textSwitchView.setTextStillTime(3000);

    }
}
