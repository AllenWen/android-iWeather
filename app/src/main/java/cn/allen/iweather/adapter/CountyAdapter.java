package cn.allen.iweather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.allen.iweather.R;
import cn.allen.iweather.persistence.entity.CityEntity;

/**
 * Created by allen on 2017/11/16.
 */

public class CountyAdapter extends RecyclerView.Adapter<CountyAdapter.CountyViewHolder> {
    private Context mContext;
    private List<CityEntity> mList;

    public CountyAdapter(Context context, List<CityEntity> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public CountyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_county, parent, false);
        final CountyViewHolder viewHolder = new CountyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, viewHolder.favo, (int) v.getTag());
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CountyViewHolder holder, int position) {
        CityEntity cityEntity = mList.get(position);
        holder.name.setText(cityEntity.name_zh);
        holder.parent.setText(cityEntity.province_zh);
        holder.favo.setSelected(false);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View v, ImageView favo, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public class CountyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView parent;
        ImageView favo;

        public CountyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            parent = itemView.findViewById(R.id.parent);
            favo = itemView.findViewById(R.id.favo);
        }
    }
}
