package project.senior.com.firebaselottery.FirebaseHelper.FBAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import project.senior.com.firebaselottery.FirebaseHelper.FBHolder.ModePurchaseHolder;
import project.senior.com.firebaselottery.Models.PurchaseModel;
import project.senior.com.firebaselottery.R;

public class ModePurchaseAdapter extends RecyclerView.Adapter<ModePurchaseHolder> {

    Context c;
    ArrayList<PurchaseModel> purchaseModels;

    public ModePurchaseAdapter(Context c, ArrayList<PurchaseModel> purchaseModels) {
        this.c = c;
        this.purchaseModels = purchaseModels;
    }


    @Override
    public ModePurchaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.item_purchase_list, parent, false);
        return new ModePurchaseHolder(v);
    }

    @Override
    public void onBindViewHolder(ModePurchaseHolder holder, int position) {
        holder.pur_dateTextView.setText(purchaseModels.get(position).getLottery_date());
        holder.pur_statusTextView.setText(purchaseModels.get(position).getLottery_status());
        holder.pur_lotteryNumberTextView.setText(purchaseModels.get(position).getLottery_number());
        holder.pur_amountTextView.setText(purchaseModels.get(position).getLottery_amount());
        holder.pur_paidTextView.setText(purchaseModels.get(position).getLottery_paid());
        holder.pur_valueTextView.setText(purchaseModels.get(position).getLottery_value());

    }

    @Override
    public int getItemCount() {
        return purchaseModels.size();
    }
}
