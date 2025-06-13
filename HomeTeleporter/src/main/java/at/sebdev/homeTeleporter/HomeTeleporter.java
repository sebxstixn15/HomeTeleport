package at.sebdev.homeTeleporter;

import at.sebdev.homeTeleporter.command.*;
import at.sebdev.homeTeleporter.command.TabCompleter.AdminTabCompleter;
import at.sebdev.homeTeleporter.command.TabCompleter.HomeTabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

public final class HomeTeleporter extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("Started HomeTeleporter");

        getCommand("setHome").setExecutor(new SetHomeCommand());
        getCommand("home").setExecutor(new HomeCommand());
        getCommand("renameHome").setExecutor(new RenameHomeCommand());
        getCommand("DeleteHome").setExecutor(new DeleteHomeCommand());
        getCommand("admin").setExecutor(new AdminCommand());

        getCommand("home").setTabCompleter(new HomeTabCompleter());
        getCommand("renameHome").setTabCompleter(new HomeTabCompleter());
        getCommand("DeleteHome").setTabCompleter(new HomeTabCompleter());
        getCommand("admin").setTabCompleter(new AdminTabCompleter());

    }

    @Override
    public void onDisable() {
        System.out.println("Stopped HomeTeleporter");
    }
}
