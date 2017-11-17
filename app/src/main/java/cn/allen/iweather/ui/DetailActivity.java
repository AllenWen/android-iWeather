package cn.allen.iweather.ui;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.allen.iweather.R;
import cn.allen.iweather.lifecycle.DetailObserver;
import cn.allen.iweather.utils.Configs;
import cn.allen.iweather.utils.ToastUtils;
import cn.allen.iweather.webservice.ApiResponse;
import cn.allen.iweather.webservice.entity.BaseWrapperEntity;
import cn.allen.iweather.webservice.entity.LifeSuggestEntity;
import cn.allen.iweather.webservice.entity.LocationEntity;
import cn.allen.iweather.webservice.entity.WeatherDailyEntity;
import cn.allen.iweather.webservice.entity.WeatherNowEntity;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/17
 * Email: wenxueguo@medlinker.com
 * Description:天气详情
 */

public class DetailActivity extends AppCompatActivity implements LifecycleOwner {
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.desc)
    TextView desc;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.car_wash)
    TextView car_wash;
    @BindView(R.id.dressing)
    TextView dressing;
    @BindView(R.id.flu)
    TextView flu;
    @BindView(R.id.sport)
    TextView sport;
    @BindView(R.id.travel)
    TextView travel;
    @BindView(R.id.uv)
    TextView uv;

    private DetailViewModel mViewModel;
    private WeatherNowEntity mData;
    private LocationEntity mLocation;
    private int mUpdateCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        getLifecycle().addObserver(new DetailObserver(this));
        mViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        initData();
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        swipeRefreshLayout.setDistanceToTriggerSync(300);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        loadData();
    }


    private void loadData() {
        mUpdateCount = 0;
        swipeRefreshLayout.setRefreshing(true);
        mViewModel.life(mLocation.getId()).observe(this, new Observer<ApiResponse<BaseWrapperEntity<LifeSuggestEntity>>>() {
            @Override
            public void onChanged(@Nullable ApiResponse<BaseWrapperEntity<LifeSuggestEntity>> baseWrapperEntityApiResponse) {
                ++mUpdateCount;
                if (baseWrapperEntityApiResponse != null && baseWrapperEntityApiResponse.isSuccess()) {
                    BaseWrapperEntity<LifeSuggestEntity> wrapperEntity = baseWrapperEntityApiResponse.body;
                    if (wrapperEntity != null) {
                        List<LifeSuggestEntity> results = wrapperEntity.getResults();
                        if (results != null && results.size() > 0) {
                            LifeSuggestEntity lifeSuggestEntity = results.get(0);
                            updateLife(lifeSuggestEntity);
                        } else {
                            ToastUtils.show(DetailActivity.this, R.string.get_life_failed);
                        }
                    } else {
                        ToastUtils.show(DetailActivity.this, R.string.get_life_failed);
                    }
                } else {
                    ToastUtils.show(DetailActivity.this, R.string.get_life_failed);
                }
                stopRefreshing();
            }
        });
        mViewModel.daily(mLocation.getId(), 0, 5).observe(this, new Observer<ApiResponse<BaseWrapperEntity<WeatherDailyEntity>>>() {
            @Override
            public void onChanged(@Nullable ApiResponse<BaseWrapperEntity<WeatherDailyEntity>> baseWrapperEntityApiResponse) {
                ++mUpdateCount;
                stopRefreshing();
            }
        });
    }

    private void updateLife(LifeSuggestEntity lifeSuggestEntity) {
        LifeSuggestEntity.SuggestEntity suggestion = lifeSuggestEntity.getSuggestion();
        if (suggestion == null) return;
        car_wash.setText(suggestion.getCar_washing().getBrief());
        dressing.setText(suggestion.getDressing().getBrief());
        flu.setText(suggestion.getFlu().getBrief());
        sport.setText(suggestion.getSport().getBrief());
        travel.setText(suggestion.getTravel().getBrief());
        uv.setText(suggestion.getUv().getBrief());
    }

    private void stopRefreshing() {
        if (mUpdateCount == 2) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent == null) return;
        mData = intent.getParcelableExtra(Configs.KEY_NOW);
        if (mData == null) return;
        mLocation = mData.getLocation();
        initView();
    }

    private void initView() {
        WeatherNowEntity.NowEntity now = mData.getNow();
        if (now != null) {
            String imgRes = "ic_weather_" + now.getCode();
            int resId = getResources().getIdentifier(imgRes, "mipmap", getPackageName());
            icon.setImageResource(resId);
            desc.setText(getString(R.string.format_desc, now.getText(), now.getTemperature()));
        }
        if (mLocation != null) {
            name.setText(mLocation.getName());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

}
