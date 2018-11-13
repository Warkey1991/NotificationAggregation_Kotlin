package yj.song.notiaggregate

import android.app.Application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import yj.song.notiaggregate.db.DbManager
import yj.song.notiaggregate.model.DBEntityWrapper
import yj.song.notiaggregate.util.AndroidUtil
import yj.song.notiaggregate.util.NotiAggregate
import yj.song.notiaggregate.util.PrefUtil
import kotlin.properties.Delegates

/**
 * Created by YJ.Song on 2018/11/8.
 */
class App : Application() {
    val whites = listOf("com.tencent.mm",
            "com.facebook.katana",
            "com.whatsapp",
            "com.instagram.android",
            "com.tencent.mobileqq")

    companion object {
        var instance: App by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        NotiAggregate.toggleNotificationListenerService(this)

        initDB()
    }

    fun initDB() {
        val firstComeIn = PrefUtil.getBoolean("first_in", true)
        if (firstComeIn) {
            GlobalScope.launch(Dispatchers.IO) {
                val apps = AndroidUtil.getInstallApps()
                apps.forEach {
                    if (whites.contains(it.pkgName)) return@forEach //相当于java中的continue
                    val app = DbManager.getInstance().getAppDao()?.load(it.pkgName)
                    if (app == null) {
                        val notificationBean = DBEntityWrapper.appInfoConvertNotificationApp(it)
                        DbManager.getInstance().getAppDao()?.insert(notificationBean)
                    }
                }
                PrefUtil.putBoolean("first_in", false)
            }
        }
    }
}