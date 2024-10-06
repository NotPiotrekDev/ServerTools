package pl.notpiotrekdev.serverTools.Items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
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

public class opVoucher implements Listener {
    private final ServerTools plugin;

    public opVoucher(ServerTools plugin) {
        this.plugin = plugin;
    }

    public static ItemStack getItem() {
        ItemStack item = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + ">>" + ChatColor.RED + " Voucher " + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "<<");
        List<String> lore = Arrays.asList(ChatColor.LIGHT_PURPLE + "Milego opa <3");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.INFINITY, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS);
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void onUse(PlayerInteractEvent event) {
        Action action = event.getAction();

        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            ItemStack itemInHand = player.getInventory().getItemInMainHand();
            ItemStack opVoucher = this.getItem();

            if (itemInHand != null && compareItemsIgnoringFlags(itemInHand, opVoucher)) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.setOp(true);
                        player.sendMessage(ChatColor.LIGHT_PURPLE + "<3");
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
