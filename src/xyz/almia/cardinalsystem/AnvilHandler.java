package xyz.almia.cardinalsystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import xyz.almia.accountsystem.Account;
import xyz.almia.itemsystem.ItemHandler;
import xyz.almia.itemsystem.ItemTypes;
import xyz.almia.itemsystem.Weapon;
import xyz.almia.menu.MenuItem;
import xyz.almia.utils.Message;

public class AnvilHandler implements Listener{
	public List<Anvil> anvils = new ArrayList<Anvil>();
	ItemStack yes = MenuItem.createBetterItem(ChatColor.GREEN+ "" + ChatColor.BOLD + "AGREE", Arrays.asList(ChatColor.GRAY+"Click to accept."), Material.EMERALD);
	ItemStack no = MenuItem.createBetterItem(ChatColor.RED+ "" + ChatColor.BOLD + "CANCEL", Arrays.asList(ChatColor.GRAY+"Click to cancel."), Material.BARRIER);
	
	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent event){
		for(Anvil anvil : anvils){
			if(event.getItem().getItemStack().equals(anvil.getItemStack())){
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onAnvilRightClick(PlayerInteractEvent event){
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			if(event.getClickedBlock() != null){
				if(event.getClickedBlock().getType().equals(Material.ANVIL)){
					event.setCancelled(true);
					Player player = event.getPlayer();
					player.closeInventory();
					if(player.getInventory().getItemInMainHand() != null){
						if(ItemHandler.getType(player.getInventory().getItemInMainHand()).equals(ItemTypes.WEAPON)){
							
							Weapon weapon = new Weapon(player.getInventory().getItemInMainHand());
							if(weapon.getDurability() < weapon.getMaxDurability()){
								Inventory inventory = createAnvil(weapon, player);
								Anvil anvil = new Anvil(player.getInventory().getItemInMainHand(), event.getClickedBlock(), player, inventory);
								anvils.add(anvil);
								anvil.setShowItem();
								player.openInventory(inventory);
								return;
							}else{
								Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
								Message.sendCenteredMessage(player, ChatColor.BOLD + "Anvil");
								Message.sendCenteredMessage(player, ChatColor.YELLOW+ "Weapon doesn't need to be repaired!");
								Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
								return;
							}
						}else{
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							Message.sendCenteredMessage(player, ChatColor.BOLD + "Anvil");
							Message.sendCenteredMessage(player, ChatColor.YELLOW+ "Anvils can only repair weapons!");
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							return;
						}
					}else{
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(player, ChatColor.BOLD + "Anvil");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+ "Anvils can only repair weapons!");
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						return;
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event){
		for(Anvil anvil : anvils){
			if(event.getInventory().equals(anvil.getInventory())){
				event.setCancelled(true);
				if(event.getCurrentItem() == null){
					return;
				}else if(event.getCurrentItem().equals(yes)){
					Player player = (Player)event.getWhoClicked();
					Account account = new Account(player);
					xyz.almia.accountsystem.Character character = account.getLoadedCharacter();
					if(!(character.withdraw(anvil.getCost()))){
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(player, ChatColor.BOLD + "Anvil");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+ "You cannot afford to repair this weapon.");
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						event.getWhoClicked().closeInventory();
						anvil.deleteShowItem();
						return;
					}
					Weapon weapon = anvil.getWeapon();
					weapon.setDurability(anvil.getWeapon().getMaxDurability());
					event.getWhoClicked().getInventory().setItemInMainHand(weapon.getItemStack());
					anvil.deleteShowItem();
					event.getWhoClicked().closeInventory();
				}else if(event.getCurrentItem().equals(no)){
					event.getWhoClicked().closeInventory();
					anvil.deleteShowItem();
				}else{
					return;
				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event){
		for(Anvil anvil : anvils){
			if(event.getPlayer().equals(anvil.getPlayer())){
				anvil.deleteShowItem();
				anvils.remove(anvil);
				return;
			}
		}
	}
	
	public Inventory createAnvil(Weapon weapon, Player player){
		int cost = (weapon.getMaxDurability() - weapon.getDurability())*3;
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.DARK_GRAY+ "Fix "+ ChatColor.GRAY + weapon.getName() + ChatColor.DARK_GRAY + " for " + ChatColor.GREEN + cost + ChatColor.DARK_GRAY + " col?");
		ItemStack emp = MenuItem.createItem("", "", Material.STAINED_GLASS_PANE);
		inv.setItem(0, emp);
		inv.setItem(1, emp);
		inv.setItem(2, emp);
		inv.setItem(3, yes);
		inv.setItem(4, emp);
		inv.setItem(5, no);
		inv.setItem(6, emp);
		inv.setItem(7, emp);
		inv.setItem(8, emp);
		return inv;
	}
	
	
}
