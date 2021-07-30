package me.albert.amazingbot;

import com.xbaimiao.easybot.EasyBot;
import com.xbaimiao.easybot.data.UserData;
import com.xbaimiao.easybot.utils.Setting;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

/**
 * 保留原有api，主类已移动至 EasyBot
 *
 * @see EasyBot
 */
public class AmazingBot {

    public static EasyBot getInstance() {
        return EasyBot.INSTANCE;
    }

    @NotNull
    public static Setting getData() {
        return EasyBot.INSTANCE.getData();
    }

    @NotNull
    public static UserData getBindIO() {
        return EasyBot.INSTANCE.getUserData();
    }

    public static void start() {
        Bukkit.getPluginCommand("amazingbot").setExecutor(new Commands());
    }

}
