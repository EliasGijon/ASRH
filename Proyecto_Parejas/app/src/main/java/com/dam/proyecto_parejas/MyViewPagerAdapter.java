package com.dam.proyecto_parejas;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.dam.proyecto_parejas.fragments.Home;
import com.dam.proyecto_parejas.fragments.PlayFragment;
import com.dam.proyecto_parejas.fragments.test;

public class MyViewPagerAdapter extends FragmentStateAdapter {


    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Home();
            case 1:
                return new PlayFragment();
            case 2:
                return new test();
            default:
                return new Home();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
