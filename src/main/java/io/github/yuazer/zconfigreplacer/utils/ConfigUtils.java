package io.github.yuazer.zconfigreplacer.utils;

import io.github.yuazer.zconfigreplacer.Main;
import io.github.yuazer.zconfigreplacer.runnable.ConfigRunnable;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigUtils {
    public static void loadConfig() {
        File file = new File("plugins/ZConfigReplacer/plans");
        if (!file.exists()) {
            file.mkdirs();
        }
        if (file.listFiles() == null) {
            return;
        }
        for (File confFile : file.listFiles()) {
            String fileName = confFile.getName().replace(".yml", "");
            YamlConfiguration conf = YamlConfiguration.loadConfiguration(confFile);
            Main.getConfMap().put(confFile, conf);
            Main.getRunnableManager().stopRunnable(fileName);
            Main.getRunnableManager().put(fileName, new ConfigRunnable(fileName, conf));
            Main.getRunnableManager().startRunnable(fileName, 0L, YamlUtils.getConfigInt("checkTime")*20L);
        }
    }

    public static void loadConfigAsync() {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), ConfigUtils::loadConfig);
    }
}
