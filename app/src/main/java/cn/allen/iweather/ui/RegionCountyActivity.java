package cn.allen.iweather.ui;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.allen.iweather.R;
import cn.allen.iweather.adapter.CountyAdapter;
import cn.allen.iweather.lifecycle.RegionCountyObserver;
import cn.allen.iweather.persistence.entity.CityEntity;
import cn.allen.iweather.persistence.entity.FavoriteEntity;
import cn.allen.iweather.utils.Configs;
import cn.allen.iweather.utils.ToastUtils;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/16
 * Email: wenxueguo@medlinker.com
 * Description:选择县
 */

public class RegionCountyActivity extends AppCompatActivity implements LifecycleOwner {
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.loading)
    ProgressBar loadingView;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private RegionCountyViewModel mViewModel;
    private CountyAdapter mAdapter;
    private List<CityEntity> mList = new ArrayList<>();
    private String mParent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region);
        ButterKnife.bind(this);
        getLifecycle().addObserver(new RegionCountyObserver(this));
        setSupportActionBar(toolbar);
        mViewModel = ViewModelProviders.of(this).get(RegionCountyViewModel.class);
        initData();
        toolbar.setTitle(mParent);

        mAdapter = new CountyAdapter(this, mList);
        mAdapter.setOnItemClickListener(new CountyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, ImageView favo, int position) {
                CityEntity entity = mList.get(position);
                final FavoriteEntity favoriteEntity = new FavoriteEntity(entity.id, entity.name_zh, entity.province_zh + "," + entity.country_name);
                mViewModel.insertFavorite(favoriteEntity);
                favo.setSelected(true);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        getCounties();
    }

    private void getCounties() {
        if (TextUtils.isEmpty(mParent)) return;
        showLoading();
        mViewModel.getCounties(mParent).observe(this, new Observer<List<CityEntity>>() {
            @Override
            public void onChanged(@Nullable List<CityEntity> list) {
                if (list != null && list.size() > 0) {
                    mList.clear();
                    mList.addAll(list);
                    mAdapter.notifyDataSetChanged();
                } else {
                    ToastUtils.show(RegionCountyActivity.this, R.string.search_city_failed);
                }
                hideLoading();
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent == null) return;
        mParent = intent.getStringExtra(Configs.KEY_PARENT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    private void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void hideLoading() {
        loadingView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

}
