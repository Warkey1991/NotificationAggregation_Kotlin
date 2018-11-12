package yj.song.notiaggregate.util

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.provider.Settings
import android.text.TextUtils
import yj.song.notiaggregate.App
import yj.song.notiaggregate.model.AppPackageInfo
import yj.song.notiaggregate.service.NotificationCollectService
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by YJ.Song on 2018/11/8.
 */
object AndroidUtil {
    /**
     *  检测权限
     */
    fun checkPermission(context: Context): Boolean {
        val notiListeners: String = Settings.Secure.getString(context.contentResolver, "enabled_notification_listeners")
        return !TextUtils.isEmpty(notiListeners) && notiListeners.contains(NotificationCollectService::class.java.name)
    }

    /**
     * 跳转设置页开启通知管理权限
     */
    fun requestPermission(context: Context) {
        try {
            context.startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 毫秒转小时分钟
     */
    fun formatNotificationWhen(time: Long?): String {
        var date = Date(time!!)
        var outputsdf = SimpleDateFormat("HH:mm")
        return outputsdf.format(date)
    }

    fun getInstallApps(): List<AppPackageInfo> {
        try {
            val pm = App.instance.packageManager
            val installApps = pm.getInstalledPackages(0)
            val nonSystemApps = mutableListOf<AppPackageInfo>()
            installApps.forEach {
                //过滤掉系统app
                if ((ApplicationInfo.FLAG_SYSTEM and it.applicationInfo.flags) == 0) {
                    if (it.packageName != App.instance.packageName) {
                        val pkgName = it.packageName
                        val appName = it.applicationInfo.loadLabel(pm).toString()
                        val icon = it.applicationInfo.loadIcon(pm)
                        val iconBitmap = (icon as BitmapDrawable).bitmap
                        val app = AppPackageInfo(pkgName, appName, icon, false)
                        nonSystemApps.add(app)

                        saveIcon(iconBitmap, pkgName)
                    }
                }
            }
            return nonSystemApps
        } catch (e: Exception) {
            NLog.d(e.message.toString())
        }
        return mutableListOf()
    }

    fun saveIcon(icon: Bitmap, pkgName: String) {
        val directory = App.instance.getDir("icon", Context.MODE_PRIVATE)
        if (icon != null) {
            val iconFile = File(directory, pkgName + ".png")
            if (iconFile.exists() && iconFile.length() > 0) return
            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(iconFile)
                icon.compress(Bitmap.CompressFormat.PNG, 100, fos)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    if (fos != null)
                        fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun getDrawableFromIconFile(pkgName: String): Bitmap {
        val directory = App.instance.getDir("icon", Context.MODE_PRIVATE)
        var needFilePath = ""
        if (directory.exists() && directory.isDirectory) {
            val files = directory.listFiles()
            if (files != null) {
                for (file in files!!) {
                    if (file.path.contains(pkgName + ".png")) {
                        NLog.d("file path :" + file.path)
                        needFilePath = file.absolutePath
                        break
                    }
                }
            }
        }
        return BitmapFactory.decodeFile(needFilePath)
    }


    /**
     * dp2px
     *
     * @param context
     * @param dpValue
     * @return
     * @since 3.5
     */
    fun dp2px(context: Context, dpValue: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun dp2px(dpValue: Int): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun dp2px(dpValue: Float): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * px2dp
     *
     * @param context
     * @param pxValue
     * @return
     * @since 3.5
     */
    fun px2dp(context: Context, pxValue: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }


}