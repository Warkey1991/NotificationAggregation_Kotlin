package yj.song.notiaggregate.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_noti_clean.*
import yj.song.notiaggregate.R
import yj.song.notiaggregate.base.BaseActivity
import yj.song.notiaggregate.manager.GlobalSingleManager
import yj.song.notiaggregate.manager.NotificationFuncManager

/**
 * 通知列表页:所有的通知都会显示在此处
 */
class NotiCleanActivity : BaseActivity() {
    var notificationAdapter: NotificationListAdapter? = null

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, NotiCleanActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noti_clean)

        initToolBar()

        notiRecyclerview.layoutManager = LinearLayoutManager(this)
        notificationAdapter = NotificationListAdapter(this)
        notificationAdapter?.notifications = GlobalSingleManager.instance.notificationBeans
        notiRecyclerview.adapter = notificationAdapter

        btnClean.setOnClickListener {
            GlobalSingleManager.instance.removeAllNotification()
            notificationAdapter?.notifyDataSetChanged()
            NotificationFuncManager.instance.refreshNotificationBar()
        }
    }


    fun initToolBar() {
        setSupportActionBar(toolBar)
        toolBar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
