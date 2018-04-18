package project.senior.com.firebaselottery.Activities.ModeSimulation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import project.senior.com.firebaselottery.R;

public class SimStatActivity extends AppCompatActivity {

    private PieChart sim_pieChart;
    private Date date;
    private String sim_date;
    private ArrayList<String> arrayType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_simulation);

//        mPresenter = new SimStatTypeActivityPresenter(this, this.getApplicationContext());

        date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("MM/yyyy");
        sim_date = String.valueOf(format.format(date));

        arrayType = new ArrayList<>();
        arrayType.add(getResources().getString(R.string.text_win_lottery));
        arrayType.add(getResources().getString(R.string.text_did_not_win_lottery));

        sim_pieChart = (PieChart) findViewById(R.id.sim_pieChart);
    }


}
