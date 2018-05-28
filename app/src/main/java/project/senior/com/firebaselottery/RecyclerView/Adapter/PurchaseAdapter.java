package project.senior.com.firebaselottery.RecyclerView.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

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
                .inflate(R.layout.item_purchase_list, parent, false);
        PurchaseHolder holder = new PurchaseHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PurchaseHolder holder, int position) {

        holder.pur_dateTextView.setText(listPurchase.get(position).getLottery_date());
        holder.pur_statusTextView.setText(listPurchase.get(position).getLottery_status());
        holder.pur_lotteryNumberTextView.setText(listPurchase.get(position).getLottery_number());
        holder.pur_amountTextView.setText(listPurchase.get(position).getLottery_amount());
        holder.pur_paidTextView.setText(listPurchase.get(position).getLottery_paid());
        holder.pur_valueTextView.setText(listPurchase.get(position).getLottery_value());

    }

    @Override
    public int getItemCount() {
        return listPurchase.size();
    }

    public class PurchaseHolder extends RecyclerView.ViewHolder {
        public TextView pur_dateTextView, pur_statusTextView, pur_lotteryNumberTextView, pur_amountTextView, pur_paidTextView, pur_valueTextView;
        public RelativeLayout pur_background;
        public LinearLayout pur_foreground;

        public PurchaseHolder(View itemView){
            super(itemView);

            pur_dateTextView = (TextView) itemView.findViewById(R.id.pur_dateTextView);
            pur_statusTextView = (TextView) itemView.findViewById(R.id.pur_statusTextView);
            pur_lotteryNumberTextView = (TextView) itemView.findViewById(R.id.pur_lotteryNumberTextView);
            pur_amountTextView = (TextView) itemView.findViewById(R.id.pur_amountTextView);
            pur_paidTextView = (TextView) itemView.findViewById(R.id.pur_paidTextView);
            pur_background = (RelativeLayout) itemView.findViewById(R.id.pur_background);
            pur_foreground = (LinearLayout) itemView.findViewById(R.id.pur_foreground);
            pur_valueTextView = (TextView) itemView.findViewById(R.id.pur_valueTextView);
        }
    }

//    public void deletePurchase(int position){
//        PurchaseModel model = listPurchase.get(position);
//        int id = model.getId();
//
//        DBPurchaseAdapter db = new DBPurchaseAdapter(context);
//        db.openDB();
//        if(db.deleteLottery(id)){
//            listPurchase.remove(position);
//            Toast.makeText(context,R.string.message_delete_complete,Toast.LENGTH_SHORT).show();
//
//        } else {
//            Toast.makeText(context,"Unable To Delete",Toast.LENGTH_SHORT).show();
//        }
//        db.closeDB();
//
//        this.notifyItemRemoved(position);
//    }
}
