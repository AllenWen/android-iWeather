package cn.allen.iweather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.allen.iweather.R;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/13
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private Context mContext;
    private List<String> mList;

    public HomeAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HomeViewHolder holder = new HomeViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_home, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        holder.tv.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public HomeViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv);
        }
    }
}
