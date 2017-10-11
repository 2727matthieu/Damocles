package ca.damocles.menusystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import ca.damocles.clansystem.Clan;
import ca.damocles.clansystem.Clans;

public class ClanMenu implements Listener{
	
	public static Inventory generateClanMenu(Clans clan){
		Inventory inv = Bukkit.createInventory(null, 27, clan.toString().toLowerCase().substring(0, 1).toUpperCase() + clan.toString().toLowerCase().substring(1) + " Clan Info");
		
		ItemStack emp = MenuItem.createItem("Empty", "", Material.STAINED_GLASS_PANE);
		ItemStack clansmen = MenuItem.createBetterItem(ChatColor.DARK_GRAY+"Clansmen", Arrays.asList(new String[] {ChatColor.GRAY+"Click to see all this clans Clansmen."}), Material.NETHER_STAR);
		ItemStack clanInfo = MenuItem.createClanPane(clan);
		
		inv.setItem(0, emp);
		inv.setItem(1, emp);
		inv.setItem(2, emp);
		inv.setItem(3, emp);
		inv.setItem(4, clanInfo);
		inv.setItem(5, emp);
		inv.setItem(6, emp);
		inv.setItem(7, emp);
		inv.setItem(8, emp);
		inv.setItem(9, emp);
		inv.setItem(10, clansmen);
		inv.setItem(11, emp);
		inv.setItem(12, emp);
		inv.setItem(13, emp);
		inv.setItem(14, emp);
		inv.setItem(15, emp);
		inv.setItem(16, emp);
		inv.setItem(17, emp);
		inv.setItem(18, emp);
		inv.setItem(19, emp);
		inv.setItem(20, emp);
		inv.setItem(21, emp);
		inv.setItem(22, emp);
		inv.setItem(23, emp);
		inv.setItem(24, emp);
		inv.setItem(25, emp);
		inv.setItem(26, emp);
		
		return inv;
	}
	
	public static List<Inventory> createMemberMenu(Clans clan){
		Clan clanProfile = new Clan(clan);
		List<Inventory> pages = new ArrayList<Inventory>();
		int page = 1;
		pages.add(Bukkit.createInventory(null, 54, "Page "+page));
		List<ca.damocles.accountsystem.Character> clansmen = clanProfile.getClansmen();
		for(ca.damocles.accountsystem.Character members : clansmen){
		       ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
		       SkullMeta meta = (SkullMeta) skull.getItemMeta();
		       meta.setOwningPlayer(Bukkit.getOfflinePlayer(members.uuid));
		         skull.setItemMeta(meta);
			if(pages.get(pages.size()-1).firstEmpty() == -1){
				page = page+1;
				pages.add(Bukkit.createInventory(null, 54, "Page "+page));
				pages.get(pages.size()-1).addItem(skull);
			}else{
				pages.get(pages.size()-1).addItem(skull);
			}
			members.getPlayer().updateInventory();
		}
		return pages;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event){
		if(event.getInventory().getName().contains(" Clan Info")){
			if(event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().getDisplayName().contains(ChatColor.DARK_GRAY+"Clansmen")){
				String[] s = event.getInventory().getName().split(" ");
				event.getWhoClicked().openInventory(createMemberMenu(Clans.valueOf(s[0].toUpperCase())).get(0));
			}
			event.setCancelled(true);
		}
	}
	
}
