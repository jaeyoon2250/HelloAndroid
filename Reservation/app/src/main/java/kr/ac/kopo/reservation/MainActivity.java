package kr.ac.kopo.reservation;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Chronometer;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Chronometer chronometer;
    RadioGroup rg;
    CalendarView calendar;
    TimePicker timePicker;
    TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        chronometer = findViewById(R.id.chro);
        rg = findViewById(R.id.rg);
        calendar = findViewById(R.id.calendar);
        timePicker = findViewById(R.id.timepicker);
        textResult = findViewById(R.id.tv_result);

        timePicker.setVisibility(View.INVISIBLE);

        Button btnStart = findViewById(R.id.btn_start);
        Button btnDone = findViewById(R.id.btn_done);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                chronometer.setTextColor(Color.RED);
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();
                chronometer.setTextColor(Color.BLUE);
            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_time) {
                    calendar.setVisibility(View.INVISIBLE);
                    timePicker.setVisibility(View.VISIBLE);
                }
                else {
                    timePicker.setVisibility(View.INVISIBLE);
                    calendar.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}