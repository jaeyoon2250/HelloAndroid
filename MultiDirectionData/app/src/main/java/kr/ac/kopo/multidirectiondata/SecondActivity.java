package kr.ac.kopo.multidirectiondata;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {

    TextView textQuestion;
    int sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        textQuestion = findViewById(R.id.text_question);

        Intent intent = getIntent();
        int num1 = intent.getIntExtra("num1", 0);
        int num2 = intent.getIntExtra("num2", 0);
        String operator = intent.getStringExtra("operator");
        switch (operator) {
            case "+":
                sum = num1 + num2;
                break;
            case "-":
                sum = num1 - num2;
                break;
            case "*":
                sum = num1 * num2;
                break;
            case "/":
                sum = num1 / num2;
                break;
        }

        textQuestion.setText(num1 + " " + operator + " "+ num2 + " = ?");
        Button btnResult = findViewById(R.id.btn_result);

        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(SecondActivity.this, MainActivity.class);
                mainIntent.putExtra("sum", sum);
                setResult(RESULT_OK, mainIntent);
                finish();
            }
        });
    }
}