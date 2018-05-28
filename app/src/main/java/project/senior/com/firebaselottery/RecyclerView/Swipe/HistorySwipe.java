package project.senior.com.firebaselottery.RecyclerView.Swipe;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import project.senior.com.firebaselottery.RecyclerView.Adapter.HistoryAdapter;

public class HistorySwipe extends ItemTouchHelper.SimpleCallback {

    private HistorySwipeListener listener;
    HistoryAdapter adapter;

    //    public HistorySwipe(int drag, int swipe){
//        super(drag, swipe);
//    }

    public HistorySwipe(int drag, int swipe, HistorySwipeListener listener){
        super(drag, swipe);
        this.listener = listener;

    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//        return false;
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if(viewHolder != null){
            final View foregroundView = ((HistoryAdapter.HistoryHolder) viewHolder).view_foreground;
            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView
            , RecyclerView.ViewHolder viewHolder, float dX, float dY
            , int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((HistoryAdapter.HistoryHolder) viewHolder).view_foreground;
        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive);

    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final View foregroundView = ((HistoryAdapter.HistoryHolder) viewHolder).view_foreground;
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((HistoryAdapter.HistoryHolder) viewHolder).view_foreground;

        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive);
    }



    public HistorySwipe(HistoryAdapter adapter){
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT);
        this.adapter = adapter;
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//        adapter.deleteHistory(viewHolder.getAdapterPosition());
    }

    public interface HistorySwipeListener{
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }
}
