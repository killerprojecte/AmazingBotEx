package com.xbaimiao.easybot

import com.xbaimiao.easybot.data.UserData
import com.xbaimiao.easybot.data.sql.MySQL
import com.xbaimiao.easybot.data.sql.SQLite
import com.xbaimiao.easybot.data.yaml.Yaml
import com.xbaimiao.easybot.utils.Setting
import me.albert.amazingbot.AmazingBot
import me.albert.amazingbot.bot.Bot
import me.albert.amazingbot.listeners.NewPlayer
import me.albert.amazingbot.listeners.OnBind
import me.albert.amazingbot.listeners.OnCommand
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

/**
 * 插件主类，原来为 AmazingBot
 */
class EasyBot : JavaPlugin() {

    companion object {
        lateinit var INSTANCE: EasyBot
    }

    init {
        KotlinLoader.start()
        INSTANCE = this
        MiraiLoader.start()
    }

    lateinit var data: Setting
        private set

    lateinit var userData: UserData
        private set

    override fun onEnable() {
        saveDefaultConfig()
        AmazingBot.start()
        registerEvent(NewPlayer())
        registerEvent(OnBind())
        registerEvent(OnCommand())
        Bukkit.getScheduler().runTaskLater(this, Runnable { Bot.start() }, 30L)
        data = Setting(INSTANCE, "data.yml")
        if (MySQL.isENABLED()) {
            userData = MySQL()
            logger.info("enable mysql save this bot data")
            return
        }
        if (SQLite.isEnable) {
            userData = SQLite
            logger.info("enable sqlite save this bot data")
            return
        }
        userData = Yaml()
        logger.info("enable yaml save this bot data")
    }

    private fun registerEvent(listener: Listener) {
        Bukkit.getServer().pluginManager.registerEvents(listener, INSTANCE)
    }

    override fun onDisable() {
        userData.save()
    }

}