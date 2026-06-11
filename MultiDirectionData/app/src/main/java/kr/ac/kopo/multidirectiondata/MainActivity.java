package kr.ac.kopo.multidirectiondata;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText editNum1, editNum2;
    TextView text_result;
    RadioGroup rg;
    Button btnCreate;

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
        editNum1 = findViewById(R.id.edit_num1);
        editNum2 = findViewById(R.id.edit_num2);
        text_result = findViewById(R.id.text_result);
        rg = findViewById(R.id.rg);
        btnCreate = findViewById(R.id.btn_create);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup group, int checkedId) {
                int selectedRgId = checkedId;
                String operatorName = "더하기";
                if (selectedRgId == R.id.rb_plus)
                    operatorName = "더하기";
                else if (selectedRgId == R.id.rb_minus)
                    operatorName = "빼기";
                else if (selectedRgId == R.id.rb_multi)
                    operatorName = "곱하기";
                else
                    operatorName = "나누기";
                btnCreate.setText(operatorName + " 문제 생성");
            }
        });
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num1 = Integer.parseInt(editNum1.getText().toString());
                int num2 = Integer.parseInt(editNum2.getText().toString());
                editNum1.setText("");
                editNum2.setText("");
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("num1", num1);
                intent.putExtra("num2", num2);

                String operator = "+";
                int selectedRgId = rg.getCheckedRadioButtonId();
                if (selectedRgId == R.id.rb_plus)
                    operator = "+";
                else if (selectedRgId == R.id.rb_minus)
                    operator = "-";
                else if (selectedRgId == R.id.rb_multi)
                    operator = "*";
                else
                    operator = "/";
                intent.putExtra("operator", operator);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            int sum = data.getIntExtra("sum", 0);
            text_result.setText("정답: " + sum);
        }
    }
}