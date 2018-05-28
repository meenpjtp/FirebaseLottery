package project.senior.com.firebaselottery.FirebaseHelper.FBHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import project.senior.com.firebaselottery.R;

public class ModePurchaseHolder extends RecyclerView.ViewHolder {

    public TextView pur_dateTextView, pur_statusTextView, pur_lotteryNumberTextView,
            pur_amountTextView, pur_paidTextView, pur_valueTextView;

    public ModePurchaseHolder(View itemView) {
        super(itemView);

        pur_dateTextView = (TextView) itemView.findViewById(R.id.pur_dateTextView);
        pur_statusTextView = (TextView) itemView.findViewById(R.id.pur_statusTextView);
        pur_lotteryNumberTextView = (TextView) itemView.findViewById(R.id.pur_lotteryNumberTextView);
        pur_amountTextView = (TextView) itemView.findViewById(R.id.pur_amountTextView);
        pur_paidTextView = (TextView) itemView.findViewById(R.id.pur_paidTextView);
        pur_valueTextView = (TextView) itemView.findViewById(R.id.pur_valueTextView);

    }
}
