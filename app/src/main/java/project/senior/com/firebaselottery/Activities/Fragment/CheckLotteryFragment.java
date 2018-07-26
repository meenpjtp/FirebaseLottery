package project.senior.com.firebaselottery.Activities.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import project.senior.com.firebaselottery.FirebaseHelper.FBAdapter.CheckLotteryAdapter;
import project.senior.com.firebaselottery.FirebaseHelper.FBHelper.CheckLotteryHelper;
import project.senior.com.firebaselottery.MainActivity;
import project.senior.com.firebaselottery.Models.HistoryModel;
import project.senior.com.firebaselottery.R;
import project.senior.com.firebaselottery.Utils.StringUtil;

public class CheckLotteryFragment extends Fragment {

    public static final String TAG = "CheckLotteryFragment";
    private MainActivity mMainActivity;

    private LinearLayout checkLotteryFragment;
    private Spinner spinnerSelectDate;
    private EditText editTextLotteryNumber;
    private Button buttonSelect;
    private RecyclerView recyclerViewCheckLottery;

    // Firebase
    private HistoryModel historyModel;
    DatabaseReference refLottery, refCheck, refDate, refResult;
    private CheckLotteryHelper helper;
    private CheckLotteryAdapter lotteryAdapter;

    int countFalse = 0;
    int countTrue = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_check_lottery, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        initialize();

        refLottery = FirebaseDatabase.getInstance().getReference("LOTTERY");
        refResult = refLottery.child("RESULT");
        refCheck = refLottery.child("CHECK");

        historyModel = new HistoryModel();
        helper = new CheckLotteryHelper(refCheck); // Insert data to Firebase
        lotteryAdapter = new CheckLotteryAdapter(getContext(), helper.retrieveData());
        helper.retrieveData();

    }

    private void initialize() {

        mMainActivity = (MainActivity) getActivity();

        //Internet is not connect
        if(!isConnected(getContext())) buildDialog(getContext()).show();
        else {

        }

        getViewComponents();
        setRecyclerView();
    }

    private void setRecyclerView() {

        recyclerViewCheckLottery = (RecyclerView) getView().findViewById(R.id.recyclerViewCheckLottery);

        recyclerViewCheckLottery.setHasFixedSize(true);
        RecyclerView.LayoutManager LM = new LinearLayoutManager(getContext());
        recyclerViewCheckLottery.setLayoutManager(LM);
        recyclerViewCheckLottery.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCheckLottery.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

    }

    private void getViewComponents() {

        // Init view
        editTextLotteryNumber = (EditText) getView().findViewById(R.id.editTextLotteryNumber);
        checkLotteryFragment = (LinearLayout) getView().findViewById(R.id.checkLotteryFragment);
        spinnerSelectDate = (Spinner) getView().findViewById(R.id.spinnerSelectDate);
        buttonSelect = (Button) getView().findViewById(R.id.buttonSelect);

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
                ArrayAdapter<String> dateAdapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_item, dateSet);
                dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSelectDate.setAdapter(dateAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //
        editTextLotteryNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewCheckLottery.setAdapter(lotteryAdapter);
                lotteryAdapter.notifyDataSetChanged();
            }
        });


        //Button save to database
        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Click edittext retrieve data from firebase
                recyclerViewCheckLottery.setAdapter(lotteryAdapter);
                lotteryAdapter.notifyDataSetChanged();

                // Error Message when input length != 6
                if(StringUtil.isEmpty(editTextLotteryNumber.getText().toString())){
                    editTextLotteryNumber.setError(getString(R.string.error_message_length));
                    return;
                } if(StringUtil.isLength(editTextLotteryNumber.getText().toString())){
                    editTextLotteryNumber.setError(getString(R.string.error_message_length));
                    return;
                }

                final DatabaseReference refDate = refResult.child(spinnerSelectDate.getSelectedItem().toString());
                refResult.child(spinnerSelectDate.getSelectedItem().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        // Result Announcement
                        if(dataSnapshot.getValue() != null){
                            Log.i("testExists", "data exists");


                            refDate.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot1) {
                                    ArrayList<String> resultList = new ArrayList<>();

                                    for(DataSnapshot data : dataSnapshot1.getChildren()){

                                        /**
                                         *  Counting True or False to tell you win or not
                                         *  if counting False == 173 you did not win
                                         *  Counting True < 173 you win!
                                         */
                                        boolean match1 = data.child("lottery_number").getValue().toString().equals(editTextLotteryNumber.getText().toString());
                                        boolean match2 = data.child("lottery_number").getValue().toString().equals(editTextLotteryNumber.getText().toString().substring(4,6))
                                                && data.child("lottery_prize").getValue().toString().equals("เลขท้าย 2 ตัว");
                                        boolean match3 = data.child("lottery_number").getValue().toString().equals(editTextLotteryNumber.getText().toString().substring(3,6))
                                                && data.child("lottery_prize").getValue().toString().equals("เลขท้าย 3 ตัว");
                                        boolean match4 = data.child("lottery_number").getValue().toString().equals(editTextLotteryNumber.getText().toString().substring(0,3))
                                                && data.child("lottery_prize").getValue().toString().equals("เลขหน้า 3 ตัว");


                                        String result = data.child("lottery_prize").getValue().toString();
                                        String id = refCheck.push().getKey();
                                        String date = spinnerSelectDate.getSelectedItem().toString();
                                        String lottery_number = editTextLotteryNumber.getText().toString();



                                        // 1st prize | 2nd prize | 3rd prize | 4th prize | 5th prize

                                        if(match1 || match2 || match3 || match4){

                                            // Display Snackbar
//                                            Snackbar.make(checkLotteryFragment, "คุณถูก" + data.child("lottery_prize").getValue(), Snackbar.LENGTH_SHORT).show();
                                            resultList.add("คุณถูก" + data.child("lottery_prize").getValue());
                                            countTrue++;
                                            // Save check lottery to database
                                            historyModel = new HistoryModel(id, date, lottery_number, result);
                                            refCheck.child(id).setValue(historyModel);
                                        }


                                        // countFalse == 5 --> Did not win lottery!
                                        // change 5 -> 173


                                        // Waiting for result
                                        if(data.child("lottery_number").getValue().toString().equals("กำลังรอผล")){
                                            Snackbar.make(checkLotteryFragment, "กำลังรอผล", Snackbar.LENGTH_SHORT).show();

                                            historyModel = new HistoryModel(id, date, lottery_number, result);
                                            refCheck.child(id).setValue(historyModel);
                                            break;

                                        }

                                    } // end loop

                                    if(countTrue < 1){
                                        Log.i("countTrue", String.valueOf(countTrue));

                                        Snackbar.make(checkLotteryFragment, "คุณไม่ถูกรางวัล", Snackbar.LENGTH_SHORT).show();

                                        // Save check lottery to database
//                                        historyModel = new HistoryModel(id, date, lottery_number, "ไม่ถูกรางวัล");
//                                        refCheck.child(id).setValue(historyModel);
                                        clear();
                                    } else {
                                        String text = "";
                                        for (String resultText :
                                                resultList) {
                                            text += resultText + "\n";
                                        }

                                        Snackbar.make(checkLotteryFragment, text, Snackbar.LENGTH_LONG).show();
                                    }

                                    clear();
                                    countFalse = 0; // reset countFalse
                                    countTrue = 0;
                                    recyclerViewCheckLottery.setAdapter(lotteryAdapter);
                                }


                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });

    }


    // Clear EditText when press button
    private void clear(){
        editTextLotteryNumber.setText("");
    }

    // Internet is not connect
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

    // Save check lottery to database
   /* private void save(String selected_date, String lottery_number, String lottery_result){
        DBHistoryAdapter db = new DBHistoryAdapter(getContext());
        db.openDB();
        if(db.addLottery(selected_date, lottery_number, lottery_result)){
//            editTextLotteryNumber.setText("");
        } else {
            Toast.makeText(getContext(),"Unable to save", Toast.LENGTH_SHORT).show();;
        }
        db.closeDB();
    }*/

// Update list history to recyclerview
    /*private void getLotteries(){
        listHistory.clear();

        DBHistoryAdapter db = new DBHistoryAdapter(getContext());
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
            recyclerViewCheckLottery.setAdapter(adapter);
        }
    }*/
