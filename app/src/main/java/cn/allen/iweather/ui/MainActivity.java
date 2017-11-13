package cn.allen.iweather.ui;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.allen.iweather.R;
import cn.allen.iweather.adapter.HomeAdapter;
import cn.allen.iweather.lifecycle.MainObserver;
import cn.allen.iweather.persistence.entity.FavoriteEntity;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/8
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class MainActivity extends AppCompatActivity implements LifecycleOwner {
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton button;

    private MainViewModel mViewModel;
    private HomeAdapter mAdapter;
    private List<FavoriteEntity> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getLifecycle().addObserver(new MainObserver(this));
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        swipeRefreshLayout.setDistanceToTriggerSync(300);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        mAdapter = new HomeAdapter(this, mList);
//        mAdapter.setFooterView(LayoutInflater.from(this).inflate(R.layout.view_header, null));
        mAdapter.setHeaderView(LayoutInflater.from(this).inflate(R.layout.view_header, null));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        getData();
    }

    private void getData() {
        swipeRefreshLayout.setRefreshing(true);
        mViewModel.loadFavorites().observe(this, new Observer<List<FavoriteEntity>>() {
            @Override
            public void onChanged(@Nullable List<FavoriteEntity> list) {
                if (list == null || list.size() == 0) {
                    //显示为空
                } else {
                    Log.d(TAG,"loadFavorites: "+list.toString());
                    mList.clear();
                    mList.addAll(list);
                    mAdapter.notifyDataSetChanged();
                }
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    @OnClick(R.id.fab)
    public void onClick() {
        Intent intent = new Intent(this, AddCityActivity.class);
        startActivity(intent);
    }

}
