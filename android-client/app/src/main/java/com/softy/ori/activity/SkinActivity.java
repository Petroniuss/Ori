package com.softy.ori.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.softy.ori.R;
import com.softy.ori.customization.SkinAdapter;
import com.softy.ori.util.ActivityUtils;

import cdflynn.android.library.turn.TurnLayoutManager;
import cdflynn.android.library.turn.TurnLayoutManager.Gravity;
import cdflynn.android.library.turn.TurnLayoutManager.Orientation;

public class SkinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.fullscreen(this);
        setContentView(R.layout.activity_skin);


        final RecyclerView recyclerView = findViewById(R.id.skin_recycler_view);
        final RecyclerView.LayoutManager layoutManager = new TurnLayoutManager(this, Gravity.START,
                Orientation.VERTICAL, 1000, 500, true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new SkinAdapter(this));

    }
}
