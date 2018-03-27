package project.senior.com.firebaselottery.Activities.ModePurchase;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import project.senior.com.firebaselottery.R;

public class ModePurchaseActivity extends AppCompatActivity {

    private RecyclerView recyclerviewPurchase;
    private FloatingActionButton fabAddLotteryPurchase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_purchase);

        initObjects();
    }

    private void initObjects(){
        recyclerviewPurchase = (RecyclerView) findViewById(R.id.fabAddLotteryPurchase);
        fabAddLotteryPurchase = (FloatingActionButton) findViewById(R.id.fabAddLotteryPurchase);

    }
}
