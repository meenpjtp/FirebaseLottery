package project.senior.com.firebaselottery.Activities.ModeSimulation;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import java.util.ArrayList;
import project.senior.com.firebaselottery.DBHelper.DBHelperSimulation.DBSimulationAdapter;
import project.senior.com.firebaselottery.Models.SimulationModel;
import project.senior.com.firebaselottery.R;
import project.senior.com.firebaselottery.RecyclerView.Adapter.SimulationAdapter;
import project.senior.com.firebaselottery.RecyclerView.Swipe.SimulationSwipe;

public class ModeSimulationActivity extends AppCompatActivity {

    private RecyclerView recyclerviewSimulation;
    private FloatingActionButton sim_fabAdd,sim_fabStat;
    private FloatingActionMenu sim_fabMenu;
    private RelativeLayout activityModeSimulation;
    private Toolbar m_simToolbar;

    // SQLite
    private ArrayList<SimulationModel> listModel;
    SimulationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_simulation);

        initViews();
        initObjects();
        getLotteries();

    }

    private void initViews(){
        activityModeSimulation = (RelativeLayout) findViewById(R.id.activityModeSimulation);
        sim_fabAdd = (FloatingActionButton) findViewById(R.id.sim_fabAdd);
        m_simToolbar = (Toolbar) findViewById(R.id.m_simToolbar);

        // Display Button Back To ModeSimulationActivity
        setSupportActionBar(m_simToolbar);
        getSupportActionBar().setTitle("โหมดจำลอง");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

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

        //Floating Action Button
        sim_fabStat = (FloatingActionButton) findViewById(R.id.sim_fabStat);
        sim_fabMenu = (FloatingActionMenu) findViewById(R.id.sim_fabMenu);

        sim_fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(ModeSimulationActivity.this, AddLotterySimulationActivity.class);
                startActivity(a);
            }
        });

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

    //Menu SearchView
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.m_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint(getString(R.string.text_search_date));

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

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.m_search:
//                Intent intent = new Intent(this, SimStatActivity.class);
//                startActivity(intent);
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

}
