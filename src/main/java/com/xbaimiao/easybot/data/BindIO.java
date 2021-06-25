package com.xbaimiao.easybot.data;

import java.util.UUID;

public interface BindIO {

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
    default void save() {

    }

}
