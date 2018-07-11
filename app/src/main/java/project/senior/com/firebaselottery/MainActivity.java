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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import project.senior.com.firebaselottery.Activities.Fragment.CheckLotteryFragment;
import project.senior.com.firebaselottery.Activities.Fragment.DisplayLotteryFragment;
import project.senior.com.firebaselottery.Activities.Fragment.ViewPager.CustomViewPager;
import project.senior.com.firebaselottery.Activities.ModePurchase.ModePurchaseActivity;
import project.senior.com.firebaselottery.Activities.ModeSimulation.ModeSimulationActivity;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Button btn_sim, btn_pur;

    private Context mContext;

    CustomViewPager customViewPager;

    private int[] tabIcon = {
            R.drawable.ic_lottery_result,
            R.drawable.ic_check_lottery};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initObjects();
    }


    private void initObjects(){

        mContext = this;

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        btn_sim = (Button) findViewById(R.id.btn_sim);
        btn_pur = (Button) findViewById(R.id.btn_pur);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
//        setUpTabIcons();

        // Press button start intent ModeSimulation, ModePurchase
        btn_sim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(MainActivity.this, ModeSimulationActivity.class);
                startActivity(a);
//                Intent a = new Intent(MainActivity.this, DisplayImageLotteryActivity.class);
//                startActivity(a);
            }
        });

        btn_pur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(MainActivity.this, ModePurchaseActivity.class);
                startActivity(b);
            }
        });
    }

    private void setUpTabIcons(){
        tabLayout.getTabAt(0).setIcon(tabIcon[0]);
        tabLayout.getTabAt(1).setIcon(tabIcon[1]);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DisplayLotteryFragment(), "ผลรางวัล");
        adapter.addFragment(new CheckLotteryFragment(), "ตรวจล็อตเตอรี่");
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

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.m_search:
//                Intent intent = new Intent(this, ModeSimulationActivity.class);
//                startActivity(intent);
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

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




