package com.amriksinghpadam.myplayer;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MyViewPagerAdapter extends FragmentStatePagerAdapter {
    private int section;
    public MyViewPagerAdapter(FragmentManager fm,int section) {
        super(fm);
        this.section = section;
    }

    @Override
    public Fragment getItem(int position) {
        position= position+1;
        if(section==1){
            if(position==1){
                SongFragment songFragment = new SongFragment();
                return songFragment;
            }else{
                VideoFragment videoFragment = new VideoFragment();
                return videoFragment;
            }
        }else if(section==2){
            if(position==1){
                VideoFragment videoFragment = new VideoFragment();
                return videoFragment;

            }else{
                SongFragment songFragment = new SongFragment();
                return songFragment;
            }
        }else{
            SongFragment songFragment = new SongFragment();
            return songFragment;
        }

    }
    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(section==1){
            if(position==0){
                return "Songs";
            }else{
                return "Videos";
            }
        }else if(section==2){
            if(position==0){
                return "Videos";
            }else{
                return "Songs";
            }
        }else{
            if(position==0){
                return "Songs";
            }else{
                return "Videos";
            }
        }

    }
}
