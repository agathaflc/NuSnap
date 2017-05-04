package hk.ust.nusnap;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
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
public class AnalyzeActivity extends AppCompatActivity {
    List<Fact> nutritionFacts = new ArrayList<>();
    ListView nutritionFactsListView;
    FactAdapter factAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze);
        android.app.ActionBar actionBar = getActionBar();
        if (actionBar!= null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Bitmap foodPicBitmap = (Bitmap) getIntent().getParcelableExtra(UserProfile.BITMAP_IMAGE);
        ImageView foodPicImageView = (ImageView) findViewById(R.id.ivFoodPic);
        foodPicImageView.setImageBitmap(foodPicBitmap);

        int count = (int) getIntent().getIntExtra(UserProfile.COUNTER, 0);

        setFacts(count);

        nutritionFactsListView = (ListView) findViewById(R.id.lvFacts);
        factAdapter = new FactAdapter(this, nutritionFacts);
        nutritionFactsListView.setAdapter(factAdapter);
    }

    private void setFacts(int count) {
        if (count%2 != 0) {
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

            Fact Potassium = new Fact();
            Potassium.name = "Potassium";
            Potassium.amount = "0 mg";
            nutritionFacts.add(Potassium);

            Fact Protein = new Fact();
            Protein.name = "Protein";
            Protein.amount = "6 g";
            nutritionFacts.add(Protein);
        } else {
            Fact empty = new Fact();
            empty.name = "This picture cannot be recognised as food.";
            empty.amount = "";
            nutritionFacts.add(empty);
        }
    }

    class Fact {
        String name;
        String amount;
    }

    class FactAdapter extends ArrayAdapter<Fact> {
        TextView factTextView;
        TextView amountTextView;
        View row;

        public FactAdapter(Context context, List<Fact> meals) {
            super(context, R.layout.activity_analyze, meals);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            try {
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(R.layout.item_fact, parent, false);
                Fact fact = nutritionFacts.get(position);
                factTextView = (TextView) findViewById(R.id.tvFactName);
                factTextView.setText(fact.name);
                amountTextView = (TextView) findViewById(R.id.tvFactAmount);
                amountTextView.setText(fact.amount);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return row;
        }
    }
}
