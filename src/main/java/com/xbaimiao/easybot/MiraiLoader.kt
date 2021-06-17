package com.xbaimiao.easybot

/* Download the mirai library during the first loading
 * This can highly reduce the size of jar file
 * Currently this system work but still not the best solution
 *
 * This system is completely depent on Taboolib library system
 */

import io.izzel.taboolib.loader.util.ILoader
import io.izzel.taboolib.loader.util.IO
import me.albert.amazingbot.AmazingBot
import java.io.File

object MiraiLoader {

    private val folder = "plugins${File.separator}TabooLib${File.separator}libs"

    private val libs = ArrayList<Lib>().also {
        val miraiVersion = "2.6.6"
        it.add(Lib("https://maven.aliyun.com/repository/public/org/bouncycastle/bcprov-jdk15on/1.64/bcprov-jdk15on-1.64.jar"))
        it.add(Lib("https://maven.aliyun.com/repository/public/net/mamoe/mirai-core-all/$miraiVersion/mirai-core-all-$miraiVersion-all.jar"))
    }

    @JvmStatic
    fun start() {
        for (lib in libs) {
            if (!lib.file.exists()) {
                AmazingBot.getInstance().logger.info("download ${lib.file.name} ...")
                IO.downloadFile(lib.url, lib.file)
                AmazingBot.getInstance().logger.info("download ${lib.file.name} success!")
            }
            ILoader.addPath(lib.file)
        }
        AmazingBot.getInstance().logger.info("EasyBot 所需依赖加载完成")
    }

    class Lib(val url: String) {

        private val args = url.split("/")

        val file = File(folder, args[args.size - 1])

    }

}