package kr.ac.kopo.reservation;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.FrameLayout;
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
    DatePicker datePicker;
    TimePicker timePicker;
    TextView textResult;
    FrameLayout frame;
    int selectedYear, selectedMonth, selectedDay;
    int selectedHour, selectedMin;

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
        datePicker = findViewById(R.id.calendar);
        timePicker = findViewById(R.id.timepicker);
        textResult = findViewById(R.id.tv_result);

        timePicker.setVisibility(View.INVISIBLE);
        rg.setVisibility(View.INVISIBLE);
        datePicker.setVisibility(View.INVISIBLE);

        chronometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                chronometer.setTextColor(Color.RED);
                rg.setVisibility(View.VISIBLE);
                datePicker.setVisibility(View.VISIBLE);
            }
        });

        textResult.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                chronometer.stop();
                chronometer.setTextColor(Color.BLUE);
                selectedYear = datePicker.getYear();
                selectedMonth = datePicker.getMonth() + 1;
                selectedDay = datePicker.getDayOfMonth();
                selectedHour = timePicker.getHour();
                selectedMin = timePicker.getMinute();
                textResult.setText(selectedYear + "년 " + selectedMonth + "월 "
                        + selectedDay + "일 " + selectedHour + "시 " + selectedMin + "분으로 예약 완료");
                timePicker.setVisibility(View.INVISIBLE);
                rg.setVisibility(View.INVISIBLE);
                datePicker.setVisibility(View.INVISIBLE);
                return true;
            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_time) {
                    datePicker.setVisibility(View.INVISIBLE);
                    timePicker.setVisibility(View.VISIBLE);
                }
                else {
                    timePicker.setVisibility(View.INVISIBLE);
                    datePicker.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}