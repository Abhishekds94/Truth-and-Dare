package truthdare.developer.com.app.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import truthdare.developer.com.app.R;
import truthdare.developer.com.app.utils.ConstantsData;

public class SelectOptionActivity extends AppCompatActivity {

    ConstraintLayout constraintLayout;
    CircleImageView truth,dare,home;
    private FirebaseFirestore db;
    private final static String TAG ="Options";
    private ProgressDialog progressDialog;
    private AdView mAdView;
    private TextView questiondare;
    int max,random,counter;
    InterstitialAd interstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.select_option_activity_layout);
        createInterstitial(this);
        counter=1;
        MobileAds.initialize(this, "ca-app-pub-4882246940435880~8039652959");
        questiondare  = findViewById(R.id.textView);
        mAdView = findViewById(R.id.adView5);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        db = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(SelectOptionActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading...");
        getData();
        constraintLayout = findViewById(R.id.select_option_constraintlayout);
        constraintLayout.setBackground(ConstantsData.gradientDrawable);
        truth = findViewById(R.id.option_truth_iv);
        dare  = findViewById(R.id.option_dare_iv);
        home = findViewById(R.id.option_home_iv);
        home.setCircleBackgroundColor(ConstantsData.color[0]);
        truth.setCircleBackgroundColor(ConstantsData.color[1]);
        dare.setCircleBackgroundColor(ConstantsData.color[1]);




        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        truth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter++;
                if(counter%10 == 0)
                {
                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                        interstitialAd.setAdListener(new AdListener() {
                            @Override
                            public void onAdLoaded() {
                                // not call show interstitial ad from here
                            }

                            @Override
                            public void onAdClosed() {
                                loadInterstitial();

                                if(ConstantsData.questions.size()>=1) {
                                    max = ConstantsData.questions.size() ;

                                    random = new Random().nextInt(max);

                                    questiondare.setText(ConstantsData.questions.get(random));
                                    ConstantsData.questions.remove(random);
                                }
                                else {
                                    android.app.AlertDialog.Builder quitDialog = new android.app.AlertDialog.Builder(SelectOptionActivity.this);
                                    quitDialog.setTitle("All the truths of this category are exhaushed.");
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
                        loadInterstitial();
                    }
                }
                else
                {
                    if(ConstantsData.questions.size()>=1) {
                        max = ConstantsData.questions.size() ;

                        random = new Random().nextInt(max);

                        questiondare.setText(ConstantsData.questions.get(random));
                        ConstantsData.questions.remove(random);
                    }
                    else {
                        android.app.AlertDialog.Builder quitDialog = new android.app.AlertDialog.Builder(SelectOptionActivity.this);
                        quitDialog.setTitle("All the truths of this category are exhaushed.");
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
            }
        });
        dare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter++;
                if(counter%10 == 0)
                {
                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                        interstitialAd.setAdListener(new AdListener() {
                            @Override
                            public void onAdLoaded() {
                                // not call show interstitial ad from here
                            }

                            @Override
                            public void onAdClosed() {
                                loadInterstitial();

                                if(ConstantsData.dares.size()>=1) {
                                    max = ConstantsData.dares.size();

                                    random = new Random().nextInt(max);

                                    questiondare.setText(ConstantsData.dares.get(random));
                                    ConstantsData.dares.remove(random);
                                }
                                else {
                                    android.app.AlertDialog.Builder quitDialog = new android.app.AlertDialog.Builder(SelectOptionActivity.this);
                                    quitDialog.setTitle("All the dares of this category are exhaushed.");
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
                        loadInterstitial();
                    }
                }
                else
                {
                    if(ConstantsData.dares.size()>=1) {
                        max = ConstantsData.dares.size();

                        random = new Random().nextInt(max);

                        questiondare.setText(ConstantsData.dares.get(random));
                        ConstantsData.dares.remove(random);
                    }
                    else {
                        android.app.AlertDialog.Builder quitDialog = new android.app.AlertDialog.Builder(SelectOptionActivity.this);
                        quitDialog.setTitle("All the dares of this category are exhaushed.");
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
            }
        });
    }

    public void getData()
    {
        progressDialog.show();
        db.collection("dares")
                .get()
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SelectOptionActivity.this,"Please Try After Sometime..",Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onFailure: "+e.toString() );
                        Log.e(TAG, "Error adding document", e);
                        progressDialog.dismiss();
                        finish();
                    }
                })

                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if (task.isSuccessful()) {
                                                   ConstantsData.dares = new ArrayList<>();

                                                   for(DocumentSnapshot document : task.getResult()) {
                                                       if(document.getId().equalsIgnoreCase(ConstantsData.catId))
                                                       {    int index = document.getData().size();
                                                           for(int  i=1 ; i<=index ; i++)
                                                           {
                                                               ConstantsData.dares.add(document.get("dare"+String.valueOf(i)).toString());
                                                               Log.d(TAG+" DARE", document.get("dare"+String.valueOf(i)).toString());
                                                           }
                                                       }
                                                      // Log.d(TAG, document.getId() + " => " + document.getData());
                                                   }
                                               } else {
                                                   Toast.makeText(SelectOptionActivity.this,"Please Try After Sometime..",Toast.LENGTH_SHORT).show();
                                                   Log.e(TAG, "Error getting documents.", task.getException());
                                                   progressDialog.dismiss();
                                                   finish();
                                               }
                                           }

                                       }
                );

        db.collection("truths")
                .get()
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SelectOptionActivity.this,"Please Try After Sometime..",Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onFailure: "+e.toString() );
                        Log.e(TAG, "Error adding document", e);
                        progressDialog.dismiss();
                    }
                })

                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if (task.isSuccessful()) {
                                                   ConstantsData.questions = new ArrayList<>();

                                                   for(DocumentSnapshot document : task.getResult()) {
                                                       if(document.getId().equalsIgnoreCase(ConstantsData.catId))
                                                       {    int index = document.getData().size();
                                                           for(int  i=1 ; i<=index ; i++)
                                                           {
                                                               ConstantsData.questions.add(document.get("truth"+String.valueOf(i)).toString());
                                                               Log.d(TAG+" TRUTH", document.get("truth"+String.valueOf(i)).toString());
                                                           }
                                                       }
                                                       //Log.d(TAG, document.getId() + " => " + document.getData());
                                                   }
                                               } else {
                                                   Toast.makeText(SelectOptionActivity.this,"Please Try After Sometime..",Toast.LENGTH_SHORT).show();
                                                   Log.e(TAG, "Error getting documents.", task.getException());

                                                   progressDialog.dismiss();
                                                   finish();
                                               }
                                           }

                                       }
                );
        progressDialog.dismiss();
    }

    public void createInterstitial(Context context) {
        interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        loadInterstitial();

    }

    public void loadInterstitial() {
        AdRequest interstitialRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(interstitialRequest);

    }
}
