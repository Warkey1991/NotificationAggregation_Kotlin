package yj.song.notiaggregate.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by YJ.Song on 2018/11/8.
 */
@Entity
public class NotificationAppEntity {
    @Id
    private String pkgName;
    private String appName;
    private boolean interpet;

    @Generated(hash = 634847376)
    public NotificationAppEntity(String pkgName, String appName, boolean interpet) {
        this.pkgName = pkgName;
        this.appName = appName;
        this.interpet = interpet;
    }

    @Generated(hash = 952763577)
    public NotificationAppEntity() {
    }

    public String getPkgName() {
        return this.pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public boolean getInterpet() {
        return this.interpet;
    }

    public void setInterpet(boolean interpet) {
        this.interpet = interpet;
    }

}
