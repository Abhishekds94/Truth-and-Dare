package truthdare.developer.com.app.activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import truthdare.developer.com.app.utils.CarouselEffectTransformer;
import truthdare.developer.com.app.beans.HomeCardBean;
import truthdare.developer.com.app.R;
import truthdare.developer.com.app.adapters.HomePagerAdapter;

public class HomeActivity extends AppCompatActivity {

    ArrayList<HomeCardBean> arrayList;
    ViewPager viewPager;
    HomePagerAdapter homePagerAdapter;
    int[] colors;
    private FirebaseFirestore db;
    private final static String TAG = "HomeActivity";
    private ProgressDialog progressDialog;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.home_activity_layout);
        MobileAds.initialize(this, "ca-app-pub-4882246940435880~8039652959");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        db = FirebaseFirestore.getInstance();
        viewPager = findViewById(R.id.home_viewpager);
        arrayList = new ArrayList<>();
        progressDialog = new ProgressDialog(HomeActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        db.collection("categories")
                .get()
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(HomeActivity.this,"Please Try After Sometime..",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        finish();
                        Log.e(TAG, "onFailure: "+e.toString() );
                        Log.e(TAG, "Error adding document", e);
                    }
                })

                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for(DocumentSnapshot document : task.getResult()) {
                                colors= new int[2];
                                colors[0] = Color.parseColor(document.getString("color1").toString());
                                colors[1] = Color.parseColor(document.getString("color2").toString());
                                arrayList.add(new HomeCardBean(document.getString("catName"),colors , document.getId()));
                                homePagerAdapter = new HomePagerAdapter(HomeActivity.this,arrayList);

                                viewPager.setClipChildren(false);
                                viewPager.setClipToPadding(false);
                                viewPager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.pager_margin));
                                viewPager.setOffscreenPageLimit(5);
                                viewPager.setPageTransformer(false, new CarouselEffectTransformer(HomeActivity.this)); // Set transformer
                                viewPager.setAdapter(homePagerAdapter);
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                progressDialog.dismiss();
                            }
                        } else {
                            Log.e(TAG, "Error getting documents.", task.getException());
                        }
                    }

                });
    }
}
