package xyz.almia.menu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import mkremins.fanciful.FancyMessage;
import xyz.almia.cardinalsystem.Cardinal;
import xyz.almia.clansystem.Clans;
import xyz.almia.utils.Message;

public class SelectionMenu implements Listener{
	
	/*
	 *  0(none),  1(red),  2(blue),  3(white),  4(black),  5(green),  6(gold),  7(colorless),  8(none),
	 */
	
    private static SelectionMenu instance;
    
	Plugin plugin = Cardinal.getPlugin();
    
    public SelectionMenu() {}
    
    
    public static SelectionMenu getInstance() {
        if (instance == null)
            instance = new SelectionMenu();
        return instance;
    }
	
	private ItemStack emp = MenuItem.createItem("Empty", "", Material.BARRIER);
	private ItemStack red = MenuItem.createClanPane(Clans.RED);
	private ItemStack blue = MenuItem.createClanPane(Clans.BLUE);
	private ItemStack white = MenuItem.createClanPane(Clans.WHITE);
	private ItemStack black = MenuItem.createClanPane(Clans.BLACK);
	private ItemStack green = MenuItem.createClanPane(Clans.GREEN);
	private ItemStack gold = MenuItem.createClanPane(Clans.GOLD);
	private ItemStack colorless = MenuItem.createClanPane(Clans.COLORLESS);
	
	
	public Inventory generateSelectionInventory(){
		Inventory inv = Bukkit.createInventory(null, 9, "Clan Selection");
		inv.setItem(0, emp);
		inv.setItem(1, colorless);
		inv.setItem(2, white);
		inv.setItem(3, black);
		inv.setItem(4, gold);
		inv.setItem(5, green);
		inv.setItem(6, red);
		inv.setItem(7, blue);
		inv.setItem(8, emp);
		return inv;
	}
	
	@EventHandler
	public void onSelectionClick(InventoryClickEvent event){
		Player player = (Player) event.getWhoClicked();
		if(event.getInventory().getName().equalsIgnoreCase("clan selection")){
			event.setCancelled(true);
			ItemStack item = event.getCurrentItem();
			if(item != null){
				if(item.equals(emp)){
					return;
				}else if(item.equals(red)){
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Help");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+"Are you sure want to join the "+ChatColor.RED+"Red "+ChatColor.YELLOW+"clan?");
						new FancyMessage("                  Accept")
						.command("/clan join red")
						.color(ChatColor.GREEN)
						.then("     /     ")
						.color(ChatColor.GRAY)
						.then("Decline")
						.command("/clan choose")
						.color(ChatColor.RED)
						.send(player);
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				}else if(item.equals(blue)){
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Help");
					Message.sendCenteredMessage(player, ChatColor.YELLOW+"Are you sure want to join the "+ChatColor.BLUE+"Blue "+ChatColor.YELLOW+"clan?");
					new FancyMessage("                  Accept")
					.command("/clan join blue")
					.color(ChatColor.GREEN)
					.then("     /     ")
					.color(ChatColor.GRAY)
					.then("Decline")
					.command("/clan choose")
					.color(ChatColor.RED)
					.send(player);
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				}else if(item.equals(black)){
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Help");
					Message.sendCenteredMessage(player, ChatColor.YELLOW+"Are you sure want to join the "+ChatColor.BLACK+"Black "+ChatColor.YELLOW+"clan?");
					new FancyMessage("                  Accept")
					.command("/clan join black")
					.color(ChatColor.GREEN)
					.then("     /     ")
					.color(ChatColor.GRAY)
					.then("Decline")
					.command("/clan choose")
					.color(ChatColor.RED)
					.send(player);
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				}else if(item.equals(white)){
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Help");
					Message.sendCenteredMessage(player, ChatColor.YELLOW+"Are you sure want to join the "+ChatColor.WHITE+"White "+ChatColor.YELLOW+"clan?");
					new FancyMessage("                  Accept")
					.command("/clan join white")
					.color(ChatColor.GREEN)
					.then("     /     ")
					.color(ChatColor.GRAY)
					.then("Decline")
					.command("/clan choose")
					.color(ChatColor.RED)
					.send(player);
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				}else if(item.equals(gold)){
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Help");
					Message.sendCenteredMessage(player, ChatColor.YELLOW+"Are you sure want to join the "+ChatColor.GOLD+"Gold "+ChatColor.YELLOW+"clan?");
					new FancyMessage("                  Accept")
					.command("/clan join gold")
					.color(ChatColor.GREEN)
					.then("     /     ")
					.color(ChatColor.GRAY)
					.then("Decline")
					.command("/clan choose")
					.color(ChatColor.RED)
					.send(player);
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				}else if(item.equals(green)){
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Help");
					Message.sendCenteredMessage(player, ChatColor.YELLOW+"Are you sure want to join the "+ChatColor.GREEN+"Green "+ChatColor.YELLOW+"clan?");
					new FancyMessage("                  Accept")
					.command("/clan join green")
					.color(ChatColor.GREEN)
					.then("     /     ")
					.color(ChatColor.GRAY)
					.then("Decline")
					.command("/clan choose")
					.color(ChatColor.RED)
					.send(player);
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				}else if(item.equals(colorless)){
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Help");
					Message.sendCenteredMessage(player, ChatColor.YELLOW+"Are you sure want to join the "+ChatColor.DARK_GRAY+"Colorless "+ChatColor.YELLOW+"clan?");
					new FancyMessage("                  Accept")
					.command("/clan join colorless")
					.color(ChatColor.GREEN)
					.then("     /     ")
					.color(ChatColor.GRAY)
					.then("Decline")
					.command("/clan choose")
					.color(ChatColor.RED)
					.send(player);
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				}else{
					return;
				}
				player.closeInventory();
			}
			event.setCancelled(true);
			return;
		}
		
	}
	
}
