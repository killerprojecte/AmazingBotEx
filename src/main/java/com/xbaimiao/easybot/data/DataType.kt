package com.xbaimiao.easybot.data

import com.xbaimiao.easybot.EasyBot

enum class DataType {
    MYSQL, SQLITE, YAML;

    companion object {

        /**
         * 获取插件启动什么方式储存数据
         */
        @JvmStatic
        fun getDataType(): DataType {
            return when (EasyBot.INSTANCE.config.getString("mode")) {
                "sqlite" -> SQLITE
                "mysql" -> MYSQL
                else -> YAML
            }
        }

        /**
         * 获取储存数据是否为数据库方式
         */
        @JvmStatic
        fun isSQL(): Boolean {
            return DataType.getDataType() == DataType.MYSQL || DataType.getDataType() == DataType.SQLITE
        }

    }
}