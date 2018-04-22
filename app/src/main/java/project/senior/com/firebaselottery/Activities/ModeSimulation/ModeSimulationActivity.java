package project.senior.com.firebaselottery.Activities.ModeSimulation;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import java.util.ArrayList;

import project.senior.com.firebaselottery.DBHelper.DBHelperSimulation.DBSimulationAdapter;
import project.senior.com.firebaselottery.Intent.AddLotterySimulationActivity;
import project.senior.com.firebaselottery.Models.SimulationModel;
import project.senior.com.firebaselottery.R;
import project.senior.com.firebaselottery.RecyclerView.Adapter.SimulationAdapter;
import project.senior.com.firebaselottery.RecyclerView.Swipe.SimulationSwipe;

public class ModeSimulationActivity extends AppCompatActivity {

    private RecyclerView recyclerviewSimulation;
    private FloatingActionButton fabAddLotterySimulation;
    private RelativeLayout activityModeSimulation;
    private LinearLayout linearLayoutWithoutData;

    // SQLite
    private ArrayList<SimulationModel> listModel;
    SimulationAdapter adapter;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_simulation);

        initViews();
        initObjects();
        getLotteries();
//        checkLayoutVisibility(listModel.size());

        // Floating Action Button
        fabAddLotterySimulation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(ModeSimulationActivity.this, AddLotterySimulationActivity.class);
                startActivity(a);
            }
        });
    }

    private void initViews(){
        fabAddLotterySimulation = (FloatingActionButton) findViewById(R.id.fabAddLotterySimulation);
        activityModeSimulation = (RelativeLayout) findViewById(R.id.activityModeSimulation);
        linearLayoutWithoutData = (LinearLayout) findViewById(R.id.linearLayoutWithoutData);
    }

    // Image Gone when add lottery
//    private void checkLayoutVisibility(int size){
//        if(size == 0){
//            recyclerviewSimulation.setVisibility(View.GONE);
//            linearLayoutWithoutData.setVisibility(View.VISIBLE);
//        } else {
//            recyclerviewSimulation.setVisibility(View.VISIBLE);
//            linearLayoutWithoutData.setVisibility(View.GONE);
//        }
//    }



    private void initObjects(){

        listModel = new ArrayList<>();
        adapter = new SimulationAdapter(this,listModel);

        // RecyclerView
        recyclerviewSimulation = (RecyclerView) findViewById(R.id.recyclerviewSimulation);
        recyclerviewSimulation.setLayoutManager(new LinearLayoutManager(this));
        recyclerviewSimulation.setItemAnimator(new DefaultItemAnimator());
        recyclerviewSimulation.setHasFixedSize(true);
        recyclerviewSimulation.setAdapter(adapter);

        // RecyclerView Swipe To Delete
        ItemTouchHelper.Callback callback = new SimulationSwipe(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerviewSimulation);

    }

    // Update list history to recyclerview
    private void getLotteries(){
        listModel.clear();

        DBSimulationAdapter db = new DBSimulationAdapter(this);
        db.openDB();
        Cursor cursor = db.retrieve();

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String lottery_date = cursor.getString(1);
            String lottery_number = cursor.getString(2);
            String lottery_amount = cursor.getString(3);
            String lottery_paid = cursor.getString(4);
            String lottery_status = cursor.getString(5);
            String lottery_value = cursor.getString(6);


            SimulationModel model = new SimulationModel();
            model.setId(id);
            model.setLottery_date(lottery_date);
            model.setLottery_number(lottery_number);
            model.setLottery_amount(lottery_amount);
            model.setLottery_paid(lottery_paid);
            model.setLottery_status(lottery_status);
            model.setLottery_value(lottery_value);

            listModel.add(model);

        }
        db.closeDB();

        if(listModel.size() > 0){
            recyclerviewSimulation.setAdapter(adapter);
        }
    }

    // Search List Lottery
    private void searchItem (String search)
    {
        listModel.clear();

        DBSimulationAdapter db=new DBSimulationAdapter(this);
        db.openDB();
        SimulationModel model1=null;
        Cursor cursor =db.retrieveSearch(search);

        while (cursor.moveToNext())
        {
            int id=cursor.getInt(0);
            String lottery_date=cursor.getString(1);
            String lottery_number=cursor.getString(2);
            String lottery_amount=cursor.getString(3);
            String lottery_paid=cursor.getString(4);
            String lottery_status=cursor.getString(5);
            String lottery_value = cursor.getString(6);


            model1 = new SimulationModel();
            model1.setId(id);
            model1.setLottery_date(lottery_date);
            model1.setLottery_number(lottery_number);
            model1.setLottery_amount(lottery_amount);
            model1.setLottery_paid(lottery_paid);
            model1.setLottery_status(lottery_status);
            model1.setLottery_value(lottery_value);

            listModel.add(model1);
        }

        db.closeDB();

        recyclerviewSimulation.setAdapter(adapter);
    }

    //Menu Summary
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.m_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchItem(s);
                return false;
            }
        });
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.m_summary:
                Intent intent = new Intent(this, SimStatActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
