package kr.ac.kopo.userdialogtest;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText textName, textEmail;
    Button btn;
    EditText editName, editEmail;
    View dialogView;

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

        textName = findViewById(R.id.text_Name);
        textEmail = findViewById(R.id.text_email);
        btn = findViewById(R.id.btn);

        btn.setOnClickListener(btnListener);
    }

    View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle("사용자 정보 입력");
            dialog.setIcon(R.drawable.cat);
            dialogView = View.inflate(MainActivity.this, R.layout.dialog, null);
            dialog.setView(dialogView);

            editName = dialogView.findViewById(R.id.dialog_edit_name);
            editEmail = dialogView.findViewById(R.id.dialog_edit_email);
            editName.setText(textName.getText().toString());
            editEmail.setText(textEmail.getText().toString());

            dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    textName.setText(editName.getText().toString());
                    textEmail.setText(editEmail.getText().toString());
                }
            });

            dialog.setNegativeButton("취소", null);
            dialog.show();
        }
    };
}