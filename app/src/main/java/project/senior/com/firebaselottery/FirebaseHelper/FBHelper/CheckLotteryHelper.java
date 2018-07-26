package project.senior.com.firebaselottery.FirebaseHelper.FBHelper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import project.senior.com.firebaselottery.Models.HistoryModel;

public class CheckLotteryHelper {

    DatabaseReference refLottery, refCheck;
    Boolean saved = null;
    ArrayList<HistoryModel> historyModels = new ArrayList<>();

    public CheckLotteryHelper(DatabaseReference refCheck) {
        this.refCheck = refCheck;
    }

    public Boolean save (HistoryModel historyModel) {
        if(historyModel == null){
            saved = false;
        } else {
            try {
                refLottery = FirebaseDatabase.getInstance().getReference("LOTTERY");
                refCheck = refLottery.child("CHECK");
                refCheck.push().setValue(historyModel);
                saved = true;
            } catch (DatabaseException e){
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
    }

    public ArrayList<HistoryModel> retrieveData() {
        refCheck.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return historyModels;
    }

    private void fetchData(DataSnapshot dataSnapshot){
        historyModels.clear();
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            HistoryModel historyModel = ds.getValue(HistoryModel.class);
            historyModels.add(historyModel);
        }
    }
}
