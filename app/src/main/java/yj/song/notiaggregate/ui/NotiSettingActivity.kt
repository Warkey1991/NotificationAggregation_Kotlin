package yj.song.notiaggregate.ui

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_noti_setting.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import yj.song.notiaggregate.Constant
import yj.song.notiaggregate.R
import yj.song.notiaggregate.db.DbManager
import yj.song.notiaggregate.manager.GlobalSingleManager
import yj.song.notiaggregate.manager.NotificationFuncManager
import yj.song.notiaggregate.model.AppPackageInfo
import yj.song.notiaggregate.model.DBEntityWrapper
import yj.song.notiaggregate.util.AndroidUtil
import yj.song.notiaggregate.util.PrefUtil
import yj.song.notiaggregate.widget.LoadDataView

class NotiSettingActivity : AppCompatActivity() {
    var appAdapter: NonSystemAppAdapter? = null
    var loadView: LoadDataView? = null

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, NotiSettingActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noti_setting)
        toolBar.setNavigationOnClickListener { finish() }

        loadView = findViewById(R.id.loadDataView)
        loadDataView.recyclerView?.layoutManager = LinearLayoutManager(this)
        appAdapter = NonSystemAppAdapter(this)
        loadDataView.recyclerView?.adapter = appAdapter

        loadApps()

        initSwitchState()
    }

    fun initSwitchState() {
        val check = PrefUtil.getBoolean(Constant.NOTI_OPEN)
        notiSwitchCheckBox.isChecked = check
        notiSwitchCheckBox.setOnCheckedChangeListener { compoundButton, b ->
            PrefUtil.putBoolean(Constant.NOTI_OPEN, b)
            if (b) NotificationFuncManager.instance.showPermanentNotification(GlobalSingleManager.instance.notificationBeans!!)
            else NotificationFuncManager.instance.closePermanentNotification()

        }
    }

    fun loadApps() {
        loadView?.loading()
        GlobalScope.launch(Dispatchers.IO) {
            val installApps = AndroidUtil.getInstallApps()
            val dbApps = DbManager.getInstance().getAppDao()?.loadAll()
            val needApps = DBEntityWrapper.compareInterrpetAppInfo(dbApps = dbApps, installApps = installApps)
            delay(1000)
            showApps(needApps)
        }
    }

    fun showApps(needApps: List<AppPackageInfo>) {
        GlobalScope.launch(Dispatchers.Main) {
            layoutTop.visibility = View.VISIBLE
            loadView?.showData()
            appAdapter?.installApps = needApps
        }
    }

}
