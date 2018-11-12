package yj.song.notiaggregate.manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.widget.RemoteViews
import yj.song.notiaggregate.App
import yj.song.notiaggregate.R
import yj.song.notiaggregate.model.NotificationBean
import yj.song.notiaggregate.ui.NotiCleanActivity

/**
 * Created by YJ.Song on 2018/11/9.
 */
class NotificationFuncManager private constructor() {
    private val channelId = "noti_aggr_1001"
    private val channelName = "noti_aggr"
    private val MAX_SHOW_COUNT = 5
    private var notificationManager: NotificationManager? = null
    private var permanentBuilder: NotificationCompat.Builder? = null

    init {
        notificationManager = App.instance.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private object Holder {
        val INSTANCE = NotificationFuncManager()
    }

    companion object {
        val instance: NotificationFuncManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { NotificationFuncManager.Holder.INSTANCE }
    }


    /**
     * 创建常驻通知栏
     *
     * @param context
     * @param mNotificationEntityList
     */
    fun showPermanentNotification(mNotificationEntityList: MutableList<NotificationBean>, context: Context = App.instance) {
        permanentBuilder = NotificationCompat.Builder(context, channelId)
        val remoteViews = updateRemoteViews(context, mNotificationEntityList)
        if (remoteViews == null) {
            notificationManager?.cancel(0x11)
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager?.createNotificationChannel(channel)
        }
        val intent = Intent(context, NotiCleanActivity::class.java)
        intent.putExtra("NotificationMng", "clean")
        intent.putExtra("source", "notification")
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notification = permanentBuilder!!.setSmallIcon(R.mipmap.icon_noti_bar)
                .setWhen(System.currentTimeMillis())//通知的时间
                .setAutoCancel(false)//点击后消失
                .setContentIntent(pendingIntent)//设置意图
                .setOngoing(true)//常驻
                .setPriority(NotificationCompat.PRIORITY_HIGH)//通知程度
                .setContent(remoteViews).build()
        try {
            notificationManager?.notify(0x11, notification)//显示通知
        } catch (ignored: Exception) {
        }
    }

    fun closePermanentNotification() {
        notificationManager?.cancel(0x11)
    }

    private fun updateRemoteViews(context: Context, mNotificationEntityList: List<NotificationBean>?): RemoteViews? {
        val remoteViews = RemoteViews(context.packageName, R.layout.notifiaction_remote_view)
        remoteViews.removeAllViews(R.id.remote_view_layout2)

        var showCount = MAX_SHOW_COUNT
        if (mNotificationEntityList!!.size < MAX_SHOW_COUNT) {
            showCount = mNotificationEntityList!!.size
        }
        for (i in 0 until showCount) {
            val notificationEntity = mNotificationEntityList!!.get(i)
            val remoteView = RemoteViews(context.packageName, R.layout.item_rempte_image_view)

            if (i == MAX_SHOW_COUNT - 1) {
                remoteView.setImageViewResource(R.id.remote_view_image_item, R.mipmap.ic_noti_bar_more)
            } else if (notificationEntity.icon != null) {
                remoteView.setImageViewBitmap(R.id.remote_view_image_item, notificationEntity.icon)
            } else {
                remoteView.setImageViewResource(R.id.remote_view_image_item, R.mipmap.ic_launcher)
            }
            remoteViews.addView(R.id.remote_view_layout2, remoteView)
        }
        val intent = Intent(context, NotiCleanActivity::class.java)
        intent.putExtra("NotificationMng", "clean")
        intent.putExtra("source", "notification")
        val cleanPi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        remoteViews.setOnClickPendingIntent(R.id.remote_view_button, cleanPi)
        var count = 0
        if (mNotificationEntityList != null) {
            count = mNotificationEntityList.size
        }
        remoteViews.setTextViewText(R.id.remote_view_textview, "" + count)
        return remoteViews
    }


    fun refreshNotificationBar() {
        showPermanentNotification(GlobalSingleManager.instance.notificationBeans!!)
    }

}