package kr.ac.kopo.smartpantry_android;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(FoodItem item);
    }

    private Context context;
    private List<FoodItem> foodList;
    private OnItemClickListener listener;

    public FoodAdapter(Context context, List<FoodItem> foodList, OnItemClickListener listener) {
        this.context = context;
        this.foodList = foodList;
        this.listener = listener;
    }

    public void updateList(List<FoodItem> newList) {
        this.foodList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        FoodItem item = foodList.get(position);
        holder.bind(item);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return foodList != null ? foodList.size() : 0;
    }

    class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView tvIcon, tvName, tvExpiryDate, tvBadge;

        FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIcon = itemView.findViewById(R.id.tv_food_icon);
            tvName = itemView.findViewById(R.id.tv_food_name);
            tvExpiryDate = itemView.findViewById(R.id.tv_expiry_date);
            tvBadge = itemView.findViewById(R.id.tv_badge);
        }

        void bind(FoodItem item) {
            // 아이콘: 첫 글자 + 배경색
            tvIcon.setText(item.getFirstChar());
            tvName.setText(item.getName());
            tvExpiryDate.setText(context.getString(R.string.expiry_format, item.getExpiryDate()));

            int days = item.getDaysRemaining();
            applyBadge(days);
            applyIconColor(days);
        }

        private void applyBadge(int days) {
            String badgeText;
            int bgRes;
            int textColorRes;

            if (days < 0) {
                badgeText = context.getString(R.string.d_day_expired);
                bgRes = R.drawable.bg_badge_red;
                textColorRes = R.color.badge_expired_text;
            } else if (days == 0) {
                badgeText = context.getString(R.string.d_day_today);
                bgRes = R.drawable.bg_badge_orange;
                textColorRes = R.color.badge_today_text;
            } else {
                badgeText = context.getString(R.string.d_day_format, days);
                bgRes = R.drawable.bg_badge_green;
                textColorRes = R.color.badge_safe_text;
            }

            tvBadge.setText(badgeText);
            tvBadge.setBackground(ContextCompat.getDrawable(context, bgRes));
            tvBadge.setTextColor(ContextCompat.getColor(context, textColorRes));
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
            tvIcon.setBackground(ContextCompat.getDrawable(context, bgRes));
        }
    }
}
