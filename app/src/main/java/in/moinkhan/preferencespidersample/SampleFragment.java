package in.moinkhan.preferencespidersample;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.moinkhan.preferencespider.PreferenceSpider;
import in.moinkhan.preferencespider_annotations.Preference;


/**
 * A simple {@link Fragment} subclass.
 */
public class SampleFragment extends android.app.Fragment {

    @Preference
    int spFragmentInt;

    public SampleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sample, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PreferenceSpider.read(this);

        spFragmentInt = 50;

        PreferenceSpider.write(this);
    }
}
