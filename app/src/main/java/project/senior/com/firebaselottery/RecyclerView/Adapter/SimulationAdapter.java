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

import project.senior.com.firebaselottery.Models.SimulationModel;
import project.senior.com.firebaselottery.R;

public class SimulationAdapter extends RecyclerView.Adapter<SimulationAdapter.SimulationHolder>{

    Context context;
    ArrayList<SimulationModel> listSimulation;

    public SimulationAdapter(Context context, ArrayList<SimulationModel> listSimulation){
        this.context = context;
        this.listSimulation = listSimulation;
    }

    @Override
    public SimulationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_simulation_list, parent, false);
        SimulationHolder holder = new SimulationHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(SimulationHolder holder, int position) {

        holder.sim_dateTextView.setText(listSimulation.get(position).getLottery_date());
        holder.sim_lotteryNumberTextView.setText(listSimulation.get(position).getLottery_number());
        holder.sim_amountTextView.setText(listSimulation.get(position).getLottery_amount());
        holder.sim_paidTextView.setText(listSimulation.get(position).getLottery_paid());
        holder.sim_statusTextView.setText(listSimulation.get(position).getLottery_status());
        holder.sim_valueTextView.setText(listSimulation.get(position).getLottery_value());

    }

    @Override
    public int getItemCount() {
        return listSimulation.size();
    }

    public class SimulationHolder extends RecyclerView.ViewHolder {
        public TextView sim_dateTextView, sim_statusTextView, sim_lotteryNumberTextView,
                sim_amountTextView, sim_paidTextView, sim_valueTextView;
        public RelativeLayout sim_background;
        public LinearLayout sim_foreground;

        public SimulationHolder(View itemView){
            super(itemView);

            sim_dateTextView = (TextView) itemView.findViewById(R.id.sim_dateTextView);
            sim_statusTextView = (TextView) itemView.findViewById(R.id.sim_statusTextView);
            sim_lotteryNumberTextView = (TextView) itemView.findViewById(R.id.sim_lotteryNumberTextView);
            sim_amountTextView = (TextView) itemView.findViewById(R.id.sim_amountTextView);
            sim_paidTextView = (TextView) itemView.findViewById(R.id.sim_paidTextView);
            sim_background = (RelativeLayout) itemView.findViewById(R.id.sim_background);
            sim_foreground = (LinearLayout) itemView.findViewById(R.id.sim_foreground);
            sim_valueTextView = (TextView) itemView.findViewById(R.id.sim_valueTextView);
        }
    }

//    public void deleteSimulation(int position){
//        SimulationModel model = listSimulation.get(position);
//        int id = model.getId();
//
//        DBSimulationAdapter db = new DBSimulationAdapter(context);
//        db.openDB();
//        if(db.deleteLottery(id)){
//            listSimulation.remove(position);
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
