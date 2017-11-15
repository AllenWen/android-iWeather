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
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.allen.iweather.R;
import cn.allen.iweather.adapter.CityAdapter;
import cn.allen.iweather.lifecycle.AddLocalObserver;
import cn.allen.iweather.persistence.entity.CityEntity;
import cn.allen.iweather.persistence.entity.FavoriteEntity;
import cn.allen.iweather.webservice.entity.LocationEntity;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/9
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class AddLocalActivity extends AppCompatActivity implements LifecycleOwner {
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private AddLocalViewModel mViewModel;
    private CityAdapter mAdapter;
    private List<LocationEntity> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_local);
        ButterKnife.bind(this);
        getLifecycle().addObserver(new AddLocalObserver(this));
        setSupportActionBar(toolbar);
        mViewModel = ViewModelProviders.of(this).get(AddLocalViewModel.class);

        mAdapter = new CityAdapter(this, mList);
        mAdapter.setOnItemClickListener(new CityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, ImageView favo, int position) {
                LocationEntity entity = mList.get(position);
                final FavoriteEntity favoriteEntity = new FavoriteEntity(entity.getId(), entity.getName(), entity.getPath());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mViewModel.insertFavorite(favoriteEntity);
                    }
                }).start();
                entity.setFavorite(true);
                favo.setSelected(true);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_local, menu);
        MenuItem item = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint(getString(R.string.local_search_hint));
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
        mViewModel.searchCity(key).observe(AddLocalActivity.this, new Observer<List<CityEntity>>() {
            @Override
            public void onChanged(@Nullable List<CityEntity> cityEntities) {
                if (cityEntities != null && cityEntities.size() > 0) {
                    mList.clear();
                    for (CityEntity cityEntity : cityEntities) {
                        LocationEntity entity = new LocationEntity(cityEntity.id, cityEntity.name_zh, cityEntity.province_zh + "," + cityEntity.country_name);
                        mList.add(entity);
                    }
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
