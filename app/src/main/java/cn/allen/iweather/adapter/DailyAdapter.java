package cn.allen.iweather.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/17
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.DailyViewHolder> {


    @Override
    public DailyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(DailyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class DailyViewHolder extends RecyclerView.ViewHolder {


        public DailyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
