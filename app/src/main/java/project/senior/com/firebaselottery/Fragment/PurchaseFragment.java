package project.senior.com.firebaselottery.Fragment;

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
import project.senior.com.firebaselottery.DBHelper.DBHelperPurchase.DBPurchaseAdapter;
import project.senior.com.firebaselottery.Intent.AddLotteryPurchaseActivity;
import project.senior.com.firebaselottery.MainActivity;
import project.senior.com.firebaselottery.Models.PurchaseModel;
import project.senior.com.firebaselottery.R;
import project.senior.com.firebaselottery.RecyclerView.Adapter.PurchaseAdapter;
import project.senior.com.firebaselottery.RecyclerView.Swipe.PurchaseSwipe;

public class PurchaseFragment extends Fragment {

    public static final String TAG = "PurchaseFragment";
    private MainActivity mMainActivity;

    private RecyclerView recyclerviewPurchase;
    private FloatingActionButton fabAddLotteryPurchase1;
    private RelativeLayout purchaseFragment;
//    private LinearLayout linearLayoutWithoutData;
    private SearchView serchViewPurchase;
    FloatingActionMenu pur_fabMenu;
    FloatingActionButton pur_fabAdd, pur_fabStat;

    // SQLite
    private ArrayList<PurchaseModel> listModel;
    PurchaseAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_purchase, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pur_fabMenu = (FloatingActionMenu) view.findViewById(R.id.pur_fabMenu);
        pur_fabAdd = (FloatingActionButton) view.findViewById(R.id.pur_fabAdd);
        pur_fabStat = (FloatingActionButton) view.findViewById(R.id.pur_fabStat);

        //FloatingActionButton
        pur_fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a  = new Intent(getContext(), AddLotteryPurchaseActivity.class);
                startActivity(a);
            }
        });
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

//        fabAddLotteryPurchase1 = (FloatingActionButton) getView().findViewById(R.id.fabAddLotteryPurchase1);
//
//        //Press Floating Action Button start intent add lottery to purchase
//        fabAddLotteryPurchase1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent a  = new Intent(getContext(), AddLotteryPurchaseActivity.class);
//                startActivity(a);
//            }
//        });
    }

    private void getViewComponents() {

        //Init view
        purchaseFragment = (RelativeLayout) getView().findViewById(R.id.purchaseFragment);
        serchViewPurchase = (SearchView) getView().findViewById(R.id.serchViewPurchase);

        // Search View
        serchViewPurchase.setQueryHint(getString(R.string.text_search_date));
        serchViewPurchase.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        recyclerviewPurchase = (RecyclerView) getView().findViewById(R.id.recyclerviewPurchase);

        listModel = new ArrayList<>();
        adapter = new PurchaseAdapter(getContext(), listModel);

        recyclerviewPurchase.setHasFixedSize(true);
        RecyclerView.LayoutManager LM = new LinearLayoutManager(getContext());
        recyclerviewPurchase.setLayoutManager(LM);
        recyclerviewPurchase.setItemAnimator(new DefaultItemAnimator());
        recyclerviewPurchase.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        // RecyclerView Swipe To Delete
        ItemTouchHelper.Callback callback = new PurchaseSwipe(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerviewPurchase);

    }

    private void getLotteries(){
        listModel.clear();

        DBPurchaseAdapter db = new DBPurchaseAdapter(getContext());
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


            PurchaseModel model = new PurchaseModel();
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
            recyclerviewPurchase.setAdapter(adapter);
        }
    }

    // Search List Lottery
    private void searchItem (String search)
    {
        listModel.clear();

        DBPurchaseAdapter db=new DBPurchaseAdapter(getContext());
        db.openDB();
        PurchaseModel model1=null;
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


            model1 = new PurchaseModel();
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

        recyclerviewPurchase.setAdapter(adapter);
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
