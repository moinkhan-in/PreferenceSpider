package in.co.moinkhan.preferencespidersample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import in.co.moinkhan.preferencespider.PreferenceSpider;
import in.co.moinkhan.preferencespider.PreferenceUtils;
import in.co.moinkhan.preferencespider_annotations.Preference;

public class MainActivity extends AppCompatActivity {

    @Preference(key = "sp_username", defaultValue = "userDefault")
    String userName;

    @Preference(key = "sp_password", defaultValue = "passDefault")
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceSpider.readValues(this);
        Log.d("Brfore Updation: ", userName + " " + password);

        userName = "Moinkhan";
        password = "Moinkhan Pathan";

        PreferenceSpider.writeValues(this);
    }
}
