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
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.allen.iweather.R;
import cn.allen.iweather.adapter.CountyAdapter;
import cn.allen.iweather.adapter.RegionAdapter;
import cn.allen.iweather.lifecycle.RegionCityObserver;
import cn.allen.iweather.persistence.entity.CityEntity;
import cn.allen.iweather.persistence.entity.FavoriteEntity;
import cn.allen.iweather.utils.Configs;
import cn.allen.iweather.utils.ToastUtils;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/16
 * Email: wenxueguo@medlinker.com
 * Description:选择市或者直辖市
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
    private RegionAdapter mRegionAdapter;
    private CountyAdapter mCountyAdapter;
    private List<String> mRegionList = new ArrayList<>();
    private List<CityEntity> mCountyList = new ArrayList<>();
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

        mRegionAdapter = new RegionAdapter(this, mRegionList, mParent);
        mRegionAdapter.setOnItemClickListener(new RegionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String city = mRegionList.get(position);
                Intent intent = new Intent(RegionCityActivity.this, RegionCountyActivity.class);
                intent.putExtra(Configs.KEY_PARENT, city);
                startActivity(intent);
            }
        });
        mCountyAdapter = new CountyAdapter(this, mCountyList);
        mCountyAdapter.setOnItemClickListener(new CountyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, ImageView favo, int position) {
                CityEntity entity = mCountyList.get(position);
                final FavoriteEntity favoriteEntity = new FavoriteEntity(entity.id, entity.name_zh, entity.province_zh + "," + entity.country_name);
                mViewModel.insertFavorite(favoriteEntity);
                favo.setSelected(true);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mRegionAdapter);

        getCities();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent == null) return;
        mParent = intent.getStringExtra(Configs.KEY_PARENT);
    }

    private void getCities() {
        if (TextUtils.isEmpty(mParent)) return;
        showLoading();
        mViewModel.getCities(mParent).observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> cities) {
                if (cities != null && cities.size() > 0) {
                    if (cities.size() == 1 && TextUtils.isEmpty(cities.get(0))) {//直辖市
                        getMunicipalCounties(mParent);
                    } else {
                        mRegionList.clear();
                        mRegionList.addAll(cities);
                        mRegionAdapter.notifyDataSetChanged();
                        hideLoading();
                    }
                } else {
                    hideLoading();
                    ToastUtils.show(RegionCityActivity.this, R.string.search_city_failed);
                }

            }
        });
    }

    private void getMunicipalCounties(String province) {
        mViewModel.getMunicipalCounties(province).observe(this, new Observer<List<CityEntity>>() {
            @Override
            public void onChanged(@Nullable List<CityEntity> cityEntities) {
                if (cityEntities != null && cityEntities.size() > 0) {
                    mCountyList.clear();
                    mCountyList.addAll(cityEntities);
                    recyclerView.setAdapter(mCountyAdapter);
                } else {
                    ToastUtils.show(RegionCityActivity.this, R.string.search_city_failed);
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
