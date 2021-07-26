package me.albert.amazingbot.bot;

import com.xbaimiao.easybot.EasyBot;
import me.albert.amazingbot.AmazingBot;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import org.bukkit.Bukkit;

import java.util.UUID;

public class BotAPI {

    private final net.mamoe.mirai.Bot bot;

    public BotAPI(net.mamoe.mirai.Bot bot) {
        this.bot = bot;
    }

    public void sendGroupMsg(String groupID, String msg) {
        Bukkit.getScheduler().runTaskAsynchronously(AmazingBot.getInstance(), () -> {
            try {
                bot.getGroup(Long.parseLong(groupID)).sendMessage(msg);
            } catch (Exception e) {
                if (AmazingBot.getInstance().getConfig().getBoolean("debug")) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void sendPrivateMsg(String userID, String msg) {
        Bukkit.getScheduler().runTaskAsynchronously(AmazingBot.getInstance(), () -> {
            try {
                bot.getFriend(Long.parseLong(userID)).sendMessage(msg);
            } catch (Exception e) {
                if (AmazingBot.getInstance().getConfig().getBoolean("debug")) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void sendRawMsg(String msg) {
        Bukkit.getLogger().info("§e[AmazingBot] 有插件在使用过时方法发送Raw信息,此方法已被删除,可能会影响到插件功能...");
    }

    public void changeTitle(Long groupID, Long userID, String title) {
        Bukkit.getScheduler().runTaskAsynchronously(AmazingBot.getInstance(), () -> {
            try {
                bot.getGroup(groupID).get(userID).setNameCard(title);
            } catch (Exception e) {
                if (AmazingBot.getInstance().getConfig().getBoolean("debug")) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Group getGroup(Long groupID) {
        try {
            return bot.getGroup(groupID);
        } catch (Exception e) {
            if (AmazingBot.getInstance().getConfig().getBoolean("debug")) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Friend getFriend(Long userID) {
        try {
            return bot.getFriend(userID);
        } catch (Exception e) {
            if (AmazingBot.getInstance().getConfig().getBoolean("debug")) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public net.mamoe.mirai.Bot getBot() {
        return bot;
    }

    public void setBind(Long userID, UUID uuid) {
        EasyBot.INSTANCE.getUserData().setBind(userID, uuid.toString());
    }

    public UUID getPlayer(Long userID) {
        return EasyBot.INSTANCE.getUserData().getPlayer(userID);
    }

    public Long getUser(UUID playerID) {
        return EasyBot.INSTANCE.getUserData().getUser(playerID.toString());
    }


}
