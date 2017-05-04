package hk.ust.nusnap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    int counter = 0;

    public static final String GREEN = "green";
    public static final String YELLOW = "yellow";
    public static final String RED = "red";

    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String BITMAP_IMAGE = "BitmapImage";
    public static final String COUNTER = "counter";

    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
        Intent intent = new Intent(this, AnalyzeActivity.class);
        Bitmap imageBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.camera);
        intent.putExtra(BITMAP_IMAGE, scaleDownBitmap(imageBitmap, 100, this));
        intent.putExtra(COUNTER, counter);

        startActivity(intent);
    }
    public static Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {

        final float densityMultiplier = context.getResources().getDisplayMetrics().density;

        int h= (int) (newHeight*densityMultiplier);
        int w= (int) (h * photo.getWidth()/((double) photo.getHeight()));

        photo=Bitmap.createScaledBitmap(photo, w, h, true);

        return photo;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            counter++;
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Intent intent = new Intent(this, AnalyzeActivity.class);
            intent.putExtra(BITMAP_IMAGE, imageBitmap);
            intent.putExtra(COUNTER, counter);
            startActivity(intent);
        }
    }

    class Meal {
        String type;
        String time;
        @DrawableRes
        int picId = -1;
        boolean done;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_camera, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_camera:
                counter++;
                dispatchTakePictureIntent();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    class MealAdapter extends ArrayAdapter<Meal> {
        TextView typeTextView;
        TextView timeTextView;
        ImageView mealImageView;
        ImageView detailsImageView;
        ImageView bulletImageView;
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
                bulletImageView = (ImageView) row.findViewById(R.id.ivBullet);
                if (meal.done) {
                    detailsImageView.setImageResource(R.drawable.next);
                    bulletImageView.setImageResource(android.R.drawable.presence_online);
                } else {
                    detailsImageView.setImageResource(R.drawable.clock);
                    bulletImageView.setImageResource(android.R.drawable.presence_invisible);
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
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(COUNTER, counter);

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        counter = savedInstanceState.getInt(COUNTER);
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        counter = getIntent().getIntExtra(COUNTER, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // recovering the instance state
        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt(COUNTER);
        }

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

        Meal lunch = new Meal();
        lunch.type = "Lunch";
        lunch.done = true;
        lunch.picId = R.drawable.lunch;
        lunch.time = "1:30 PM";
        mealsToday.add(lunch);

        Meal tea = new Meal();
        tea.type = "Tea";
        tea.done = false;
        tea.picId = -1;
        tea.time = "4:30 PM";
        mealsToday.add(tea);

        Meal dinner = new Meal();
        dinner.type = "Dinner";
        dinner.done = false;
        dinner.picId = -1;
        dinner.time = "7:30 PM";
        mealsToday.add(dinner);
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
