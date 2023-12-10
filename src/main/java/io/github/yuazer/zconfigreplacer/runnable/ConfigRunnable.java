package io.github.yuazer.zconfigreplacer.runnable;

import io.github.yuazer.zconfigreplacer.Main;
import io.github.yuazer.zconfigreplacer.utils.FileUtils;
import io.github.yuazer.zconfigreplacer.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConfigRunnable extends BukkitRunnable {
    private String week;
    private String hours;
    private List<String> urlList;
    private List<String> replaceList;
    private YamlConfiguration conf;
    private String name;

    public ConfigRunnable(String name, YamlConfiguration conf) {
        this.conf = conf;
        this.name = name;
        this.hours = conf.getString("time.hours");
        this.week = conf.getString("time.week");
        this.urlList = conf.getStringList("UrlList");
        this.replaceList = conf.getStringList("ReplaceList");
    }

    @Override
    public void run() {
        if (!TimeUtils.getTodayWeekday().equalsIgnoreCase(week)||!TimeUtils.getCurrentTimeFormatted().equalsIgnoreCase(hours)) {
            return;
        }
        String status = conf.getString("status");
        if (status.equalsIgnoreCase("false")) {
            Main.getConfMap().forEach((s, c) -> {
                String fileName = s.getName().replace(".yml", "");
                if (!fileName.equalsIgnoreCase(name)
                        && !c.getString("time").equalsIgnoreCase(TimeUtils.getTodayWeekday())) {
                    try {
                        c.set("status", "false");
                        c.save(s);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            //TODO 配置文件替换
            try {
                FileUtils.updateLocalYmlFiles(urlList, replaceList);
                conf.set("status", "true");
                conf.save(new File("plugins/ZConfigReplacer/plans/" + name + ".yml"));
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                    conf.getStringList("commands").forEach(c -> {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c);
                    });
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
