package project.senior.com.firebaselottery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import project.senior.com.firebaselottery.Activities.CheckLotteryActivity;
import project.senior.com.firebaselottery.Activities.DisplayLotteriesActivity;
import project.senior.com.firebaselottery.Activities.ModePurchase.ModePurchaseActivity;
import project.senior.com.firebaselottery.Activities.ModeSimulation.ModeSimulationActivity;

public class MainActivity extends AppCompatActivity {

    private Button buttonCheckLottery, buttonDisplayLottery, buttonModeSimulation, buttonModePurchase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initObjects();
    }

    private void initObjects(){
        buttonCheckLottery = (Button) findViewById(R.id.buttonCheckLottery);
        buttonDisplayLottery = (Button) findViewById(R.id.buttonDisplayLottery);
        buttonModeSimulation = (Button) findViewById(R.id.buttonModeSimulation);
        buttonModePurchase = (Button) findViewById(R.id.buttonModePurchase);
    }

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
    }

}
