package io.github.yuazer.zconfigreplacer;

import io.github.yuazer.zconfigreplacer.commands.MainCommand;
import io.github.yuazer.zconfigreplacer.manager.BukkitRunnableManager;
import io.github.yuazer.zconfigreplacer.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

public final class Main extends JavaPlugin {
    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    private static BukkitRunnableManager runnableManager;

    public static BukkitRunnableManager getRunnableManager() {
        return runnableManager;
    }

    private static ConcurrentHashMap<File, YamlConfiguration> confMap;

    public static ConcurrentHashMap<File, YamlConfiguration> getConfMap() {
        return confMap;
    }

    @Override
    public void onEnable() {
        instance = this;
        runnableManager = new BukkitRunnableManager(this);
        confMap = new ConcurrentHashMap<>();
        saveDefaultConfig();
        Bukkit.getPluginCommand("zconfigreplacer").setExecutor(new MainCommand());
        ConfigUtils.loadConfigAsync();
        logLoaded(this);
    }

    @Override
    public void onDisable() {
        logDisable(this);
    }

    public static void logLoaded(JavaPlugin plugin) {
        Bukkit.getLogger().info(String.format("§e[§b%s§e] §f已加载", plugin.getName()));
        Bukkit.getLogger().info("§b作者:§eZ菌[QQ:1109132]");
        Bukkit.getLogger().info("§b版本:§e" + plugin.getDescription().getVersion());
    }

    public static void logDisable(JavaPlugin plugin) {
        Bukkit.getLogger().info(String.format("§e[§b%s§e] §c已卸载", plugin.getName()));
    }
}
