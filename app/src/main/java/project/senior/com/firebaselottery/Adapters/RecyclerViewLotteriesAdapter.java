package project.senior.com.firebaselottery.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import project.senior.com.firebaselottery.Models.ResultModel;
import project.senior.com.firebaselottery.R;

public class RecyclerViewLotteriesAdapter extends RecyclerView.Adapter<RecyclerViewLotteriesAdapter.MyViewHolder>{

    List<ResultModel> listLotteries;

    public  RecyclerViewLotteriesAdapter(List<ResultModel> list){
        this.listLotteries = list;
    }

    @Override
    public RecyclerViewLotteriesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lotteries, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ResultModel data = listLotteries.get(position);

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
        return listLotteries.size();
    }
}