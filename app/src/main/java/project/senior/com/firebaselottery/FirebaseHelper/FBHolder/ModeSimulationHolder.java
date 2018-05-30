package project.senior.com.firebaselottery.FirebaseHelper.FBHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import project.senior.com.firebaselottery.R;

public class ModeSimulationHolder extends RecyclerView.ViewHolder {

    public TextView sim_dateTextView, sim_statusTextView, sim_lotteryNumberTextView,
            sim_amountTextView, sim_paidTextView, sim_valueTextView;

    public ModeSimulationHolder(View itemView) {
        super(itemView);

        sim_dateTextView = (TextView) itemView.findViewById(R.id.sim_dateTextView);
        sim_statusTextView = (TextView) itemView.findViewById(R.id.sim_statusTextView);
        sim_lotteryNumberTextView = (TextView) itemView.findViewById(R.id.sim_lotteryNumberTextView);
        sim_amountTextView = (TextView) itemView.findViewById(R.id.sim_amountTextView);
        sim_paidTextView = (TextView) itemView.findViewById(R.id.sim_paidTextView);
        sim_valueTextView = (TextView) itemView.findViewById(R.id.sim_valueTextView);

    }
}
