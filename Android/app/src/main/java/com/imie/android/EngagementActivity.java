package com.imie.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.microsoft.azure.engagement.activity.EngagementFragmentActivity;

public class EngagementActivity extends EngagementFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engagement);
    }


}
