package ru.ipimenov.violetrainbow;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import ru.ipimenov.violetrainbow.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayoutTabs;
    Toolbar toolBar;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        viewPager = findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        tabLayoutTabs = findViewById(R.id.tabLayoutTabs);
        tabLayoutTabs.setTabTextColors(getResources().getColor(R.color.colorWhite), getResources().getColor(R.color.colorAccent));
        tabLayoutTabs.setupWithViewPager(viewPager);
    }

    // Добавляем фрагменты во вкладки
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new CatalogFragment(NetworkUtils.RU_UA_SELECTION, 2), "Российско-Украинская селекция");
        adapter.addFragment(new CatalogFragment(NetworkUtils.FOREIGN_SELECTION, 2), "Зарубежная селекция");
        adapter.addFragment(new CatalogFragment(NetworkUtils.MINI_SELECTION, 2), "Миниатюры и трейлеры");
        viewPager.setAdapter(adapter);
    }

    private static class Adapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public Adapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }
    }
}
