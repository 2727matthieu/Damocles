package xyz.almia.commandsystem;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import net.md_5.bungee.api.ChatColor;
import xyz.almia.arrowsystem.CustomArrow;
import xyz.almia.itemblueprints.Scroll;
import xyz.almia.itemsystem.ItemType.ScrollType;
import xyz.almia.lootsystem.CardinalDrops;
import xyz.almia.lootsystem.Items;
import xyz.almia.menu.MenuItem;
import xyz.almia.potionsystem.Effect;
import xyz.almia.potionsystem.Potion;
import xyz.almia.potionsystem.PotionColors;
import xyz.almia.potionsystem.PotionType;
import xyz.almia.utils.Color;

public class Cardinal implements CommandExecutor{
	Plugin plugin;
	
	public Cardinal(Plugin plugin){
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String labe, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("You must be a player to use commands!");
		}
		Player player = (Player)sender;
		if(cmd.getName().equalsIgnoreCase("cardinal")){
			
			Inventory inv = Bukkit.createInventory(null, 36, "items");
			CardinalDrops drop = new CardinalDrops();
			
			for(Items item : Items.values()){
				inv.addItem(drop.getItem(item));
			}
			
			for(ScrollType type : ScrollType.values()){
				inv.addItem(new Scroll(type, 1, 100, 0).getItemStack());
			}
			inv.addItem(new ItemStack(Material.BOW));
			inv.addItem(new CustomArrow(new Effect(PotionType.EXPLOSION, 2, 20), 1, new Color(PotionColors.BLACK).getInt()).getItemStack());
			inv.addItem(new CustomArrow(new Effect(PotionType.LIGHTNING, 1, 20), 1, new Color(PotionColors.YELLOW).getInt()).getItemStack());
			inv.addItem(new CustomArrow(new Effect(PotionType.SPEED, 1, 300), 1, new Color(PotionColors.CYAN).getInt()).getItemStack());
			inv.addItem(new Potion(new Effect(PotionType.HEAL, 1, 20), new Color(PotionColors.GREEN).getInt(), false).buildItemStack());
			inv.addItem(new Potion(new Effect(PotionType.HEAL, 1, 20), new Color(PotionColors.GREEN).getInt(), true).buildItemStack());
			inv.addItem(new Potion(new Effect(PotionType.SPEED, 1, 300), new Color(PotionColors.CYAN).getInt(), false).buildItemStack());
			inv.addItem(new Potion(new Effect(PotionType.EXPLOSION, 1, 20), new Color(PotionColors.BLACK).getInt(), true).buildItemStack());
			inv.addItem(MenuItem.createItem(ChatColor.GOLD+"Basic Bank", Material.YELLOW_SHULKER_BOX));
			inv.addItem(new Scroll(ScrollType.DAMAGE, 1, 0, 100).getItemStack());
			player.openInventory(inv);
			
			return true;
		}
		return true;
	}
}
