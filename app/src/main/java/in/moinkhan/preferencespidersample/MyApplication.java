package in.moinkhan.preferencespidersample;

import android.app.Application;

import in.moinkhan.preferencespider.PreferenceSpider;

/**
 * Created by moin on 11/7/18.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new PreferenceSpider.Builder(getApplicationContext())
//                .preferenceName("moin")
                .allowLog(true)
                .build();
    }
}
