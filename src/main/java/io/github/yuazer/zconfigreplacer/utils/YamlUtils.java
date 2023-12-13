package io.github.yuazer.zconfigreplacer.utils;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import io.github.yuazer.zconfigreplacer.Main;

import java.util.List;

public class YamlUtils {
    public static String getConfigMessage(String path) {
        if (Main.getInstance().getConfig().getString(path) != null && !Main.getInstance().getConfig().getString(path).isEmpty()) {
            return Main.getInstance().getConfig().getString(path).replace("&", "§");
        }
        return "";
    }

    public static List<String> getConfigStringList(String path) {
        return Main.getInstance().getConfig().getStringList(path);
    }

    public static boolean getConfigBoolean(String path) {
        return Main.getInstance().getConfig().getBoolean(path);
    }

    public static int getConfigInt(String path) {
        return Main.getInstance().getConfig().getInt(path);
    }

    public static double getConfigDouble(String path) {
        return Main.getInstance().getConfig().getDouble(path);
    }

    /**
     * 保存 YamlConfiguration 到文件，尽量保留原始格式和注释。
     *
     * @param config YamlConfiguration 对象
     * @param file   要保存到的文件
     * @throws IOException 如果发生 IO 错误
     */
    public static void saveWithComments(YamlConfiguration config, File file) throws IOException {
        // 存储原始文件的行
        Map<String, String> originalLines = new LinkedHashMap<>();
        String currentPath = "";
        // 正则表达式，用于匹配 YAML 键
        Pattern pattern = Pattern.compile("^\\s*([^#][^:]*):");
        // 读取原始文件，保留每行内容
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    // 配置项的行
                    currentPath = matcher.group(1).trim();
                    originalLines.put(currentPath, line + "\n");
                } else {
                    // 注释或空行
                    originalLines.put(currentPath, originalLines.getOrDefault(currentPath, "") + line + "\n");
                }
            }
        }
        // 更新配置项的值
        for (String key : config.getKeys(true)) {
            if (!config.isConfigurationSection(key)) {
                String valueLine = key + ": " + config.get(key).toString();
                originalLines.put(key, valueLine + "\n");
            }
        }
        // 将更新后的内容写回文件
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String line : originalLines.values()) {
                writer.write(line);
            }
        }
    }
}
