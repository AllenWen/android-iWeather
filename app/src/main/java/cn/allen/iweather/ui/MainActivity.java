package cn.allen.iweather.ui;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.allen.iweather.R;
import cn.allen.iweather.adapter.HomeAdapter;
import cn.allen.iweather.lifecycle.MainObserver;
import cn.allen.iweather.persistence.entity.FavoriteEntity;
import cn.allen.iweather.webservice.ApiResponse;
import cn.allen.iweather.webservice.entity.BaseWrapperEntity;
import cn.allen.iweather.webservice.entity.LocationEntity;
import cn.allen.iweather.webservice.entity.WeatherNowEntity;

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
    @BindView(R.id.fam)
    FloatingActionsMenu fam;
    @BindView(R.id.fab_search)
    FloatingActionButton fab_search;
    @BindView(R.id.fab_local)
    FloatingActionButton fab_local;

    private MainViewModel mViewModel;
    private HomeAdapter mAdapter;
    private List<WeatherNowEntity> mList = new ArrayList<>();
    private int mUpdateCount;

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
                loadFavo();
            }
        });
        mAdapter = new HomeAdapter(this, mList);
        mAdapter.setFooterView(R.layout.item_header);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        loadFavo();
    }

    private void loadFavo() {
        mUpdateCount = 0;
        swipeRefreshLayout.setRefreshing(true);
        mViewModel.loadFavorites().observe(this, new Observer<List<FavoriteEntity>>() {
            @Override
            public void onChanged(@Nullable List<FavoriteEntity> list) {
                if (list != null && list.size() > 0) {
                    mList.clear();
                    for (FavoriteEntity entity : list) {
                        WeatherNowEntity weatherNowEntity = new WeatherNowEntity(new LocationEntity(entity.id, entity.name, entity.path), null, "");
                        mList.add(weatherNowEntity);
                    }
                    mAdapter.notifyDataSetChanged();
                    loadWeather(list);
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void loadWeather(List<FavoriteEntity> list) {
        for (FavoriteEntity entity : list) {
            mViewModel.now(entity.id).observe(this, new Observer<ApiResponse<BaseWrapperEntity<WeatherNowEntity>>>() {
                @Override
                public void onChanged(@Nullable ApiResponse<BaseWrapperEntity<WeatherNowEntity>> baseWrapperEntityApiResponse) {
                    if (baseWrapperEntityApiResponse != null && baseWrapperEntityApiResponse.isSuccess()) {
                        BaseWrapperEntity<WeatherNowEntity> wrapperEntity = baseWrapperEntityApiResponse.body;
                        if (wrapperEntity != null) {
                            WeatherNowEntity weatherNowEntity = wrapperEntity.getResults().get(0);
                            updateWeather(weatherNowEntity);
                        } else {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    } else {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            });
        }
    }

    private void updateWeather(WeatherNowEntity weatherNowEntity) {
        for (WeatherNowEntity nowEntity : mList) {
            if (nowEntity.getLocation().getId().equals(weatherNowEntity.getLocation().getId())) {
                nowEntity.setNow(weatherNowEntity.getNow());
                nowEntity.setLast_update(weatherNowEntity.getLast_update());
                ++mUpdateCount;
                break;
            }
        }
        mAdapter.notifyDataSetChanged();
        if (mUpdateCount == mList.size()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @OnClick({R.id.fab_search, R.id.fab_local})
    public void onClick(View view) {
        fam.collapse();
        if (view.getId() == R.id.fab_search) {
            Intent intent = new Intent(this, AddRemoteActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.fab_local) {
            Intent intent = new Intent(this, AddLocalActivity.class);
            startActivity(intent);
        }
    }

}
