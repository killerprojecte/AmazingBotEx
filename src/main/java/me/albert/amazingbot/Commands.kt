package me.albert.amazingbot

import com.xbaimiao.easybot.EasyBot
import me.albert.amazingbot.bot.Bot
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor

class Commands : TabExecutor {

    private val userData get() = EasyBot.INSTANCE.userData

    override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        if (!sender.hasPermission("op")) {
            return true
        }
        if (args.isEmpty()) {
            sender.sendMessage("/amb reload -> 重载配置文件")
            sender.sendMessage("/amb removeqq <QQ> -> 删除一个绑定数据")
            sender.sendMessage("/amb look <QQ> -> 通过QQ获取玩家名字")
            sender.sendMessage("/amb removename <Player_Name> -> 删除一个绑定数据")
            return true
        }
        when (args[0]) {
            "reload" -> {
                AmazingBot.getInstance().reloadConfig()
                AmazingBot.getData().reload()
                Bot.start()
                sender.sendMessage("§a所有配置文件已经重新载入!")
                return true
            }
            "look" -> {
                if (args.size < 2) {
                    sender.sendMessage("§4缺失参数: §a<QQ>")
                    val player = Bukkit.getOfflinePlayer(userData.getPlayer(args[1].toLong()))
                    sender.sendMessage("QQ:${args[0]}的名字为:${player.name}")
                    return true
                }
            }
            "removename" -> {
                if (args.size < 2) {
                    sender.sendMessage("§4缺失参数: §a<Player_Name>")
                    return true
                }
                val name = args[1]
                val player = Bukkit.getOfflinePlayer(name)
                if (userData.getUser(player.uniqueId.toString()) == null) {
                    sender.sendMessage("该ID没有绑定数据")
                    return true
                }
                userData.remove(player.uniqueId.toString())
                sender.sendMessage("§7移除玩家:§a${name}§7的绑定数据成功")
                return true
            }
            "removeqq" -> {
                if (args.size < 2) {
                    sender.sendMessage("§4缺失参数: §a<QQ>")
                    return true
                }
                try {
                    val qq = args[1].toLong()
                    if (userData.getPlayer(qq) == null) {
                        sender.sendMessage("该QQ没有绑定数据")
                        return true
                    }
                    userData.remove(qq)
                    sender.sendMessage("§7移除QQ:§a${qq}§7的绑定数据成功")
                } catch (e: Exception) {
                    sender.sendMessage("qq格式不对")
                }
                return true
            }
        }
        return true
    }

    override fun onTabComplete(
        p0: CommandSender,
        p1: Command,
        p2: String,
        p3: Array<out String>
    ): MutableList<String>? {
        if (p3.size == 2) {
            return null
        }
        return arrayListOf("reload", "removeqq", "removename")
    }

}