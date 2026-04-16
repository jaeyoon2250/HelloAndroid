package kr.ac.kopo.framlayouttest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button btnred, btnblue;
    LinearLayout linearred, linearblue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main13);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main13), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnred = findViewById(R.id.btn_red);
        btnblue = findViewById(R.id.btn_blue);
        linearred = findViewById(R.id.Linear_red);
        linearblue = findViewById(R.id.Linear_blue);

        btnred.setOnClickListener(btnListener);
        btnblue.setOnClickListener(btnListener);
    }
    View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Button btnEvent = (Button) v;
            if (btnEvent == btnred) {
                linearred.setVisibility(View.VISIBLE);
            } else {
                linearblue.setVisibility(View.VISIBLE);
            }
        }
    };
}