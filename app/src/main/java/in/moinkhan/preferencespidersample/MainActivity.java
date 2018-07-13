package in.moinkhan.preferencespidersample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import in.moinkhan.preferencespider.PreferenceSpider;
import in.moinkhan.preferencespider_annotations.Preference;

public class MainActivity extends AppCompatActivity {

    @Preference(key = "sp_string", defaultValue = "userDefault", format = "This is men.")
    String spString;

    @Preference(name = "myfile", key = "sp_boolean", defaultValue = "true")
    boolean spBoolean;

    @Preference(key = "sp_float", defaultValue = "12.15f", readOnly = true)
    float spFloat;

    @Preference(key = "sp_long", defaultValue = "1215L")
    long spLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceSpider.read(this);
        spString = "Moinkhan";
        PreferenceSpider.write(this);
    }
}
