package rynkbit.tk.coffeelist.ui

import android.content.Context
import android.content.res.Resources
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager

/**
 * Created by michael on 1/13/17.
 */

class ResponsiveStaggeredGridLayoutManager(context: Context, orientation: Int) : StaggeredGridLayoutManager(2, orientation) {
    init {

        val metrics = DisplayMetrics()
        val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        manager.defaultDisplay.getMetrics(metrics)
        val resources = context.resources
        val px = TypedValue
                .applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        138f,
                        resources.displayMetrics)
        val colCount = metrics.widthPixels / px

        this.spanCount = colCount.toInt()
    }
}
