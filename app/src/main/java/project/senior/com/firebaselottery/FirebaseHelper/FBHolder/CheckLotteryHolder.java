package project.senior.com.firebaselottery.FirebaseHelper.FBHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import project.senior.com.firebaselottery.R;

public class CheckLotteryHolder extends RecyclerView.ViewHolder {

    public TextView dateTextView;
    public TextView lotteryNumberTextView;
    public TextView resultTextView;

    public CheckLotteryHolder(View itemView){
        super(itemView);

        dateTextView = (TextView) itemView.findViewById(R.id.dateTextView);
        lotteryNumberTextView = (TextView) itemView.findViewById(R.id.lotteryNumberTextView);
        resultTextView = (TextView) itemView.findViewById(R.id.resultTextView);
    }
}
