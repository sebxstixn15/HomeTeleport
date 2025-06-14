package at.sebdev.homeTeleporter.command;

import at.sebdev.homeTeleporter.playerManager.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HomeCommand implements CommandExecutor {

    private final Map<UUID, Long> cooldowns = new HashMap<>();

    private int cooldownSeconds = 0;

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        File file = new File("plugins/HomeTeleporter/config.yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

        try {
            if (file.createNewFile()) {
                //default timer when no one is set
                yamlConfiguration.set("timer", 5);
                yamlConfiguration.save(file);
            } else {
                //get timer from config
                cooldownSeconds = yamlConfiguration.getInt("timer");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "This Command can only be used by Players.");
            return true;
        }

        UUID uuid = player.getUniqueId();
        long now = System.currentTimeMillis();

        //check if timer is active

        if (cooldowns.containsKey(uuid)) {
            long lastUsed = cooldowns.get(uuid);
            long timePassed = (now - lastUsed) / 1000;

            if (timePassed < cooldownSeconds) {
                long timeLeft = cooldownSeconds - timePassed;
                player.sendMessage(ChatColor.YELLOW + "Please wait " + timeLeft + " more Seconds, before using this command again.");
                return true;
            }
        }


        if (args.length == 0) {
            //if no arguments are given with use standard home
            try {
                boolean teleported = PlayerManager.getInstance().teleport(player, "home");
                if (teleported) {
                    player.sendMessage(ChatColor.GREEN + "Successfully teleported to Home");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            cooldowns.put(uuid, now);
            return true;
        }

        if (args.length == 1) {
            //teleport to home with name
            try {
                boolean teleported = PlayerManager.getInstance().teleport(player, args[0]);
                if (teleported) {
                    player.sendMessage(ChatColor.GREEN + "Successfully teleported to " + args[0]);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            cooldowns.put(uuid, now);
            return true;
        }

        player.sendMessage("§cUnknown Command. Use /home or /home [name].");
        cooldowns.put(uuid, now);
        return true;
    }
}
