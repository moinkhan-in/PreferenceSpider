package in.moinkhan.preferencespider;

import android.content.Context;

/**
 * Created by moin on 4/5/18.
 */

public interface PreferenceBinder {
    void bindPreferenceValue(Context context);

    void commitPreferenceValues(Context context);

    void registerCallBacks(Context context);
}
