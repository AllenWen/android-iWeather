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
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.allen.iweather.R;
import cn.allen.iweather.adapter.RegionAdapter;
import cn.allen.iweather.lifecycle.RegionObserver;
import cn.allen.iweather.utils.Configs;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/9
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class RegionActivity extends AppCompatActivity implements LifecycleOwner {
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.loading)
    ProgressBar loadingView;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private RegionViewModel mViewModel;
    private RegionAdapter mAdapter;
    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region);
        ButterKnife.bind(this);
        getLifecycle().addObserver(new RegionObserver(this));
        setSupportActionBar(toolbar);
        mViewModel = ViewModelProviders.of(this).get(RegionViewModel.class);

        mAdapter = new RegionAdapter(this, mList, Configs.COUNTRY);
        mAdapter.setOnItemClickListener(new RegionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String province = mList.get(position);
                Intent intent = new Intent(RegionActivity.this, RegionCityActivity.class);
                intent.putExtra(Configs.KEY_PARENT, province);
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        getProvinces();
    }

    private void getProvinces() {
        showLoading();
        mViewModel.getProvinces().observe(this, new Observer<List<String>>() {
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
