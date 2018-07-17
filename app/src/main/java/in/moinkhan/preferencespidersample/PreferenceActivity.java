package in.moinkhan.preferencespidersample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import in.moinkhan.preferencespider_annotations.Preference;

public class PreferenceActivity extends AppCompatActivity {

//    @Preference(key = "increment", defaultValue = "as", format = "asd")
//    String increment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
    }
}
