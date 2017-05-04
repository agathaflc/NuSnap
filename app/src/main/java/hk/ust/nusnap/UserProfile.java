package hk.ust.nusnap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class UserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ImageView headerView = (ImageView) findViewById(R.id.ivHeader);
        Glide.with(this).load("http://healthyfoodhouse.com/wp-content/uploads/2012/09/Eat-Healthy.jpeg").into(headerView);
    }
}
