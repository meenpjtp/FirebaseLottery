package project.senior.com.firebaselottery.Activities.ModeSimulation;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import project.senior.com.firebaselottery.R;

public class AddLotterySimulationActivity extends AppCompatActivity {

    private LinearLayout addLotterySimulation;
    private TextView textViewPriceLotterySimulation; // 80 Baht
    private TextView textViewSelectedDateLotterySimulation; // 30 Dec 2017
    private Spinner spinnerSelectDateSimulation;
    private TextInputLayout textInputLayoutAddLotteryNumberSimulation; // Error display when don't input this field
    private TextInputEditText editTextAddLotteryNumberSimulation; // 911234
    private TextInputLayout textInputLayoutAddAmountLotterySimulation;
    private TextInputEditText editTextAddAmountLotterySimulation;
    private Button buttonSaveSimulation; // save to SQLite after check with firebase

    private String selectedDate;
    private final int PRICE = 80;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lottery_simulation);

        initObjects();

        /**
         * Display Dialog when is not connect internet
         */
        if(!isConnected(AddLotterySimulationActivity.this)) buildDialog(AddLotterySimulationActivity.this).show();
        else {

        }

        textViewPriceLotterySimulation.setText(String.valueOf(PRICE));

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
                ArrayAdapter<String> dateAdapter = new ArrayAdapter<String>(AddLotterySimulationActivity.this,
                        android.R.layout.simple_spinner_item, dateSet);
                dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSelectDateSimulation.setAdapter(dateAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
    }

    private void initObjects(){
        addLotterySimulation = (LinearLayout) findViewById(R.id.addLotterySimulation);
        textViewPriceLotterySimulation = (TextView) findViewById(R.id.textViewPriceLotterySimulation);
        textViewSelectedDateLotterySimulation = (TextView) findViewById(R.id.textViewSelectedDateLotterySimulation);
        spinnerSelectDateSimulation = (Spinner) findViewById(R.id.spinnerSelectDateSimulation);
        textInputLayoutAddLotteryNumberSimulation = (TextInputLayout) findViewById(R.id.textInputLayoutAddLotteryNumberSimulation);
        editTextAddLotteryNumberSimulation = (TextInputEditText) findViewById(R.id.editTextAddLotteryNumberSimulation);
        textInputLayoutAddAmountLotterySimulation = (TextInputLayout)findViewById(R.id.textInputLayoutAddAmountLotterySimulation);
        editTextAddAmountLotterySimulation = (TextInputEditText) findViewById(R.id.editTextAddAmountLotterySimulation);
        buttonSaveSimulation = (Button) findViewById(R.id.buttonSaveSimulation);
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
