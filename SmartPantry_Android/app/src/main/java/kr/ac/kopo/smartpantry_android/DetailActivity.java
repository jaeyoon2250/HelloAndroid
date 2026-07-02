package kr.ac.kopo.smartpantry_android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_FOOD_ID = "food_id";

    private FoodDatabaseHelper dbHelper;
    private FoodItem currentItem;

    private TextView tvIcon, tvNameHeader, tvDetailName, tvDetailExpiry, tvDetailDays, tvDetailCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getWindow().setStatusBarColor(getResources().getColor(R.color.green_primary, getTheme()));

        dbHelper = new FoodDatabaseHelper(this);
        initViews();

        int foodId = getIntent().getIntExtra(EXTRA_FOOD_ID, -1);
        if (foodId < 0) {
            Toast.makeText(this, "잘못된 접근입니다.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadFoodDetail(foodId);
    }

    private void initViews() {
        tvIcon = findViewById(R.id.tv_icon);
        tvNameHeader = findViewById(R.id.tv_food_name_header);
        tvDetailName = findViewById(R.id.tv_detail_name);
        tvDetailExpiry = findViewById(R.id.tv_detail_expiry);
        tvDetailDays = findViewById(R.id.tv_detail_days);
        tvDetailCategory = findViewById(R.id.tv_detail_category);

        // 뒤로가기
        findViewById(R.id.btn_back).setOnClickListener(v -> finish());

        // 수정하기
        Button btnEdit = findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(v -> {
            if (currentItem == null) return;
            Intent intent = new Intent(this, AddFoodActivity.class);
            intent.putExtra(AddFoodActivity.EXTRA_FOOD_ID, currentItem.getId());
            startActivityForResult(intent, 100);
        });

        // 삭제하기
        Button btnDelete = findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(v -> showDeleteDialog());
    }

    private void loadFoodDetail(int id) {
        currentItem = dbHelper.getFoodById(id);
        if (currentItem == null) {
            Toast.makeText(this, "식재료 정보를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        bindData();
    }

    private void bindData() {
        int days = currentItem.getDaysRemaining();

        // 아이콘
        tvIcon.setText(currentItem.getFirstChar());
        applyIconColor(days);

        // 헤더 이름
        tvNameHeader.setText(currentItem.getName());

        // 상세 정보
        tvDetailName.setText(currentItem.getName());
        tvDetailExpiry.setText(currentItem.getExpiryDate());
        tvDetailCategory.setText(currentItem.getCategory());

        // D-Day 표시 및 색상
        String dDayText;
        int dDayColor;
        if (days < 0) {
            dDayText = getString(R.string.d_day_expired);
            dDayColor = ContextCompat.getColor(this, R.color.badge_expired_text);
        } else if (days == 0) {
            dDayText = getString(R.string.d_day_today);
            dDayColor = ContextCompat.getColor(this, R.color.badge_today_text);
        } else {
            dDayText = getString(R.string.d_day_format, days);
            dDayColor = ContextCompat.getColor(this, R.color.badge_safe_text);
        }
        tvDetailDays.setText(dDayText);
        tvDetailDays.setTextColor(dDayColor);
    }

    private void applyIconColor(int days) {
        int bgRes;
        if (days < 0) {
            bgRes = R.drawable.bg_circle_red;
        } else if (days == 0) {
            bgRes = R.drawable.bg_circle_orange;
        } else {
            bgRes = R.drawable.bg_circle_green;
        }
        tvIcon.setBackground(ContextCompat.getDrawable(this, bgRes));
    }

    private void showDeleteDialog() {
        new AlertDialog.Builder(this)
                .setTitle("삭제 확인")
                .setMessage(getString(R.string.delete_confirm))
                .setPositiveButton(getString(R.string.confirm), (dialog, which) -> {
                    dbHelper.deleteFood(currentItem.getId());
                    Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                })
                .setNegativeButton("취소", null)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // 수정 후 데이터 새로고침
            loadFoodDetail(currentItem.getId());
            setResult(RESULT_OK);
        }
    }
}
