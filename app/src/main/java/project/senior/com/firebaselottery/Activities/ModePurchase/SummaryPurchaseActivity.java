package project.senior.com.firebaselottery.Activities.ModePurchase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import project.senior.com.firebaselottery.R;

public class SummaryPurchaseActivity extends AppCompatActivity {

    String s[] = {"win", "lose"};
    float per[] = {50.0f, 50.0f};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_purchase);

        setupPieChart();
    }

    private void setupPieChart(){
        List<PieEntry> pieEntries = new ArrayList<>();
        for(int i = 0; i < per.length; i++){
            pieEntries.add(new PieEntry(per[i], s[i]));
        }


        PieDataSet dataSet = new PieDataSet(pieEntries, "Percentage");
        PieData data = new PieData(dataSet);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieChart chart = (PieChart)findViewById(R.id.pieChart);
        chart.setData(data);
        chart.animateY(1000);
        chart.invalidate();
    }
}


