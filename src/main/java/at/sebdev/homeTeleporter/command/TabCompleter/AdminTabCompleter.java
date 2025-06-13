package at.sebdev.homeTeleporter.command.TabCompleter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AdminTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("admin")) {
            try {
                if (args.length == 1) {
                    List<String> complete = new ArrayList<>();
                    complete.add("setTimer");
                    String currentInput = args[0].toLowerCase();
                    return complete.stream()
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
