package com.xbaimiao.easybot

import com.xbaimiao.easybot.data.DataType
import com.xbaimiao.easybot.data.UserData
import com.xbaimiao.easybot.data.sql.SQLImpl
import com.xbaimiao.easybot.data.yaml.Yaml
import com.xbaimiao.easybot.load.KotlinLoader
import com.xbaimiao.easybot.load.MiraiLoader
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
        if (DataType.isSQL()) {
            userData = SQLImpl
            logger.info("enable sql save this bot data")
            return
        }
        userData = Yaml()
        logger.info("enable yaml save this bot data")
        Bukkit.getScheduler().runTaskTimer(this, Runnable {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"amb reload")
            System.out.println("自动刷新机器人状态")
        }, 72000L, 72000L)
    }

    private fun registerEvent(listener: Listener) {
        Bukkit.getServer().pluginManager.registerEvents(listener, INSTANCE)
    }

    override fun onDisable() {
        userData.save()
    }

}