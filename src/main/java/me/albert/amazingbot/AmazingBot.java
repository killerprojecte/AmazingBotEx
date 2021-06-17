package me.albert.amazingbot;

import com.xbaimiao.easybot.EasyBot;
import me.albert.amazingbot.utils.CustomConfig;
import org.bukkit.plugin.java.JavaPlugin;

public class AmazingBot {

    public static JavaPlugin getInstance() {
        return EasyBot.INSTANCE.getPlugin();
    }

    public static CustomConfig getData() {
        return EasyBot.INSTANCE.getData();
    }

}
