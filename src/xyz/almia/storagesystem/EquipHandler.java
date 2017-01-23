package xyz.almia.storagesystem;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import net.md_5.bungee.api.ChatColor;
import xyz.almia.accountsystem.Account;
import xyz.almia.itemsystem.ItemType;
import xyz.almia.itemsystem.ItemType.ArmorTypes;
import xyz.almia.itemsystem.ItemType.ItemTypes;
import xyz.almia.itemsystem.NBTHandler;
import xyz.almia.storagesystem.Equips.Slot;

public class EquipHandler implements Listener{
	
	@EventHandler
	public void onBankClose(InventoryCloseEvent event){
		if(event.getInventory().getName().equalsIgnoreCase("Bank")){
			Player player = (Player) event.getPlayer();
			Account account = new Account(player);
			xyz.almia.accountsystem.Character character = account.getLoadedCharacter();
			Inventory inv = event.getInventory();
			ItemStack bank = character.getEquip(Slot.BANK);
			new NBTHandler(bank).setBoxInventory(inv);
			character.setEquip(Slot.BANK, bank);
			return;
		}
	}

	@EventHandler
	public void onBankClick(InventoryClickEvent event){
		Player player = (Player) event.getWhoClicked();
		Account account = new Account(player);
		xyz.almia.accountsystem.Character character = account.getLoadedCharacter();
		
		if(character == null){
			return;
		}
		
		if(event.getInventory().getName().equalsIgnoreCase(character.getUsername()+"'s Bank")){
			if(event.getCurrentItem() == null){
				return;
			}
			event.setCancelled(true);
			
			if(event.getCurrentItem().hasItemMeta()){
				
				if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Empty Bank")){
					event.setCancelled(true);
					return;
				}
				
				if(event.getSlot() == 5){
					if(event.getCurrentItem().getItemMeta() instanceof BlockStateMeta){
						ItemStack item = event.getCurrentItem();
						event.getWhoClicked().closeInventory();
						event.getWhoClicked().openInventory(new NBTHandler(item).getBoxInventory());
					}
				}
				
				
			}
			
		}
	}
	
	@EventHandler
	public void onEquipClick(InventoryClickEvent event){
		
		if(event.getCurrentItem() == null){
			return;
		}
		
		if(event.getInventory().getName().equalsIgnoreCase("Equip Inventory")){
			
			if(event.getClickedInventory().equals(event.getWhoClicked().getInventory())){
				return;
			}
			
			ItemType type = new ItemType(event.getCursor());
			
			if(event.getAction().equals(InventoryAction.PLACE_ALL) || event.getAction().equals(InventoryAction.PICKUP_ALL)){
				
				if(event.getCurrentItem().hasItemMeta()){
					
					if(event.getCurrentItem().getItemMeta().hasDisplayName()){
						if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GRAY+"bank slot")){
							event.setCancelled(true);
							return;
						}
						if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GRAY+"Empty")){
							event.setCancelled(true);
							return;
						}
						if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GRAY+"Ring slot")){
							event.setCancelled(true);
							return;
						}
						if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GRAY+"glove slot")){
							event.setCancelled(true);
							return;
						}
						if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GRAY+"belt slot")){
							event.setCancelled(true);
							return;
						}
						if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GRAY+"spellbook slot")){
							event.setCancelled(true);
							return;
						}
						if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GRAY+"helmet slot")){
							event.setCancelled(true);
							return;
						}
						if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GRAY+"chestplate slot")){
							event.setCancelled(true);
							return;
						}
						if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GRAY+"leggings slot")){
							event.setCancelled(true);
							return;
						}
						if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GRAY+"boots slot")){
							event.setCancelled(true);
							return;
						}
					}
					
				}
				
				if(event.getSlot() == 1){
					if(event.getCursor().getType().equals(Material.AIR)){
						return;
					}
					
					if(!(type.getArmorType().equals(ArmorTypes.HEAD))){
						event.setCancelled(true);
						return;
					}
					return;
				}
				
				if(event.getSlot() == 2){
					if(event.getCursor().getType().equals(Material.AIR)){
						return;
					}
					if(!(type.getArmorType().equals(ArmorTypes.CHEST))){
						event.setCancelled(true);
						return;
					}
					return;
				}
				
				if(event.getSlot() == 6){
					if(event.getCursor().getType().equals(Material.AIR)){
						return;
					}
					if(!(type.getArmorType().equals(ArmorTypes.LEGS))){
						event.setCancelled(true);
						return;
					}
					return;
				}
				
				if(event.getSlot() == 7){
					if(event.getCursor().getType().equals(Material.AIR)){
						return;
					}
					if(!(type.getArmorType().equals(ArmorTypes.FEET))){
						event.setCancelled(true);
						return;
					}
					return;
				}
				
				if(event.getSlot() == 19){
					if(event.getCursor().getType().equals(Material.AIR)){
						return;
					}
					if(!(type.getType().equals(ItemTypes.BELT))){
						event.setCancelled(true);
						return;
					}
				}
				
				if(event.getSlot() == 20){
					if(event.getCursor().getType().equals(Material.AIR)){
						return;
					}
					if(!(type.getType().equals(ItemTypes.GLOVES))){
						event.setCancelled(true);
						return;
					}
				}
				
				if(event.getSlot() == 24 || event.getSlot() == 25 || event.getSlot() == 42 || event.getSlot() == 43){
					if(event.getCursor().getType().equals(Material.AIR)){
						return;
					}
					if(!(type.getType().equals(ItemTypes.RING))){
						event.setCancelled(true);
						return;
					}
				}
				
				if(event.getSlot() == 37){
					if(event.getCursor().getType().equals(Material.AIR)){
						return;
					}
					if(!(type.getType().equals(ItemTypes.SPELLBOOK))){
						event.setCancelled(true);
						return;
					}
				}
				
				
				if(event.getSlot() == 38){
					if(event.getCursor().getType().equals(Material.AIR)){
						return;
					}
					if(!(event.getCursor().getItemMeta() instanceof BlockStateMeta)){
						event.setCancelled(true);
						return;
					}
					return;
				}
				
				return;
			}else{
				event.setCancelled(true);
				return;
			}
		}
	}
	
	@EventHandler
	public void onDrag(InventoryDragEvent event){
		if(event.getInventory().getName().equalsIgnoreCase("Equip Inventory")){
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent event){
		BlockState state = event.getBlockPlaced().getState();
		if(state instanceof ShulkerBox){
			event.setCancelled(true);
		}
		
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent event){
		Inventory inv = event.getInventory();
		if(inv.getName().equals("Equip Inventory")){
			Player player = (Player) event.getPlayer();
			Account account = new Account(player);
			xyz.almia.accountsystem.Character character = account.getLoadedCharacter();
			player.getInventory().setHelmet(inv.getItem(1));
			character.setEquip(Slot.HELMET, inv.getItem(1));
			player.getInventory().setChestplate(inv.getItem(2));
			character.setEquip(Slot.CHESTPLATE, inv.getItem(2));
			player.getInventory().setLeggings(inv.getItem(6));
			character.setEquip(Slot.LEGGINGS, inv.getItem(6));
			player.getInventory().setBoots(inv.getItem(7));
			character.setEquip(Slot.BOOTS, inv.getItem(7));
			character.setEquip(Slot.BELT, inv.getItem(19));
			character.setEquip(Slot.GLOVES, inv.getItem(20));
			character.setEquip(Slot.RING1, inv.getItem(24));
			character.setEquip(Slot.RING2, inv.getItem(25));
			character.setEquip(Slot.SPELLBOOK, inv.getItem(37));
			character.setEquip(Slot.BANK, inv.getItem(38));
			character.setEquip(Slot.RING3, inv.getItem(42));
			character.setEquip(Slot.RING4, inv.getItem(43));
		}
	}
	
}
