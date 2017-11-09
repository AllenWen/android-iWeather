package cn.allen.iweather.ui;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.allen.iweather.R;
import cn.allen.iweather.lifecycle.MainObserver;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/8
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class MainActivity extends AppCompatActivity implements LifecycleOwner {
    private static final String TAG = MainActivity.class.getSimpleName();
    private MainViewModel mViewModel;

    @BindView(R.id.fab)
    FloatingActionButton button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_main);
        ButterKnife.bind(this);
        getLifecycle().addObserver(new MainObserver(this));
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mViewModel.getContinents().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                Log.d(TAG, strings.toString());
            }
        });
    }

    @OnClick(R.id.fab)
    public void onClick() {
        Intent intent = new Intent(this, AddCityActivity.class);
        startActivity(intent);
    }

}
