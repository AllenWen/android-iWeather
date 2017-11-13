package cn.allen.iweather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.allen.iweather.R;
import cn.allen.iweather.persistence.entity.FavoriteEntity;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/13
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_FOOTER = 2;
    private static final int TYPE_NORMAL = 3;

    private Context mContext;
    private List<FavoriteEntity> mList;
    private View mHeaderView;
    private View mFooterView;

    public HomeAdapter(Context context, List<FavoriteEntity> list) {
        mContext = context;
        mList = list;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(View mHeaderView) {
        this.mHeaderView = mHeaderView;
        notifyItemInserted(0);
    }

    public View getFooterView() {
        return mFooterView;
    }

    public void setFooterView(View mFooterView) {
        this.mFooterView = mFooterView;
        notifyItemInserted(getItemCount() - 1);
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new HomeViewHolder(mHeaderView);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new HomeViewHolder(mFooterView);
        }
        return new HomeViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_home, parent, false));
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            FavoriteEntity cityEntity;
            if (mHeaderView == null) {
                cityEntity = mList.get(position);
            } else {
                cityEntity = mList.get(position - 1);
            }
            holder.tv.setText(cityEntity.name_zh + " , " + cityEntity.city_zh + " , " + cityEntity.province_zh + " , " + cityEntity.country_name);
        }
    }

    @Override
    public int getItemCount() {
        if (mHeaderView == null && mFooterView == null) {
            return mList.size();
        } else if (mHeaderView == null) {//footer
            return mList.size() + 1;
        } else if (mFooterView == null) {//header
            return mList.size() + 1;
        } else {//header 和 footer
            return mList.size() + 2;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null) {
            return TYPE_NORMAL;
        } else if (mFooterView == null) {//header
            if (position == 0) {
                return TYPE_HEADER;
            } else {
                return TYPE_NORMAL;
            }
        } else if (mHeaderView == null) {
            if (position == getItemCount() - 1) {//footer
                return TYPE_FOOTER;
            } else {
                return TYPE_NORMAL;
            }
        } else {//header 和 footer
            if (position == 0) {
                return TYPE_HEADER;
            } else if (position == getItemCount() - 1) {
                return TYPE_FOOTER;
            } else {
                return TYPE_NORMAL;
            }
        }
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public HomeViewHolder(View view) {
            super(view);
            //如果是headerview或者是footerview,直接返回
            if (itemView == mHeaderView) {
                return;
            }
            if (itemView == mFooterView) {
                return;
            }
            tv = (TextView) view.findViewById(R.id.tv);
        }
    }
}
