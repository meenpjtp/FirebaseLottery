package project.senior.com.firebaselottery.Activities.ModePurchase;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import project.senior.com.firebaselottery.DBHelper.DBHelperHistory.DBHistoryAdapter;
import project.senior.com.firebaselottery.Utils.InputValidation;
import project.senior.com.firebaselottery.Models.SimulationModel;
import project.senior.com.firebaselottery.R;

public class AddLotteryPurchaseActivity extends AppCompatActivity {

    private LinearLayout addLotteryPurchase;
    private TextView textViewPriceLottery;
    private TextView textViewSelectedDate;
    private Spinner spinnerSelectDate;
    private TextInputLayout textInputLayoutAddLottery;
    private TextInputLayout textInputLayoutAddAmount;
    private TextInputEditText editTextAddLottery;
    private TextInputEditText editTextAddAmount;
    private Button buttonSave;

    private final int PRICE = 80;
    private int countFalse = 0;

    private InputValidation inputValidation;

    //SQLite
    private ArrayList<SimulationModel> listModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lottery_purchase);

        initObjects();
        initViews();


        // TextView set Price
        textViewPriceLottery.setText(String.valueOf(PRICE));

        // Display Dialog when is not connect internet
        if(!isConnected(AddLotteryPurchaseActivity.this)) buildDialog(AddLotteryPurchaseActivity.this).show();
        else {

        }

    }

    private void initViews(){
        addLotteryPurchase = (LinearLayout) findViewById(R.id.addLotteryPurchase);
        textViewPriceLottery = (TextView) findViewById(R.id.textViewPriceLottery);
        textViewSelectedDate = (TextView) findViewById(R.id.textViewSelectedDate);
        spinnerSelectDate = (Spinner) findViewById(R.id.spinnerSelectDate);
        textInputLayoutAddLottery = (TextInputLayout) findViewById(R.id.textInputLayoutAddLottery);
        textInputLayoutAddAmount = (TextInputLayout) findViewById(R.id.textInputLayoutAddAmount);
        editTextAddLottery = (TextInputEditText) findViewById(R.id.editTextAddLottery);
        editTextAddAmount = (TextInputEditText) findViewById(R.id.editTextAddAmount);
        buttonSave = (Button) findViewById(R.id.buttonSave);
    }

    private void initObjects(){

        inputValidation = new InputValidation(this);

        // Display Dialog when is not connect internet
        if(!isConnected(AddLotteryPurchaseActivity.this)) buildDialog(AddLotteryPurchaseActivity.this).show();
        else {
        }

        // Spinner
        DatabaseReference lottery = FirebaseDatabase.getInstance().getReference("LOTTERY");
        lottery.child("DATE").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> dateSet = new ArrayList<String>();

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String date = snapshot.child("title").getValue(String.class);
                    dateSet.add(date);
                }
                ArrayAdapter<String> dateAdapter = new ArrayAdapter<String>(AddLotteryPurchaseActivity.this,
                        android.R.layout.simple_spinner_item, dateSet);
                dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSelectDate.setAdapter(dateAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    // Press button save to database
    public void saveLotteryNumber(View view){

        // Display Error
        if (!inputValidation.isInputEditTextFilled(editTextAddLottery, textInputLayoutAddLottery, getString(R.string.error_message_length))){
            return;
        }

        // Call Firebase
        DatabaseReference refLottery = FirebaseDatabase.getInstance().getReference("LOTTERY");
        final DatabaseReference refResult = refLottery.child("RESULT");
        final DatabaseReference refDate = refResult.child(spinnerSelectDate.getSelectedItem().toString());

        refDate.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if(data.child("lottery_number").getValue().toString().equals(
                            editTextAddLottery.getText().toString())) {

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    // Dialog Display when not connect Internet
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

    // Dialog Display when not connect Internet
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

    // Save check lottery to database
    private void save(String selected_date, String lottery_number, String lottery_result){
        DBHistoryAdapter db = new DBHistoryAdapter(this);
        db.openDB();
        if(db.addLottery(selected_date, lottery_number, lottery_result)){
//            editTextLotteryNumber.setText("");
        } else {
            Toast.makeText(this,"Unable to save", Toast.LENGTH_SHORT).show();;
        }
        db.closeDB();
    }
}
