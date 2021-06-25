package me.albert.amazingbot

import me.albert.amazingbot.bot.Bot
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class Commands : CommandExecutor {

    override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>): Boolean {
        if (!p0.hasPermission("op")){
            return true
        }
        AmazingBot.getInstance().reloadConfig()
        AmazingBot.getData().reload()
        Bot.start()
        p0.sendMessage("§a所有配置文件已经重新载入!")
        return true
    }

}