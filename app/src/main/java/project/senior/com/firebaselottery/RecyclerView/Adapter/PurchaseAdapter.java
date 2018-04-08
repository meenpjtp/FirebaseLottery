package project.senior.com.firebaselottery.RecyclerView.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import project.senior.com.firebaselottery.DBHelper.DBAdapter;
import project.senior.com.firebaselottery.Models.PurchaseModel;
import project.senior.com.firebaselottery.R;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.PurchaseHolder> {

    Context context;
    ArrayList<PurchaseModel> listPurchase;

    public PurchaseAdapter(Context context, ArrayList<PurchaseModel> listPurchase){
        this.context = context;
        this.listPurchase = listPurchase;
    }

    @Override
    public PurchaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        PurchaseHolder holder = new PurchaseHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PurchaseHolder holder, int position) {

        holder.selectedDateTextView.setText(listPurchase.get(position).getLottery_date());
        holder.statusTextView.setText(listPurchase.get(position).getLottery_status());
        holder.lotteryNumberTextView.setText(listPurchase.get(position).getLottery_number());
        holder.amountTextView.setText(listPurchase.get(position).getLottery_amount());
        holder.paidTextView.setText(listPurchase.get(position).getLottery_paid());


    }

    @Override
    public int getItemCount() {
        return listPurchase.size();
    }

    public class PurchaseHolder extends RecyclerView.ViewHolder {
        public TextView selectedDateTextView, statusTextView, lotteryNumberTextView, amountTextView, paidTextView;
        public RelativeLayout view_background;
        public LinearLayout view_foreground;

        public PurchaseHolder(View itemView){
            super(itemView);

            selectedDateTextView = (TextView) itemView.findViewById(R.id.selectedDateTextView);
            statusTextView = (TextView) itemView.findViewById(R.id.statusTextView);
            lotteryNumberTextView = (TextView) itemView.findViewById(R.id.lotteryNumberTextView);
            amountTextView = (TextView) itemView.findViewById(R.id.amountTextView);
            paidTextView = (TextView) itemView.findViewById(R.id.paidTextView);
            view_background = (RelativeLayout) itemView.findViewById(R.id.view_background);
            view_foreground = (LinearLayout) itemView.findViewById(R.id.view_foreground);
        }
    }

    public void deleteHistory(int position){
        PurchaseModel model = listPurchase.get(position);
        int id = model.getId();

        DBAdapter db = new DBAdapter(context);
        db.openDB();
        if(db.deleteLottery(id)){
            listPurchase.remove(position);
            Toast.makeText(context,R.string.message_delete_history,Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(context,"Unable To Delete",Toast.LENGTH_SHORT).show();
        }
        db.closeDB();

        this.notifyItemRemoved(position);
    }
}