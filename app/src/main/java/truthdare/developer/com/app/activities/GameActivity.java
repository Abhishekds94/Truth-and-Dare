package truthdare.developer.com.app.activities;

import android.content.DialogInterface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import truthdare.developer.com.app.R;
import truthdare.developer.com.app.utils.ConstantsData;

public class GameActivity extends AppCompatActivity {

    ConstraintLayout constraintLayout;
    CircleImageView next;
    TextView playerName,question;
    int min,max,random;
    Animation animtoleft,animfromright;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.game_activity_layout);
        MobileAds.initialize(this, "ca-app-pub-4882246940435880~8039652959");
        mAdView = findViewById(R.id.adView3);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        animtoleft = (Animation) AnimationUtils.loadAnimation(GameActivity.this,R.anim.slide_out_left);
        animfromright = (Animation) AnimationUtils.loadAnimation(GameActivity.this,R.anim.slide_in_right);
        constraintLayout = findViewById(R.id.game_constraint_layout);
        constraintLayout.setBackground(ConstantsData.gradientDrawable);

        next= findViewById(R.id.game_next_iv);
        playerName = findViewById(R.id.game_name_tv);
        playerName.setText(ConstantsData.optionSelected);
        question = findViewById(R.id.game_question_tv);
        min = 0;
        max = ConstantsData.questions.size() - 1;

        random =  new Random().nextInt(max);

        question.setText(ConstantsData.questions.get(random));
        ConstantsData.questions.remove(random);
        max = ConstantsData.questions.size() - 1;

        next.setCircleBackgroundColor(ConstantsData.color[0]);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ConstantsData.questions.size()>1)
                {
                    random =  new Random().nextInt(max);
                    question.setText(ConstantsData.questions.get(random));
                    //question.setAnimation(animtoleft);
                    //question.setAnimation(animfromright);
                    //animtoleft.setAnimationListener(new Animation.AnimationListener() {
                      //  @Override
                        //public void onAnimationStart(Animation animation) {

                        //}

                        //@Override
                        //public void onAnimationEnd(Animation animation) {
                         //
                       /*     question.startAnimation(animfromright);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });*/
                   // question.startAnimation(animtoleft);
                    ConstantsData.questions.remove(random);
                    max = ConstantsData.questions.size() - 1;
                } /*else if (ConstantsData.questions.size()>1) {
                    question.setAnimation(animtoleft);
                    question.setAnimation(animfromright);
                    animtoleft.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            question.setText(ConstantsData.questions.get(0));
                            question.startAnimation(animfromright);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    question.startAnimation(animtoleft);

                    ConstantsData.questions.remove(0);
                    max = ConstantsData.questions.size() - 1;
                }*/ else {
                    android.app.AlertDialog.Builder quitDialog = new android.app.AlertDialog.Builder(GameActivity.this);
                    quitDialog.setTitle("All the questions of this category are exhaushed.");
                    quitDialog.setPositiveButton("QUIT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    quitDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                        }
                    });
                    quitDialog.show();
                }
            }
        });

        /*
        final int min = 20;
        final int max = 80;
        final int random = new Random().nextInt((max - min) + 1) + min;
        */
    }

    @Override
    public void onBackPressed() {
        android.app.AlertDialog.Builder quitDialog = new android.app.AlertDialog.Builder(GameActivity.this);
        quitDialog.setTitle("Are you sure you want to quit the game?");
        quitDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        quitDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
            }
        });
        quitDialog.show();
    }
}
