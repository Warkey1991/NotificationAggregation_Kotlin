package yj.song.notiaggregate.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_noti_setting.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import yj.song.notiaggregate.R
import yj.song.notiaggregate.db.DbManager
import yj.song.notiaggregate.model.DBEntityWrapper
import yj.song.notiaggregate.util.AndroidUtil

class NotiSettingActivity : AppCompatActivity() {
    var appAdapter: NonSystemAppAdapter? = null

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, NotiSettingActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noti_setting)

        appRecyclerview.layoutManager = LinearLayoutManager(this)
        appAdapter = NonSystemAppAdapter(this)
        appRecyclerview.adapter = appAdapter

        loadApps()

        toolBar.setNavigationOnClickListener { finish() }
    }

    private fun loadApps() {
        val installApps = AndroidUtil.getInstallApps()
        val dbApps = DbManager.getInstance().getAppDao()?.loadAll()

        appAdapter?.installApps = DBEntityWrapper.compareInterrpetAppInfo(dbApps = dbApps, installApps = installApps)
        GlobalScope.launch {

        }
    }
}
