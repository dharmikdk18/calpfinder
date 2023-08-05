package com.example.clapphonefinder.adapter;

import androidx.annotation.*;
import androidx.fragment.app.*;
import java.util.*;

public class MainPagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> fragmentList = new ArrayList<>();
    private final ArrayList<String> titleList = new ArrayList<>();

    public MainPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    public void addFragment(Fragment fragment, String title){
        fragmentList.add(fragment);
        titleList.add(title);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
