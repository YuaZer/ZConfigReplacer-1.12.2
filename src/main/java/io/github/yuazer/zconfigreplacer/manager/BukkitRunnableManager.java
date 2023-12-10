package io.github.yuazer.zconfigreplacer.manager;


import io.github.yuazer.zconfigreplacer.runnable.ConfigRunnable;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BukkitRunnableManager {

    //创建一个HashMap对象，用来存储String和BukkitRunnable的对应关系
    private ConcurrentHashMap<String, ConfigRunnable> runnables;

    //创建一个JavaPlugin对象，用来获取插件实例
    private JavaPlugin plugin;

    //创建一个构造方法，传入插件实例
    public BukkitRunnableManager(JavaPlugin plugin) {
        //初始化HashMap对象
        runnables = new ConcurrentHashMap<>();
        //赋值插件实例
        this.plugin = plugin;
    }

    //创建一个方法，用来添加一个String和BukkitRunnable的对应关系
    public void put(String key1, ConfigRunnable runnable) {
        runnables.put(key1, runnable);
    }

    public ConcurrentHashMap<String, ConfigRunnable> getRunnables() {
        return runnables;
    }

    //创建一个方法，用来移除一个String和BukkitRunnable的对应关系
    public void remove(String key1) {
        //从HashMap中移除name对应的runnable
        runnables.remove(key1);
    }

    public void removePlayerAll() {
        getPlayerAllRunnableID().forEach(n -> {
            runnables.remove(n);
        });
    }

    //创建一个方法，用来开启指定的BukkitRunnable
    public void startRunnable(String key1, long delay, long period) {
        //从HashMap中获取name对应的runnable
        ConfigRunnable runnable = runnables.get(key1);
        //判断runnable是否存在
        if (runnable != null) {
            //如果存在，调用runTaskTimer方法，传入插件实例，延迟时间和周期时间
            runnable.runTaskTimerAsynchronously(plugin, delay, period);
        }
    }

    public ConfigRunnable getRunnable(String key1) {
        return runnables.get(key1);
    }

    //创建一个方法，用来关闭指定的BukkitRunnable
    public void stopRunnable(String key1) {
        //从HashMap中获取name对应的runnable
        ConfigRunnable runnable = runnables.get(key1);
        //判断runnable是否存在
        if (runnable != null) {
            //如果存在，调用cancel方法，取消任务
            runnable.cancel();
        }
    }

    public Set<String> getPlayerAllRunnableID() {
        return runnables.keySet();
    }

}
