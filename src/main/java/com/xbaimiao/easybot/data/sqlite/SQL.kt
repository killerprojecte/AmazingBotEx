package com.xbaimiao.easybot.data.sqlite

import me.albert.amazingbot.AmazingBot
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement


object SQL {

    @JvmStatic
    val connection: Connection

    @JvmStatic
    val dataBase:Statement

    val TABLE_NAME = "QQData"

    init {
        Class.forName("org.sqlite.JDBC")
        connection =
            DriverManager.getConnection("jdbc:sqlite:${AmazingBot.getInstance().dataFolder}${File.separator}data.db")
        println("Opened database successfully")
        this.dataBase = connection.createStatement()
        val sql = "CREATE TABLE IF NOT EXISTS $TABLE_NAME " +
                "(" +
                "qq LONG PRIMARY KEY NOT NULL," +
                "uuid VARCHAR(255) NOT NULL" +
                ");"
        this.dataBase.executeUpdate(sql)
    }

}
