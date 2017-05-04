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
        Restaurant uniq = new Restaurant();
        uniq.name = "Unique";
        uniq.resId = R.drawable.unique;
        restaurantList.add(uniq);

        Restaurant verdura = new Restaurant();
        verdura.name = "Verdura";
        verdura.resId = R.drawable.verdura;
        restaurantList.add(verdura);

        Restaurant veggieGrill = new Restaurant();
        veggieGrill.name = "Unique";
        veggieGrill.resId = R.drawable.veggie_grill;
        restaurantList.add(veggieGrill);

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
                nameTextView = (TextView) row.findViewById(R.id.tvRestoName);
                nameTextView.setText(restaurant.name);
                restoImageView = (ImageView) row.findViewById(R.id.ivRestoImg);
                restoImageView.setImageResource(restaurant.resId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return row;
        }
    }
}
