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

import project.senior.com.firebaselottery.Models.HistoryModel;
import project.senior.com.firebaselottery.R;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {

    Context context;
    ArrayList<HistoryModel> listHistory;

    public HistoryAdapter(Context context, ArrayList<HistoryModel> listHistory){
        this.context = context;
        this.listHistory = listHistory;
    }

    @Override
    public HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history_list, parent, false);
        HistoryHolder holder = new HistoryHolder(view);
        return holder;
//        return new HistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryHolder holder, int position) {
//        HistoryModel data = listSimulation.get(position);

        holder.dateTextView.setText(listHistory.get(position).getSelected_date());
        holder.lotteryNumberTextView.setText(listHistory.get(position).getLottery_number());
        holder.resultTextView.setText(listHistory.get(position).getLottery_result());
    }

    @Override
    public int getItemCount() {
        return listHistory.size();
    }

    public class HistoryHolder extends RecyclerView.ViewHolder {
        public TextView dateTextView, lotteryNumberTextView, resultTextView;
        public RelativeLayout view_background;
        public LinearLayout view_foreground;

        public HistoryHolder(View itemView){
            super(itemView);

            dateTextView = (TextView) itemView.findViewById(R.id.dateTextView);
            lotteryNumberTextView = (TextView) itemView.findViewById(R.id.lotteryNumberTextView);
            resultTextView = (TextView) itemView.findViewById(R.id.resultTextView);
            view_background = (RelativeLayout) itemView.findViewById(R.id.view_background);
            view_foreground = (LinearLayout) itemView.findViewById(R.id.view_foreground);
        }
    }

//    public void deleteHistory(int position){
//        HistoryModel model = listHistory.get(position);
//        int id = model.getId();
//
//        DBHistoryAdapter db = new DBHistoryAdapter(context);
//        db.openDB();
//        if(db.deleteLottery(id)){
//            listHistory.remove(position);
//            Toast.makeText(context,R.string.message_delete_history,Toast.LENGTH_SHORT).show();
//
//        } else {
//            Toast.makeText(context,"Unable To Delete",Toast.LENGTH_SHORT).show();
//        }
//        db.closeDB();
//
//        this.notifyItemRemoved(position);
//    }
}
