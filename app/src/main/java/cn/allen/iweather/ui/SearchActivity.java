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
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.allen.iweather.R;
import cn.allen.iweather.adapter.CityAdapter;
import cn.allen.iweather.lifecycle.SearchObserver;
import cn.allen.iweather.persistence.entity.FavoriteEntity;
import cn.allen.iweather.utils.ToastUtils;
import cn.allen.iweather.webservice.ApiResponse;
import cn.allen.iweather.webservice.entity.BaseWrapperEntity;
import cn.allen.iweather.webservice.entity.LocationEntity;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/15
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class SearchActivity extends AppCompatActivity implements LifecycleOwner {
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.loading)
    ProgressBar loadingView;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private SearchViewModel mViewModel;
    private CityAdapter mAdapter;
    private List<LocationEntity> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        getLifecycle().addObserver(new SearchObserver());
        setSupportActionBar(toolbar);
        mViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);

        mAdapter = new CityAdapter(this, mList);
        mAdapter.setOnItemClickListener(new CityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, ImageView favo, int position) {
                LocationEntity entity = mList.get(position);
                final FavoriteEntity favoriteEntity = new FavoriteEntity(entity.getId(), entity.getName(), entity.getPath());
                mViewModel.insertFavorite(favoriteEntity);
                entity.setFavorite(true);
                favo.setSelected(true);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
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

    private void searchCity(String query) {
        if (TextUtils.isEmpty(query.trim())) {
            return;
        }
        showLoading();
        mViewModel.searchCity(query).observe(this, new Observer<ApiResponse<BaseWrapperEntity<LocationEntity>>>() {
            @Override
            public void onChanged(@Nullable ApiResponse<BaseWrapperEntity<LocationEntity>> baseWrapperEntityApiResponse) {
                mList.clear();
                if (baseWrapperEntityApiResponse != null && baseWrapperEntityApiResponse.isSuccess()) {
                    BaseWrapperEntity<LocationEntity> wrapperEntity = baseWrapperEntityApiResponse.body;
                    if (wrapperEntity != null) {
                        List<LocationEntity> results = wrapperEntity.getResults();
                        if (results != null && results.size() > 0) {
                            mList.addAll(results);
                        } else {
                            ToastUtils.show(SearchActivity.this, R.string.search_city_failed);
                        }
                    } else {
                        ToastUtils.show(SearchActivity.this, R.string.search_city_failed);
                    }
                } else {
                    ToastUtils.show(SearchActivity.this, R.string.search_city_failed);
                }
                hideLoading();
                mAdapter.notifyDataSetChanged();
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

    private void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void hideLoading() {
        loadingView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

}
