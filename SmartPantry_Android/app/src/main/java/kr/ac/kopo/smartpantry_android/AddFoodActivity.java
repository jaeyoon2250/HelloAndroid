package kr.ac.kopo.smartpantry_android;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddFoodActivity extends AppCompatActivity {

    public static final String EXTRA_FOOD_ID = "food_id"; // 수정 모드일 때 전달

    private EditText etFoodName;
    private TextView tvExpiryDate;
    private Spinner spinnerCategory;
    private Button btnSave;
    private TextView tvTitle;

    private FoodDatabaseHelper dbHelper;
    private int editFoodId = -1; // -1이면 추가, 0 이상이면 수정
    private String selectedDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        getWindow().setStatusBarColor(getResources().getColor(R.color.green_primary, getTheme()));

        dbHelper = new FoodDatabaseHelper(this);
        initViews();

        // 수정 모드 확인
        editFoodId = getIntent().getIntExtra(EXTRA_FOOD_ID, -1);
        if (editFoodId >= 0) {
            loadFoodForEdit(editFoodId);
        }
    }

    private void initViews() {
        etFoodName = findViewById(R.id.et_food_name);
        tvExpiryDate = findViewById(R.id.tv_expiry_date);
        spinnerCategory = findViewById(R.id.spinner_category);
        btnSave = findViewById(R.id.btn_save);
        tvTitle = findViewById(R.id.tv_title);

        // 카테고리 스피너 설정
        String[] categories = getResources().getStringArray(R.array.categories);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(spinnerAdapter);

        // 날짜 선택 클릭
        LinearLayout layoutDatePicker = findViewById(R.id.layout_date_picker);
        layoutDatePicker.setOnClickListener(v -> showDatePicker());
        tvExpiryDate.setOnClickListener(v -> showDatePicker());

        // 저장 버튼
        btnSave.setOnClickListener(v -> saveFood());

        // 취소 버튼
        TextView btnCancel = findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(v -> finish());

        // 뒤로가기
        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }

    private void showDatePicker() {
        Calendar cal = Calendar.getInstance();
        // 이미 선택된 날짜가 있으면 해당 날짜로 초기화
        if (!selectedDate.isEmpty()) {
            try {
                String[] parts = selectedDate.split("-");
                cal.set(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]) - 1, Integer.parseInt(parts[2]));
            } catch (Exception ignored) {}
        }

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, year, month, day) -> {
                    selectedDate = String.format("%04d-%02d-%02d", year, month + 1, day);
                    tvExpiryDate.setText(selectedDate);
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        );
        dialog.show();
    }

    private void loadFoodForEdit(int id) {
        FoodItem item = dbHelper.getFoodById(id);
        if (item == null) {
            Toast.makeText(this, "식재료 정보를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tvTitle.setText(getString(R.string.edit_food));
        btnSave.setText(getString(R.string.update));

        etFoodName.setText(item.getName());
        selectedDate = item.getExpiryDate();
        tvExpiryDate.setText(selectedDate);

        // 카테고리 선택
        String[] categories = getResources().getStringArray(R.array.categories);
        for (int i = 0; i < categories.length; i++) {
            if (categories[i].equals(item.getCategory())) {
                spinnerCategory.setSelection(i);
                break;
            }
        }
    }

    private void saveFood() {
        String name = etFoodName.getText().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString();

        // 유효성 검사
        if (name.isEmpty()) {
            etFoodName.setError("식재료 이름을 입력해주세요.");
            etFoodName.requestFocus();
            return;
        }
        if (selectedDate.isEmpty()) {
            Toast.makeText(this, "유통기한을 선택해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (editFoodId >= 0) {
            // 수정 (UPDATE)
            FoodItem item = new FoodItem(editFoodId, name, selectedDate, category);
            dbHelper.updateFood(item);
            Toast.makeText(this, "수정되었습니다.", Toast.LENGTH_SHORT).show();
        } else {
            // 신규 저장 (INSERT)
            FoodItem item = new FoodItem(name, selectedDate, category);
            dbHelper.insertFood(item);
            Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
        }

        setResult(RESULT_OK);
        finish();
    }
}
