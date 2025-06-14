package at.sebdev.homeTeleporter.command.TabCompleter;

import at.sebdev.homeTeleporter.playerManager.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class HomeTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("home")) {
            //tabcompleter for home and homes already exists
            try {
                if (args.length == 1) {
                    List<String> homes = PlayerManager.getInstance().getAllHomeNames(player.getUniqueId());
                    String currentInput = args[0].toLowerCase();
                    return homes.stream()
                            .filter(h -> h.toLowerCase().startsWith(currentInput))
                            .toList();
                }
            } catch (Exception e) {
                return new ArrayList<>();
            }

        } else if (command.getName().equalsIgnoreCase("renameHome")) {
            //tabcompleter for old_name homes
            try {
                if (args.length == 1) {
                    List<String> homes = PlayerManager.getInstance().getAllHomeNames(player.getUniqueId());
                    String currentInput = args[0].toLowerCase();
                    return homes.stream()
                            .filter(h -> h.toLowerCase().startsWith(currentInput))
                            .toList();
                }
            } catch (Exception e) {
                return new ArrayList<>();
            }
        } else if (command.getName().equalsIgnoreCase("deleteHome")) {
            //tabcompleter for homes to delete
            try {
                if (args.length == 1) {
                    List<String> homes = PlayerManager.getInstance().getAllHomeNames(player.getUniqueId());
                    String currentInput = args[0].toLowerCase();
                    return homes.stream()
                            .filter(h -> h.toLowerCase().startsWith(currentInput))
                            .toList();
                }
            } catch (Exception e) {
                return new ArrayList<>();
            }
        }
        return new ArrayList<>();
    }
}
