package project.senior.com.firebaselottery.Intent;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import project.senior.com.firebaselottery.DBHelper.DBHelperPurchase.DBPurchaseAdapter;
import project.senior.com.firebaselottery.Models.PurchaseModel;
import project.senior.com.firebaselottery.R;
import project.senior.com.firebaselottery.Utils.InputValidation;

public class AddLotteryPurchaseActivity extends AppCompatActivity {

    private LinearLayout addLotteryPurchase;
    private TextView pur_textViewPriceLottery;
    private Spinner pur_spinnerSelectDate;
    private TextInputLayout pur_textInputLayoutAddLottery;
    private TextInputLayout pur_textInputLayoutAddAmount;
    private TextInputEditText pur_editTextAddLottery;
    private TextInputEditText pur_editTextAddAmount;
    private Button pur_buttonSave;
    private Toolbar pur_toolbar;

    private final int PRICE = 80;
    private int countFalse = 0;

    private InputValidation inputValidation;

    //SQLite
    private ArrayList<PurchaseModel> listModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lottery_purchase);

        initObjects();
        initViews();
        getLotteries();

        // TextView set Price
        pur_textViewPriceLottery.setText(String.valueOf(PRICE));

        // Display Dialog when is not connect internet
        if(!isConnected(AddLotteryPurchaseActivity.this)) buildDialog(AddLotteryPurchaseActivity.this).show();
        else {

        }

    }

    private void initViews() {
        addLotteryPurchase = (LinearLayout) findViewById(R.id.addLotteryPurchase);
        pur_toolbar = (Toolbar) findViewById(R.id.pur_toolbar);
        pur_spinnerSelectDate = (Spinner) findViewById(R.id.pur_spinnerSelectDate);
        pur_textInputLayoutAddLottery = (TextInputLayout) findViewById(R.id.pur_textInputLayoutAddLottery);
        pur_editTextAddLottery = (TextInputEditText) findViewById(R.id.pur_editTextAddLottery);
        pur_textInputLayoutAddAmount = (TextInputLayout) findViewById(R.id.pur_textInputLayoutAddAmount);
        pur_editTextAddAmount = (TextInputEditText) findViewById(R.id.pur_editTextAddAmount);
        pur_buttonSave = (Button) findViewById(R.id.pur_buttonSave);
        pur_textViewPriceLottery = (TextView) findViewById(R.id.pur_textViewPriceLottery);

    }

    public Toolbar getToolbar() {
        if (pur_toolbar == null) {
            pur_toolbar = (Toolbar) findViewById(R.id.pur_toolbar);
        }
        return pur_toolbar;
    }

    private void initObjects(){

        setSupportActionBar(pur_toolbar);
//        getToolbar().setTitle(getString(R.string.app_name));

        // Error when field is empty
        inputValidation = new InputValidation(this);

        // Save To SQLite
        listModel = new ArrayList<>();

        // Spinner
        DatabaseReference lottery = FirebaseDatabase.getInstance().getReference("LOTTERY");
        lottery.child("DATE").orderByChild("order").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> dateSet = new ArrayList<String>();

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String date = snapshot.child("date").getValue(String.class);
                    dateSet.add(date);
                }
                ArrayAdapter<String> dateAdapter = new ArrayAdapter<String>(AddLotteryPurchaseActivity.this,
                        android.R.layout.simple_spinner_item, dateSet);
                dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                pur_spinnerSelectDate.setAdapter(dateAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

    }

    // Press button save to database
    public void saveLotteryNumber(View view){

        // Display Error
        if (!inputValidation.isInputEditTextFilled(pur_editTextAddLottery, pur_textInputLayoutAddLottery, getString(R.string.error_message_length))){
            return;
        }

        // Call Firebase
        DatabaseReference refLottery = FirebaseDatabase.getInstance().getReference("LOTTERY");
        final DatabaseReference refResult = refLottery.child("RESULT");
        final DatabaseReference refDate = refResult.child(pur_spinnerSelectDate.getSelectedItem().toString());

        refResult.child(pur_spinnerSelectDate.getSelectedItem().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final int save_paid = Integer.parseInt(pur_editTextAddAmount.getText().toString()) * PRICE;

                // Result Announcement
                if(dataSnapshot.getValue() != null){
                    Log.i("testExists", "data exists");

                    refDate.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot1) {

                            for(DataSnapshot data : dataSnapshot1.getChildren()){

                                /**
                                 *  Counting True or False to tell you win or not
                                 *  if counting False == 5 you did not win
                                 *  Counting True < 5 you win!
                                 */
                                boolean match1 = data.child("lottery_number").getValue().toString().equals(pur_editTextAddLottery.getText().toString());
                                boolean match2 = data.child("lottery_number").getValue().toString().equals(pur_editTextAddLottery.getText().toString().substring(4,6));
                                boolean match3 = data.child("lottery_number").getValue().toString().equals(pur_editTextAddLottery.getText().toString().substring(3,6));
                                boolean match4 = data.child("lottery_number").getValue().toString().equals(pur_editTextAddLottery.getText().toString().substring(0,3));

                                int value = Integer.parseInt(String.valueOf(data.child("lottery_value").getValue()));
                                int amount = Integer.parseInt(pur_editTextAddAmount.getText().toString());
                                int totalValue = value * amount;

                                // 1st prize | 2nd prize | 3rd prize | 4th prize | 5th prize
                                if(match1 == true){

                                    // Save check lottery to database
                                    save(pur_spinnerSelectDate.getSelectedItem().toString(),
                                            pur_editTextAddLottery.getText().toString(),
                                            pur_editTextAddAmount.getText().toString(),
                                            String.valueOf(save_paid),
                                            "คุณถูก" + data.child("lottery_prize").getValue(),
                                            String.valueOf(totalValue));
                                    break;
                                }

                                // Last 2 number
                                else if(match2 == true){

                                    // Save check lottery to database
                                    save(pur_spinnerSelectDate.getSelectedItem().toString(),
                                            pur_editTextAddLottery.getText().toString(),
                                            pur_editTextAddAmount.getText().toString(),
                                            String.valueOf(save_paid),
                                            "คุณถูก" + data.child("lottery_prize").getValue(),
                                            String.valueOf(data.child("lottery_value").getValue()));

                                    break;

                                }

                                // Last 3 number
                                else if(match3 == true){

                                    // Save check lottery to database
                                    save(pur_spinnerSelectDate.getSelectedItem().toString(),
                                            pur_editTextAddLottery.getText().toString(),
                                            pur_editTextAddAmount.getText().toString(),
                                            String.valueOf(save_paid),
                                            "คุณถูก" + data.child("lottery_prize").getValue(),
                                            String.valueOf(data.child("lottery_value").getValue()));

                                    break;
                                }

                                // First 3 number
                                else if(match4 == true){

                                    // Save check lottery to database
                                    save(pur_spinnerSelectDate.getSelectedItem().toString(),
                                            pur_editTextAddLottery.getText().toString(),
                                            pur_editTextAddAmount.getText().toString(),
                                            String.valueOf(save_paid),
                                            "คุณถูก" + data.child("lottery_prize").getValue(),
                                            String.valueOf(data.child("lottery_value").getValue()));
//                                    Toast.makeText(AddLotteryPurchaseActivity.this, "ถูก", Toast.LENGTH_SHORT).show();

                                    break;

                                }


                                // Did not win lottery (count boolean False == 5)
                                if(match1 == false && match2 == false && match3 == false && match4 == false) {
                                    countFalse++;
                                    Log.i("testCountFalse", String.valueOf(countFalse));
                                }

                                // countFalse == 5 --> Did not win lottery!
                                // change 5 -> 173
                                if(countFalse ==152){

                                    save(pur_spinnerSelectDate.getSelectedItem().toString(),
                                            pur_editTextAddLottery.getText().toString(),
                                            pur_editTextAddAmount.getText().toString(),
                                            String.valueOf(save_paid),
                                            "คุณไม่ถูกรางวัล",
                                            "-");
//                                    clear();
                                    Log.i("testCountFalse", String.valueOf(countFalse));
                                    break;

                                }
                            }

                            countFalse = 0; // reset countFalse
                            clear();
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

                // Waiting Result...
                else{

                    Log.i("testExists", "data not exists");

                    // Save check lottery to database
                    save(pur_spinnerSelectDate.getSelectedItem().toString(),
                            pur_editTextAddLottery.getText().toString(),
                            pur_editTextAddAmount.getText().toString(),
                            String.valueOf(save_paid),
                            "รอผลรางวัล",
                            "รอผลรางวัล");
                    clear();


                }

//                getLotteries();
                Snackbar.make(addLotteryPurchase, "บันทึกเรียบร้อย", Snackbar.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void clear(){
        pur_editTextAddAmount.setText("");
        pur_editTextAddLottery.setText("");
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
    private void save(String lottery_date, String lottery_number, String lottery_amount, String lottery_paid, String lottery_status, String lottery_value){
        DBPurchaseAdapter db = new DBPurchaseAdapter(this);
        db.openDB();
        if(db.addLottery(lottery_date, lottery_number, lottery_amount, lottery_paid, lottery_status, lottery_value)){
//            editTextLotteryNumber.setText("");
        } else {
            Toast.makeText(this,"Unable to save", Toast.LENGTH_SHORT).show();;
        }
        db.closeDB();
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

//        if(listModel.size() > 0){
//            recyclerViewCheckedLottery.setAdapter(adapter);
//        }
    }
}
