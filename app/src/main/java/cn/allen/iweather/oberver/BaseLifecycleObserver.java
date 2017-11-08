package cn.allen.iweather.oberver;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/8
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public interface BaseLifecycleObserver extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate();

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart();

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume();

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause();

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy();
}
