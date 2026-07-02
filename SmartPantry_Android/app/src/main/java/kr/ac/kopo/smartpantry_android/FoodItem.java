package kr.ac.kopo.smartpantry_android;

public class FoodItem {
    private int id;
    private String name;
    private String expiryDate;  // "yyyy-MM-dd"
    private String category;

    public FoodItem() {}

    public FoodItem(int id, String name, String expiryDate, String category) {
        this.id = id;
        this.name = name;
        this.expiryDate = expiryDate;
        this.category = category;
    }

    public FoodItem(String name, String expiryDate, String category) {
        this.name = name;
        this.expiryDate = expiryDate;
        this.category = category;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getExpiryDate() { return expiryDate; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    /**
     * 오늘 기준 남은 일수를 계산합니다.
     * 양수 = 남은 일수, 0 = 오늘, 음수 = 만료
     */
    public int getDaysRemaining() {
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
            java.util.Date expiry = sdf.parse(expiryDate);
            java.util.Date today = sdf.parse(sdf.format(new java.util.Date()));
            if (expiry == null || today == null) return 0;
            long diff = expiry.getTime() - today.getTime();
            return (int) (diff / (1000 * 60 * 60 * 24));
        } catch (Exception e) {
            return 0;
        }
    }

    /** 식재료 이름의 첫 글자를 반환합니다 (아이콘용). */
    public String getFirstChar() {
        if (name != null && !name.isEmpty()) {
            return String.valueOf(name.charAt(0));
        }
        return "?";
    }

    @Override
    public String toString() {
        return "FoodItem{id=" + id + ", name='" + name + "', expiryDate='" + expiryDate + "', category='" + category + "'}";
    }
}
