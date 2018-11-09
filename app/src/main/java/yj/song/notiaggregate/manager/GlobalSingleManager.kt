package yj.song.notiaggregate.manager

import yj.song.notiaggregate.db.DbManager
import yj.song.notiaggregate.model.NotificationBean

/**
 * Created by YJ.Song on 2018/11/8.
 */
class GlobalSingleManager private constructor() {

    var notificationBeans: MutableList<NotificationBean>? = null

    init {
        notificationBeans = mutableListOf()
    }

    private object Holder {
        val INSTANCE = GlobalSingleManager()
    }

    companion object {
        val instance: GlobalSingleManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { Holder.INSTANCE }
    }

    fun addNotificationBean(bean: NotificationBean) {
        notificationBeans?.add(bean)
    }

    fun removeNotification(bean: NotificationBean) {
        notificationBeans?.remove(bean)
    }

    fun removeAllNotification() {
        notificationBeans?.clear()
    }

    fun checkIfNeedInterrupt(pkgName: String?): Boolean {
        val apps = DbManager.getInstance().getAppDao()?.loadAll()
        return (apps?.indexOfFirst { it.pkgName == pkgName } != -1)
    }

}