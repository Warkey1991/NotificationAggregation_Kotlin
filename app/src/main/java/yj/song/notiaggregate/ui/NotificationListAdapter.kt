package yj.song.notiaggregate.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import yj.song.notiaggregate.R
import yj.song.notiaggregate.manager.GlobalSingleManager
import yj.song.notiaggregate.manager.NotificationFuncManager
import yj.song.notiaggregate.model.NotificationBean
import yj.song.notiaggregate.util.AndroidUtil

/**
 * Created by YJ.Song on 2018/11/8.
 */
class NotificationListAdapter(context: Context) : RecyclerView.Adapter<NotificationListAdapter.ItemHolder>() {
    init {

    }

    var notifications: List<NotificationBean>? = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_noti, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder?, position: Int) {
        val bean: NotificationBean? = notifications?.get(position)
        holder?.bindData(bean!!)
    }

    override fun getItemCount(): Int {
        if (notifications == null) {
            return 0
        }
        return notifications!!.size
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iconImageView: ImageView? = null
        var titleTextView: TextView? = null
        var contentTextView: TextView? = null
        var whenTextView: TextView? = null

        init {
            iconImageView = itemView.findViewById(R.id.icon_view)
            titleTextView = itemView.findViewById(R.id.title_text)
            contentTextView = itemView.findViewById(R.id.content_text)
            whenTextView = itemView.findViewById(R.id.when_text)
        }

        fun bindData(bean: NotificationBean) = with(bean) {
            iconImageView?.setImageBitmap(bean.icon)
            titleTextView?.text = bean.title
            contentTextView?.text = bean.content
            whenTextView?.text = AndroidUtil.formatNotificationWhen(bean.sendTime)

            itemView.setOnClickListener {
                GlobalSingleManager.instance.removeNotification(bean)
                NotificationFuncManager.instance.refreshNotificationBar()
                val position = notifications?.indexOf(bean)!!
                notifyItemRemoved(position)
                if (bean.pendingIntent != null) {
                    bean.pendingIntent.send()
                }
            }
        }
    }
}