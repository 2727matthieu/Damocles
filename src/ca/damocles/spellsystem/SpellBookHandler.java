package ca.damocles.spellsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import ca.damocles.itemblueprints.SpellTome;
import ca.damocles.itemsystem.ItemType;
import ca.damocles.itemsystem.ItemType.ItemTypes;
import ca.damocles.menusystem.MenuItem;
import ca.damocles.utils.Data;

public class SpellBookHandler implements Listener{
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent event){
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		if(item != null){
			if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
				if(new ItemType(item).getType().equals(ItemTypes.SPELLBOOK)){
					SpellBook book = new SpellBook(item);
					player.openInventory(book.getSpellventory());
				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event){
		if(event.getInventory().getName().contains("SpellBook")){
			if(event.getCurrentItem().equals(MenuItem.getEmpty())){
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event){
		if(event.getInventory().getName().contains("SpellBook")){
			String name = event.getInventory().getName();
			String[] names = name.split(" ");
			Inventory inv = event.getInventory();
			UUID uuid = UUID.fromString(Data.decodeItemData(names[1]));
			List<SpellTome> spells = new ArrayList<SpellTome>();
			List<ItemStack> drops = new ArrayList<ItemStack>();
			SpellBook book = new SpellBook(uuid);
			book.setSpells(new ArrayList<SpellTome>());
			for(int i = 2; i < 7; i++){
				ItemStack item = inv.getItem(i);
				if(item != null){
					if(new ItemType(item).getType().equals(ItemTypes.SPELL)){
						spells.add(new SpellTome(item));
					}else{
						drops.add(item);
					}
				}
			}
			book.setSpells(spells);
			for(ItemStack drop : drops){
				event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), drop);
			}
		}
		
	}
	
}
