package io.github.yuazer.zconfigreplacer.utils;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;

public class FileUtils {
    public static void updateLocalYmlFiles(List<String> remoteUrls, List<String> localPaths) throws Exception {
        // 随机选择一个远程URL
        String selectedUrl = remoteUrls.get(new Random().nextInt(remoteUrls.size()));
        // 从选定的URL下载YML内容
        YamlConfiguration remoteConfig = downloadYmlContent(selectedUrl);
        // 将YML内容覆盖到所有本地文件
        for (String localPath : localPaths) {
            remoteConfig.save(new File(localPath));
        }
    }

    private static YamlConfiguration downloadYmlContent(String urlString) throws Exception {
        URL url = new URL(urlString);
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        // 使用YamlConfiguration加载下载的内容
        return YamlConfiguration.loadConfiguration(new StringReader(content.toString()));
    }
}
