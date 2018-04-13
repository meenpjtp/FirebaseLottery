package project.senior.com.firebaselottery.RecyclerView.Swipe;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import project.senior.com.firebaselottery.RecyclerView.Adapter.SimulationAdapter;

public class SimulationSwipe extends ItemTouchHelper.SimpleCallback {

    private SimulationSwipeListener listener;
    SimulationAdapter adapter;

    public SimulationSwipe(int dragDirs, int swipeDirs, SimulationSwipeListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if(viewHolder != null){
            final View foregroundView = ((SimulationAdapter.SimulationHolder) viewHolder).sim_foreground;
            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView
            , RecyclerView.ViewHolder viewHolder, float dX, float dY
            , int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((SimulationAdapter.SimulationHolder) viewHolder).sim_foreground;
        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive);

    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final View foregroundView = ((SimulationAdapter.SimulationHolder) viewHolder).sim_foreground;
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((SimulationAdapter.SimulationHolder) viewHolder).sim_foreground;

        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive);
    }

    public SimulationSwipe(SimulationAdapter adapter){
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT);
        this.adapter = adapter;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.deleteSimulation(viewHolder.getAdapterPosition());
    }

    public interface SimulationSwipeListener{
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }
}
