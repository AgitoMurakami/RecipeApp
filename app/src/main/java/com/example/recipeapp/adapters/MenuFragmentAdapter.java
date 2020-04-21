package com.example.recipeapp.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.recipeapp.fragments.FavoriteFragment;
import com.example.recipeapp.fragments.MenuFragment;

public class MenuFragmentAdapter extends FragmentStatePagerAdapter {
    public MenuFragmentAdapter(FragmentManager fm){
        super(fm);
    }

    @Override    public Fragment getItem(int position) {
        switch (position){
            case 0: return new MenuFragment();
            case 1: return new FavoriteFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override    public CharSequence getPageTitle(int position) {        switch (position){
        case 0: return "Tab 1";
        case 1: return "Tab 2";
        default: return null;
    }
    }
}