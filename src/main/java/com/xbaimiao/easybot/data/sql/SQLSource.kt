package com.xbaimiao.easybot.data.sql

import com.xbaimiao.easybot.EasyBot
import com.xbaimiao.easybot.data.DataType
import com.xbaimiao.easybot.utils.Setting
import me.albert.amazingbot.AmazingBot
import org.bukkit.Bukkit
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement

/**
 * 数据源
 */
abstract class SQLSource {

    lateinit var connection: Connection
        private set

    lateinit var dataBase: Statement
        private set

    private var mysqlUser: String? = null

    private var mysqlPassword: String? = null

    val tabName = "QQData"

    private fun createTab() {
        val sql = "CREATE TABLE IF NOT EXISTS $tabName " +
                "(" +
                "qq BIGINT(20) PRIMARY KEY NOT NULL," +
                "uuid VARCHAR(255) NOT NULL," +
                "name VARCHAR(255) NOT NULL" +
                ");"
        dataBase.executeUpdate(sql)
    }

    init {
        when (DataType.getDataType()) {
            DataType.SQLITE -> {
                Class.forName("org.sqlite.JDBC")
                this.connection =
                    DriverManager.getConnection("jdbc:sqlite:${EasyBot.INSTANCE.dataFolder}${File.separator}data.db")
                this.dataBase = connection.createStatement()
            }
            DataType.MYSQL -> {
                val mysql = Setting("mysql.yml")
                this.mysqlUser = mysql.getString("storage.username")
                this.mysqlPassword = mysql.getString("storage.password")
                val dbUrl = String.format(
                    "jdbc:mysql://%s:%s/%s?autoReconnect=true&useSSL=%s",
                    mysql.getString("storage.host"),
                    mysql.getString("storage.port"),
                    mysql.getString("storage.database"),
                    mysql.getString("storage.useSSL")
                )
                Class.forName("com.mysql.jdbc.Driver")
                this.connection = DriverManager.getConnection(dbUrl, mysqlUser, mysqlPassword)
                this.dataBase = connection.createStatement()
                Bukkit.getScheduler().runTaskTimerAsynchronously(EasyBot.INSTANCE, Runnable {
                    SQLImpl.getPlayer(3104026189)
                }, 200, 200)
            }
            else -> println("未找到数据库")
        }
        createTab()
    }

}
