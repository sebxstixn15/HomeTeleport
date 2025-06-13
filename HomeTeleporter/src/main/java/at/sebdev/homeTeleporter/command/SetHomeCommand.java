package at.sebdev.homeTeleporter.command;

import at.sebdev.homeTeleporter.playerManager.PlayerManager;
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
            try {
                PlayerManager.getInstance().save(player, player.getLocation(), "home");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }

        if (args.length == 1) {
            try {
                PlayerManager.getInstance().save(player, player.getLocation(), args[0]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }

        player.sendMessage("§cUnknown Command. Use /sethome or /sethome [name].");
        return true;
    }
}
