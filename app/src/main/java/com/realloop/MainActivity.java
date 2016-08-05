package com.realloop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Auth：yujunyao
 * Since: 2016/8/5 16:52
 * Email：yujunyao@yonglibao.com
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.first);
        list.add(R.drawable.second);
        list.add(R.drawable.third);
        list.add(R.drawable.fourth);

        AdvView advView = (AdvView) findViewById(R.id.adv_view);
        advView.setBanner(list, new AdvView.BannerListener() {
            @Override
            public void backResult() {
                Toast.makeText(MainActivity.this, "adv item click", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
