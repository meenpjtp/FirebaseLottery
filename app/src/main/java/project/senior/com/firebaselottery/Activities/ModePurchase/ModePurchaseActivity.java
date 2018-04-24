package project.senior.com.firebaselottery.Activities.ModePurchase;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.View;
import android.widget.SearchView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

import project.senior.com.firebaselottery.DBHelper.DBHelperPurchase.DBPurchaseAdapter;
import project.senior.com.firebaselottery.Models.PurchaseModel;
import project.senior.com.firebaselottery.R;
import project.senior.com.firebaselottery.RecyclerView.Adapter.PurchaseAdapter;
import project.senior.com.firebaselottery.RecyclerView.Swipe.PurchaseSwipe;

public class ModePurchaseActivity extends AppCompatActivity {

    private RecyclerView recyclerviewPurchase;
    private FloatingActionMenu pur_fabMenu;
    private FloatingActionButton pur_fabAdd,pur_fabStat;
    private Toolbar m_purToolbar;

    // SQLite
    private ArrayList<PurchaseModel> listModel;
    PurchaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_purchase);

        initObjects();
        getLotteries();

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

        // Display Button Back To ModePurchaseActivity
        setSupportActionBar(m_purToolbar);
        getSupportActionBar().setTitle("โหมดซื้อจริง");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //RecyclerView
        listModel = new ArrayList<>();
        adapter = new PurchaseAdapter(this,listModel);

        // RecyclerView
        recyclerviewPurchase.setLayoutManager(new LinearLayoutManager(this));
        recyclerviewPurchase.setItemAnimator(new DefaultItemAnimator());
        recyclerviewPurchase.setHasFixedSize(true);
        recyclerviewPurchase.setAdapter(adapter);

        // RecyclerView Swipe To Delete
        ItemTouchHelper.Callback callback = new PurchaseSwipe(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerviewPurchase);
    }

    // Update list history to recyclerview
    private void getLotteries(){
        listModel.clear();

        DBPurchaseAdapter db = new DBPurchaseAdapter(this);
        db.openDB();
        Cursor cursor = db.retrieve();

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String lottery_date = cursor.getString(1);
            String lottery_number = cursor.getString(2);
            String lottery_amount = cursor.getString(3);
            String lottery_paid = cursor.getString(4);
            String lottery_status = cursor.getString(5);
            String lottery_value = cursor.getString(6);


            PurchaseModel model = new PurchaseModel();
            model.setId(id);
            model.setLottery_date(lottery_date);
            model.setLottery_number(lottery_number);
            model.setLottery_amount(lottery_amount);
            model.setLottery_paid(lottery_paid);
            model.setLottery_status(lottery_status);
            model.setLottery_value(lottery_value);

            listModel.add(model);

        }
        db.closeDB();

        if(listModel.size() > 0){
            recyclerviewPurchase.setAdapter(adapter);
        }
    }

    // Search List Lottery
    private void searchItem (String search)
    {
        listModel.clear();

        DBPurchaseAdapter db=new DBPurchaseAdapter(this);
        db.openDB();
        PurchaseModel model1=null;
        Cursor cursor =db.retrieveSearch(search);

        while (cursor.moveToNext())
        {
            int id=cursor.getInt(0);
            String lottery_date=cursor.getString(1);
            String lottery_number=cursor.getString(2);
            String lottery_amount=cursor.getString(3);
            String lottery_paid=cursor.getString(4);
            String lottery_status=cursor.getString(5);
            String lottery_value = cursor.getString(6);


            model1 = new PurchaseModel();
            model1.setId(id);
            model1.setLottery_date(lottery_date);
            model1.setLottery_number(lottery_number);
            model1.setLottery_amount(lottery_amount);
            model1.setLottery_paid(lottery_paid);
            model1.setLottery_status(lottery_status);
            model1.setLottery_value(lottery_value);

            listModel.add(model1);
        }

        db.closeDB();

        recyclerviewPurchase.setAdapter(adapter);
    }

    //Menu SearchView
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.m_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint(getString(R.string.text_search_date));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchItem(s);
                return false;
            }
        });
        return true;

    }




}
