package cn.allen.iweather.ui;

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
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.allen.iweather.R;
import cn.allen.iweather.adapter.RegionAdapter;
import cn.allen.iweather.lifecycle.RegionCityObserver;
import cn.allen.iweather.lifecycle.RegionObserver;
import cn.allen.iweather.utils.Configs;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/16
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class RegionCityActivity extends AppCompatActivity {
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.loading)
    ProgressBar loadingView;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private RegionCityViewModel mViewModel;
    private RegionAdapter mAdapter;
    private List<String> mList = new ArrayList<>();
    private String mParent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region);
        ButterKnife.bind(this);
        getLifecycle().addObserver(new RegionCityObserver(this));
        setSupportActionBar(toolbar);
        mViewModel = ViewModelProviders.of(this).get(RegionCityViewModel.class);

        initData();

        toolbar.setTitle(mParent);
        mAdapter = new RegionAdapter(this, mList, mParent);
        mAdapter.setOnItemClickListener(new RegionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        getProvinces();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent == null) return;
        mParent = intent.getStringExtra(Configs.KEY_PARENT);
    }

    private void getProvinces() {
        if (TextUtils.isEmpty(mParent)) return;
        showLoading();
        mViewModel.getCities(mParent).observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> provinces) {
                if (provinces != null && provinces.size() > 0) {
                    mList.clear();
                    mList.addAll(provinces);
                    mAdapter.notifyDataSetChanged();
                }
                hideLoading();
            }
        });
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
