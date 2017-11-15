package cn.allen.iweather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.allen.iweather.R;
import cn.allen.iweather.webservice.entity.LocationEntity;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/13
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.AddCityViewHolder> {
    private Context mContext;
    private List<LocationEntity> mList;

    public CityAdapter(Context context, List<LocationEntity> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public AddCityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_city, parent, false);
        final AddCityViewHolder viewHolder = new AddCityViewHolder(view);
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
    public void onBindViewHolder(AddCityViewHolder holder, int position) {
        LocationEntity locationEntity = mList.get(position);
        holder.name.setText(locationEntity.getName());
        holder.addr.setText(locationEntity.getPath());
        if (TextUtils.isEmpty(locationEntity.getTimezone_offset())) {
            holder.timezone.setVisibility(View.GONE);
        } else {
            holder.timezone.setVisibility(View.VISIBLE);
            holder.timezone.setText(mContext.getString(R.string.timezone_format, locationEntity.getTimezone_offset()));
        }
        holder.favo.setSelected(locationEntity.isFavorite());
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

    class AddCityViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView addr;
        TextView timezone;
        ImageView favo;

        AddCityViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            addr = view.findViewById(R.id.addr);
            timezone = view.findViewById(R.id.timezone);
            favo = view.findViewById(R.id.favo);
        }
    }
}