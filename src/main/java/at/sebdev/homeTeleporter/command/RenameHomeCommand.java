package at.sebdev.homeTeleporter.command;

import at.sebdev.homeTeleporter.playerManager.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class RenameHomeCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This Command can only be used by Players.");
            return true;
        }

        if (args.length == 2) {
            try {
                PlayerManager.getInstance().rename(player, args[0], args[1]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }

        player.sendMessage("§cUnknown Command. Use /renameHome [old_name] [new_name].");
        return true;
    }
}
