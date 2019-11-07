package com.interviewsortout.cakephpapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.NativeExpressAdView;
import com.interviewsortout.cakephpapp.adapter.PostListAdapter;
import com.interviewsortout.cakephpapp.databinding.ActivityMainBinding;
import com.interviewsortout.cakephpapp.model.PostListModel;
import com.interviewsortout.cakephpapp.postListViewModel.ListViewModel;

import java.util.List;

public class MainActivity extends BaseActivity implements OnItemCLickListener{
    private static final String TAG = MainActivity.class.getSimpleName();
    private ContentLoadingProgressBar mProgressBar;
    private RecyclerView recyclerView;
    private PostListAdapter postListAdapter;
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;
    private ListViewModel listViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        enitiizeElement(activityMainBinding);
        addImplimentation();
        // MVVM architecture
        listViewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        listViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) mProgressBar.show();
                else mProgressBar.hide();
            }
        });

        listViewModel.getListDataValue().observe(this, new Observer<List<PostListModel>>() {
            @Override
            public void onChanged(List<PostListModel> list) {
                listViewModel.getIsLoading().postValue(false);
                postListAdapter.setData(list);
            }
        });
    }

    private void addImplimentation() {
        final NativeExpressAdView adView = (NativeExpressAdView) findViewById(R.id.adView1);
        adView.setVisibility(View.GONE);
        AdRequest request = new AdRequest.Builder()
                .addTestDevice(String.valueOf(R.string.appid))
                .build();
        adView.loadAd(request);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adView.setVisibility(View.VISIBLE);
            }
        });
        mInterstitialAd = new InterstitialAd(this);

// set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));


// Load ads into Interstitial Ads

        mAdView = (AdView) findViewById(R.id.adView);
        mAdView.setVisibility(View.GONE);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mAdView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void enitiizeElement(ActivityMainBinding activityMainBinding) {
        mProgressBar = activityMainBinding.progressBar;
        recyclerView = activityMainBinding.rvPostList;
        Toolbar toolbar = (Toolbar) activityMainBinding.toolbar;
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);



        ((TextView) findViewById(R.id.txvHeader)).setText(getString(R.string.app_name));
        ((TextView) findViewById(R.id.txvHeader)).setGravity(Gravity.CENTER);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(layoutManager);
        postListAdapter = new PostListAdapter(this);
        postListAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(postListAdapter);

    }

    @Override
    public void onItemClick(View v, int pos) {
        if (v.getId() == R.id.cardView) {
            Intent intent = new Intent(this, WebviewActivity.class);
            intent.putExtra(AppConstant.EXTRAS.TITLE, postListAdapter.getItemAt(pos).getTitle());
            intent.putExtra(AppConstant.EXTRAS.WEB_URL, postListAdapter.getItemAt(pos).getUrl());
            startActivity(intent);

        }else if(v.getId() == R.id.ivShare){
           share(this, postListAdapter.getItemAt(pos).getUrl());
        }
    }

    public  void share(Activity activity, String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        activity.startActivity(sendIntent);
    }
}
