package yj.song.notiaggregate.widget

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import yj.song.notiaggregate.R

/**
 * Created by YJ.Song on 2018/11/12.
 */
open class LoadDataView(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {
    var recyclerView: RecyclerView
    var progressBar: ProgressBar
    val LOADING = 1
    val SUCCESS = 2
    val ERROR = 3

    var status = LOADING

    init {
        View.inflate(context, R.layout.layout_load_data_view, this)
        recyclerView = findViewById(R.id.appRecyclerView)
        progressBar = findViewById(R.id.progressBar)
    }

    fun loading() {
        status = LOADING
        switchStated()
    }

    fun showData() {
        status = SUCCESS
        switchStated()
    }

    private fun switchStated() {
        when (status) {
            LOADING -> progressBar.visibility = View.VISIBLE
            SUCCESS -> progressBar.visibility = View.GONE
        }
    }


}