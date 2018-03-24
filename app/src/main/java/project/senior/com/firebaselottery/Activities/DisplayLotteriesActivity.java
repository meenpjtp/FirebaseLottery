package project.senior.com.firebaselottery.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import project.senior.com.firebaselottery.Adapters.RecyclerViewLotteriesAdapter;
import project.senior.com.firebaselottery.Models.ResultModel;
import project.senior.com.firebaselottery.R;

public class DisplayLotteriesActivity extends AppCompatActivity {

    private Spinner spinnerSelectDate;
    private Button buttonSelect;
    private RecyclerView recyclerViewLotteries;

    private RecyclerViewLotteriesAdapter adapter; //RecyclerView
    private List<ResultModel> listResult;
//    ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_lotteries);

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
         * ButtonSelect
         */
        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference selectedDate = FirebaseDatabase.getInstance().getReference(spinnerSelectDate.getSelectedItem().toString());
                selectedDate.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

//                        for(DataSnapshot snapshot: dataSnapshot.getChildren()){
//
////                            adapter.notifyDataSetChanged();
//
//                            String lottery_number = (String) snapshot.child("lottery_number").getValue();
//                            String lottery_prize = (String) snapshot.child("lottery_prize").getValue();
//
//                        }
                        ResultModel model = dataSnapshot.getValue(ResultModel.class);
                        listResult.add(model);
                        recyclerViewLotteries.setAdapter(adapter);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
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
        listResult = new ArrayList<>();
        adapter = new RecyclerViewLotteriesAdapter(listResult);

    }



}
