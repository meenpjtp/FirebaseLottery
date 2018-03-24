package project.senior.com.firebaselottery.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Spinner;

import project.senior.com.firebaselottery.R;

public class CheckLotteryActivity extends AppCompatActivity {

    private Spinner spinnerSelectDate;
    private Button buttonSelect;
    private RecyclerView recyclerViewCheckedLottery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_lottery);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects(){
        spinnerSelectDate = (Spinner) findViewById(R.id.spinnerSelectDate);
        buttonSelect = (Button) findViewById(R.id.buttonSelect);
        recyclerViewCheckedLottery = (RecyclerView) findViewById(R.id.recyclerViewCheckedLotteries);
    }
}
