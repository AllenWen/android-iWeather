package cn.allen.iweather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import cn.allen.iweather.R;
import cn.allen.iweather.webservice.entity.WeatherNowEntity;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/13
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private static final int INVALID_ID = -1;
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_FOOTER = 2;
    private static final int TYPE_NORMAL = 3;

    private Context mContext;
    private List<WeatherNowEntity> mList;
    private int mHeaderRes = INVALID_ID;
    private int mFooterRes = INVALID_ID;
    private View mHeaderView;
    private View mFooterView;

    public HomeAdapter(Context context, List<WeatherNowEntity> list) {
        mContext = context;
        mList = list;
    }

    public void setHeaderView(int layoutRes) {
        this.mHeaderRes = layoutRes;
        notifyItemInserted(0);
    }

    public void setFooterView(int layoutRes) {
        this.mFooterRes = layoutRes;
        notifyItemInserted(getItemCount() - 1);
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (mHeaderRes != INVALID_ID && viewType == TYPE_HEADER) {
            view = LayoutInflater.from(mContext).inflate(mHeaderRes, parent, false);
            mHeaderView = view;
        } else if (mFooterRes != INVALID_ID && viewType == TYPE_FOOTER) {
            view = LayoutInflater.from(mContext).inflate(mFooterRes, parent, false);
            mFooterView = view;
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_main, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onClick(v, (int) v.getTag());
                    }
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnItemLongClickListener != null) {
                        mOnItemLongClickListener.onLongClick(v, (int) v.getTag());
                        return true;
                    }
                    return false;
                }
            });
        }
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == TYPE_HEADER) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            String week = "";
            switch (dayOfWeek) {
                case 1:
                    week = mContext.getString(R.string.sunday);
                    break;
                case 2:
                    week = mContext.getString(R.string.monday);
                    break;
                case 3:
                    week = mContext.getString(R.string.tuesday);
                    break;
                case 4:
                    week = mContext.getString(R.string.wednesday);
                    break;
                case 5:
                    week = mContext.getString(R.string.thursday);
                    break;
                case 6:
                    week = mContext.getString(R.string.friday);
                    break;
                case 7:
                    week = mContext.getString(R.string.saturday);
                    break;
            }
            String noon = calendar.get(Calendar.HOUR_OF_DAY) < 12 ? mContext.getString(R.string.forenoon) : mContext.getString(R.string.afternoon);
            holder.date.setText(mContext.getString(R.string.format_header, noon, year, month, day, week));
        } else if (viewType == TYPE_NORMAL) {
            WeatherNowEntity entity;
            if (mHeaderRes == INVALID_ID) {
                entity = mList.get(position);
                holder.itemView.setTag(position);
            } else {
                entity = mList.get(position - 1);
                holder.itemView.setTag(position - 1);
            }
            if (entity.getLocation() != null) {
                holder.name.setText(entity.getLocation().getName());
                holder.addr.setText(entity.getLocation().getPath());
            } else {
                holder.name.setText(R.string.default_name);
                holder.addr.setText(R.string.default_text);
            }
            if (entity.getNow() != null) {
                holder.desc.setText(entity.getNow().getText());
                holder.temperature.setText(entity.getNow().getTemperature() + "℃");
                String imgRes = "ic_weather_" + entity.getNow().getCode();
                int resId = mContext.getResources().getIdentifier(imgRes, "mipmap", mContext.getPackageName());
                holder.image.setImageResource(resId);
            } else {
                holder.desc.setText(R.string.default_text);
                holder.temperature.setText(R.string.default_temperature);
                holder.image.setImageResource(R.mipmap.ic_weather_99);
            }
            if (!TextUtils.isEmpty(entity.getLast_update())) {
                String last_update = entity.getLast_update();
                holder.time.setText(mContext.getString(R.string.format_update, last_update.substring(11, 16)));
            } else {
                holder.time.setText(mContext.getString(R.string.format_update, mContext.getString(R.string.default_time)));
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mHeaderRes == INVALID_ID && mFooterRes == INVALID_ID) {
            return mList.size();
        } else if (mHeaderRes == INVALID_ID) {//footer
            return mList.size() + 1;
        } else if (mFooterRes == INVALID_ID) {//header
            return mList.size() + 1;
        } else {//header 和 footer
            return mList.size() + 2;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderRes == INVALID_ID && mFooterRes == INVALID_ID) {
            return TYPE_NORMAL;
        } else if (mFooterRes == INVALID_ID) {//header
            if (position == 0) {
                return TYPE_HEADER;
            } else {
                return TYPE_NORMAL;
            }
        } else if (mHeaderRes == INVALID_ID) {
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

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    private OnItemLongClickListener mOnItemLongClickListener;

    public interface OnItemLongClickListener {
        void onLongClick(View v, int position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

    class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView addr;
        TextView desc;
        TextView temperature;
        TextView time;
        ImageView image;

        TextView date;

        HomeViewHolder(View view) {
            super(view);
            if (itemView == mHeaderView) {
                date = view.findViewById(R.id.date);
                return;
            }
            if (itemView == mFooterView) {
                return;
            }
            name = view.findViewById(R.id.name);
            addr = view.findViewById(R.id.addr);
            desc = view.findViewById(R.id.desc);
            time = view.findViewById(R.id.time);
            temperature = view.findViewById(R.id.temperature);
            image = view.findViewById(R.id.image);
        }
    }
}
