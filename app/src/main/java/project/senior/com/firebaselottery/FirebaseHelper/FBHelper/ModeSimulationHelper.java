package project.senior.com.firebaselottery.FirebaseHelper.FBHelper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import project.senior.com.firebaselottery.Models.SimulationModel;

public class ModeSimulationHelper {

    DatabaseReference refLottery, refModeSimulation;
    Boolean saved = null;
    ArrayList<SimulationModel> simulationModels = new ArrayList<>();

    public ModeSimulationHelper(DatabaseReference refModeSimulation) {
        this.refModeSimulation = refModeSimulation;
    }

    public ArrayList<SimulationModel> retrieveData() {
        refModeSimulation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return simulationModels;
    }

    private void fetchData(DataSnapshot dataSnapshot){
        simulationModels.clear();

        for(DataSnapshot ds:dataSnapshot.getChildren()){
            SimulationModel simulationModel = ds.getValue(SimulationModel.class);
            simulationModels.add(simulationModel);
        }
    }
}
