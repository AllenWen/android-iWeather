package cn.allen.iweather.ui;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.allen.iweather.R;
import cn.allen.iweather.adapter.AddCityAdapter;
import cn.allen.iweather.lifecycle.AddCityObserver;
import cn.allen.iweather.persistence.entity.CityEntity;
import cn.allen.iweather.persistence.entity.FavoriteEntity;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/9
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class AddCityActivity extends AppCompatActivity implements LifecycleOwner {
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private AddCityViewModel mViewModel;
    private AddCityAdapter mAdapter;
    private List<CityEntity> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcity);
        ButterKnife.bind(this);
        getLifecycle().addObserver(new AddCityObserver(this));
        setSupportActionBar(toolbar);
        mViewModel = ViewModelProviders.of(this).get(AddCityViewModel.class);

        mAdapter = new AddCityAdapter(this, mList);
        mAdapter.setOnItemClickListener(new AddCityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                CityEntity entity = mList.get(position);
                String path = entity.province_zh + "," + entity.country_name;
                final FavoriteEntity favoriteEntity = new FavoriteEntity(entity.id, entity.name_zh, path);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mViewModel.insertFavorite(favoriteEntity);
                    }
                }).start();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_city, menu);
        MenuItem item = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchCity(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchCity(newText);
                return false;
            }
        });
        return true;
    }

    private void searchCity(String key) {
        mViewModel.searchCity(key).observe(AddCityActivity.this, new Observer<List<CityEntity>>() {
            @Override
            public void onChanged(@Nullable List<CityEntity> cityEntities) {
                if (cityEntities != null && cityEntities.size() > 0) {
                    mList.clear();
                    mList.addAll(cityEntities);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
