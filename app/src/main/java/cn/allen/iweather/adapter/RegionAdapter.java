package cn.allen.iweather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.allen.iweather.R;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/16
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class RegionAdapter extends RecyclerView.Adapter<RegionAdapter.RegionViewHolder> {
    private Context mContext;
    private List<String> mList;
    private String mParent;

    public RegionAdapter(Context context, List<String> list, String parent) {
        mContext = context;
        mList = list;
        mParent = parent;
    }

    @Override
    public RegionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_region, parent, false);
        final RegionViewHolder viewHolder = new RegionViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, (int) v.getTag());
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RegionViewHolder holder, int position) {
        holder.name.setText(mList.get(position));
        if (TextUtils.isEmpty(mParent)) {
            holder.parent.setVisibility(View.GONE);
        } else {
            holder.parent.setVisibility(View.VISIBLE);
            holder.parent.setText(mParent);
        }
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View v,int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public class RegionViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView parent;

        public RegionViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}
