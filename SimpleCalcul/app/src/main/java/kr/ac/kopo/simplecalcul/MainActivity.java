package kr.ac.kopo.simplecalcul;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText edit1, edit2;
    Button btn_plus, btn_minus, btn_multi, btn_divi;
    TextView text_result;
    int[] btnNumIds = {R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4,
            R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9};
    Button[] btnNums = new Button[btnNumIds.length];
    int i;
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
        edit1 = findViewById(R.id.edt1);
        edit2 = findViewById(R.id.edt2);
        btn_plus = findViewById(R.id.btn_plus);
        btn_minus = findViewById(R.id.btn_minus);
        btn_multi = findViewById(R.id.btn_multi);
        btn_divi = findViewById(R.id.btn_divi);
        text_result = findViewById(R.id.text_result);

        for (int i = 0; i < btnNums.length; i++) {
            final int index = i;

            btnNums[i] = findViewById(btnNumIds[i]);
            btnNums[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (edit1.isFocused()) {
                        edit1.setText(edit1.getText().toString() + btnNums[index].getText().toString());
                    } else if (edit2.isFocused()) {
                        edit2.setText(edit2.getText().toString() + btnNums[index].getText().toString());
                    } else
                        Toast.makeText(MainActivity.this, "입력칸을 선택해주세요.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        btn_plus.setOnClickListener(btnOperationListener);
        btn_minus.setOnClickListener(btnOperationListener);
        btn_multi.setOnClickListener(btnOperationListener);
        btn_divi.setOnClickListener(btnOperationListener);
    }
    View.OnClickListener btnOperationListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button btnEvent = (Button) v;
            String edit1str = edit1.getText().toString();
            String edit2str = edit2.getText().toString();
            int num1 = Integer.parseInt(edit1str);
            int num2 = Integer.parseInt(edit2str);
            int result = 0;

            if (btnEvent == btn_plus) {
                result = num1 + num2;
            } else if (btnEvent == btn_minus) {
                result = num1 - num2;
            } else if (btnEvent == btn_multi) {
                result = num1 * num2;
            } else {
                result = num1 / num2;
            }
            text_result.setText("계산 결과: " + result);
        }
    };
}