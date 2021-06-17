package com.xbaimiao.easybot.sqlite

/* Add SQLite to store the data
 * Depend on TabooLib SQL untils
 */

import com.xbaimiao.easybot.EasyBot
import com.xbaimiao.easybot.sqlite.SQL.data
import com.xbaimiao.easybot.sqlite.SQL.sql
import io.izzel.taboolib.module.db.sql.query.Where
import me.albert.amazingbot.AmazingBot
import java.util.*

object SQLer {

    @JvmStatic
    val isEnable: Boolean
        get() = EasyBot.config.getString("mode") == "sqlite"

    init {
        if (isEnable) {
            if (!hasData()) {
                AmazingBot.getInstance().logger.info("§c检测到切换到sqlite储存,且尚未有任何绑定数据,开始从yaml导入....")
                val data = AmazingBot.getData().config
                var imported = 0
                for (qq in data.getKeys(false)) {
                    val uuid: String = data.getString(qq)!!
                    imported += 1
                    setBinding(qq.toLong(), uuid)
                }
                AmazingBot.getInstance().logger.info("§c已从YAML储存导入了" + imported + "条数据!")
            }
        }
    }

    private fun hasData(): Boolean {
        val result = data.select("qq").to(sql)
        if (result.find()) {
            return true
        }
        return AmazingBot.getData().config.getKeys(true).isNotEmpty()
    }

    @JvmStatic
    fun setBinding(qq: Long, uuid: String) {
        val result = data.select().where(Where.`is`("qq", qq)).to(sql)
        if (!result.find()) {
            data.insert(qq, uuid).run(sql)
            return
        }
        data.update(Where.`is`("qq", qq)).set("uuid", uuid).run(sql)
    }

    @JvmStatic
    fun getUUID(qq: Long): UUID? {
        val result = data.select().where(Where.`is`("qq", qq)).to(sql)
        if (!result.find()) {
            return null
        }
        var uuid: String? = null
        result.first {
            uuid = it.getString("uuid")
        }
        return UUID.fromString(uuid)
    }

    @JvmStatic
    fun getQQ(id: UUID): Long? {
        val result = data.select().where(Where.`is`("uuid", id.toString())).to(sql)
        if (!result.find()) {
            return null
        }
        var qq: Long = 0
        result.first {
            qq = it.getLong("qq")
        }
        return qq
    }

}
