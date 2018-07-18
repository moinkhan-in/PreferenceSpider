package in.moinkhan.preferencespidersample;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import in.moinkhan.preferencespider.PreferenceSpider;
import in.moinkhan.preferencespider_annotations.Preference;

/**
 * Created by moin on 18/7/18.
 */

public class SampleAdapter extends BaseAdapter {

    @Preference
    String userName;

    private Context context;

    SampleAdapter(Context context) {
        this.context = context;
        PreferenceSpider.read(this, context);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
