package project.senior.com.firebaselottery.Activities.ModeSimulation;

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
import project.senior.com.firebaselottery.DBHelper.DBHelperSimulation.DBSimulationAdapter;
import project.senior.com.firebaselottery.Error.InputValidation;
import project.senior.com.firebaselottery.Models.SimulationModel;
import project.senior.com.firebaselottery.R;

public class AddLotterySimulationActivity extends AppCompatActivity {

    private LinearLayout addLotterySimulation;
    private TextView textViewPriceLottery; // 80 Baht
    private TextView textViewSelectedDate; // 30 Dec 2017
    private Spinner spinnerSelectDate;
    private TextInputLayout textInputLayoutAddLottery; // Error display when don't input this field
    private TextInputEditText editTextAddLottery; // 911234
    private TextInputLayout textInputLayoutAddAmount;
    private TextInputEditText editTextAddAmount;
    private Button buttonSave; // save to SQLite after check with firebase

    private final int PRICE = 80;
    private int countFalse = 0;

    private InputValidation inputValidation;

    //SQLite
    private ArrayList<SimulationModel> listModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lottery_simulation);

        initObjects();
        initViews();
        getLotteries();

        // Display Dialog when is not connect internet
        if(!isConnected(AddLotterySimulationActivity.this)) buildDialog(AddLotterySimulationActivity.this).show();
        else {

        }

        textViewPriceLottery.setText(String.valueOf(PRICE));

    }

    private void initViews(){
        addLotterySimulation = (LinearLayout) findViewById(R.id.addLotterySimulation);
        textViewPriceLottery= (TextView) findViewById(R.id.textViewPriceLottery);
        textViewSelectedDate = (TextView) findViewById(R.id.textViewSelectedDate);
        spinnerSelectDate = (Spinner) findViewById(R.id.spinnerSelectDate);
        textInputLayoutAddLottery = (TextInputLayout) findViewById(R.id.textInputLayoutAddLottery);
        editTextAddLottery = (TextInputEditText) findViewById(R.id.editTextAddLottery);
        textInputLayoutAddAmount = (TextInputLayout)findViewById(R.id.textInputLayoutAddAmount);
        editTextAddAmount = (TextInputEditText) findViewById(R.id.editTextAddAmount);
        buttonSave = (Button) findViewById(R.id.buttonSave);
    }

    private void initObjects(){

        // Error when field is empty
        inputValidation = new InputValidation(this);

        // Spinner
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
                spinnerSelectDate.setAdapter(dateAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

        // Display Dialog when is not connect internet
        if(!isConnected(AddLotterySimulationActivity.this)) buildDialog(AddLotterySimulationActivity.this).show();
        else {
        }

        // Save To SQLite
        listModel = new ArrayList<>();

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

        refResult.child(spinnerSelectDate.getSelectedItem().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final int save_paid = Integer.parseInt(editTextAddAmount.getText().toString()) * PRICE;

                // Result Announcement
                if(dataSnapshot.getValue() != null){
                    Log.i("testExists", "data exists");

                    refDate.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for(DataSnapshot data : dataSnapshot.getChildren()){

                                /**
                                 *  Counting True or False to tell you win or not
                                 *  if counting False == 5 you did not win
                                 *  Counting True < 5 you win!
                                 */
                                Boolean match1 = data.child("lottery_number").getValue().toString().equals(editTextAddLottery.getText().toString());
                                Boolean match2 = data.child("lottery_number").getValue().toString().equals(editTextAddLottery.getText().toString().substring(4,6));
                                Boolean match3 = data.child("lottery_number").getValue().toString().equals(editTextAddLottery.getText().toString().substring(3,6));
                                Boolean match4 = data.child("lottery_number").getValue().toString().equals(editTextAddLottery.getText().toString().substring(0,3));

                                // 1st prize | 2nd prize | 3rd prize | 4th prize | 5th prize
                                if(match1 == true){

                                    // Save check lottery to database
                                    save(spinnerSelectDate.getSelectedItem().toString(),
                                            editTextAddLottery.getText().toString(),
                                            editTextAddAmount.getText().toString(),
                                            String.valueOf(save_paid),
                                            "คุณถูก" + data.child("lottery_prize").getValue(),
                                            String.valueOf(data.child("lottery_value").getValue()));

                                    //Log.i("logggggg", String.valueOf(data));
                                }

                                // Last 2 number
                                if(match2 == true){

                                    // Save check lottery to database
                                    save(spinnerSelectDate.getSelectedItem().toString(),
                                            editTextAddLottery.getText().toString(),
                                            editTextAddAmount.getText().toString(),
                                            String.valueOf(save_paid),
                                            "คุณถูก" + data.child("lottery_prize").getValue(),
                                            String.valueOf(data.child("lottery_value").getValue()));

                                    Log.i("testLength", editTextAddLottery.getText().toString().substring(4,6));

                                }

                                // Last 3 number
                                if(match3 == true){

                                    // Save check lottery to database
                                    save(spinnerSelectDate.getSelectedItem().toString(),
                                            editTextAddLottery.getText().toString(),
                                            editTextAddAmount.getText().toString(),
                                            String.valueOf(save_paid),
                                            "คุณถูก" + data.child("lottery_prize").getValue(),
                                            String.valueOf(data.child("lottery_value").getValue()));

                                }

                                // First 3 number
                                if(match4 == true){

                                    // Save check lottery to database
                                    save(spinnerSelectDate.getSelectedItem().toString(),
                                            editTextAddLottery.getText().toString(),
                                            editTextAddAmount.getText().toString(),
                                            String.valueOf(save_paid),
                                            "คุณถูก" + data.child("lottery_prize").getValue(),
                                            String.valueOf(data.child("lottery_value").getValue()));

                                }


                                // Did not win lottery (count boolean False == 5)
                                if(match1 == false  && match2 == false&& match3 == false && match4 == false) {
                                    countFalse++;
                                    Log.i("countFalse", String.valueOf(countFalse));

                                }

                                // countFalse == 5 --> Did not win lottery!
                                // change 5 -> 173
                                if(countFalse ==5){

                                    save(spinnerSelectDate.getSelectedItem().toString(),
                                            editTextAddLottery.getText().toString(),
                                            editTextAddAmount.getText().toString(),
                                            String.valueOf(save_paid),
                                            "คุณไม่ถูกรางวัล",
                                            "-");
//                                    clear();

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
                    save(spinnerSelectDate.getSelectedItem().toString(),
                            editTextAddLottery.getText().toString(),
                            editTextAddAmount.getText().toString(),
                            String.valueOf(save_paid),
                            "รอผลรางวัล",
                            "รอผลรางวัล");
                    clear();

                }

                getLotteries();
                Snackbar.make(addLotterySimulation, "บันทึกเรียบร้อย", Snackbar.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void clear(){
        editTextAddAmount.setText("");
        editTextAddLottery.setText("");
    }

    // Save check lottery to database
    private void save(String lottery_date, String lottery_number, String lottery_amount, String lottery_paid, String lottery_status, String lottery_value){
        DBSimulationAdapter db = new DBSimulationAdapter(this);
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

        DBSimulationAdapter db = new DBSimulationAdapter(this);
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

            SimulationModel model = new SimulationModel();
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
}
