package hk.ust.nusnap;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fagatha on 5/5/2017.
 */
public class RecommendationActivity extends AppCompatActivity {
    List<Restaurant> restaurantList = new ArrayList<>();
    ListView restaurantListView;
    RestaurantAdapter restaurantAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations);
        android.app.ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        restaurantListView = (ListView) findViewById(R.id.lvResto);
        restaurantAdapter = new RestaurantAdapter(this, restaurantList);
        restaurantListView.setAdapter(restaurantAdapter);
    }

    private void initResto() {
        Fact fat = new Fact();
        fat.name = "Total Fat";
        fat.amount = "28 g";
        nutritionFacts.add(fat);

        Fact calories = new Fact();
        calories.name = "Calories";
        calories.amount = "393";
        nutritionFacts.add(calories);

        Fact carb = new Fact();
        carb.name = "Total Carbs";
        carb.amount = "29 g";
        nutritionFacts.add(carb);

        Fact Saturated = new Fact();
        Saturated.name = "Saturated";
        Saturated.amount = "0 g";
        nutritionFacts.add(Saturated);

        Fact Polyunsaturated = new Fact();
        Polyunsaturated.name = "Polyunsaturated";
        Polyunsaturated.amount = "0 g";
        nutritionFacts.add(Polyunsaturated);

        Fact Protein = new Fact();
        Protein.name = "Protein";
        Protein.amount = "6 g";
        nutritionFacts.add(Protein);

        Fact Potassium = new Fact();
        Potassium.name = "Potassium";
        Potassium.amount = "0 mg";
        nutritionFacts.add(Potassium);
    }

    class Restaurant {
        String name;
        @DrawableRes
        int resId;
    }

    class RestaurantAdapter extends ArrayAdapter<Restaurant> {
        TextView nameTextView;
        ImageView restoImageView;
        View row;

        public RestaurantAdapter(Context context, List<Restaurant> restaurants) {
            super(context, R.layout.activity_recommendations, restaurants);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            try {
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(R.layout.item_fact, parent, false);
                Restaurant restaurant = restaurantList.get(position);
                nameTextView = (TextView) row.findViewById(R.id.t);
                factTextView.setText(fact.name);
                amountTextView = (TextView) row.findViewById(R.id.tvFactAmount);
                amountTextView.setText(fact.amount);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return row;
        }
    }
}
