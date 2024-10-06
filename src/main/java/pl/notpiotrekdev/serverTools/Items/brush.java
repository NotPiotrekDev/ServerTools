package pl.notpiotrekdev.serverTools.Items;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import pl.notpiotrekdev.serverTools.ServerTools;

import java.util.Arrays;
import java.util.List;

public class brush implements Listener {
    private final ServerTools plugin;
    public brush(ServerTools plugin) {
        this.plugin = plugin;
    }

    public static ItemStack getItem() {
        ItemStack item = new ItemStack(Material.DIAMOND_SHOVEL, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + ">>" + ChatColor.RED + " Brush " + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "<<");
        List<String> lore = Arrays.asList(ChatColor.LIGHT_PURPLE + "<3");
        meta.setLore(lore);
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.INFINITY, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS);
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void onUse(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            ItemStack item = player.getInventory().getItemInMainHand();
            ItemStack item1 = this.getItem();
            if (compareItemsIgnoringFlags(item, item1)) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Block block = player.getTargetBlockExact(100);
                        if (block != null) {
                            Location loc = block.getLocation();
                            createAirSphere(loc, 8);
                        }
                    }
                }.runTaskLater(plugin, 1L);
            }
        }
    }

    private void createAirSphere(Location center, int radius) {
        World world = center.getWorld();
        if (world == null) return;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    double distance = Math.sqrt(x * x + y * y + z * z);
                    if (distance <= radius) {
                        Location blockLocation = center.clone().add(x, y, z);
                        world.getBlockAt(blockLocation).setType(Material.AIR);
                    }
                }
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
