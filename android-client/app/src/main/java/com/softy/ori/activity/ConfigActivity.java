package com.softy.ori.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.softy.ori.R;
import com.softy.ori.customization.Config;
import com.softy.ori.util.ActivityUtils;

public class ConfigActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.fullscreen(this);
        setContentView(R.layout.activity_config);

        final EditText nickEditText = findViewById(R.id.nick_edit_text);

        nickEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Config.getInstance(ConfigActivity.this).setName(s.toString());
            }
        });
    }
}
