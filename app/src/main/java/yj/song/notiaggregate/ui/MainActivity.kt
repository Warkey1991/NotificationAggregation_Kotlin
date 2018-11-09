package yj.song.notiaggregate.ui

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import yj.song.notiaggregate.R
import yj.song.notiaggregate.base.BaseActivity
import yj.song.notiaggregate.util.AndroidUtil

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_permission.setOnClickListener { AndroidUtil.requestPermission(this) }
        if (AndroidUtil.checkPermission(this)) {
            btn_permission.text = "已获得权限"
        } else {
            btn_permission.text = "去设置页获取权限"
        }

        btnRight.setOnClickListener {
            NotiSettingActivity.start(this)
        }

        btn_noti_list.setOnClickListener { NotiCleanActivity.start(this) }
    }

}
