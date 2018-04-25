package project.senior.com.firebaselottery.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import project.senior.com.firebaselottery.RecyclerView.Adapter.DisplayImageLotteryAdapter;
import project.senior.com.firebaselottery.Models.ImageModel;
import project.senior.com.firebaselottery.R;

public class DisplayImageLotteryActivity extends AppCompatActivity {

    private Spinner spinner;
    private Button button;
    private RecyclerView recyclerView;

    private List<ImageModel> mModels;
    DisplayImageLotteryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image_lottery);

        spinner = (Spinner) findViewById(R.id.spinner);
        button = (Button) findViewById(R.id.button);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mModels = new ArrayList<>();


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
                ArrayAdapter<String> dateAdapter = new ArrayAdapter<String>(DisplayImageLotteryActivity.this,
                        android.R.layout.simple_spinner_item, dateSet);
                dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(dateAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

    }

    public void RunDatabse(View view){
        DatabaseReference refLottery = FirebaseDatabase.getInstance().getReference("LOTTERY");
        DatabaseReference refResult = refLottery.child("IMAGE");
        DatabaseReference refDate = refResult.child(spinner.getSelectedItem().toString());

        refDate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                mModels.clear();
//                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
//                    ImageModel model = dataSnapshot1.getValue(ImageModel.class);
//                    mModels.add(model);
//
//                }
                ImageModel model = dataSnapshot.getValue(ImageModel.class);
                mModels.add(model);
                adapter = new DisplayImageLotteryAdapter(DisplayImageLotteryActivity.this, mModels);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
