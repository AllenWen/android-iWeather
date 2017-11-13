package cn.allen.iweather.ui;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.allen.iweather.R;
import cn.allen.iweather.lifecycle.AddCityObserver;
import cn.allen.iweather.utils.Configs;
import cn.allen.iweather.webservice.ApiResponse;
import cn.allen.iweather.webservice.entity.BaseWrapperEntity;
import cn.allen.iweather.webservice.entity.WeatherNowEntity;

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

    private AddCityViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcity);
        ButterKnife.bind(this);
        getLifecycle().addObserver(new AddCityObserver(this));
        setSupportActionBar(toolbar);
        mViewModel = ViewModelProviders.of(this).get(AddCityViewModel.class);
        mViewModel.now(Configs.KEY, "chengdu", Configs.LANG, Configs.UNIT).observe(this, new Observer<ApiResponse<BaseWrapperEntity<WeatherNowEntity>>>() {
            @Override
            public void onChanged(@Nullable ApiResponse<BaseWrapperEntity<WeatherNowEntity>> baseWrapperEntityApiResponse) {
                if (baseWrapperEntityApiResponse.isSuccess()) {
                    BaseWrapperEntity<WeatherNowEntity> baseWrapperEntity = baseWrapperEntityApiResponse.body;
                } else {

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_city, menu);
        MenuItem item = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
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
