package cn.allen.iweather.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Pair;

import java.util.List;

import cn.allen.iweather.R;
import cn.allen.iweather.utils.DimenUtils;

/**
 * Created by allen on 2017/11/17.
 */

public class PathRecyclerView extends RecyclerView {
    private Paint mHighPaint;
    private Paint mLowPaint;
    private Path mHighPath;
    private Path mLowPath;
    private List<Pair<Float, Float>> mHighs;
    private List<Pair<Float, Float>> mLows;

    public PathRecyclerView(Context context) {
        super(context);
        init();
    }

    public PathRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PathRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mHighPaint = new Paint();
        mHighPaint.setColor(getResources().getColor(R.color.colorPrimary));
        mHighPaint.setAntiAlias(true);
        mHighPaint.setStyle(Paint.Style.STROKE);
        mHighPaint.setStrokeWidth(DimenUtils.dip2px(1));

        mLowPaint = new Paint();
        mLowPaint.setColor(getResources().getColor(R.color.colorAccent));
        mLowPaint.setAntiAlias(true);
        mLowPaint.setStyle(Paint.Style.STROKE);
        mLowPaint.setStrokeWidth(DimenUtils.dip2px(1));

        mHighPath = new Path();
        mLowPath = new Path();
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
        if (mHighs != null && mHighs.size() > 1) {
            mHighPath.moveTo(mHighs.get(0).first, mHighs.get(0).second);
            for (Pair<Float, Float> pair : mHighs) {
                mHighPath.lineTo(pair.first, pair.second);
            }
            c.drawPath(mHighPath, mHighPaint);
        }
        if (mLows != null && mLows.size() > 1) {
            mLowPath.moveTo(mLows.get(0).first, mLows.get(0).second);
            for (Pair<Float, Float> pair : mLows) {
                mLowPath.lineTo(pair.first, pair.second);
            }
            c.drawPath(mLowPath, mLowPaint);
        }
    }

    public void setPath(List<Pair<Float, Float>> highs, List<Pair<Float, Float>> lows) {
        mHighs = highs;
        mLows = lows;
        invalidate();
    }
}
