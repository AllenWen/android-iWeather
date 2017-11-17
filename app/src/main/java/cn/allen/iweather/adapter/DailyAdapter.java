package cn.allen.iweather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;

import cn.allen.iweather.R;
import cn.allen.iweather.utils.DateUtil;
import cn.allen.iweather.utils.DeviceUtils;
import cn.allen.iweather.utils.DimenUtils;
import cn.allen.iweather.webservice.entity.WeatherDailyEntity;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/17
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.DailyViewHolder> {
    private Context mContext;
    private List<WeatherDailyEntity.DailyEntity> mList;
    private int max = 0, min = 0;

    public DailyAdapter(Context context, List<WeatherDailyEntity.DailyEntity> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public DailyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_daily, parent, false);
        return new DailyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DailyViewHolder holder, int position) {
        int width = (DeviceUtils.getScreenWidth(mContext) - 2 * DimenUtils.dip2px(15)) / getItemCount();
        ViewGroup.LayoutParams layoutParams = holder.root.getLayoutParams();
        layoutParams.width = width;
        holder.root.setLayoutParams(layoutParams);
        WeatherDailyEntity.DailyEntity dailyEntity = mList.get(position);
        Calendar calendar = Calendar.getInstance();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = format.parse(dailyEntity.getDate());
            calendar.setTime(date);
        } catch (ParseException e) {
            calendar.setTime(new Date());
            e.printStackTrace();
        }
        String week = DateUtil.getWeek(mContext, calendar);
        if (DateUtil.isSameDay(calendar, Calendar.getInstance())) {
            week = mContext.getString(R.string.today);
        }
        holder.week.setText(week);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd", Locale.getDefault());
        String dateStr = sdf.format(calendar.getTime());
        holder.date.setText(dateStr);
        holder.text_day.setText(dailyEntity.getText_day());
        holder.text_night.setText(dailyEntity.getText_night());
        String codeDay = "ic_weather_" + dailyEntity.getCode_day();
        int resId = mContext.getResources().getIdentifier(codeDay, "mipmap", mContext.getPackageName());
        holder.code_day.setImageResource(resId);
        String codeNight = "ic_weather_" + dailyEntity.getCode_night();
        int resId2 = mContext.getResources().getIdentifier(codeNight, "mipmap", mContext.getPackageName());
        holder.code_night.setImageResource(resId2);
        holder.wind_direction.setText(dailyEntity.getWind_direction());
        holder.wind_scale.setText(mContext.getString(R.string.format_wind_scale, dailyEntity.getWind_scale()));
        holder.high.setText(dailyEntity.getHigh() + "℃");
        holder.low.setText(dailyEntity.getLow() + "℃");

        LinearLayout.LayoutParams highParams = (LinearLayout.LayoutParams) holder.high.getLayoutParams();
        LinearLayout.LayoutParams lowParams = (LinearLayout.LayoutParams) holder.low_dot.getLayoutParams();
        int dot_range = DimenUtils.dip2px(100) - 2 * DimenUtils.dip2px(15) - 2 * DimenUtils.dip2px(5);
        int per = dot_range / getMaxDelta();
        highParams.topMargin = per * (max - dailyEntity.getHigh());
        lowParams.topMargin = per * (dailyEntity.getHigh() - dailyEntity.getLow());

        holder.high.setLayoutParams(highParams);
        holder.low_dot.setLayoutParams(lowParams);

        float x = width / 2 + position * width;
        float high_y = DimenUtils.dip2px(118) + DimenUtils.dip2px(5 / 2) + highParams.topMargin;
        float low_y = DimenUtils.dip2px(118) + DimenUtils.dip2px(5) + DimenUtils.dip2px(5 / 2) + highParams.topMargin + lowParams.topMargin;
        Pair<Float, Float> highPair = new Pair<>(x, high_y);
        Pair<Float, Float> lowPair = new Pair<>(x, low_y);
        if (mListener != null) {
            mListener.onDraw(position, highPair, lowPair);
        }
    }

    private int getMaxDelta() {
        for (WeatherDailyEntity.DailyEntity dailyEntity : mList) {
            if (dailyEntity.getHigh() > max) {
                max = dailyEntity.getHigh();
            }
            if (dailyEntity.getLow() < min) {
                min = dailyEntity.getLow();
            }
        }
        return max - min;
    }

    private OnHighLowDrawListener mListener;

    public interface OnHighLowDrawListener {
        void onDraw(int position, Pair<Float, Float> highPair, Pair<Float, Float> lowPair);
    }

    public void addOnHighLowDrawListener(OnHighLowDrawListener listener) {
        mListener = listener;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class DailyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout root;
        private TextView week;
        private TextView date;
        private TextView text_day;
        private ImageView code_day;
        private TextView high;
        private View high_dot;
        private View low_dot;
        private TextView low;
        private TextView text_night;
        private ImageView code_night;
        private TextView wind_direction;
        private TextView wind_scale;

        public DailyViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.root);
            week = itemView.findViewById(R.id.week);
            date = itemView.findViewById(R.id.date);
            text_day = itemView.findViewById(R.id.text_day);
            code_day = itemView.findViewById(R.id.code_day);
            high = itemView.findViewById(R.id.high);
            high_dot = itemView.findViewById(R.id.high_dot);
            low_dot = itemView.findViewById(R.id.low_dot);
            low = itemView.findViewById(R.id.low);
            text_night = itemView.findViewById(R.id.text_night);
            code_night = itemView.findViewById(R.id.code_night);
            wind_direction = itemView.findViewById(R.id.wind_direction);
            wind_scale = itemView.findViewById(R.id.wind_scale);
        }
    }
}
