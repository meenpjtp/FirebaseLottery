package project.senior.com.firebaselottery.Activities.ModePurchase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import project.senior.com.firebaselottery.FirebaseHelper.FBAdapter.ModePurchaseAdapter;
import project.senior.com.firebaselottery.FirebaseHelper.FBHelper.ModePurchaseHelper;
import project.senior.com.firebaselottery.R;

public class ModePurchaseActivity extends AppCompatActivity {

    private RecyclerView recyclerviewPurchase;
    private FloatingActionMenu pur_fabMenu;
    private FloatingActionButton pur_fabAdd,pur_fabStat;
    private Toolbar m_purToolbar;

    //Firebase
    ModePurchaseHelper helper;
    ModePurchaseAdapter adapter;
    DatabaseReference refLottery, refModePurchase1, refModePurchase2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_purchase);

        initObjects();

        //Firebase
        refLottery = FirebaseDatabase.getInstance().getReference("LOTTERY");
        refModePurchase1 = refLottery.child("ModePurchase");
        helper = new ModePurchaseHelper(refModePurchase1);
        adapter = new ModePurchaseAdapter(this, helper.retrieveData());
        helper.retrieveData();

    }


    // This method is to initialize objects to be used
    private void initObjects(){

        // ToolBar
        m_purToolbar = (Toolbar) findViewById(R.id.m_purToolbar);
        pur_fabAdd = (FloatingActionButton) findViewById(R.id.pur_fabAdd);
        pur_fabStat = (FloatingActionButton) findViewById(R.id.pur_fabStat);
        recyclerviewPurchase = (RecyclerView) findViewById(R.id.recyclerviewPurchase);
        pur_fabMenu = (FloatingActionMenu) findViewById(R.id.pur_fabMenu);

        // Floating Action Button Start AddLotteryPpurchaseActivity Intent
        pur_fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(ModePurchaseActivity.this, AddLotteryPurchaseActivity.class);
                startActivity(a);
            }
        });

        pur_fabStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(ModePurchaseActivity.this, SummaryPurchaseActivity.class);
                startActivity(b);
            }
        });

        // Display Button Back To ModePurchaseActivity
        setSupportActionBar(m_purToolbar);
        getSupportActionBar().setTitle("โหมดซื้อจริง");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // RecyclerView
        recyclerviewPurchase.setLayoutManager(new LinearLayoutManager(this));
        recyclerviewPurchase.setItemAnimator(new DefaultItemAnimator());
        recyclerviewPurchase.setHasFixedSize(true);
        recyclerviewPurchase.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.m_refresh:

                refModePurchase1.orderByChild("timeStamp");
                recyclerviewPurchase.setAdapter(adapter);
                adapter.notifyDataSetChanged();

        }
        return super.onOptionsItemSelected(item);
    }

}

// Update list history to recyclerview
//    private void getLotteries(){
//        listModel.clear();
//
//        DBPurchaseAdapter db = new DBPurchaseAdapter(this);
//        db.openDB();
//        Cursor cursor = db.retrieve();
//
//        while (cursor.moveToNext()){
//            int id = cursor.getInt(0);
//            String lottery_date = cursor.getString(1);
//            String lottery_number = cursor.getString(2);
//            String lottery_amount = cursor.getString(3);
//            String lottery_paid = cursor.getString(4);
//            String lottery_status = cursor.getString(5);
//            String lottery_value = cursor.getString(6);
//
//
//            PurchaseModel model = new PurchaseModel();
//            model.setId(id);
//            model.setLottery_date(lottery_date);
//            model.setLottery_number(lottery_number);
//            model.setLottery_amount(lottery_amount);
//            model.setLottery_paid(lottery_paid);
//            model.setLottery_status(lottery_status);
//            model.setLottery_value(lottery_value);
//
//            listModel.add(model);
//
//        }
//        db.closeDB();
//
//        if(listModel.size() > 0){
//            recyclerviewPurchase.setAdapter(adapter);
//        }
//    }

// Search List Lottery
//    private void searchItem (String search)
//    {
//        listModel.clear();
//
//        DBPurchaseAdapter db=new DBPurchaseAdapter(this);
//        db.openDB();
//        PurchaseModel model1=null;
//        Cursor cursor =db.retrieveSearch(search);
//
//        while (cursor.moveToNext())
//        {
//            int id=cursor.getInt(0);
//            String lottery_date=cursor.getString(1);
//            String lottery_number=cursor.getString(2);
//            String lottery_amount=cursor.getString(3);
//            String lottery_paid=cursor.getString(4);
//            String lottery_status=cursor.getString(5);
//            String lottery_value = cursor.getString(6);
//
//
//            model1 = new PurchaseModel();
//            model1.setId(id);
//            model1.setLottery_date(lottery_date);
//            model1.setLottery_number(lottery_number);
//            model1.setLottery_amount(lottery_amount);
//            model1.setLottery_paid(lottery_paid);
//            model1.setLottery_status(lottery_status);
//            model1.setLottery_value(lottery_value);
//
//            listModel.add(model1);
//        }
//
//        db.closeDB();
//
//        recyclerviewPurchase.setAdapter(adapter);
//    }
