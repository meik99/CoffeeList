package rynkbit.tk.coffeelist;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by mrynkiewicz on 1/12/17.
 */

public class ResponsiveGridLayoutManager extends GridLayoutManager {
    private int itemMinWidth;

    public ResponsiveGridLayoutManager(Context context, int minWidth) {
        super(context, 1);
        itemMinWidth = minWidth;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        updateSpanCount();
        super.onLayoutChildren(recycler, state);
    }

    private void updateSpanCount() {
        int spanCount = getWidth() / itemMinWidth;
        if (spanCount < 1) {
            spanCount = 1;
        }
        this.setSpanCount(spanCount);
    }
}
