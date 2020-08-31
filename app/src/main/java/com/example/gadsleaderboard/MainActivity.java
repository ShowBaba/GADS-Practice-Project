package com.example.gadsleaderboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gadsleaderboard.adapter.FragmentAdapter;
import com.example.gadsleaderboard.ui.IqFragment;
import com.example.gadsleaderboard.ui.LearningFragment;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ViewPager mViewPager;
    TabLayout mTabLayout;
    ArrayList<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = findViewById(R.id.view_pager);
        mTabLayout = findViewById(R.id.tabs);

        mFragments = new ArrayList<>();

        mFragments.add(new LearningFragment());
        mFragments.add(new IqFragment());

        FragmentAdapter pagerAdapter = new FragmentAdapter(getSupportFragmentManager(), getApplicationContext(), mFragments);
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.getTabAt(0).setText("Learning Leaders");
        mTabLayout.getTabAt(1).setText("Skill IQ Leaders");

        Button submitBtn = findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProjectSubmissionActivity.class);
                startActivity(intent);
            }
        });

    }
}