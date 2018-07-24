package in.moinkhan.preferencespidersample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SwitchCompat;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import in.moinkhan.preferencespider_annotations.Preference;

public class WidgetActivity extends AppCompatActivity {

    @Preference
    TextView tv;

    @Preference
    AppCompatTextView appCompatTextView;

    @Preference
    EditText editText;

    @Preference(defaultValue = "true")
    CheckBox checkBox;

    @Preference
    RadioButton radioButton;

    @Preference
    ProgressBar progressBar;

    @Preference
    SeekBar seekBar;

    @Preference
    SwitchCompat switchCompat;

    @Preference
    Switch aSwitch;

    @Preference
    RatingBar ratingBar;

    @Preference
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);
    }
}
