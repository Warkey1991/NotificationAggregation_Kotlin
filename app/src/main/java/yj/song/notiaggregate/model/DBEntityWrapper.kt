package yj.song.notiaggregate.model

import android.app.Notification
import android.service.notification.StatusBarNotification
import yj.song.notiaggregate.db.NotificationAppEntity
import yj.song.notiaggregate.util.AndroidUtil

/**
 * Created by YJ.Song on 2018/11/8.
 */
object DBEntityWrapper {

    fun statusBarNotificationConvertBean(sbn: StatusBarNotification?): NotificationBean =
            with(sbn) {
                val extras = sbn?.notification?.extras
                val pkgName = sbn?.packageName
                val title = extras?.getString(Notification.EXTRA_TITLE) //通知title
                val content = extras?.getString(Notification.EXTRA_TEXT)//通知内容
                val `when` = sbn?.notification?.`when`
                val icon = AndroidUtil.getDrawableFromIconFile(pkgName!!)
                val pendingIntent = sbn?.notification?.contentIntent
                return NotificationBean(pkgName, title, content, icon, `when`, pendingIntent)
            }

    fun appInfoConvertNotificationApp(app: AppPackageInfo): NotificationAppEntity = with(app) {
        val appName = app.appName
        val pkgName = app.pkgName
        return NotificationAppEntity(pkgName, appName, false)
    }

    fun compareInterrpetAppInfo(dbApps: List<NotificationAppEntity>?, installApps: List<AppPackageInfo>?): List<AppPackageInfo> {
        var appPkgNames = mutableSetOf<String>()
        dbApps?.forEach { appPkgNames.add(it.pkgName) }
        val apps = installApps?.map { it ->
            it.interrupt = appPkgNames.contains(it.pkgName)
            it
        }
        return if (apps == null) listOf() else apps
    }
}