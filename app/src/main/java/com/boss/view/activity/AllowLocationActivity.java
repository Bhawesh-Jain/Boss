package com.boss.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.boss.R;

public class AllowLocationActivity extends AppCompatActivity {
    TextView AG_allowlocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allow_location);

        Window window = getWindow();
        window.setStatusBarColor(getColor(R.color.white));
        AG_allowlocation = findViewById(R.id.AG_allowlocation);

        AG_allowlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AllowLocationActivity.this, HomeActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                finish();
            }
        });
    }
}