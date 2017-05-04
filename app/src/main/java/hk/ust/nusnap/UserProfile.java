package hk.ust.nusnap;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UserProfile extends AppCompatActivity {
    ListView nutritionListView, foodListView;
    NutritionAdapter nutritionAdapter;
    List<Nutrition> nutritionValues = new ArrayList<>();

    public static final String GREEN = "green";
    public static final String YELLOW = "yellow";

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
                } else {
                    progressBar.setImageResource(R.drawable.yellowbar);
                    valueTextView.setTextColor(getResources().getColor(R.color.colorYelow));
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

        nutritionListView = (ListView) findViewById(R.id.lvNutrition);
        nutritionAdapter = new NutritionAdapter(this, nutritionValues);
        nutritionListView.setAdapter(nutritionAdapter);
    }
}
