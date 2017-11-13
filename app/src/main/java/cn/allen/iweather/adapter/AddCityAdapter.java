package cn.allen.iweather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.allen.iweather.R;
import cn.allen.iweather.persistence.entity.CityEntity;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/13
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class AddCityAdapter extends RecyclerView.Adapter<AddCityAdapter.AddCityViewHolder> {
    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的

    private Context mContext;
    private List<CityEntity> mList;
    private View mHeaderView;
    private View mFooterView;

    public AddCityAdapter(Context context, List<CityEntity> list) {
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
    public AddCityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new AddCityViewHolder(mHeaderView);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new AddCityViewHolder(mFooterView);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, (int) v.getTag());
                }
            }
        });
        return new AddCityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddCityViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            CityEntity cityEntity = mList.get(position);
            holder.nameTv.setText(cityEntity.name_zh + " , " + cityEntity.city_zh + " , " + cityEntity.province_zh + " , " + cityEntity.country_name);
            holder.itemView.setTag(position);
            return;
        } else if (getItemViewType(position) == TYPE_HEADER) {
            return;
        } else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        if (mHeaderView == null && mFooterView == null) {
            return mList.size();
        } else if (mHeaderView == null) {
            return mList.size() + 1;
        } else if (mFooterView == null) {
            return mList.size() + 1;
        } else {
            return mList.size() + 2;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            //第一个item应该加载Header
            return TYPE_HEADER;
        }
        if (position == getItemCount() - 1) {
            //最后一个,应该加载Footer
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public class AddCityViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv;

        public AddCityViewHolder(View view) {
            super(view);
            //如果是headerview或者是footerview,直接返回
            if (itemView == mHeaderView) {
                return;
            }
            if (itemView == mFooterView) {
                return;
            }
            nameTv = (TextView) view.findViewById(R.id.tv);
        }
    }
}