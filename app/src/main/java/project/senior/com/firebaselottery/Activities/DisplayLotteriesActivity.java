package project.senior.com.firebaselottery.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import project.senior.com.firebaselottery.RecyclerView.Adapter.LotteriesAdapter;
import project.senior.com.firebaselottery.Models.DisplayLotteryModel;
import project.senior.com.firebaselottery.R;

public class DisplayLotteriesActivity extends AppCompatActivity {

    private Spinner spinnerSelectDate;
    private Button buttonSelect;
    private RecyclerView recyclerViewLotteries;

    private LotteriesAdapter adapter; //RecyclerView
    private List<DisplayLotteryModel> listDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_lotteries);

        initObjects();

        /**
         * Display Dialog when is not connect internet
         */
        if(!isConnected(DisplayLotteriesActivity.this)) buildDialog(DisplayLotteriesActivity.this).show();
        else {

        }

        /**
         * Spinner
         */
        DatabaseReference lottery = FirebaseDatabase.getInstance().getReference("LOTTERY");
        lottery.child("DATE").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> dateSet = new ArrayList<String>();

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String date = snapshot.child("date").getValue(String.class);
                    dateSet.add(date);
                }
                ArrayAdapter<String> dateAdapter = new ArrayAdapter<String>(DisplayLotteriesActivity.this,
                        android.R.layout.simple_spinner_item, dateSet);
                dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSelectDate.setAdapter(dateAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

        /**
         * RecyclerView
         */
        recyclerViewLotteries.setHasFixedSize(true);
        RecyclerView.LayoutManager LM = new LinearLayoutManager(getApplicationContext());
        recyclerViewLotteries.setLayoutManager(LM);
        recyclerViewLotteries.setItemAnimator(new DefaultItemAnimator());
        recyclerViewLotteries.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));

    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects(){
        spinnerSelectDate = (Spinner) findViewById(R.id.spinnerSelectDate);
        buttonSelect = (Button) findViewById(R.id.buttonSelect);
        recyclerViewLotteries = (RecyclerView) findViewById(R.id.recyclerViewLotteries);
        listDisplay = new ArrayList<>();
        adapter = new LotteriesAdapter(this, listDisplay);

    }

    /**
     * Button Select
     **/
    public void RunDatabse(View view){
        DatabaseReference refLottery = FirebaseDatabase.getInstance().getReference("LOTTERY");
        DatabaseReference refResult = refLottery.child("RESULT");
        DatabaseReference refDate = refResult.child(spinnerSelectDate.getSelectedItem().toString());

        refDate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listDisplay.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    DisplayLotteryModel displayLotteryModel = dataSnapshot1.getValue(DisplayLotteryModel.class);
                    listDisplay.add(displayLotteryModel);
                }
                adapter = new LotteriesAdapter(DisplayLotteriesActivity.this, listDisplay);
                recyclerViewLotteries.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ERROR", "Unable to connect database.");

            }
        });

    }

    /**
     * Dialog Display when not connect Internet
     */
    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else
            return false;
    }

    /**
     * Dialog Display when not connect Internet
     */
    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(R.string.message_no_internet_connection);
        builder.setMessage(R.string.message_no_internet_connection_description);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder;
    }


}
