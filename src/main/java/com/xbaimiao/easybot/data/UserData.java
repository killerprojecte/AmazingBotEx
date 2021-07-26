package com.xbaimiao.easybot.data;

import java.util.UUID;

public interface UserData {

    /**
     * set bind data for qq,uuid
     */
    void setBind(Long qq, String uuid);

    /**
     * get uuid for qq
     */
    UUID getPlayer(Long qq);

    /**
     * get qq for uuid
     */
    Long getUser(String uuid);

    /**
     * save data
     */
    void save();

    /**
     * remove user
     */
    void remove(Long qq);

    /**
     * remove user
     */
    void remove(String uuid);

}
