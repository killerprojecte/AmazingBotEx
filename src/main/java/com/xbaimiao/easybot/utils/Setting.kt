package com.xbaimiao.easybot.utils

import com.xbaimiao.easybot.EasyBot
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File
import java.io.IOException

open class Setting(val file: File) : YamlConfiguration() {

    constructor(plugin: Plugin, fileName: String) : this(File(plugin.dataFolder, fileName))

    constructor(fileName: String) : this(File(EasyBot.INSTANCE.dataFolder, fileName))

    init {
        if (!file.parentFile.exists()) {
            file.parentFile.mkdirs()
        }
        if (!file.exists()) {
            try {
                file.createNewFile()
                val name = file.path.replace(EasyBot.INSTANCE.dataFolder.path, "")
                EasyBot.INSTANCE.logger.info("尝试获取文件$name")
                EasyBot.INSTANCE.getResource(name)?.let {
                    IO.toFile(it, file)
                }
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
        }
        this.load(file)
    }

    fun saveToFile() {
        save(file)
    }

    open fun reload() {
        this.load(file)
    }

    override fun getString(path: String): String? {
        return super.getString(path)?.replace("&", "§")
    }

    override fun getString(path: String, def: String?): String? {
        return super.getString(path, def)?.replace("&", "§")
    }

}