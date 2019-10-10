package truthdare.developer.com.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

import truthdare.developer.com.app.activities.AddPlayersActivity;
import truthdare.developer.com.app.activities.SelectOptionActivity;
import truthdare.developer.com.app.beans.HomeCardBean;
import truthdare.developer.com.app.R;
import truthdare.developer.com.app.utils.ConstantsData;

public class HomePagerAdapter extends PagerAdapter {
    Context context;
    ArrayList<HomeCardBean> arrayList;
    GradientDrawable gd;
    InterstitialAd interstitialAd;

    public HomePagerAdapter(Context context, ArrayList<HomeCardBean> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        createInterstitial(context);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = null;

                view = LayoutInflater.from(context).inflate(R.layout.home_cardview_layout, null);

                TextView textView = view.findViewById(R.id.card_tv1);
                textView.setText(arrayList.get(position).getCategory());

                 gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, arrayList.get(position).getColor());
                ConstraintLayout constraintLayout = view.findViewById(R.id.card_constraint_layout);
                gd.setCornerRadius(0f);

                constraintLayout.setBackground(gd);

                constraintLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

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

                                    ConstantsData.catId = arrayList.get(position).getCatId();
                                    gd = new GradientDrawable(
                                            GradientDrawable.Orientation.TOP_BOTTOM, arrayList.get(position).getColor());
                                    gd.setCornerRadius(20f);
                                    ConstantsData.gradientDrawable = gd;
                                    ConstantsData.color=arrayList.get(position).getColor();
                                    Intent intent = new Intent(context.getApplicationContext(), SelectOptionActivity.class);
                                    context.startActivity(intent);
                                }
                            });
                            loadInterstitial();
                        }


                    }
                });
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == object);
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
