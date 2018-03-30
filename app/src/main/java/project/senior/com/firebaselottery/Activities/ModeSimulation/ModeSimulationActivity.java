package project.senior.com.firebaselottery.Activities.ModeSimulation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import project.senior.com.firebaselottery.R;

public class ModeSimulationActivity extends AppCompatActivity {

    private RecyclerView recyclerviewSimulation;
    private FloatingActionButton fabAddLotterySimulation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_simulation);

        initObjects();

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

    private void initObjects(){
        recyclerviewSimulation = (RecyclerView) findViewById(R.id.recyclerviewSimulation);
        fabAddLotterySimulation = (FloatingActionButton) findViewById(R.id.fabAddLotterySimulation);
    }
}
