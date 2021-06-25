//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.xbaimiao.easybot;

import java.io.File;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import org.bukkit.Bukkit;
import sun.misc.Unsafe;

public class Loader extends URLClassLoader {

    static Lookup lookup;
    static Unsafe unsafe;
    static Method addUrlMethod;

    public Loader(URL[] urls) {
        super(urls);
    }
    static boolean isfo = false;

    public static boolean addPath(File file) {
        try {
            ClassLoader loader = Bukkit.class.getClassLoader();
            if (isfo) {
                addUrlMethod.invoke(loader, file.toURI().toURL());
            } else if (loader.getClass().getSimpleName().equals("LaunchClassLoader")) {
                MethodHandle methodHandle = lookup.findVirtual(loader.getClass(), "addURL", MethodType.methodType(Void.TYPE, URL.class));
                methodHandle.invoke(loader, file.toURI().toURL());
            } else {
                Field ucpField;
                try {
                    ucpField = loader.getClass().getDeclaredField("ucp");
                } catch (NoSuchFieldException | NoSuchFieldError var7) {
                    ucpField = loader.getClass().getSuperclass().getDeclaredField("ucp");
                }

                long ucpOffset = unsafe.objectFieldOffset(ucpField);
                Object ucp = unsafe.getObject(loader, ucpOffset);
                MethodHandle methodHandle = lookup.findVirtual(ucp.getClass(), "addURL", MethodType.methodType(Void.TYPE, URL.class));
                methodHandle.invoke(ucp, file.toURI().toURL());
            }

            return true;
        } catch (Throwable var8) {
            var8.printStackTrace();
            return false;
        }
    }

    static {
        if (isfo) {
            try {
                addUrlMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                addUrlMethod.setAccessible(true);
            } catch (Throwable var6) {
            }
        }

        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe)field.get((Object)null);
            Field lookupField = Lookup.class.getDeclaredField("IMPL_LOOKUP");
            Object lookupBase = unsafe.staticFieldBase(lookupField);
            long lookupOffset = unsafe.staticFieldOffset(lookupField);
            lookup = (Lookup)unsafe.getObject(lookupBase, lookupOffset);
        } catch (Throwable var5) {
        }

    }
}
