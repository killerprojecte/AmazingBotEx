package com.xbaimiao.easybot.data.sqlite

import com.xbaimiao.easybot.EasyBot
import com.xbaimiao.easybot.data.BindIO
import com.xbaimiao.easybot.data.sqlite.SQL.TABLE_NAME
import com.xbaimiao.easybot.data.sqlite.SQL.dataBase
import me.albert.amazingbot.AmazingBot
import java.util.*

object SQLer : BindIO {

    @JvmStatic
    val isEnable: Boolean
        get() = EasyBot.INSTANCE.config.getString("mode") == "sqlite"

    init {
        if (isEnable) {
            if (!hasData()) {
                AmazingBot.getInstance().logger.info("§c检测到切换到sqlite储存,且尚未有任何绑定数据,开始从yaml导入....")
                val data = AmazingBot.getData()
                var imported = 0
                for (qq in data.getKeys(false)) {
                    val uuid: String = data.getString(qq)!!
                    imported += 1
                    setBind(qq.toLong(), uuid)
                }
                AmazingBot.getInstance().logger.info("§c已从YAML储存导入了" + imported + "条数据!")
            }
        }
    }

    private fun hasData(): Boolean {
        val result = dataBase.executeQuery("SELECT * FROM $TABLE_NAME;") //data.select("qq").to(sql)
        if (result.next()) {
            return true
        }
        return AmazingBot.getData().getKeys(true).isNotEmpty()
    }

    override fun setBind(qq: Long, uuid: String) {
        var sql = "SELECT * FROM $TABLE_NAME WHERE qq=$qq;"
        val result = dataBase.executeQuery(sql)
        if (result.next()) {
            sql = "UPDATE $TABLE_NAME SET uuid='$uuid' WHERE qq=$qq;"
            dataBase.executeUpdate(sql)
        } else {
            sql = "INSERT INTO $TABLE_NAME (qq,uuid) VALUES ($qq,'$uuid');"
            dataBase.executeUpdate(sql)
        }
    }

    override fun getPlayer(qq: Long): UUID? {
        val sql = "SELECT * FROM $TABLE_NAME WHERE qq=$qq;"
        val result = dataBase.executeQuery(sql)
        var uuid: String? = null
        if (!result.next()) {
            return null
        } else {
            uuid = result.getString("uuid")
        }
        return UUID.fromString(uuid)
    }

    override fun getUser(uuid: String): Long? {
        val sql = "SELECT * FROM $TABLE_NAME WHERE uuid='$uuid';"
        val result = dataBase.executeQuery(sql) //data.select().where(Where.`is`("uuid", id.toString())).to(dataBase)
        return if (!result.next()) {
            null
        } else {
            result.getLong("qq")
        }
    }

    override fun save() {
        dataBase.close()
    }

}
