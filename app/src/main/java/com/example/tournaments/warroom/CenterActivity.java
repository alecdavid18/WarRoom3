package com.example.tournaments.warroom;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.example.tournaments.warroom.Adapters.EventFragmentAdapter;
import com.example.tournaments.warroom.Adapters.ShadowTransformer;
import com.example.tournaments.warroom.Fragments.EventFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import androidx.appcompat.app.AppCompatActivity;

public class CenterActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener  {

    View current_layout;
    RelativeLayout tabcontainer;
    EventFragmentAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private EventFragmentAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        tabcontainer=findViewById(R.id.cur_view);

        mCardAdapter=new EventFragmentAdapter(getSupportFragmentManager());
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.feed) {
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                } else if (tabId == R.id.events) {
                    LayoutInflater inflater = LayoutInflater.from(CenterActivity.this);
                    View inflatedLayout = inflater.inflate(R.layout.events_list_layout, null, false);
                    ViewPager mViewPager = (ViewPager) inflatedLayout.findViewById(R.id.viewPager);
                    current_layout = inflatedLayout;
                    tabcontainer.removeAllViews();
                    tabcontainer.addView(inflatedLayout);
                    for (int i = 0; i < 5; ++i) {
                        mCardAdapter.addFragment(new EventFragment(), "HAHA");
                    }
                    mViewPager.setAdapter(mCardAdapter);

                    mFragmentCardAdapter = new EventFragmentAdapter(getSupportFragmentManager(),
                            dpToPixels(2, CenterActivity.this));

                    mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
                    mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mFragmentCardAdapter);

                    mViewPager.setAdapter(mCardAdapter);
                    mViewPager.setPageTransformer(false, mCardShadowTransformer);
                    mViewPager.setOffscreenPageLimit(3);


                } else if (tabId == R.id.prize) {

                } else if (tabId == R.id.my_account) {
                    LayoutInflater inflater = LayoutInflater.from(CenterActivity.this);
                    View inflatedLayout = inflater.inflate(R.layout.my_profile_layout, null, false);
                    ViewPager mViewPager = (ViewPager) inflatedLayout.findViewById(R.id.viewPager);
                    current_layout = inflatedLayout;
                    tabcontainer.removeAllViews();
                    tabcontainer.addView(inflatedLayout);
                }
            }
        });
    }
    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        mCardShadowTransformer.enableScaling(b);
        mFragmentCardShadowTransformer.enableScaling(b);
    }

}