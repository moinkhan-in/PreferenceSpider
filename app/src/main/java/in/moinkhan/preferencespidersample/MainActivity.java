package in.moinkhan.preferencespidersample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import in.moinkhan.preferencespider.PreferenceSpider;
import in.moinkhan.preferencespider.PreferenceUtils;
import in.moinkhan.preferencespider_annotations.Preference;

public class MainActivity extends AppCompatActivity {

    @Preference
    String spString;

    @Preference(format = "Welcome: %s")
    String spString2;

    @Preference
    int spInt;

    @Preference(key = "sp_double")
    double spDouble;

    @Preference(name = "  ", key = "sp_boolean")
    boolean spBoolean;

    @Preference(key = "sp_float", defaultValue = "12f")
    float spFloat;

    @Preference(key = "sp_long", defaultValue = "12")
    long spLong;

    @Preference(key = "sp_int")
    Integer spInt2;

    @Preference(key = "sp_double")
    Double spDouble2;

    @Preference
    Boolean spBoolean2;

    @Preference(key = "sp_float", defaultValue = "12f")
    Float spFloat2;

    @Preference(key = "sp_long", defaultValue = "12")
    Long spLong2;

    @Preference
    User spUser3;

    @Preference(key = "spString2", format = "Welcome: %s", defaultValue = "Guest")
    TextView tvUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUserName = findViewById(R.id.tv_my);

        PreferenceSpider.read(this);

        spString = "Moinkhan";
        spInt = 1200;
        spInt2 = 130;
        spFloat = 15.5f;
        spFloat2 = 20.5f;

        PreferenceSpider.write(this);

        User user = new User();
        user.id = 2;
        user.name = "spUser";

        spUser3 = new User();
        spUser3.id = 3;
        spUser3.name = "spUser3";

        PreferenceUtils.getInstance(this).write("spUser", user);
        User user5 = PreferenceUtils.getInstance(this).read("spUser", User.class);

        PreferenceSpider.write(this);
    }
}
