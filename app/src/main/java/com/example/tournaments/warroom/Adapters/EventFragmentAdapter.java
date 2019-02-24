package com.example.tournaments.warroom.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.cardview.widget.CardView;

import com.example.tournaments.warroom.Fragments.EventFragment;

import java.util.ArrayList;
import java.util.List;

public class EventFragmentAdapter  extends FragmentStatePagerAdapter implements CardAdapter  {
    float mBaseElevation;
    public EventFragmentAdapter(FragmentManager fm) {
        super(fm);
    }
    public EventFragmentAdapter(FragmentManager fm, float baseElevation) {
        super(fm);
        mBaseElevation = baseElevation;
    }
    private final List<EventFragment> mFragmentList=new ArrayList<>();
    private final List<String> mFragmentTitleList=new ArrayList<>();

    public void addFragment(EventFragment fragment,String title)
    {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public float getBaseElevation() {
        return 30;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mFragmentList.get(position).getCardView();
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}