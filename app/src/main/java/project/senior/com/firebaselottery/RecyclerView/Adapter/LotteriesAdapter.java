package project.senior.com.firebaselottery.RecyclerView.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import project.senior.com.firebaselottery.Models.DisplayLotteryModel;
import project.senior.com.firebaselottery.R;

public class LotteriesAdapter extends RecyclerView.Adapter<LotteriesAdapter.MyViewHolder>{

    Context context;
    List<DisplayLotteryModel> listResult;

    public LotteriesAdapter(Context context,List<DisplayLotteryModel> list){
        this.context = context;
        this.listResult = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lotteries, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DisplayLotteryModel data = listResult.get(position);

        holder.textViewLotteryNumber.setText(data.getLottery_number());
        holder.textViewPrize.setText(data.getLottery_prize());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewLotteryNumber, textViewPrize;

        public MyViewHolder(View itemView) {
            super(itemView);

            textViewLotteryNumber = (TextView) itemView.findViewById(R.id.textViewLotteryNumber);
            textViewPrize = (TextView) itemView.findViewById(R.id.textViewPrize);

        }
    }

    @Override
    public int getItemCount() {
        return listResult.size();
    }

    public void addAllData(List<DisplayLotteryModel> models){
        this.listResult.clear();
        this.listResult.addAll(models);
    }
}