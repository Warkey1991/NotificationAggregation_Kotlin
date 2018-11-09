package yj.song.notiaggregate

import android.app.Application
import yj.song.notiaggregate.util.NotiAggregate
import kotlin.properties.Delegates

/**
 * Created by YJ.Song on 2018/11/8.
 */
class App : Application() {
    companion object {
        var instance: App by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        NotiAggregate.toggleNotificationListenerService(this)
    }
}