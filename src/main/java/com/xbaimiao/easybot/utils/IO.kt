package com.xbaimiao.easybot.utils

import java.awt.image.BufferedImage
import java.io.*
import java.net.URL
import javax.imageio.ImageIO

object IO {
    /**
     * 下载网络图片
     */
    @Throws(IOException::class)
    fun downloadImage(link: String?): BufferedImage {
        val url = URL(link)
        val connection = url.openConnection()
        connection.useCaches = false
        connection.defaultUseCaches = false
        connection.addRequestProperty("User-Agent", "Mozilla/5.0")
        connection.addRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate")
        connection.addRequestProperty("Pragma", "no-cache")
        val `in` = connection.getInputStream()
        val image = ImageIO.read(`in`)
        `in`.close()
        return image
    }

    @JvmStatic
    fun downloadFile(`in`: String?, file: File): Boolean {
        try {
            URL(`in`).openStream().use { inputStream ->
                BufferedInputStream(inputStream).use { bufferedInputStream ->
                    toFile(bufferedInputStream, file)
                    return true
                }
            }
        } catch (t: Throwable) {
            t.printStackTrace()
            return false
        }
    }

    fun imageToBytes(bufferedImage: BufferedImage?): ByteArray {
        val out = ByteArrayOutputStream()
        try {
            ImageIO.write(bufferedImage, "png", out)
        } catch (ignored: IOException) {
        }
        return out.toByteArray()
    }

    @JvmStatic
    fun toFile(inputStream: InputStream, file: File): File {
        try {
            FileOutputStream(file).use { fos ->
                BufferedOutputStream(fos).use { bos ->
                    val buf = ByteArray(1024)
                    var len: Int
                    while (inputStream.read(buf).also { len = it } > 0) {
                        bos.write(buf, 0, len)
                    }
                    bos.flush()
                }
            }
        } catch (exception: Exception) {
        }
        return file
    }

}