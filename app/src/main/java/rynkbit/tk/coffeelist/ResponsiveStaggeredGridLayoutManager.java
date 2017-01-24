package rynkbit.tk.coffeelist;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

/**
 * Created by michael on 1/13/17.
 */

public class ResponsiveStaggeredGridLayoutManager extends StaggeredGridLayoutManager {
    public ResponsiveStaggeredGridLayoutManager(Context context, int orientation) {
        super(2, orientation);

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);
        Resources resources = context.getResources();
        float px = TypedValue
                .applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        138,
                        resources.getDisplayMetrics());
        float colCount = metrics.widthPixels / px;

        this.setSpanCount((int) colCount);
    }
}
