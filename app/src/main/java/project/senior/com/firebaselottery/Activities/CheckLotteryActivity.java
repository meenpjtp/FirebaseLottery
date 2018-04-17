package project.senior.com.firebaselottery.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import project.senior.com.firebaselottery.DBHelper.DBHelperHistory.DBHistoryAdapter;
import project.senior.com.firebaselottery.Utils.StringUtil;
import project.senior.com.firebaselottery.Models.HistoryModel;
import project.senior.com.firebaselottery.R;
import project.senior.com.firebaselottery.RecyclerView.Adapter.HistoryAdapter;
import project.senior.com.firebaselottery.RecyclerView.Swipe.HistorySwipe;

public class CheckLotteryActivity extends AppCompatActivity {

    private LinearLayout checkLotteryActivity;
    private Spinner spinnerSelectDate;
    private EditText editTextLotteryNumber;
    private Button buttonSelect;
    private RecyclerView recyclerViewCheckedLottery;

    //SQLite
    private ArrayList<HistoryModel> listHistory;
    HistoryAdapter adapter;

    int countFalse = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_lottery);

        initObjects();
        initViews();
        getLotteries();

    }

    private void initViews(){
        checkLotteryActivity = (LinearLayout) findViewById(R.id.checkLotteryActivity);
        spinnerSelectDate = (Spinner) findViewById(R.id.spinnerSelectDate);
        editTextLotteryNumber = (EditText) findViewById(R.id.editTextLotteryNumber);
        buttonSelect = (Button) findViewById(R.id.buttonSelect);

    }

    // This method is to initialize objects to be used
    private void initObjects(){
        recyclerViewCheckedLottery = (RecyclerView) findViewById(R.id.recyclerViewCheckedLotteries);

        // RecyclerView
        listHistory = new ArrayList<>();

        adapter = new HistoryAdapter(this, listHistory);

        // RecyclerView Swipe To Delete
        ItemTouchHelper.Callback callback = new HistorySwipe(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerViewCheckedLottery);

        recyclerViewCheckedLottery.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCheckedLottery.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCheckedLottery.setHasFixedSize(true);

        // Display Dialog when is not connect internet
        if(!isConnected(CheckLotteryActivity.this)) buildDialog(CheckLotteryActivity.this).show();
        else {

        }

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
                ArrayAdapter<String> dateAdapter = new ArrayAdapter<String>(CheckLotteryActivity.this,
                        android.R.layout.simple_spinner_item, dateSet);
                dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSelectDate.setAdapter(dateAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    // Clear EditText when press button
    private void clear(){
        editTextLotteryNumber.setText("");
    }


    /**
     * Implement Button when press on button call this method.
     * @param view
     */
    public void checkLotteryNumber(View view){

        final int count = 0;

        // Error Message when input length != 6
        if(StringUtil.isEmpty(editTextLotteryNumber.getText().toString())){
            editTextLotteryNumber.setError(getString(R.string.error_message_length));
            return;
        } if(StringUtil.isLength(editTextLotteryNumber.getText().toString())){
            editTextLotteryNumber.setError(getString(R.string.error_message_length));
            return;
        }

        DatabaseReference refLottery = FirebaseDatabase.getInstance().getReference("LOTTERY");
        final DatabaseReference refResult = refLottery.child("RESULT");
        final DatabaseReference refDate = refResult.child(spinnerSelectDate.getSelectedItem().toString());

        refResult.child(spinnerSelectDate.getSelectedItem().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

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
                                Boolean match1 = data.child("lottery_number").getValue().toString().equals(editTextLotteryNumber.getText().toString());
                                Boolean match2 = data.child("lottery_number").getValue().toString().equals(editTextLotteryNumber.getText().toString().substring(4,6));
                                Boolean match3 = data.child("lottery_number").getValue().toString().equals(editTextLotteryNumber.getText().toString().substring(3,6));
                                Boolean match4 = data.child("lottery_number").getValue().toString().equals(editTextLotteryNumber.getText().toString().substring(0,3));

                                // 1st prize | 2nd prize | 3rd prize | 4th prize | 5th prize
                                if(match1 == true){

                                    // Display Snackbar
                                    Snackbar.make(checkLotteryActivity, "คุณถูก" + data.child("lottery_prize").getValue(), Snackbar.LENGTH_SHORT).show();

                                    // Save check lottery to database
                                    save(spinnerSelectDate.getSelectedItem().toString(),
                                            editTextLotteryNumber.getText().toString(),
                                            "ถูก" + (String) data.child("lottery_prize").getValue());
                                    getLotteries();

            //                        Log.i("logggggg", String.valueOf(data));
                                }

                                // Last 2 number
                                if(match2 == true){

                                    // Display Snackbar
                                    Snackbar.make(checkLotteryActivity, "คุณถูก" + data.child("lottery_prize").getValue(), Snackbar.LENGTH_SHORT).show();

                                    // Save check lottery to database
                                    save(spinnerSelectDate.getSelectedItem().toString(),
                                            editTextLotteryNumber.getText().toString(),
                                            "ถูก" + (String) data.child("lottery_prize").getValue());
                                    getLotteries();
                                }

                                // Last 3 number
                                if(match3 == true){

                                    // Display Snackbar
                                    Snackbar.make(checkLotteryActivity, "คุณถูก" + data.child("lottery_prize").getValue(), Snackbar.LENGTH_SHORT).show();

                                    // Save check lottery to database
                                    save(spinnerSelectDate.getSelectedItem().toString(),
                                            editTextLotteryNumber.getText().toString(),
                                            "ถูก" + (String) data.child("lottery_prize").getValue());
                                    getLotteries();
                                }

                                // First 3 number
                                if(match4 == true){

                                    // Display Snackbar
                                    Snackbar.make(checkLotteryActivity, "คุณถูก" + data.child("lottery_prize").getValue(), Snackbar.LENGTH_SHORT).show();

                                    // Save check lottery to database
                                    save(spinnerSelectDate.getSelectedItem().toString(),
                                            editTextLotteryNumber.getText().toString(),
                                            "ถูก" + (String) data.child("lottery_prize").getValue());
                                    getLotteries();

                                }


                                // Did not win lottery (count boolean False == 5)
                                if(match1 == false && match2 == false && match3 == false && match4 == false) {
                                    countFalse++;
                                    Log.i("countFalse", String.valueOf(countFalse));

                                }
                                // countFalse == 5 --> Did not win lottery!
                                // change 5 -> 173
                                if(countFalse ==5){
                                    Snackbar.make(checkLotteryActivity, "คุณไม่ถูกรางวัล", Snackbar.LENGTH_SHORT).show();

                                    // Save check lottery to database
                                    save(spinnerSelectDate.getSelectedItem().toString(),
                                            editTextLotteryNumber.getText().toString(),
                                            "ไม่ถูกรางวัล");
                                    getLotteries();
                                }
                            }

                            clear();
                            countFalse = 0; // reset countFalse
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

                // Waiting Result...
                else{

                    Log.i("testExists", "data not exists");

                    // Display Snackbar
                    Snackbar.make(checkLotteryActivity, R.string.message_result, Snackbar.LENGTH_SHORT).show();

                    // Save check lottery to database
                    save(spinnerSelectDate.getSelectedItem().toString(),
                            editTextLotteryNumber.getText().toString(),
                            "รอผลรางวัล");
                    getLotteries();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


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

    // Update list history to recyclerview
    private void getLotteries(){
        listHistory.clear();

        DBHistoryAdapter db = new DBHistoryAdapter(this);
        db.openDB();
        Cursor cursor = db.retrieve();

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String selected_date = cursor.getString(1);
            String lottery_number = cursor.getString(2);
            String lottery_result = cursor.getString(3);

            HistoryModel model = new HistoryModel();
            model.setId(id);
            model.setSelected_date(selected_date);
            model.setLottery_number(lottery_number);
            model.setLottery_result(lottery_result);

            listHistory.add(model);

        }
        db.closeDB();

        if(listHistory.size() > 0){
            recyclerViewCheckedLottery.setAdapter(adapter);
        }
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

/*
 * Check Lottery Version.1
 */
// Length input == 6 | if data exists in firebase
                    /*if(data.exists()){

                        // 1st prize, 2nd prize, 3rd prize, 4th prize, 5th prize
                        if(data.child("lottery_number").getValue().toString().equals(
                                editTextLotteryNumber.getText().toString())){

                            Log.i("QQQQ", String.valueOf(match1));

                            // Display snackbar
                            Snackbar.make(checkLotteryActivity, "คุณถูก" + data.child("lottery_prize").getValue()
                                    , Snackbar.LENGTH_SHORT).show();

                            // Save check lottery to database
                            save(spinnerSelectDate.getSelectedItem().toString(),
                                    editTextLotteryNumber.getText().toString(),
                                    (String) data.child("lottery_prize").getValue());
                            getLotteries();

                        }

                        // last 2 number prize
                        if(data.child("lottery_number").getValue().toString().equals( //last 2 number 000098
                                editTextLotteryNumber.getText().toString().substring(4,6))){

                            Log.i("QQQQ", String.valueOf(match2));

                            // Display snackbar
                            Snackbar.make(checkLotteryActivity, "คุณถูก" + data.child("lottery_prize").getValue()
                                    , Snackbar.LENGTH_SHORT).show();

                            // Save check lottery to database
                            save(spinnerSelectDate.getSelectedItem().toString(),
                                    editTextLotteryNumber.getText().toString(),
                                    (String) data.child("lottery_prize").getValue());
                            getLotteries();

                        }

                        // last 3 number prize
                        if(data.child("lottery_number").getValue().toString().equals( //last 3 number
                                editTextLotteryNumber.getText().toString().substring(3,6))){

                            Log.i("QQQQ", String.valueOf(match3));


                            // Display snackbar
                            Snackbar.make(checkLotteryActivity, "คุณถูก" + data.child("lottery_prize").getValue()
                                    , Snackbar.LENGTH_SHORT).show();

                            // Save check lottery to database
                            save(spinnerSelectDate.getSelectedItem().toString(),
                                    editTextLotteryNumber.getText().toString(),
                                    (String) data.child("lottery_prize").getValue());
                            getLotteries();

                        }

                        // first 3 number
                        if(data.child("lottery_number").getValue().toString().equals( //first 3 number
                                editTextLotteryNumber.getText().toString().substring(0,3))){

                            Log.i("QQQQ", String.valueOf(match4));

                            // Display snackbar
                            Snackbar.make(checkLotteryActivity, "คุณถูก" + data.child("lottery_prize").getValue()
                                    , Snackbar.LENGTH_SHORT).show();

                            // Save check lottery to database
                            save(spinnerSelectDate.getSelectedItem().toString(),
                                    editTextLotteryNumber.getText().toString(),
                                    (String) data.child("lottery_prize").getValue());
                            getLotteries();
                        }

                        if(!match1 && !match2 && !match3 && !match4){
                            Toast.makeText(CheckLotteryActivity.this, "เสียใจด้วยคุณไม่ถูกรางวัล", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Snackbar.make(checkLotteryActivity, "เสียใจด้วยคุณไม่ถูกรางวัล", Snackbar.LENGTH_SHORT).show();
                        Toast.makeText(CheckLotteryActivity.this, "เสียใจด้วยคุณไม่ถูกรางวัล", Toast.LENGTH_SHORT).show();
                        Log.i("TT", editTextLotteryNumber.getText().toString());

                        save(spinnerSelectDate.getSelectedItem().toString(),
                                editTextLotteryNumber.getText().toString(),
                                (String) data.child("lottery_prize").getValue());
                        getLotteries();
                    }*/

/* -------------------------------------------------------------------------------------------------------------------- */

/**
 *  Check Lottery Version.2
 */

//        refDate.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                for(DataSnapshot data : dataSnapshot.getChildren()){
//
//
//                    /**
//                     *  Counting True or False to tell you win or not
//                     *  if counting False == 5 you did not win
//                     *  Counting True < 5 you win!
//                     */
//                    Boolean match1 = data.child("lottery_number").getValue().toString().equals(editTextLotteryNumber.getText().toString());
//                    Boolean match2 = data.child("lottery_number").getValue().toString().equals(editTextLotteryNumber.getText().toString().substring(4,6));
//                    Boolean match3 = data.child("lottery_number").getValue().toString().equals(editTextLotteryNumber.getText().toString().substring(3,6));
//                    Boolean match4 = data.child("lottery_number").getValue().toString().equals(editTextLotteryNumber.getText().toString().substring(0,3));
//
//                    // 1st prize | 2nd prize | 3rd prize | 4th prize | 5th prize
//                    if(match1 == true){
//
//                        // Display Snackbar
//                        Snackbar.make(checkLotteryActivity, "คุณถูก" + data.child("lottery_prize").getValue(), Snackbar.LENGTH_SHORT).show();
//
//                        // Save check lottery to database
//                        save(spinnerSelectDate.getSelectedItem().toString(),
//                                editTextLotteryNumber.getText().toString(),
//                                "ถูก" + (String) data.child("lottery_prize").getValue());
//                        getLotteries();
//
////                        Log.i("logggggg", String.valueOf(data));
//                    }
//
//                    // Last 2 number
//                    if(match2 == true){
//
//                        // Display Snackbar
//                        Snackbar.make(checkLotteryActivity, "คุณถูก" + data.child("lottery_prize").getValue(), Snackbar.LENGTH_SHORT).show();
//
//                        // Save check lottery to database
//                        save(spinnerSelectDate.getSelectedItem().toString(),
//                                editTextLotteryNumber.getText().toString(),
//                                "ถูก" + (String) data.child("lottery_prize").getValue());
//                        getLotteries();
//                    }
//
//                    // Last 3 number
//                    if(match3 == true){
//
//                        // Display Snackbar
//                        Snackbar.make(checkLotteryActivity, "คุณถูก" + data.child("lottery_prize").getValue(), Snackbar.LENGTH_SHORT).show();
//
//                        // Save check lottery to database
//                        save(spinnerSelectDate.getSelectedItem().toString(),
//                                editTextLotteryNumber.getText().toString(),
//                                "ถูก" + (String) data.child("lottery_prize").getValue());
//                        getLotteries();
//                    }
//
//                    // First 3 number
//                    if(match4 == true){
//
//                        // Display Snackbar
//                        Snackbar.make(checkLotteryActivity, "คุณถูก" + data.child("lottery_prize").getValue(), Snackbar.LENGTH_SHORT).show();
//
//                        // Save check lottery to database
//                        save(spinnerSelectDate.getSelectedItem().toString(),
//                                editTextLotteryNumber.getText().toString(),
//                                "ถูก" + (String) data.child("lottery_prize").getValue());
//                        getLotteries();
//
//                    }
//
//                    // Did not win lottery (count boolean False == 5)
//                    if(match1 == false && match2 == false && match3 == false && match4 == false) {
//                        countFalse++;
//                        Log.i("countFalse", String.valueOf(countFalse));
//
//                    }
//                    // countFalse == 5 --> Did not win lottery!
//                    // change 5 -> 173
//                    if(countFalse ==5){
//                        Snackbar.make(checkLotteryActivity, "คุณไม่ถูกรางวัล", Snackbar.LENGTH_SHORT).show();
//
//                        // Save check lottery to database
//                        save(spinnerSelectDate.getSelectedItem().toString(),
//                                editTextLotteryNumber.getText().toString(),
//                                "ไม่ถูกรางวัล");
//                        getLotteries();
//                    }
//                }
//
//                clear();
//                countFalse = 0; // reset countFalse
//            }
//
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
