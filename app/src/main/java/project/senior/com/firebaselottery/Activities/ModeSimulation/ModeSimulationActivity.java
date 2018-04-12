package project.senior.com.firebaselottery.Activities.ModeSimulation;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import project.senior.com.firebaselottery.DBHelper.DBHelperSimulation.DBSimulationAdapter;
import project.senior.com.firebaselottery.Models.SimulationModel;
import project.senior.com.firebaselottery.R;
import project.senior.com.firebaselottery.RecyclerView.Adapter.SimulationAdapter;

public class ModeSimulationActivity extends AppCompatActivity {

    private RecyclerView recyclerviewSimulation;
    private FloatingActionButton fabAddLotterySimulation;

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

        /**
         * Floating Action Button
         */
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
    }

    private void initObjects(){

        listModel = new ArrayList<>();
        adapter = new SimulationAdapter(this, listModel);

        // RecyclerView
        recyclerviewSimulation = (RecyclerView) findViewById(R.id.recyclerviewSimulation);
        recyclerviewSimulation.setLayoutManager(new LinearLayoutManager(this));
        recyclerviewSimulation.setItemAnimator(new DefaultItemAnimator());
        recyclerviewSimulation.setHasFixedSize(true);
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

            SimulationModel model = new SimulationModel();
            model.setId(id);
            model.setLottery_date(lottery_date);
            model.setLottery_number(lottery_number);
            model.setLottery_amount(lottery_amount);
            model.setLottery_paid(lottery_paid);
            model.setLottery_status(lottery_status);

            listModel.add(model);

        }
        db.closeDB();

        if(listModel.size() > 0){
            recyclerviewSimulation.setAdapter(adapter);
        }
    }

}
