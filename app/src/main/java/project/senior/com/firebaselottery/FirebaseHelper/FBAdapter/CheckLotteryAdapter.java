package project.senior.com.firebaselottery.FirebaseHelper.FBAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import project.senior.com.firebaselottery.FirebaseHelper.FBHolder.CheckLotteryHolder;
import project.senior.com.firebaselottery.Models.HistoryModel;
import project.senior.com.firebaselottery.R;

public class CheckLotteryAdapter extends RecyclerView.Adapter<CheckLotteryHolder> {

    Context c;
    ArrayList<HistoryModel> historyModels;

    public CheckLotteryAdapter(Context c, ArrayList<HistoryModel> historyModels) {
        this.c = c;
        this.historyModels = historyModels;
    }

    @Override
    public CheckLotteryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.item_history_list, parent, false);
        return new CheckLotteryHolder(v);
    }

    @Override
    public void onBindViewHolder(CheckLotteryHolder holder, int position) {
        holder.dateTextView.setText(historyModels.get(position).getSelected_date());
        holder.lotteryNumberTextView.setText(historyModels.get(position).getLottery_number());
        holder.resultTextView.setText(historyModels.get(position).getLottery_result());
    }

    @Override
    public int getItemCount() {
        return historyModels.size();
    }
}
