package kr.ac.kopo.smartpantry_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class FoodDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 1;

    // 테이블 및 컬럼명
    public static final String TABLE_FOOD = "food_items";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EXPIRY_DATE = "expiry_date";
    public static final String COLUMN_CATEGORY = "category";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_FOOD + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT NOT NULL, " +
            COLUMN_EXPIRY_DATE + " TEXT NOT NULL, " +
            COLUMN_CATEGORY + " TEXT" +
            ")";

    public FoodDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
        onCreate(db);
    }

    /** 식재료 추가 (INSERT) */
    public long insertFood(FoodItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, item.getName());
        values.put(COLUMN_EXPIRY_DATE, item.getExpiryDate());
        values.put(COLUMN_CATEGORY, item.getCategory());
        long id = db.insert(TABLE_FOOD, null, values);
        db.close();
        return id;
    }

    /** 전체 식재료 목록 조회 (SELECT ALL) - 유통기한 오름차순 */
    public List<FoodItem> getAllFoods() {
        List<FoodItem> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_FOOD, null, null, null, null, null,
                COLUMN_EXPIRY_DATE + " ASC"
        );
        if (cursor.moveToFirst()) {
            do {
                FoodItem item = cursorToFoodItem(cursor);
                list.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    /** ID로 식재료 단건 조회 */
    public FoodItem getFoodById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_FOOD, null,
                COLUMN_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null
        );
        FoodItem item = null;
        if (cursor.moveToFirst()) {
            item = cursorToFoodItem(cursor);
        }
        cursor.close();
        db.close();
        return item;
    }

    /** 이름으로 검색 (LIKE) */
    public List<FoodItem> searchFoodsByName(String keyword) {
        List<FoodItem> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_FOOD, null,
                COLUMN_NAME + " LIKE ?", new String[]{"%" + keyword + "%"},
                null, null, COLUMN_EXPIRY_DATE + " ASC"
        );
        if (cursor.moveToFirst()) {
            do {
                list.add(cursorToFoodItem(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    /** 식재료 수정 (UPDATE) */
    public int updateFood(FoodItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, item.getName());
        values.put(COLUMN_EXPIRY_DATE, item.getExpiryDate());
        values.put(COLUMN_CATEGORY, item.getCategory());
        int rows = db.update(TABLE_FOOD, values,
                COLUMN_ID + "=?", new String[]{String.valueOf(item.getId())});
        db.close();
        return rows;
    }

    /** 식재료 삭제 (DELETE) */
    public int deleteFood(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_FOOD,
                COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rows;
    }

    /** Cursor → FoodItem 변환 헬퍼 */
    private FoodItem cursorToFoodItem(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
        String expiryDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPIRY_DATE));
        String category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY));
        return new FoodItem(id, name, expiryDate, category);
    }
}
