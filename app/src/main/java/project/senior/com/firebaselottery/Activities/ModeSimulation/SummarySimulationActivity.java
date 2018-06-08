package project.senior.com.firebaselottery.Activities.ModeSimulation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import project.senior.com.firebaselottery.R;

public class SummarySimulationActivity extends AppCompatActivity {

    private TextView sim_amountLotteryTextView, sim_winTextView, sim_didNotWinTextView, sim_totalPaidTextView,
            sim_totalValueTextView, sim_profitTextView, sim_lossTextView;
    private DatabaseReference refLottery, refModeSimulation;
    private Toolbar sim_toolbar;
    int win = 0;
    int didNotWin = 0;
    int totalLottery = 0;
    int totalPaid = 0;
    int totalValue =0 ;
    private final int PRICE = 80;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_simulation);

        //Initial View
        sim_amountLotteryTextView = (TextView) findViewById(R.id.sim_amountLotteryTextView);
        sim_winTextView = (TextView) findViewById(R.id.sim_winTextView);
        sim_didNotWinTextView = (TextView) findViewById(R.id.sim_didNotWinTextView);
        sim_totalPaidTextView = (TextView) findViewById(R.id.sim_totalPaidTextView);
        sim_totalValueTextView = (TextView) findViewById(R.id.sim_totalValueTextView);
        sim_toolbar = (Toolbar) findViewById(R.id.sim_toolbar);
        sim_profitTextView = (TextView) findViewById(R.id.sim_profitTextView);
        sim_lossTextView = (TextView) findViewById(R.id.sim_lossTextView);

        // Display Button Back To ModeSimulationActivity
        setSupportActionBar(sim_toolbar);
        getSupportActionBar().setTitle("สรุปผลโหมดจำลอง");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Firebase
        refLottery = FirebaseDatabase.getInstance().getReference("LOTTERY");
        refModeSimulation = refLottery.child("ModeSimulation");
        refModeSimulation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    for(DataSnapshot data: dataSnapshot.getChildren()){

                        //Win
                        boolean first_prize = data.child("lottery_status").getValue().toString().equals("รางวัลที่ 1");
                        boolean second_prize = data.child("lottery_status").getValue().toString().equals("รางวัลที่ 2");
                        boolean third_prize = data.child("lottery_status").getValue().toString().equals("รางวัลที่ 3");
                        boolean fourth_prize = data.child("lottery_status").getValue().toString().equals("รางวัลที่ 4");
                        boolean fifth_prize = data.child("lottery_status").getValue().toString().equals("รางวัลที่ 5");
                        boolean last_2 = data.child("lottery_status").getValue().toString().equals("เลขท้าย 2 ตัว");
                        boolean first_3 = data.child("lottery_status").getValue().toString().equals("เลขหน้า 3 ตัว");
                        boolean last_3 = data.child("lottery_status").getValue().toString().equals("เลขท้าย 3 ตัว");
                        boolean near_1 = data.child("lottery_status").getValue().toString().equals("รางวัลข้างเคียงรางวัลที่ 1");

                        //Did not win
                        boolean did_notWin = data.child("lottery_status").getValue().toString().equals("ไม่ถูกรางวัล");

                        if(win == 0 || didNotWin == 0){
                            sim_winTextView.setText("-");
                            sim_didNotWinTextView.setText("-");
                        }

                        if(first_prize == true){
                            win+=1;
                            totalLottery+=1;
                            sim_winTextView.setText(String.valueOf(win));

                        } if(second_prize == true){
                            win+=1;
                            totalLottery+=1;
                            sim_winTextView.setText(String.valueOf(win));

                        } if(third_prize == true){
                            win+= 1;
                            totalLottery+=1;
                            sim_winTextView.setText(String.valueOf(win));

                        } if(fourth_prize == true){
                            win+=1;
                            totalLottery+=1;
                            sim_winTextView.setText(String.valueOf(win));

                        } if(fifth_prize == true){
                            win+=1;
                            totalLottery+=1;
                            sim_winTextView.setText(String.valueOf(win));

                        } if(last_2 == true){
                            win+=1;
                            totalLottery+=1;
                            sim_winTextView.setText(String.valueOf(win));

                        } if(first_3 == true){
                            win+=1;
                            totalLottery+=1;
                            sim_winTextView.setText(String.valueOf(win));

                        } if (last_3 == true){
                            win+=1;
                            totalLottery+=1;
                            sim_winTextView.setText(String.valueOf(win));

                        } if(near_1 == true){
                            win+=1;
                            totalLottery+=1;
                            sim_winTextView.setText(String.valueOf(win));

                        } if(did_notWin == true){
                            didNotWin += 1;
                            totalLottery+=1;
                            sim_didNotWinTextView.setText(String.valueOf(didNotWin));
                        }

                        sim_amountLotteryTextView.setText(String.valueOf(totalLottery));

                        // Display Amount You Paid
                        DecimalFormat comma = new DecimalFormat("###,###,###");
                        int paid = Integer.parseInt(data.child("lottery_amount").getValue().toString());
                        totalPaid += paid;
                        int total_paid = totalPaid * PRICE;
                        sim_totalPaidTextView.setText(String.valueOf(comma.format(total_paid)));

                        float percentage_win = (win * 100) / (win + didNotWin);
                        float percentage_didNotWin = (didNotWin * 100)/ (win + didNotWin);
                        Log.i("gggg", String.valueOf(percentage_win));

                        //Display Profit and Loss
                        int total_value = Integer.parseInt(data.child("value").getValue().toString());
                        totalValue += total_value;
                        sim_totalValueTextView.setText(String.valueOf(comma.format(totalValue)));

                        int profit_loss = totalValue - total_paid;
                        if(totalValue - total_paid >= 0){
                            sim_profitTextView.setText(String.valueOf(comma.format(profit_loss)));
                            sim_lossTextView.setText("-");
                        } else {
                            sim_lossTextView.setText(String.valueOf(comma.format(profit_loss)));
                            sim_profitTextView.setText("-");

                        }



                        // Display Pie Chart
                        float[] type = {percentage_win, percentage_didNotWin};
                        String[] str = {"ถูกรางวัล", "ไม่ถูกรางวัล"};

                        List<PieEntry> pieEntries = new ArrayList<>();
                        for(int i = 0; i < type.length; i++){
                            pieEntries.add(new PieEntry(type[i], str[i]));
                        }


                        PieDataSet dataSet = new PieDataSet(pieEntries, "หน่วย : เปอร์เซ็นต์");
                        PieData data1 = new PieData(dataSet);
                        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

                        PieChart sim_pieChart = (PieChart) findViewById(R.id.sim_pieChart);
                        sim_pieChart.setData(data1);
                        sim_pieChart.animateY(1000);
                        sim_pieChart.invalidate();

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        sim_pieChart = (PieChart) findViewById(R.id.sim_pieChart);
//        data = new ArrayList<>();
//        data.add(new ChartData("win", win));
//        data.add(new ChartData("did not win", 2));
//
//        data1 = new ArrayList<>();
//        data1.add(new ChartData("win", 10, Color.BLACK));
//        data1.add(new ChartData("did not win", 2, Color.BLACK));
//        data1.add(new ChartData("did not win", 10, Color.BLACK));
//        data1.add(new ChartData("win", 2, Color.BLACK));
//        data1.add(new ChartData("did not win", 10, Color.BLACK));
//
//
//        sim_pieChart.setChartData(data1);

    }

//    private void setupPieChart(){
//
//        int[] type = {win, didNotWin};
//        String[] str = {"Win", "Did not win"};
//
//        Log.i("gggg", String.valueOf(type));
//
//        List<PieEntry> pieEntries = new ArrayList<>();
//        for(int i = 0; i < type.length; i++){
//            pieEntries.add(new PieEntry(type[i], str[i]));
//        }
//
//
//        PieDataSet dataSet = new PieDataSet(pieEntries, "Percentage");
//        PieData data = new PieData(dataSet);
//        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
//
//        PieChart sim_pieChart = (PieChart) findViewById(R.id.sim_pieChart);
//        sim_pieChart.setData(data);
//        sim_pieChart.animateY(1000);
//        sim_pieChart.invalidate();
//    }

}
