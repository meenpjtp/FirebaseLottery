package project.senior.com.firebaselottery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import project.senior.com.firebaselottery.Activities.ModeSimulation.ModeSimulationActivity;
import project.senior.com.firebaselottery.Fragment.CheckLotteryFragment;
import project.senior.com.firebaselottery.Fragment.DisplayLotteryFragment;
import project.senior.com.firebaselottery.Fragment.PurchaseFragment;
import project.senior.com.firebaselottery.Fragment.SimulationFragment;
import project.senior.com.firebaselottery.Fragment.ViewPager.CustomViewPager;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private Context mContext;

    CustomViewPager customViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initObjects();
    }


    private void initObjects(){

        mContext = this;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        setSupportActionBar(toolbar);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    public Toolbar getToolbar() {
        if (toolbar == null) {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
        }
        return toolbar;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DisplayLotteryFragment(), "ผลรางวัล");
        adapter.addFragment(new CheckLotteryFragment(), "ตรวจล็อตเตอรี่");
        adapter.addFragment(new SimulationFragment(), "โหมดจำลอง");
        adapter.addFragment(new PurchaseFragment(), "โหมดซื้อจริง");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
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

    //Menu Summary
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.m_search:
                Intent intent = new Intent(this, ModeSimulationActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}

/*---------------------- Version.1 ----------------------------*/

    /*private Button buttonCheckLottery, buttonDisplayLottery, buttonModeSimulation, buttonModePurchase;

            buttonCheckLottery = (Button) findViewById(R.id.buttonCheckLottery);
        buttonDisplayLottery = (Button) findViewById(R.id.buttonDisplayLottery);
        buttonModeSimulation = (Button) findViewById(R.id.buttonModeSimulation);
        buttonModePurchase = (Button) findViewById(R.id.buttonModePurchase);


    public void onClick(View view){
        switch (view.getId()){
            case R.id.buttonCheckLottery:
                Intent a= new Intent(MainActivity.this, CheckLotteryActivity.class);
                startActivity(a);
                break;

            case R.id.buttonDisplayLottery:
                Intent b = new Intent(MainActivity.this, DisplayLotteriesActivity.class);
                startActivity(b);
                break;

            case R.id.buttonModeSimulation:
                Intent c = new Intent(MainActivity.this, ModeSimulationActivity.class);
                startActivity(c);
                break;

            case R.id.buttonModePurchase:
                Intent d = new Intent(MainActivity.this, ModePurchaseActivity.class);
                startActivity(d);
                break;
        }
    }*/




