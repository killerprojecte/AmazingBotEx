package com.xbaimiao.easybot.sqlite

/* Add SQLite to store the data
 * Depend on TabooLib SQL untils
 */

import io.izzel.taboolib.module.db.sql.SQLColumn
import io.izzel.taboolib.module.db.sql.SQLColumnType
import io.izzel.taboolib.module.db.sql.SQLTable
import io.izzel.taboolib.module.db.sqlite.SQLiteHost
import me.albert.amazingbot.AmazingBot

import java.io.File
import javax.sql.DataSource

object SQL {

    @JvmStatic
    val sqlHost: SQLiteHost = SQLiteHost(
        File("${AmazingBot.getInstance().dataFolder}${File.separator}data.db"),
        AmazingBot.getInstance()
    )

    @JvmStatic
    val data = SQLTable(
        "QQData",
        SQLColumn(SQLColumnType.VARCHAR, 255, "qq"),
        SQLColumn(SQLColumnType.VARCHAR, 255, "uuid")
    )

    @JvmStatic
    val sql: DataSource = sqlHost.createDataSource()

    init {
        data.create(sql)
    }

}
