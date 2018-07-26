package project.senior.com.firebaselottery.Repository;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import project.senior.com.firebaselottery.FirebaseHelper.FBHelper.CheckLotteryHelper;
import project.senior.com.firebaselottery.Models.HistoryModel;

public class LotteryChecker {


    public static void isFirstPrize(String number) {
        DatabaseReference refLottery = FirebaseDatabase.getInstance().getReference("LOTTERY");
        DatabaseReference refResult = refLottery.child("RESULT");
//        DatabaseReference refCheck = refLottery.child("CHECK");
        CheckLotteryHelper helper = new CheckLotteryHelper(refResult);
        ArrayList<HistoryModel> historyList = helper.retrieveData();
        Log.i("ggggg", String.valueOf(historyList));
//        return target.equals(number);
    }
}
