package com.xbaimiao.easybot

import com.xbaimiao.easybot.sqlite.SQLer.isEnable
import io.izzel.taboolib.loader.Plugin
import io.izzel.taboolib.module.config.TConfig
import io.izzel.taboolib.module.inject.TInject
import me.albert.amazingbot.bot.Bot
import me.albert.amazingbot.database.MySQL
import me.albert.amazingbot.listeners.NewPlayer
import me.albert.amazingbot.listeners.OnBind
import me.albert.amazingbot.listeners.OnCommand
import me.albert.amazingbot.utils.CustomConfig
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

object EasyBot : Plugin() {

    init {
        MiraiLoader.start()
    }

    @TInject(value = ["config.yml"])
    lateinit var config: TConfig

    private var instance: JavaPlugin? = null
    private var data: CustomConfig? = null

    fun getData(): CustomConfig? {
        return data
    }


    override fun onEnable() {
        instance = plugin
        plugin.saveDefaultConfig()
        Bukkit.getScheduler().runTaskLater(plugin, Runnable { Bot.start() }, 30L)
        registerEvent(OnCommand())
        registerEvent(NewPlayer())
        registerEvent(OnBind())
        data = CustomConfig("data.yml", instance)
        if (MySQL.cfg.getBoolean("enable")) {
            MySQL.setUP()
        }
        if (isEnable) {
            plugin.logger.info("已启用sqlite储存数据")
        }
    }

    private fun registerEvent(listener: Listener) {
        Bukkit.getServer().pluginManager.registerEvents(listener, instance!!)
    }

    override fun onDisable() {
        if (MySQL.ENABLED) {
            MySQL.close()
            return
        }
        data!!.save()
    }

}