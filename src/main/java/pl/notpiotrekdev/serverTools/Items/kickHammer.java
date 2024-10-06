package pl.notpiotrekdev.serverTools.Items;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import pl.notpiotrekdev.serverTools.ServerTools;

import java.util.Arrays;
import java.util.List;

public class kickHammer implements Listener {
    private final ServerTools plugin;
    public kickHammer(ServerTools plugin) {
        this.plugin = plugin;
    }

    public static ItemStack getItem() {
        ItemStack item = new ItemStack(Material.STICK, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + ">>" + ChatColor.RED + " Kick Hammer " + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "<<");
        List<String> lore = Arrays.asList(ChatColor.LIGHT_PURPLE + "<3");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.INFINITY, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS);
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player attacker = (Player) event.getDamager();
            Player victim = (Player) event.getEntity();
            ItemStack item = attacker.getInventory().getItemInMainHand();
            ItemStack item1 = getItem();
            if (compareItemsIgnoringFlags(item, item1)) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        victim.kick(Component.text("Timed out"));
                    }
                }.runTaskLater(plugin, 1L);
            }
        }
    }

    private boolean compareItemsIgnoringFlags(ItemStack item1, ItemStack item2) {
        if (item1.getType() != item2.getType()) {
            return false;
        }

        ItemMeta meta1 = item1.getItemMeta();
        ItemMeta meta2 = item2.getItemMeta();

        if (meta1 == null || meta2 == null) {
            return false;
        }

        if (!meta1.getDisplayName().equals(meta2.getDisplayName())) {
            return false;
        }

        if (!meta1.getLore().equals(meta2.getLore())) {
            return false;
        }

        if (!meta1.getEnchants().equals(meta2.getEnchants())) {
            return false;
        }

        return true;
    }
}
