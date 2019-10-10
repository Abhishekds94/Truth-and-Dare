package truthdare.developer.com.app.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;
import truthdare.developer.com.app.R;
import truthdare.developer.com.app.utils.ConstantsData;

public class AddPlayersActivity extends AppCompatActivity {

    TextInputLayout player1,player2,player3,player4,player5;
    CircleImageView add,next,back;
    int count = 1;
    String catId;
    int color[];
    Bundle bundle;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        bundle = getIntent().getExtras();
        catId = bundle.getString("catId");
        color = new int[2];
        ConstantsData.color  = color;
        ConstantsData.catId = catId;
        color[0] = (bundle.getInt("color1"));
        color[1] = (bundle.getInt("color2"));
        setContentView(R.layout.add_players_activity_layout);
        constraintLayout = findViewById(R.id.addplayers_constraint_layout);
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, color);

        gd.setCornerRadius(15f);
        ConstantsData.gradientDrawable = gd;
        constraintLayout.setBackground(gd);

        player1 = findViewById(R.id.til1);
        player2 = findViewById(R.id.til2);
        player3 = findViewById(R.id.til3);
        player4 = findViewById(R.id.til4);
        player5 = findViewById(R.id.til5);
        add = findViewById(R.id.add_iv);
        back = findViewById(R.id.back_iv);
        next  = findViewById(R.id.next_iv);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                switch (count)
                {
                    case 2:
                    {
                        player2.setVisibility(View.VISIBLE);
                        break;
                    }
                    case 3:
                    {
                        player3.setVisibility(View.VISIBLE);
                        break;
                    }
                    case 4:
                    {
                        player4.setVisibility(View.VISIBLE);
                        break;
                    }
                    case 5:
                    {
                        player5.setVisibility(View.VISIBLE);
                        break;
                    }
                    default:
                    {
                        Toast.makeText(AddPlayersActivity.this,"Maximum of 5 players can be added!!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddPlayersActivity.this, SelectOptionActivity.class));
                finish();
            }
        });
    }
}
