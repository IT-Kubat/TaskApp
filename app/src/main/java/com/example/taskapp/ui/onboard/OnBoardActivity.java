package com.example.taskapp.ui.onboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import com.example.taskapp.MainActivity;
import com.example.taskapp.R;
import com.example.taskapp.ui.Prefs;
import com.google.android.material.tabs.TabLayout;


public class OnBoardActivity extends AppCompatActivity {

    TabLayout tabLayout;
    Button btnSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager()));
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager,true);


    }

    public void onClick(View view) {
        Prefs.getInstance(getApplicationContext()).saveShown();
        startActivity(new Intent(getApplicationContext(),  MainActivity.class));
        finish();
    }

    public class SectionsPagerAdapter   extends FragmentPagerAdapter{

        public SectionsPagerAdapter(@NonNull FragmentManager fm) {
            super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putInt("pos", position);
            BoardFragment fragment = new BoardFragment();
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
