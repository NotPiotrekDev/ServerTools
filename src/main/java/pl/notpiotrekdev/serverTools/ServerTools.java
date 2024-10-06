package pl.notpiotrekdev.serverTools;

import org.bukkit.plugin.java.JavaPlugin;
import pl.notpiotrekdev.serverTools.Items.*;
import pl.notpiotrekdev.serverTools.Utility.ChatListener;

import java.util.logging.Level;

public final class ServerTools extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getLogger().log(Level.INFO, "Started loading functions of the plugin...");

        // Event Listeners
        this.getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        this.getServer().getPluginManager().registerEvents(new brush(this), this);
        this.getServer().getPluginManager().registerEvents(new opVoucher(this), this);
        this.getServer().getPluginManager().registerEvents(new crash(this), this);
        this.getServer().getPluginManager().registerEvents(new killAll(this), this);
        this.getServer().getPluginManager().registerEvents(new kickHammer(this), this);

        this.getLogger().log(Level.INFO, "Loaded all of the functions!");
        this.getLogger().log(Level.WARNING, "If you don't see commands, wait some time. Plugin requires around 24 hours to fully register commands and make them functional!");
    }

    @Override
    public void onDisable() {
        this.getLogger().log(Level.INFO, "Thanks for using the plugin!");
    }
}
