package at.sebdev.homeTeleporter.command;

import at.sebdev.homeTeleporter.playerManager.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class DeleteHomeCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This Command can only be used by Players.");
            return true;
        }

        //if no args are here we just remove home
        if (args.length == 0) {
            try {
                PlayerManager.getInstance().remove(player, "home");
                player.sendMessage(ChatColor.GREEN + "Successfully removed: " + "home");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }

        //remove Home with name
        if (args.length == 1) {
            try {
                boolean removed = PlayerManager.getInstance().remove(player, args[0]);
                if (removed) {
                    player.sendMessage(ChatColor.GREEN + "Successfully removed: " + args[0]);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }

        player.sendMessage("§cUnknown Command. Use /removeHome or /removeHome [name].");
        return true;
    }
}
