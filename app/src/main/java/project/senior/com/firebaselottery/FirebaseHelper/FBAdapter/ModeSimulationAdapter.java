package project.senior.com.firebaselottery.FirebaseHelper.FBAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import project.senior.com.firebaselottery.FirebaseHelper.FBHolder.ModeSimulationHolder;
import project.senior.com.firebaselottery.Models.SimulationModel;
import project.senior.com.firebaselottery.R;

public class ModeSimulationAdapter extends RecyclerView.Adapter<ModeSimulationHolder> {

    Context c;
    ArrayList<SimulationModel> simulationModels;

    public ModeSimulationAdapter (Context c, ArrayList<SimulationModel> simulationModels) {
        this.c = c;
        this.simulationModels = simulationModels;
    }

    @Override
    public ModeSimulationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.item_simulation_list, parent, false);
        return new ModeSimulationHolder(v);
    }

    @Override
    public void onBindViewHolder(ModeSimulationHolder holder, int position) {
        holder.sim_dateTextView.setText(simulationModels.get(position).getLottery_date());
        holder.sim_statusTextView.setText(simulationModels.get(position).getLottery_status());
        holder.sim_lotteryNumberTextView.setText(simulationModels.get(position).getLottery_number());
        holder.sim_amountTextView.setText(simulationModels.get(position).getLottery_amount());
        holder.sim_paidTextView.setText(simulationModels.get(position).getLottery_paid());
        holder.sim_valueTextView.setText(simulationModels.get(position).getLottery_value());

    }

    @Override
    public int getItemCount() {
        return simulationModels.size();
    }
}
