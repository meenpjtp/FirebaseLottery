package project.senior.com.firebaselottery.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import project.senior.com.firebaselottery.MainActivity;
import project.senior.com.firebaselottery.Models.HistoryModel;
import project.senior.com.firebaselottery.R;
import project.senior.com.firebaselottery.RecyclerView.Adapter.HistoryAdapter;
import project.senior.com.firebaselottery.RecyclerView.Swipe.HistorySwipe;
import project.senior.com.firebaselottery.Utils.StringUtil;

public class CheckLotteryFragment extends Fragment {

    public static final String TAG = "CheckLotteryFragment";
    private MainActivity mMainActivity;

    private LinearLayout checkLotteryFragment;
    private Spinner spinnerSelectDate;
    private EditText editTextLotteryNumber;
    private Button buttonSelect;
    private RecyclerView recyclerViewCheckLottery;

    //SQLite
    private ArrayList<HistoryModel> listHistory;
    HistoryAdapter adapter;

    int countFalse = 0;

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
    }

    private void initialize() {

        mMainActivity = (MainActivity) getActivity();

        //Internet is not connect
        if(!isConnected(getContext())) buildDialog(getContext()).show();
        else {

        }

        getViewComponents();
        setRecyclerView();
        getLotteries();

    }

    private void setRecyclerView() {

        recyclerViewCheckLottery = (RecyclerView) getView().findViewById(R.id.recyclerViewCheckLottery);
        listHistory = new ArrayList<>();
        adapter = new HistoryAdapter(getContext(), listHistory);

        recyclerViewCheckLottery.setHasFixedSize(true);
        RecyclerView.LayoutManager LM = new LinearLayoutManager(getContext());
        recyclerViewCheckLottery.setLayoutManager(LM);
        recyclerViewCheckLottery.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCheckLottery.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        // RecyclerView Swipe To Delete
        ItemTouchHelper.Callback callback = new HistorySwipe(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerViewCheckLottery);

        getLotteries();
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


        //Button save to database
        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                                public void onDataChange(DataSnapshot dataSnapshot1) {

                                    for(DataSnapshot data : dataSnapshot1.getChildren()){

                                        /**
                                         *  Counting True or False to tell you win or not
                                         *  if counting False == 5 you did not win
                                         *  Counting True < 5 you win!
                                         */
                                        boolean match1 = data.child("lottery_number").getValue().toString().equals(editTextLotteryNumber.getText().toString());
                                        boolean match2 = data.child("lottery_number").getValue().toString().equals(editTextLotteryNumber.getText().toString().substring(4,6));
                                        boolean match3 = data.child("lottery_number").getValue().toString().equals(editTextLotteryNumber.getText().toString().substring(3,6));
                                        boolean match4 = data.child("lottery_number").getValue().toString().equals(editTextLotteryNumber.getText().toString().substring(0,3));


                                        // 1st prize | 2nd prize | 3rd prize | 4th prize | 5th prize
                                        if(match1 == true){

                                            // Display Snackbar
                                            Snackbar.make(checkLotteryFragment, "คุณถูก" + data.child("lottery_prize").getValue(), Snackbar.LENGTH_SHORT).show();

                                            // Save check lottery to database
                                            save(spinnerSelectDate.getSelectedItem().toString(),
                                                    editTextLotteryNumber.getText().toString(),
                                                    "ถูก" + (String) data.child("lottery_prize").getValue());

                                            getLotteries();
                                            clear();
                                            break;
                                        }

                                        // Last 2 number
                                        else if(match2 == true){

                                            // Display Snackbar
                                            Snackbar.make(checkLotteryFragment, "คุณถูก" + data.child("lottery_prize").getValue(), Snackbar.LENGTH_SHORT).show();

                                            // Save check lottery to database
                                            save(spinnerSelectDate.getSelectedItem().toString(),
                                                    editTextLotteryNumber.getText().toString(),
                                                    "ถูก" + (String) data.child("lottery_prize").getValue());

                                            getLotteries();
                                            clear();
                                            break;
                                        }

                                        // Last 3 number
                                        else if(match3 == true){

                                            // Display Snackbar
                                            Snackbar.make(checkLotteryFragment, "คุณถูก" + data.child("lottery_prize").getValue(), Snackbar.LENGTH_SHORT).show();

                                            // Save check lottery to database
                                            save(spinnerSelectDate.getSelectedItem().toString(),
                                                    editTextLotteryNumber.getText().toString(),
                                                    "ถูก" + (String) data.child("lottery_prize").getValue());

                                            getLotteries();
                                            clear();
                                            break;
                                        }

                                        // First 3 number
                                        else if(match4 == true){

                                            // Display Snackbar
                                            Snackbar.make(checkLotteryFragment, "คุณถูก" + data.child("lottery_prize").getValue(), Snackbar.LENGTH_SHORT).show();

                                            // Save check lottery to database
                                            save(spinnerSelectDate.getSelectedItem().toString(),
                                                    editTextLotteryNumber.getText().toString(),
                                                    "ถูก" + (String) data.child("lottery_prize").getValue());

                                            getLotteries();
                                            clear();
                                            break;

                                        }


                                        // Did not win lottery (count boolean False == 5)
                                        if(match1 == false && match2 == false && match3 == false && match4 == false) {
                                            countFalse++;
                                            Log.i("countFalse", String.valueOf(countFalse));


                                        }
                                        // countFalse == 5 --> Did not win lottery!
                                        // change 5 -> 173
                                        if(countFalse == 152){
                                            Log.i("countFalse", String.valueOf(countFalse));

                                            Snackbar.make(checkLotteryFragment, "คุณไม่ถูกรางวัล", Snackbar.LENGTH_SHORT).show();

                                            // Save check lottery to database
                                            save(spinnerSelectDate.getSelectedItem().toString(),
                                                    editTextLotteryNumber.getText().toString(),
                                                    "ไม่ถูกรางวัล");

                                            getLotteries();
                                            clear();
                                            break;
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
                            Snackbar.make(checkLotteryFragment, R.string.message_result, Snackbar.LENGTH_SHORT).show();

                            // Save check lottery to database
                            save(spinnerSelectDate.getSelectedItem().toString(),
                                    editTextLotteryNumber.getText().toString(),
                                    "รอผลรางวัล");

                            getLotteries();
                            clear();


                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

    }

    // Save check lottery to database
    private void save(String selected_date, String lottery_number, String lottery_result){
        DBHistoryAdapter db = new DBHistoryAdapter(getContext());
        db.openDB();
        if(db.addLottery(selected_date, lottery_number, lottery_result)){
//            editTextLotteryNumber.setText("");
        } else {
            Toast.makeText(getContext(),"Unable to save", Toast.LENGTH_SHORT).show();;
        }
        db.closeDB();
    }

    // Update list history to recyclerview
    private void getLotteries(){
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