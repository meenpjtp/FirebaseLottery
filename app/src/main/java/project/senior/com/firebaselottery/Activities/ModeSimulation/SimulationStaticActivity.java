package project.senior.com.firebaselottery.Activities.ModeSimulation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;

import project.senior.com.firebaselottery.R;

public class SimulationStaticActivity extends AppCompatActivity {

    private PieChart sim_pieChart;
    private ArrayList<String> arrayType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_simulation);


    }


}
