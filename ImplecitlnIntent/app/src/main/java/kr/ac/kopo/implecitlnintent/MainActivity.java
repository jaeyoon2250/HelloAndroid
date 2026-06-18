package kr.ac.kopo.implecitlnintent;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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

        int[] btnIds = {R.id.btn_dial, R.id.btn_homepage, R.id.btn_map, R.id.btn_search, R.id.btn_sms, R.id.btn_photo, R.id.btn_finish};
        Button[] btns = new Button[btnIds.length];

        int i = 0;
        for (int btnId : btnIds) {
            btns[i] = findViewById(btnId);
            btns[i].setOnClickListener(btnListener);
            i++;
        }

        Log.i("로그확인", "onCreate() 호출");
    }

//    액티비티 실행되는 메소드들
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("로그확인", "onStart() 호출");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("로그확인", "onResume() 호출");
    }

//    액티비티 종료되는 메소드들
    @Override
    protected void onPause() {
        super.onPause();
        Log.i("로그확인", "onPause() 호출");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("로그확인", "onStop() 호출");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("로그확인", "onDestroy() 호출");
    }

//    액티비티가 다른화면에 가려졌다가 다시 나타날 때
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("로그확인", "onRestart() 호출");
    }

    View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            Uri uri = null;
            int selectedId = v.getId();
            if (selectedId == R.id.btn_dial) {
                uri = Uri.parse("tel:01055557777");
                intent = new Intent(Intent.ACTION_DIAL, uri);
            } else if (selectedId == R.id.btn_homepage) {
                uri = Uri.parse("https://www.naver.com");
                intent = new Intent(Intent.ACTION_VIEW, uri);
            } else if (selectedId == R.id.btn_map) {
                uri = Uri.parse("https://maps.google.co.kr/maps?q=37.52465024040647, 126.98084526495141");
                intent = new Intent(Intent.ACTION_VIEW, uri);
            } else if (selectedId == R.id.btn_search) {
                intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, "국립중앙박물관");
            } else if (selectedId == R.id.btn_sms) {
                intent = new Intent(Intent.ACTION_SENDTO);
                intent.putExtra("sms_body", "나랑 국립중앙박물관 같이 갈래?");
                uri = Uri.parse("smsto:01044446666");
                intent.setData(uri);
            } else if (selectedId == R.id.btn_photo) {
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            }

            if (selectedId == R.id.btn_finish) {
                finish();
            } else
                startActivity(intent);
        }
    };
}