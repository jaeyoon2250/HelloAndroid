package kr.ac.kopo.simplecalculater;

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
    Button btnplus, btnminus, btnmulti, btndivi, btnrest;
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

        edit1 = findViewById(R.id.edit1);
        edit2 = findViewById(R.id.edit2);
        textResult = findViewById(R.id.view1);

        btnplus = findViewById(R.id.btn_plus);
        btnminus = findViewById(R.id.btn_minus);
        btnmulti = findViewById(R.id.btn_multi);
        btndivi = findViewById(R.id.btn_divi);
        btnrest = findViewById(R.id.btn_rest);

        btnplus.setOnClickListener(btnListener);
        btnminus.setOnClickListener(btnListener);
        btnmulti.setOnClickListener(btnListener);
        btndivi.setOnClickListener(btnListener);
        btnrest.setOnClickListener(btnListener);
    }

    View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button eBtn = (Button) v;
            String str1 = edit1.getText().toString();
            String str2 = edit2.getText().toString();
            if (str1.isEmpty() || str2.isEmpty()) {
                Toast.makeText(getApplicationContext(), "숫자가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            Double inputNum1 = Double.parseDouble(str1);
            Double inputNum2 = Double.parseDouble(str2);
            Double result = 0.0;

            if(eBtn == btnplus) {
                result = inputNum1 + inputNum2;
            } else if (eBtn == btnminus) {
                result = inputNum1 - inputNum2;
            } else if (eBtn == btnmulti) {
                result = inputNum1 * inputNum2;
            } else if (eBtn == btndivi) {
                if (inputNum2 == 0) {
                    Toast.makeText(getApplicationContext(), "나누는 값이 0입니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    result = inputNum1 / inputNum2;
                }
            } else {
                if (inputNum2 == 0) {
                    Toast.makeText(getApplicationContext(), "나누는 값이 0입니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    result = inputNum1 % inputNum2;
                }
            }
            textResult.setText(String.format("계산 결과: %.3f", result));
        }
    };
}