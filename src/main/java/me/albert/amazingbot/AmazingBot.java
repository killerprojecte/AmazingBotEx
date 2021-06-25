package me.albert.amazingbot;

import com.xbaimiao.easybot.EasyBot;
import com.xbaimiao.easybot.utils.Setting;
import me.albert.amazingbot.utils.CustomConfig;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * 保留原有api，主类已移动至 EasyBot
 *
 * @see EasyBot
 */
public class AmazingBot {

    public static JavaPlugin getInstance() {
        return EasyBot.INSTANCE;
    }

    public static Setting getData() {
        return EasyBot.INSTANCE.getData();
    }

    public static void start() {
        Bukkit.getPluginCommand("amazingbot").setExecutor(new Commands());
    }

}
