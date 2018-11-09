package yj.song.notiaggregate.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import yj.song.notiaggregate.App

/**
 * Created by YJ.Song on 2018/11/8.
 */
class DbManager private constructor(context: Context) {
    private val DB_NAME = "Noti_DB"
    private var mDaoSession: DaoSession? = null
    private var mDevOpenHelper: DaoMaster.DevOpenHelper? = null
    private var mDaoMaster: DaoMaster? = null
    private var notificationDao: NotificationAppEntityDao? = null

    companion object {
        @Volatile
        var instance: DbManager? = null

        fun getInstance(context: Context = App.instance): DbManager {
            if (instance == null) {
                synchronized(DbManager::class.java) {
                    if (instance == null) {
                        instance = DbManager(context)
                    }
                }
            }
            return instance!!
        }
    }

    init {
        mDevOpenHelper = DaoMaster.DevOpenHelper(context, DB_NAME)
        mDaoSession = getDaoSession()
        mDaoMaster = getDaoMaster()
    }

    fun getReadableDataBase(context: Context): SQLiteDatabase? {
        if (null == mDevOpenHelper) {
            getInstance(context)
        }

        return mDevOpenHelper?.readableDatabase
    }

    fun getWriteableDataBase(context: Context): SQLiteDatabase? {
        if (null == mDevOpenHelper) {
            getInstance(context)
        }

        return mDevOpenHelper?.writableDatabase
    }

    fun getDaoMaster(): DaoMaster? {
        if (mDaoMaster == null) {
            synchronized(DbManager::class.java) {
                if (mDaoMaster == null) {
                    mDaoMaster = DaoMaster(getWriteableDataBase(App.instance))
                }
            }
        }

        return mDaoMaster
    }

    fun getDaoSession(): DaoSession? {
        if (null == mDaoSession) {
            synchronized(DbManager::class.java) {
                mDaoSession = getDaoMaster()?.newSession()
            }
        }
        return mDaoSession
    }

    fun getAppDao(): NotificationAppEntityDao? {
        notificationDao = getDaoSession()?.notificationAppEntityDao
        return notificationDao
    }

}