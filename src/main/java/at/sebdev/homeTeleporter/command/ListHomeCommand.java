package at.sebdev.homeTeleporter.command;

import at.sebdev.homeTeleporter.model.NameCoordinate;
import at.sebdev.homeTeleporter.playerManager.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.List;


public class ListHomeCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This Command can only be used by Players.");
            return true;
        }

        if (args.length == 0) {
            //get all Homes with Coordinate
            List<NameCoordinate> nameCoordinateList = PlayerManager.getInstance().loadHomes(player.getUniqueId());

            if (nameCoordinateList.isEmpty()) {
                player.sendMessage(ChatColor.RED + "There are no Homes");
            } else {
                for (NameCoordinate nameCoordinate : nameCoordinateList) {
                    player.sendMessage(ChatColor.GREEN + "Name: " + nameCoordinate.getName() + ", X: " + nameCoordinate.getX() + ", Y: " + nameCoordinate.getY() + ", Z: " + nameCoordinate.getZ());
                }
            }
            return true;
        }

        player.sendMessage("§cUnknown Command. Use /listhomes");
        return true;
    }
}
