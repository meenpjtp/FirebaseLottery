package project.senior.com.firebaselottery.Activities.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

import project.senior.com.firebaselottery.DBHelper.DBHelperSimulation.DBSimulationAdapter;
import project.senior.com.firebaselottery.Activities.ModeSimulation.AddLotterySimulationActivity;
import project.senior.com.firebaselottery.MainActivity;
import project.senior.com.firebaselottery.Models.SimulationModel;
import project.senior.com.firebaselottery.R;
import project.senior.com.firebaselottery.RecyclerView.Adapter.SimulationAdapter;
import project.senior.com.firebaselottery.RecyclerView.Swipe.SimulationSwipe;

public class SimulationFragment extends Fragment {

    public static final String TAG = "SimulationFragment";
    private MainActivity mMainActivity;

    private RecyclerView recyclerviewSimulation;
    private FloatingActionButton sim_fabAdd,sim_fabStat;
    private FloatingActionMenu sim_fabMenu;
    private RelativeLayout simulationFragment;
    private SearchView serchViewSimulation;

    // SQLite
    private ArrayList<SimulationModel> listModel;
    SimulationAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_simulation, container, false);
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

    private void getViewComponents() {

        //Init view
        simulationFragment = (RelativeLayout) getView().findViewById(R.id.simulationFragment);
        serchViewSimulation = (SearchView) getView().findViewById(R.id.serchViewSimulation);
        sim_fabMenu = (FloatingActionMenu) getView().findViewById(R.id.sim_fabMenu);
        sim_fabAdd = (FloatingActionButton) getView().findViewById(R.id.sim_fabAdd);
        sim_fabStat = (FloatingActionButton) getView().findViewById(R.id.sim_fabStat);

        //SearchView
        serchViewSimulation.setQueryHint(getString(R.string.text_search_date));

        //Press Floating Action Button start intent add lottery to simulation
        sim_fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddLotterySimulationActivity.class);
                startActivity(intent);
            }
        });


        serchViewSimulation.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchItem(s);
                return false;
            }
        });

    }

    //Recyclerview
    private void setRecyclerView() {
        recyclerviewSimulation = (RecyclerView) getView().findViewById(R.id.recyclerviewSimulation);

        listModel = new ArrayList<>();
        adapter = new SimulationAdapter(getContext(), listModel);

        recyclerviewSimulation.setHasFixedSize(true);
        RecyclerView.LayoutManager LM = new LinearLayoutManager(getContext());
        recyclerviewSimulation.setLayoutManager(LM);
        recyclerviewSimulation.setItemAnimator(new DefaultItemAnimator());
        recyclerviewSimulation.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        // RecyclerView Swipe To Delete
        ItemTouchHelper.Callback callback = new SimulationSwipe(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerviewSimulation);

    }

    private void getLotteries(){
        listModel.clear();

        DBSimulationAdapter db = new DBSimulationAdapter(getContext());
        db.openDB();
        Cursor cursor = db.retrieve();

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String lottery_date = cursor.getString(1);
            String lottery_number = cursor.getString(2);
            String lottery_amount = cursor.getString(3);
            String lottery_paid = cursor.getString(4);
            String lottery_status = cursor.getString(5);
            String lottery_value = cursor.getString(6);


            SimulationModel model = new SimulationModel();
            model.setId(id);
            model.setLottery_date(lottery_date);
            model.setLottery_number(lottery_number);
            model.setLottery_amount(lottery_amount);
            model.setLottery_paid(lottery_paid);
            model.setLottery_status(lottery_status);
            model.setLottery_value(lottery_value);

            listModel.add(model);

        }
        db.closeDB();

        if(listModel.size() > 0){
            recyclerviewSimulation.setAdapter(adapter);
        }
    }

    // Search List Lottery
    private void searchItem (String search)
    {
        listModel.clear();

        DBSimulationAdapter db=new DBSimulationAdapter(getContext());
        db.openDB();
        SimulationModel model1=null;
        Cursor cursor =db.retrieveSearch(search);

        while (cursor.moveToNext())
        {
            int id=cursor.getInt(0);
            String lottery_date=cursor.getString(1);
            String lottery_number=cursor.getString(2);
            String lottery_amount=cursor.getString(3);
            String lottery_paid=cursor.getString(4);
            String lottery_status=cursor.getString(5);
            String lottery_value = cursor.getString(6);


            model1 = new SimulationModel();
            model1.setId(id);
            model1.setLottery_date(lottery_date);
            model1.setLottery_number(lottery_number);
            model1.setLottery_amount(lottery_amount);
            model1.setLottery_paid(lottery_paid);
            model1.setLottery_status(lottery_status);
            model1.setLottery_value(lottery_value);

            listModel.add(model1);
        }

        db.closeDB();

        recyclerviewSimulation.setAdapter(adapter);
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
