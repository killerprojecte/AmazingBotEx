package com.xbaimiao.easybot.data.sql

import com.xbaimiao.easybot.EasyBot
import me.albert.amazingbot.AmazingBot
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement

object SQL {

    @JvmStatic
    val connection: Connection

    @JvmStatic
    val dataBase: Statement

    const val TABLE_NAME = "QQData"

    init {
        Class.forName("org.sqlite.JDBC")
        connection =
            DriverManager.getConnection("jdbc:sqlite:${AmazingBot.getInstance().dataFolder}${File.separator}data.db")
        this.dataBase = connection.createStatement()
        val sql = "CREATE TABLE IF NOT EXISTS $TABLE_NAME " +
                "(" +
                "qq LONG PRIMARY KEY NOT NULL," +
                "uuid VARCHAR(255) NOT NULL," +
                "name VARCHAR(255) NOT NULL" +
                ");"
        EasyBot.INSTANCE.logger.info("open database 'data.db' success")
        this.dataBase.executeUpdate(sql)
    }

}
