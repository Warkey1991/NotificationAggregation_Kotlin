package yj.song.notiaggregate.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import yj.song.notiaggregate.R
import yj.song.notiaggregate.db.DbManager
import yj.song.notiaggregate.model.AppPackageInfo
import yj.song.notiaggregate.model.DBEntityWrapper

/**
 * Created by YJ.Song on 2018/11/8.
 */
class NonSystemAppAdapter(context: Context) : RecyclerView.Adapter<NonSystemAppAdapter.ItemHolder>() {

    var installApps = listOf<AppPackageInfo>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onBindViewHolder(holder: ItemHolder?, position: Int) {
        val appBean = installApps[position]
        holder?.bindData(appBean)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_nonsystem_app, parent, false)
        return ItemHolder(view)
    }

    override fun getItemCount(): Int {
        return installApps.size
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var appIcon: ImageView? = null
        var appNameText: TextView? = null
        var checkBox: CheckBox? = null

        init {
            appIcon = itemView.findViewById(R.id.appIcon)
            appNameText = itemView.findViewById(R.id.appNameText)
            checkBox = itemView.findViewById(R.id.checkbox)
        }

        fun bindData(appInfo: AppPackageInfo) = with(appInfo) {
            appIcon?.setImageDrawable(appInfo.icon)
            appNameText?.text = appInfo.appName
            checkBox?.isChecked = appInfo.interrupt

            checkBox?.setOnCheckedChangeListener { _, b ->
                var notificationApp = DBEntityWrapper.appInfoConvertNotificationApp(appInfo)
                notificationApp.interpet = b
                val app = DbManager.getInstance().getAppDao()?.load(notificationApp.pkgName)
                if (app == null) {
                    DbManager.getInstance().getAppDao()?.insert(notificationApp)
                } else {
                    DbManager.getInstance().getAppDao()?.delete(notificationApp)
                }
            }
        }
    }
}