package tasks.com.pharmproject.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tasks.com.pharmproject.R;
import tasks.com.pharmproject.fragment.ChatsFragment;
import tasks.com.pharmproject.fragment.HomeFragment;
import tasks.com.pharmproject.fragment.NotificationsFragment;
import tasks.com.pharmproject.fragment.ProfileFragment;

public class Main2Activity extends AppCompatActivity {
    FloatingActionButton fab;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Setting ViewPager for each Tabs
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(viewPager);
        final TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        tabs.getTabAt(3).setIcon(R.mipmap.ic_notifay4);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText() == "") {
                    tab.setIcon(R.mipmap.ic_notification);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getText() == "") {
                    tab.setIcon(R.mipmap.ic_notifay4);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (tabs.getSelectedTabPosition()) {
                    case 0:
                        Toast.makeText(Main2Activity.this, "profile", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(Main2Activity.this, "home", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(Main2Activity.this, "chats", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(Main2Activity.this, "notification", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new ProfileFragment(), "Profile");
        adapter.addFragment(new HomeFragment(), "Home");
        adapter.addFragment(new ChatsFragment(), "Chats");
        adapter.addFragment(new NotificationsFragment(), "");
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
      /*  if (id == R.id.sign_out) {
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }


}
