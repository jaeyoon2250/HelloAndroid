package kr.ac.kopo.smartpantry_android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DELAY = 1500; // 1.5초

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 상태바 색상 설정
        getWindow().setStatusBarColor(getResources().getColor(R.color.green_primary, getTheme()));

        // 1.5초 후 MainActivity로 이동
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // 뒤로가기 시 스플래시로 돌아오지 않도록
        }, SPLASH_DELAY);
    }
}
