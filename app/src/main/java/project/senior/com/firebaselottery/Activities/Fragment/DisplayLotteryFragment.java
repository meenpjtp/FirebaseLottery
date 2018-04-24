package project.senior.com.firebaselottery.Activities.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import project.senior.com.firebaselottery.MainActivity;
import project.senior.com.firebaselottery.Models.DisplayLotteryModel;
import project.senior.com.firebaselottery.R;
import project.senior.com.firebaselottery.RecyclerView.Adapter.LotteriesAdapter;

public class DisplayLotteryFragment extends Fragment {

    public static final String TAG = "DisplayLotteryFragment";
    private MainActivity mMainActivity;

    private Spinner spinnerSelectDate;
    private Button buttonSelect;
    private RecyclerView recyclerViewLotteries;

    private LotteriesAdapter adapter; //RecyclerView
    private List<DisplayLotteryModel> listDisplay;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_display_lottery, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        initialize();
    }

    private void initialize() {

        mMainActivity = (MainActivity) getActivity();

        if(!isConnected(getContext())) buildDialog(getContext()).show();
        else {

        }

        setRecyclerView();
        getViewComponents();

    }

    private void getViewComponents() {

        //Init view
        spinnerSelectDate = (Spinner) getView().findViewById(R.id.spinnerSelectDate);
        buttonSelect = (Button) getView().findViewById(R.id.buttonSelect);

        //Spinner
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

        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference refLottery = FirebaseDatabase.getInstance().getReference("LOTTERY");
                DatabaseReference refResult = refLottery.child("RESULT");
                DatabaseReference refDate = refResult.child(spinnerSelectDate.getSelectedItem().toString());

                refDate.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        listDisplay.clear();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            DisplayLotteryModel displayLotteryModel = dataSnapshot1.getValue(DisplayLotteryModel.class);
                            listDisplay.add(displayLotteryModel);
                        }
                        adapter = new LotteriesAdapter(getContext(), listDisplay);
                        recyclerViewLotteries.setAdapter(adapter);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("ERROR", "Unable to connect database.");

                    }
                });

            }
        });
    }

    private void setRecyclerView() {

        recyclerViewLotteries = (RecyclerView) getView().findViewById(R.id.recyclerViewLotteries);
        listDisplay = new ArrayList<>();
        adapter = new LotteriesAdapter(getContext(), listDisplay);

        recyclerViewLotteries.setHasFixedSize(true);
        RecyclerView.LayoutManager LM = new LinearLayoutManager(getContext());
        recyclerViewLotteries.setLayoutManager(LM);
        recyclerViewLotteries.setItemAnimator(new DefaultItemAnimator());
        recyclerViewLotteries.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

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
