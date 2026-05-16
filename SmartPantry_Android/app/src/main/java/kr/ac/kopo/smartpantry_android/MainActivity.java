package kr.ac.kopo.smartpantry_android;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_ADD = 100;
    public static final int REQUEST_DETAIL = 200;

    private FoodDatabaseHelper dbHelper;
    private FoodAdapter adapter;
    private List<FoodItem> foodList;

    private RecyclerView recyclerFood;
    private TextView tvEmpty;
    private LinearLayout layoutSearch;
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 상태바 색상
        getWindow().setStatusBarColor(getResources().getColor(R.color.green_primary, getTheme()));

        dbHelper = new FoodDatabaseHelper(this);

        initViews();
        loadFoodList();
    }

    private void initViews() {
        recyclerFood = findViewById(R.id.recycler_food);
        tvEmpty = findViewById(R.id.tv_empty);
        layoutSearch = findViewById(R.id.layout_search);
        etSearch = findViewById(R.id.et_search);

        // RecyclerView 설정
        recyclerFood.setLayoutManager(new LinearLayoutManager(this));

        // FAB 클릭 → AddFoodActivity
        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddFoodActivity.class);
            startActivityForResult(intent, REQUEST_ADD);
        });

        // 검색 버튼 클릭 → 검색바 토글
        findViewById(R.id.btn_search).setOnClickListener(v -> toggleSearch(true));
        findViewById(R.id.btn_search_cancel).setOnClickListener(v -> {
            etSearch.setText("");
            toggleSearch(false);
            loadFoodList();
        });

        // 실시간 검색
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString().trim();
                if (keyword.isEmpty()) {
                    loadFoodList();
                } else {
                    List<FoodItem> results = dbHelper.searchFoodsByName(keyword);
                    adapter.updateList(results);
                    updateEmptyView(results.isEmpty());
                }
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void toggleSearch(boolean show) {
        layoutSearch.setVisibility(show ? View.VISIBLE : View.GONE);
        // layout_content는 CoordinatorLayout의 자식이므로 CoordinatorLayout.LayoutParams 사용
        LinearLayout layoutContent = findViewById(R.id.layout_content);
        androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams clParams =
                (androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams) layoutContent.getLayoutParams();
        clParams.topMargin = (int) ((show ? 120 : 64) * getResources().getDisplayMetrics().density);
        layoutContent.setLayoutParams(clParams);
    }

    private void loadFoodList() {
        foodList = dbHelper.getAllFoods();

        if (adapter == null) {
            adapter = new FoodAdapter(this, foodList, item -> {
                Intent intent = new Intent(this, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_FOOD_ID, item.getId());
                startActivityForResult(intent, REQUEST_DETAIL);
            });
            recyclerFood.setAdapter(adapter);
        } else {
            adapter.updateList(foodList);
        }

        updateEmptyView(foodList.isEmpty());
    }

    private void updateEmptyView(boolean isEmpty) {
        tvEmpty.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        recyclerFood.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 추가/수정/삭제 후 목록 갱신
        if (resultCode == RESULT_OK) {
            loadFoodList();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFoodList();
    }
}
