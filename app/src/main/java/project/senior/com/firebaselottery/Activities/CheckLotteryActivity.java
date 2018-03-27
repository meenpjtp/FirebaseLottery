package project.senior.com.firebaselottery.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import project.senior.com.firebaselottery.R;

public class CheckLotteryActivity extends AppCompatActivity {

    private Spinner spinnerSelectDate;
    private EditText editTextLotteryNumber;
    private Button buttonSelect;
    private RecyclerView recyclerViewCheckedLottery;

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
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects(){
        spinnerSelectDate = (Spinner) findViewById(R.id.spinnerSelectDate);
        editTextLotteryNumber = (EditText) findViewById(R.id.editTextLotteryNumber);
        buttonSelect = (Button) findViewById(R.id.buttonSelect);
        recyclerViewCheckedLottery = (RecyclerView) findViewById(R.id.recyclerViewCheckedLotteries);

    }


    public void checkLotteryNumber(View view){

        DatabaseReference refLottery = FirebaseDatabase.getInstance().getReference("LOTTERY");
        final DatabaseReference refResult = refLottery.child("RESULT");
        DatabaseReference refDate = refResult.child(spinnerSelectDate.getSelectedItem().toString());

        refDate.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){

                    if(data.exists()){

                        if(data.child("lottery_number").getValue().toString().equals(
                                editTextLotteryNumber.getText().toString())){
                            Toast.makeText(CheckLotteryActivity.this,
                                    "Win" + data.child("lottery_prize").getValue(), Toast.LENGTH_SHORT).show();
                        } if(data.child("lottery_number").getValue().toString().equals( //last 2 number 000098
                                editTextLotteryNumber.getText().toString().substring(4,6))){
                            Toast.makeText(CheckLotteryActivity.this,
                                    "Win" + data.child("lottery_prize").getValue(), Toast.LENGTH_SHORT).show();
                        } if(data.child("lottery_number").getValue().toString().equals( //last 3 number
                                editTextLotteryNumber.getText().toString().substring(3,6))){
                            Toast.makeText(CheckLotteryActivity.this,
                                    "Win" + data.child("lottery_prize").getValue(), Toast.LENGTH_SHORT).show();
                        } if(data.child("lottery_number").getValue().toString().equals( //first 3 number
                                editTextLotteryNumber.getText().toString().substring(0,3))){
                            Toast.makeText(CheckLotteryActivity.this,
                                    "Win" + data.child("lottery_prize").getValue(), Toast.LENGTH_SHORT).show();
                        }

                    } if(!data.exists()) {
                        Toast.makeText(CheckLotteryActivity.this,
                                "Lose", Toast.LENGTH_SHORT).show();
                    }

//                    if(data.child("lottery_number").getValue().toString().equals(
//                            editTextLotteryNumber.getText().toString())){
//                        Toast.makeText(CheckLotteryActivity.this,
//                                "Win" + data.child("lottery_prize").getValue(), Toast.LENGTH_SHORT).show();
//                    } if(data.child("lottery_number").getValue().toString().equals( //last 2 number 000098
//                            editTextLotteryNumber.getText().toString().substring(4,6))){
//                        Toast.makeText(CheckLotteryActivity.this,
//                                "Win" + data.child("lottery_prize").getValue(), Toast.LENGTH_SHORT).show();
//                    } if(data.child("lottery_number").getValue().toString().equals( //last 3 number
//                            editTextLotteryNumber.getText().toString().substring(3,6))){
//                        Toast.makeText(CheckLotteryActivity.this,
//                                "Win" + data.child("lottery_prize").getValue(), Toast.LENGTH_SHORT).show();
//                    } if(data.child("lottery_number").getValue().toString().equals( //first 3 number
//                            editTextLotteryNumber.getText().toString().substring(0,3))){
//                        Toast.makeText(CheckLotteryActivity.this,
//                                "Win" + data.child("lottery_prize").getValue(), Toast.LENGTH_SHORT).show();
//                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        refLotteryNumber.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot data : dataSnapshot.getChildren()){
//                    ResultModel resultModel = dataSnapshot.getValue(ResultModel.class);
//                    if(data.child(editTextLotteryNumber.getText().toString()).exists()){
//                        Toast.makeText(CheckLotteryActivity.this,
//                                "win",Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }
}
