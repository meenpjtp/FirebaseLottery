package project.senior.com.firebaselottery.Activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import project.senior.com.firebaselottery.DBHelper.DBHelperHistory;
import project.senior.com.firebaselottery.Models.HistoryCheckLotteryModel;
import project.senior.com.firebaselottery.R;
import project.senior.com.firebaselottery.Util.StringUtil;

public class CheckLotteryActivity extends AppCompatActivity {

    private LinearLayout checkLotteryActivity;
    private Spinner spinnerSelectDate;
    private EditText editTextLotteryNumber;
    private Button buttonSelect;
    private RecyclerView recyclerViewCheckedLottery;

    //SQLite
    private ArrayList<HistoryCheckLotteryModel> historyList;
    private DBHelperHistory db;
    private int ID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_lottery);

        initObjects();

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
                ArrayAdapter<String> dateAdapter = new ArrayAdapter<String>(CheckLotteryActivity.this,
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


    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects(){
        checkLotteryActivity = (LinearLayout) findViewById(R.id.checkLotteryActivity);
        spinnerSelectDate = (Spinner) findViewById(R.id.spinnerSelectDate);
        editTextLotteryNumber = (EditText) findViewById(R.id.editTextLotteryNumber);
        buttonSelect = (Button) findViewById(R.id.buttonSelect);
        recyclerViewCheckedLottery = (RecyclerView) findViewById(R.id.recyclerViewCheckedLotteries);

        //SQLite
        db = new DBHelperHistory(this);
        historyList = new ArrayList<>();
        historyList = db.getAllHistory();

    }

    /**
     * Clear EditText when press button
     */
    private void clear(){
        editTextLotteryNumber.setText(null);
    }


    /**
     * Implement Button when press on button call this method.
     * @param view
     */
    public void checkLotteryNumber(View view){

        DatabaseReference refLottery = FirebaseDatabase.getInstance().getReference("LOTTERY");
        final DatabaseReference refResult = refLottery.child("RESULT");
        final DatabaseReference refDate = refResult.child(spinnerSelectDate.getSelectedItem().toString());

        refDate.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){

                    // Error Message when input length != 6
                    if(StringUtil.isEmpty(editTextLotteryNumber.getText().toString())){
                        editTextLotteryNumber.setError(getString(R.string.error_message_length));
                        return;
                    } if(StringUtil.isLength(editTextLotteryNumber.getText().toString())){
                        editTextLotteryNumber.setError(getString(R.string.error_message_length));
                        return;
                    }

                    if(data.exists()){

                        if(data.child("lottery_number").getValue().toString().equals(
                                editTextLotteryNumber.getText().toString())){
                            Snackbar.make(checkLotteryActivity, "คุณถูกรางวัลที่ " + data.child("lottery_prize").getValue()
                                    , Snackbar.LENGTH_SHORT).show();

                            db.addLottery(new HistoryCheckLotteryModel(ID, spinnerSelectDate.getSelectedItem().toString(),
                                    editTextLotteryNumber.getText().toString(), (String) data.child("lottery_prize").getValue()));

                        } if(data.child("lottery_number").getValue().toString().equals( //last 2 number 000098
                                editTextLotteryNumber.getText().toString().substring(4,6))){
                            Snackbar.make(checkLotteryActivity, "คุณถูกรางวัลที่ " + data.child("lottery_prize").getValue()
                                    , Snackbar.LENGTH_SHORT).show();

                        } if(data.child("lottery_number").getValue().toString().equals( //last 3 number
                                editTextLotteryNumber.getText().toString().substring(3,6))){
                            Snackbar.make(checkLotteryActivity, "คุณถูกรางวัลที่ " + data.child("lottery_prize").getValue()
                                    , Snackbar.LENGTH_SHORT).show();

                        } if(data.child("lottery_number").getValue().toString().equals( //first 3 number
                                editTextLotteryNumber.getText().toString().substring(0,3))){
                            Snackbar.make(checkLotteryActivity, "คุณถูกรางวัลที่ " + data.child("lottery_prize").getValue()
                                    , Snackbar.LENGTH_SHORT).show();
                        }

                    } if(!data.exists()) {
                        Snackbar.make(checkLotteryActivity, "เสียใจด้วยคุณไม่ถูกรางวัล", Snackbar.LENGTH_SHORT).show();
                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
