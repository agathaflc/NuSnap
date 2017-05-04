package hk.ust.nusnap;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UserProfile extends AppCompatActivity {
    ListView nutritionListView, mealListView;
    NutritionAdapter nutritionAdapter;
    MealAdapter mealAdapter;
    List<Nutrition> nutritionValues = new ArrayList<>();
    List<Meal> mealsToday = new ArrayList<>();

    public static final String GREEN = "green";
    public static final String YELLOW = "yellow";
    public static final String RED = "red";

    class Meal {
        String type;
        String time;
        @DrawableRes
        int picId = -1;
        boolean done;
    }

    class MealAdapter extends ArrayAdapter<Meal> {
        TextView typeTextView;
        TextView timeTextView;
        ImageView mealImageView;
        ImageView detailsImageView;
        View row;

        public MealAdapter(Context context, List<Meal> meals) {
            super(context, R.layout.activity_user_profile, meals);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            try {
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(R.layout.item_food, parent, false);
                Meal meal = mealsToday.get(position);
                typeTextView = (TextView) row.findViewById(R.id.tvMeal);
                typeTextView.setText(meal.type);
                timeTextView = (TextView) row.findViewById(R.id.tvMealTime);
                timeTextView.setText(meal.time);
                if (meal.picId != -1) {
                    mealImageView = (ImageView) row.findViewById(R.id.ivMealPic);
                    mealImageView.setImageDrawable(getResources().getDrawable(meal.picId));
                }
                detailsImageView = (ImageView) row.findViewById(R.id.ivDetails);
                if (meal.done) {
                    detailsImageView.setImageResource(R.drawable.next);
                } else {
                    detailsImageView.setImageResource(R.drawable.clock);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return row;
        }
    }

    class Nutrition {
        String type;
        String value;
        String unitTotal;
        String colour;
    }

    class NutritionAdapter extends ArrayAdapter<Nutrition> {
        TextView nutritionTextView;
        TextView valueTextView;
        TextView totalUnitTextView;
        ImageView progressBar;
        View row;

        public NutritionAdapter(Context context, List<Nutrition> nutritions) {
            super(context, R.layout.activity_user_profile, nutritions);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            try {
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(R.layout.item_nutrition, parent, false);
                Nutrition nutrition = nutritionValues.get(position);
                nutritionTextView = (TextView) row.findViewById(R.id.tvNutrition);
                nutritionTextView.setText(nutrition.type);
                valueTextView = (TextView) row.findViewById(R.id.tvFulfilled);
                valueTextView.setText(nutrition.value);
                totalUnitTextView = (TextView) row.findViewById(R.id.tvUnitTotal);
                totalUnitTextView.setText(nutrition.unitTotal);
                progressBar = (ImageView) row.findViewById(R.id.ivBar);
                if (nutrition.colour.equals(GREEN)) {
                    progressBar.setImageResource(R.drawable.greenbar);
                    valueTextView.setTextColor(getResources().getColor(R.color.colorGreen));
                } else if (nutrition.colour.equals(YELLOW)) {
                    progressBar.setImageResource(R.drawable.yellowbar);
                    valueTextView.setTextColor(getResources().getColor(R.color.colorYelow));
                } else {
                    progressBar.setImageResource(R.drawable.redbar);
                    valueTextView.setTextColor(getResources().getColor(R.color.colorRed));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return row;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        setNutritions();
        setMeals();

        mealListView = (ListView) findViewById(R.id.lvMeal);
        mealAdapter = new MealAdapter(this, mealsToday);
        mealListView.setAdapter(mealAdapter);

        nutritionListView = (ListView) findViewById(R.id.lvNutrition);
        nutritionAdapter = new NutritionAdapter(this, nutritionValues);
        nutritionListView.setAdapter(nutritionAdapter);

        ListUtils.setDynamicHeight(nutritionListView);
        ListUtils.setDynamicHeight(mealListView);
    }

    public static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }

    private void setMeals() {
        Meal breakfast = new Meal();
        breakfast.type = "Breakfast";
        breakfast.done = true;
        breakfast.picId = R.drawable.breakfast;
        breakfast.time = "10:30 AM";
        mealsToday.add(breakfast);
    }

    private void setNutritions() {
        Nutrition calories = new Nutrition();
        calories.type = "Calories";
        calories.colour = GREEN;
        calories.unitTotal = "/ 2000";
        calories.value = "1658";
        nutritionValues.add(calories);

        Nutrition veggies = new Nutrition();
        veggies.type = "Fibers";
        veggies.colour = YELLOW;
        veggies.unitTotal = "%";
        veggies.value = "20";
        nutritionValues.add(veggies);

        Nutrition protein = new Nutrition();
        protein.type = "Protein";
        protein.colour = GREEN;
        protein.unitTotal = "%";
        protein.value = "78";
        nutritionValues.add(protein);

        Nutrition sugar = new Nutrition();
        sugar.type = "Sugar";
        sugar.colour = RED;
        sugar.unitTotal = "%";
        sugar.value = "115";
        nutritionValues.add(sugar);

        Nutrition carb = new Nutrition();
        carb.type = "Carbohydrates";
        carb.colour = GREEN;
        carb.unitTotal = "%";
        carb.value = "78";
        nutritionValues.add(carb);
    }
}
