package at.sebdev.homeTeleporter.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class AdminCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This Command can only be used by Players.");
            return true;
        }
        //check if player is an admin or owner or just have operation rights

        if (player.isOp()) {
            if (args.length == 2 && args[0].equalsIgnoreCase("setTimer")) {
                File file = new File("plugins/HomeTeleporter/config.yml");

                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

                //edit timer from commands and parse it into config.yml

                try {
                    config.set("timer", parseInt(args[1]));
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Could not parse timer.");
                    return true;
                }
                player.sendMessage(ChatColor.GREEN + "Successfully changed timer countdown to: " + parseInt(args[1]));

                try {
                    config.save(file);
                    return true;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            player.sendMessage(ChatColor.RED + "You are not an operator, so you can't use that command!");
            return true;
        }
        player.sendMessage("§cUnknown Command. Use /admin setTimer [int].");
        return true;
    }
}
