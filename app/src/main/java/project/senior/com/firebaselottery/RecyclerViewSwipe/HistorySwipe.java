package project.senior.com.firebaselottery.RecyclerViewSwipe;


import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import project.senior.com.firebaselottery.RecyclerViewAdapters.HistoryAdapter;

public class HistorySwipe extends ItemTouchHelper.SimpleCallback {

    HistoryAdapter adapter;

    public HistorySwipe(int drag, int swipe){
        super(drag, swipe);
    }

    public HistorySwipe(HistoryAdapter adapter){
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT);
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.deleteHistory(viewHolder.getAdapterPosition());
    }
}
