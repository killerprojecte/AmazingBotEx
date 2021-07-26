package com.xbaimiao.easybot.data.yaml;

import com.xbaimiao.easybot.EasyBot;
import com.xbaimiao.easybot.data.UserData;
import com.xbaimiao.easybot.utils.Setting;

import java.util.UUID;

public class Yaml implements UserData {

    Setting setting = EasyBot.INSTANCE.getData();

    @Override
    public void setBind(Long qq, String uuid) {
        setting.set(String.valueOf(qq), uuid);
        setting.saveToFile();
    }

    @Override
    public UUID getPlayer(Long qq) {
        UUID uuid = null;
        String uid = setting.getString(String.valueOf(qq));
        if (uid != null) {
            uuid = UUID.fromString(uid);
        }
        return uuid;
    }

    @Override
    public Long getUser(String uuid) {
        Long userID = null;
        for (String key : setting.getKeys(false)) {
            String uid = setting.getString(key);
            if (uid != null && uid.equalsIgnoreCase(uuid)) {
                return Long.parseLong(key);
            }
        }
        return userID;
    }

    @Override
    public void save() {
        setting.saveToFile();
    }

    @Override
    public void remove(Long qq) {
        setting.set(String.valueOf(qq), null);
    }

    @Override
    public void remove(String uuid) {
        for (String key : setting.getKeys(false)) {
            String uid = setting.getString(key);
            if (uid != null && uid.equalsIgnoreCase(uuid)) {
                setting.set(key, null);
            }
        }
    }

}
