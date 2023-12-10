package io.github.yuazer.zconfigreplacer.commands;

import io.github.yuazer.zconfigreplacer.Main;
import io.github.yuazer.zconfigreplacer.utils.ConfigUtils;
import io.github.yuazer.zconfigreplacer.utils.YamlUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (command.getName().equalsIgnoreCase("zconfigreplacer")){
            if (args.length==0){
                sender.sendMessage("§azconfigreplacer help §7- §b查看插件指令");
                return true;
            }
            if (args[0].equalsIgnoreCase("help")&&sender.isOp()){
                sender.sendMessage("§azconfigreplacer reload §7- §b重载插件");
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")&&sender.isOp()){
                Main.getInstance().reloadConfig();
                ConfigUtils.loadConfigAsync();
                sender.sendMessage(YamlUtils.getConfigMessage("Message.reload"));
                return true;
            }
        }
        return false;
    }
}
