package in.moinkhan.preferencespidersample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import in.moinkhan.preferencespider.PreferenceSpider;
import in.moinkhan.preferencespider_annotations.Preference;

public class MainActivity extends AppCompatActivity {

    @Preference(key = "sp_string")
    String spString;

    @Preference(key = "sp_int")
    int spInt;

    @Preference(key = "sp_double")
    double spDouble;

    @Preference(key = "sp_boolean")
    boolean spBoolean;

    @Preference(key = "sp_float", defaultValue = "12f")
    float spFloat;

    @Preference(key = "sp_long", defaultValue = "12")
    long spLong;

    @Preference(key = "sp_int")
    Integer spInt2;

    @Preference(key = "sp_double")
    Double spDouble2;

    @Preference(key = "sp_boolean")
    Boolean spBoolean2;

    @Preference(key = "sp_float", defaultValue = "12f")
    Float spFloat2;

    @Preference(key = "sp_long", defaultValue = "12")
    Long spLong2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceSpider.read(this);
        spString = "Moinkhan";
        PreferenceSpider.write(this);
    }
}
