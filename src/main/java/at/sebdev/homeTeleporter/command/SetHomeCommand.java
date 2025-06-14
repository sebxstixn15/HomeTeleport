package at.sebdev.homeTeleporter.command;

import at.sebdev.homeTeleporter.playerManager.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;

import java.io.IOException;

public class SetHomeCommand implements CommandExecutor  {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This Command can only be used by Players.");
            return true;
        }

        if (args.length == 0) {
            //if no arguments are given, just create home
            try {
                boolean saved = PlayerManager.getInstance().save(player, player.getLocation(), "home");
                if (saved) {
                    player.sendMessage(ChatColor.GREEN + "Successfully saved Home");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }

        if (args.length == 1) {
            //create home with name
            try {
                boolean saved = PlayerManager.getInstance().save(player, player.getLocation(), args[0]);
                if (saved) {
                    player.sendMessage(ChatColor.GREEN + "Successfully saved " + args[0]);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }

        player.sendMessage("§cUnknown Command. Use /sethome or /sethome [name].");
        return true;
    }
}
