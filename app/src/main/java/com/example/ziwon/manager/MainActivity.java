package com.example.ziwon.manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText partEdt;
    private EditText kwEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startApp(View view) {
        partEdt = findViewById(R.id.input_part_edt);
        kwEdt = findViewById(R.id.input_keyword_edt);

        String participant = partEdt.getText().toString();
        String keyword = kwEdt.getText().toString();
        String appNm = (String) ((Button)view).getText();
        Intent intent = this.getPackageManager().getLaunchIntentForPackage("com.example.dowkk."+appNm);

        intent.putExtra("participant", participant);
        intent.putExtra("keyword", keyword);
        intent.putExtra("appNm", appNm);
        startActivity(intent);
    }
}

