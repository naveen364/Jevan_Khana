package com.codewithnaveen.JevanKhana.Adapters;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.codewithnaveen.JevanKhana.Fragments.SimilarMealFragment;
import com.codewithnaveen.JevanKhana.Fragments.StepsFragment;

public class ViewPagerFragmentAdapter extends FragmentStateAdapter {
    int id;

    public ViewPagerFragmentAdapter(@NonNull FragmentActivity fragmentActivity, int id) {
        super(fragmentActivity);
        this.id = id;
    }

    // return fragments at every position
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new StepsFragment(id); // calls fragment
            case 1:
                return new SimilarMealFragment(id); // status fragment
        }
        return new StepsFragment(id); //chats fragment
    }

    // return total number of tabs in our case we have 3
    @Override
    public int getItemCount() {
        return 2;
    }
}
