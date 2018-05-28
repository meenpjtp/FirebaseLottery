package project.senior.com.firebaselottery.FirebaseHelper.FBHelper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import project.senior.com.firebaselottery.Models.PurchaseModel;

public class ModePurchaseHelper {

    DatabaseReference refLottery, refModePurchase;
    Boolean saved = null;
    ArrayList<PurchaseModel> purchaseModels = new ArrayList<>();

    public ModePurchaseHelper(DatabaseReference refModePurchase) {
        this.refModePurchase = refModePurchase;
    }

    public ArrayList<PurchaseModel> retrieveData() {
        refModePurchase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return purchaseModels;
    }

    private void fetchData(DataSnapshot dataSnapshot){
        purchaseModels.clear();

        for(DataSnapshot ds:dataSnapshot.getChildren()){
            PurchaseModel purchaseModel = ds.getValue(PurchaseModel.class);
            purchaseModels.add(purchaseModel);
        }
    }
}
