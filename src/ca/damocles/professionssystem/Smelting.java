package ca.damocles.professionssystem;

import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import ca.damocles.accountsystem.Account;
import ca.damocles.accountsystem.Profession;
import ca.damocles.cardinalsystem.Cardinal;
import ca.damocles.expsystem.CookingExp;
import ca.damocles.expsystem.SmeltingExp;
import ca.damocles.menusystem.MenuItem;

public class Smelting implements Listener{
	
	Plugin plugin = Cardinal.getPlugin();
	CookingExp cookingexp = new CookingExp();
	SmeltingExp smeltingexp = new SmeltingExp();
	
	ItemStack c = MenuItem.createItem(ChatColor.AQUA + "Coal", ChatColor.GRAY + "Click to turn all Coal Ore into Bars.", Material.COAL);
	ItemStack i = MenuItem.createItem(ChatColor.AQUA + "Steel", ChatColor.GRAY + "Click to turn all Steel Ore into Bars.", Material.IRON_INGOT);
	ItemStack g = MenuItem.createItem(ChatColor.AQUA + "Gold", ChatColor.GRAY + "Click to turn all Gold Ore into Bars.", Material.GOLD_INGOT);
	ItemStack d = MenuItem.createItem(ChatColor.AQUA + "Diamond", ChatColor.GRAY + "Click to turn all Diamond Ore into Diamond.", Material.DIAMOND);
	ItemStack e = MenuItem.createItem(ChatColor.AQUA + "Magic Crystal", ChatColor.GRAY + "Click to turn all Magic Crystal Ore into Magic Crystal.", Material.EMERALD);
	
	ItemStack bacon = MenuItem.createItem(ChatColor.AQUA + "Bacon", ChatColor.GRAY +  "+8 Hunger", Material.GRILLED_PORK);
	ItemStack sbacon = MenuItem.createItem(ChatColor.AQUA + "Smoked Bacon", ChatColor.GRAY +  "+10 hunger", Material.GRILLED_PORK);
	ItemStack steak = MenuItem.createItem(ChatColor.AQUA + "Steak", ChatColor.GRAY +  "+8 Hunger", Material.COOKED_BEEF);
	ItemStack rabbit = MenuItem.createItem(ChatColor.AQUA + "Cooked Rabbit", ChatColor.GRAY +  "+5 Hunger", Material.COOKED_RABBIT);
	ItemStack trout = MenuItem.createItem(ChatColor.AQUA + "Cooked Trout", ChatColor.GRAY +  "+5 Hunger", Material.COOKED_FISH);
	ItemStack mutton = MenuItem.createItem(ChatColor.AQUA + "Mutton", ChatColor.GRAY +  "+6 Hunger", Material.COOKED_MUTTON);
	ItemStack potato = MenuItem.createItem(ChatColor.AQUA + "Baked potato", ChatColor.GRAY +  "+5 Hunger", Material.BAKED_POTATO);
	
	ItemStack forging = MenuItem.createItem(ChatColor.DARK_GRAY + "Furnace", ChatColor.GRAY + "Opens the Forging menu.", Material.IRON_INGOT);
	
	ItemStack cooking = MenuItem.createItem(ChatColor.DARK_GRAY + "Stove", ChatColor.GRAY + "Opens cooking menu.", Material.COOKED_BEEF);
	
	ItemStack empty = MenuItem.createItem(ChatColor.AQUA + "Empty", "", Material.STAINED_GLASS_PANE);
	
	public void onfurnaceClick(PlayerInteractEvent event){
		if(event.getClickedBlock() != null){
			if(event.getClickedBlock().getType().equals(Material.FURNACE)){
				if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
					event.setCancelled(true);
					createChoiceFurnace(event.getPlayer());
				}
			}
		}
	}
	
	public void createChoiceFurnace(Player p){
		Inventory inv = Bukkit.createInventory(null, 9, "Stove/Furnace");
		inv.setItem(0, empty); 
		inv.setItem(1, empty);
		inv.setItem(2, empty);
		inv.setItem(3, cooking);
		inv.setItem(4, empty); 
		inv.setItem(5, forging);
		inv.setItem(6, empty);
		inv.setItem(7, empty);
		inv.setItem(8, empty); 
		p.openInventory(inv);
	}
	
	public void createCookingFurnace(Player p){
		Inventory inv = Bukkit.createInventory(null, 9, "Cooking Stove");
		
		inv.setItem(0, bacon);
		inv.setItem(1, steak);
		inv.setItem(2, rabbit);
		inv.setItem(3, trout);
		inv.setItem(4, mutton);
		inv.setItem(5, sbacon);
		inv.setItem(6, potato);
		inv.setItem(7, empty);
		inv.setItem(8, empty);
		p.openInventory(inv);
	}
	
	public void createSmeltingFurnace(Player p){
		Inventory inv = Bukkit.createInventory(null, 9, "Smelting Furnace");
		inv.setItem(0, c); //1
		inv.setItem(1, empty);
		inv.setItem(2, i); //2
		inv.setItem(3, empty);
		inv.setItem(4, d); //3
		inv.setItem(5, empty);
		inv.setItem(6, g); //4
		inv.setItem(7, empty);
		inv.setItem(8, e); //5
		p.openInventory(inv);
	}
	
	@EventHandler
	public void onSmeltInvClick(InventoryClickEvent event){
		Player player = (Player)event.getWhoClicked();
		
		if(event.getInventory().getName().equals("Cooking Stove")){
			event.setCancelled(true);
			ItemStack item = event.getCurrentItem();
			Player player1 = (Player) event.getWhoClicked();
			if(item != null){
				if(item.equals(bacon)){
					for(ItemStack i : player1.getInventory().getContents()){
						if(i != null){
							if(i.getType().equals(Material.PORK)){
								if(i.getAmount() == 1){
									player1.getInventory().remove(i);
									player1.getInventory().addItem(new ItemStack(Material.GRILLED_PORK, 1));
									new Account(player1).getLoadedCharacter().addPExp(Profession.COOKING, cookingexp.bacon());
								}
								if(i.getAmount() > 1){
									i.setAmount(i.getAmount() - 1);
									player1.getInventory().addItem(new ItemStack(Material.GRILLED_PORK, 1));
									new Account(player1).getLoadedCharacter().addPExp(Profession.COOKING, cookingexp.bacon());
								}
							}
						}
					}
				}
				if(item.equals(potato)){
					if(player1.getInventory().contains(Material.POTATO_ITEM)){
						for(ItemStack i : player1.getInventory().getContents()){
							if(i != null){
								if(i.getType().equals(Material.POTATO_ITEM)){
									if(i.getAmount() == 1){
										player1.getInventory().remove(i);
										player1.getInventory().addItem(new ItemStack(Material.BAKED_POTATO, 1));
										new Account(player1).getLoadedCharacter().addPExp(Profession.COOKING, cookingexp.potato());
									}
									if(i.getAmount() > 1){
										i.setAmount(i.getAmount() - 1);
										player1.getInventory().addItem(new ItemStack(Material.BAKED_POTATO, 1));
										new Account(player1).getLoadedCharacter().addPExp(Profession.COOKING, cookingexp.potato());
									}
								}
							}
						}
					}else{
						player1.sendMessage(ChatColor.YELLOW + "You need the proper ingredients to cook this item!");
					}
				}
				if(item.equals(sbacon)){
					return;
				}
				if(item.equals(steak)){
					if(player1.getInventory().contains(Material.RAW_BEEF)){
						for(ItemStack i : player1.getInventory().getContents()){
							if(i != null){
								if(i.getType().equals(Material.RAW_BEEF)){
									if(i.getAmount() == 1){
										player1.getInventory().remove(i);
										player1.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 1));
										new Account(player1).getLoadedCharacter().addPExp(Profession.COOKING, cookingexp.steak());
									}
									if(i.getAmount() > 1){
										i.setAmount(i.getAmount() - 1);
										player1.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 1));
										new Account(player1).getLoadedCharacter().addPExp(Profession.COOKING, cookingexp.steak());
									}
								}
							}
						}
					}else{
						player1.sendMessage(ChatColor.YELLOW + "You need the proper ingredients to cook this item!");
					}
				}
				if(item.equals(rabbit)){
					if(player1.getInventory().contains(Material.RABBIT)){
						for(ItemStack i : player1.getInventory().getContents()){
							if(i != null){
								if(i.getType().equals(Material.RABBIT)){
									if(i.getAmount() == 1){
										player1.getInventory().remove(i);
										player1.getInventory().addItem(new ItemStack(Material.COOKED_RABBIT, 1));
										new Account(player1).getLoadedCharacter().addPExp(Profession.COOKING, cookingexp.rabbit());
									}
									if(i.getAmount() > 1){
										i.setAmount(i.getAmount() - 1);
										player1.getInventory().addItem(new ItemStack(Material.COOKED_RABBIT, 1));
										new Account(player1).getLoadedCharacter().addPExp(Profession.COOKING, cookingexp.rabbit());
									}
								}
							}
						}
					}else{
						player1.sendMessage(ChatColor.YELLOW + "You need the proper ingredients to cook this item!");
					}
				}
				if(item.equals(trout)){
					if(player1.getInventory().contains(Material.RAW_FISH)){
						for(ItemStack i : player1.getInventory().getContents()){
							if(i != null){
								if(i.getType().equals(Material.RAW_FISH)){
									if(i.getAmount() == 1){
										player1.getInventory().remove(i);
										player1.getInventory().addItem(new ItemStack(Material.COOKED_FISH, 1));
										new Account(player1).getLoadedCharacter().addPExp(Profession.COOKING, cookingexp.trout());
									}
									if(i.getAmount() > 1){
										i.setAmount(i.getAmount() - 1);
										player1.getInventory().addItem(new ItemStack(Material.COOKED_FISH, 1));
										new Account(player1).getLoadedCharacter().addPExp(Profession.COOKING, cookingexp.trout());
									}
								}
							}
						}
					}else{
						player1.sendMessage(ChatColor.YELLOW + "You need the proper ingredients to cook this item!");
					}
				}
				if(item.equals(mutton)){
					if(player1.getInventory().contains(Material.MUTTON)){
						for(ItemStack i : player1.getInventory().getContents()){
							if(i != null){
								if(i.getType().equals(Material.MUTTON)){
									if(i.getAmount() == 1){
										player1.getInventory().remove(i);
										player1.getInventory().addItem(new ItemStack(Material.COOKED_MUTTON, 1));
										new Account(player1).getLoadedCharacter().addPExp(Profession.COOKING, cookingexp.mutton());
									}
									if(i.getAmount() > 1){
										i.setAmount(i.getAmount() - 1);
										player1.getInventory().addItem(new ItemStack(Material.COOKED_MUTTON, 1));
										new Account(player1).getLoadedCharacter().addPExp(Profession.COOKING, cookingexp.mutton());
									}
								}
							}
						}
					}else{
						player1.sendMessage(ChatColor.YELLOW + "You need the proper ingredients to cook this item!");
					}
				}
			}
		}
		
		if(event.getInventory().getName().equals("Stove/Furnace")){
			event.setCancelled(true);
			if(event.getCurrentItem().equals(forging)){
				player.closeInventory();
				createSmeltingFurnace(player);
			}
			if(event.getCurrentItem().equals(cooking)){
				player.closeInventory();
				createCookingFurnace(player);
			}
		}
		
		if(event.getInventory().getName().equals("Smelting Furnace")){
			event.setCancelled(true);
			int level = new Account(player).getLoadedCharacter().getPLevel(Profession.FORGING);
			if(event.getCurrentItem().equals(c)){
				for(ItemStack i : event.getWhoClicked().getInventory().getContents()){
					if(i != null && i.getType().equals(Material.COAL_ORE)){
						event.getWhoClicked().getInventory().remove(i);
						ItemStack b = new ItemStack(Material.COAL, i.getAmount());
						ItemMeta bm = b.getItemMeta();
						bm.setDisplayName(ChatColor.AQUA + "Coal");
						if(level >= 1 && level <= 15){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 0" }));
						}
						if(level >= 16 && level <= 25){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 1" }));
						}
						if(level >= 26 && level <= 35){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 2" }));
						}
						if(level >= 36 && level <= 45){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 3" }));
						}
						if(level >= 46 && level <= 55){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 4" }));
						}
						if(level >= 56 && level <= 65){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 5" }));
						}
						if(level >= 66 && level <= 75){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 6" }));
						}
						if(level >= 76 && level <= 85){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 7" }));
						}
						if(level >= 86 && level <= 95){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 8" }));
						}
						if(level >= 96 && level <= 99){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 9" }));
						}
						if(level >= 100){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 10" }));
						}
						
						b.setItemMeta(bm);
						event.getWhoClicked().getInventory().addItem(b);
						new Account(player).getLoadedCharacter().addPExp(Profession.FORGING, smeltingexp.coal() * i.getAmount());
					}
				}
			}
			if(event.getCurrentItem().equals(i)){
				for(ItemStack i : event.getWhoClicked().getInventory().getContents()){
					if(i != null && i.getType().equals(Material.IRON_ORE)){
						event.getWhoClicked().getInventory().remove(i);
						ItemStack b = new ItemStack(Material.IRON_INGOT, i.getAmount());
						ItemMeta bm = b.getItemMeta();
						bm.setDisplayName(ChatColor.AQUA + "Forged Steel Bar");
						if(level >= 1 && level <= 15){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 0" }));
						}
						if(level >= 16 && level <= 25){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 1" }));
						}
						if(level >= 26 && level <= 35){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 2" }));
						}
						if(level >= 36 && level <= 45){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 3" }));
						}
						if(level >= 46 && level <= 55){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 4" }));
						}
						if(level >= 56 && level <= 65){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 5" }));
						}
						if(level >= 66 && level <= 75){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 6" }));
						}
						if(level >= 76 && level <= 85){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 7" }));
						}
						if(level >= 86 && level <= 95){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 8" }));
						}
						if(level >= 96 && level <= 99){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 9" }));
						}
						if(level >= 100){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 10" }));
						}
						
						b.setItemMeta(bm);
						event.getWhoClicked().getInventory().addItem(b);
						new Account(player).getLoadedCharacter().addPExp(Profession.FORGING, smeltingexp.iron() * i.getAmount());
					}
				}
			}
			if(event.getCurrentItem().equals(g)){
				for(ItemStack i : event.getWhoClicked().getInventory().getContents()){
					if(i != null && i.getType().equals(Material.GOLD_ORE)){
						event.getWhoClicked().getInventory().remove(i);
						ItemStack b = new ItemStack(Material.GOLD_INGOT, i.getAmount());
						ItemMeta bm = b.getItemMeta();
						bm.setDisplayName(ChatColor.AQUA + "Forged Gold Bar");
						if(level >= 1 && level <= 15){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 0" }));
						}
						if(level >= 16 && level <= 25){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 1" }));
						}
						if(level >= 26 && level <= 35){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 2" }));
						}
						if(level >= 36 && level <= 45){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 3" }));
						}
						if(level >= 46 && level <= 55){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 4" }));
						}
						if(level >= 56 && level <= 65){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 5" }));
						}
						if(level >= 66 && level <= 75){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 6" }));
						}
						if(level >= 76 && level <= 85){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 7" }));
						}
						if(level >= 86 && level <= 95){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 8" }));
						}
						if(level >= 96 && level <= 99){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 9" }));
						}
						if(level >= 100){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 10" }));
						}
						b.setItemMeta(bm);
						event.getWhoClicked().getInventory().addItem(b);
						new Account(player).getLoadedCharacter().addPExp(Profession.FORGING, smeltingexp.gold() * i.getAmount());
					}
				}
			}
			if(event.getCurrentItem().equals(e)){
				for(ItemStack i : event.getWhoClicked().getInventory().getContents()){
					if(i != null && i.getType().equals(Material.EMERALD_ORE)){
						event.getWhoClicked().getInventory().remove(i);
						ItemStack b = new ItemStack(Material.EMERALD, i.getAmount());
						ItemMeta bm = b.getItemMeta();
						bm.setDisplayName(ChatColor.AQUA + "Forged Magic Crystal");
						if(level >= 1 && level <= 15){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 0" }));
						}
						if(level >= 16 && level <= 25){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 1" }));
						}
						if(level >= 26 && level <= 35){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 2" }));
						}
						if(level >= 36 && level <= 45){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 3" }));
						}
						if(level >= 46 && level <= 55){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 4" }));
						}
						if(level >= 56 && level <= 65){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 5" }));
						}
						if(level >= 66 && level <= 75){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 6" }));
						}
						if(level >= 76 && level <= 85){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 7" }));
						}
						if(level >= 86 && level <= 95){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 8" }));
						}
						if(level >= 96 && level <= 99){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 9" }));
						}
						if(level >= 100){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 10" }));
						}
						b.setItemMeta(bm);
						event.getWhoClicked().getInventory().addItem(b);
						new Account(player).getLoadedCharacter().addPExp(Profession.FORGING, smeltingexp.emerald() * i.getAmount());
					}
				}
			}
			if(event.getCurrentItem().equals(d)){
				for(ItemStack i : event.getWhoClicked().getInventory().getContents()){
					if(i != null && i.getType().equals(Material.DIAMOND_ORE)){
						event.getWhoClicked().getInventory().remove(i);
						ItemStack b = new ItemStack(Material.DIAMOND, i.getAmount());
						ItemMeta bm = b.getItemMeta();
						bm.setDisplayName(ChatColor.AQUA + "Forged Diamond");
						if(level >= 1 && level <= 15){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 0" }));
						}
						if(level >= 16 && level <= 25){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 1" }));
						}
						if(level >= 26 && level <= 35){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 2" }));
						}
						if(level >= 36 && level <= 45){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 3" }));
						}
						if(level >= 46 && level <= 55){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 4" }));
						}
						if(level >= 56 && level <= 65){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 5" }));
						}
						if(level >= 66 && level <= 75){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 6" }));
						}
						if(level >= 76 && level <= 85){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 7" }));
						}
						if(level >= 86 && level <= 95){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 8" }));
						}
						if(level >= 96 && level <= 99){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 9" }));
						}
						if(level >= 100){
							bm.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Forged by: " + event.getWhoClicked().getName(), ChatColor.GRAY + "Tier: 10" }));
						}
						b.setItemMeta(bm);
						event.getWhoClicked().getInventory().addItem(b);
						new Account(player).getLoadedCharacter().addPExp(Profession.FORGING, smeltingexp.diamond() * i.getAmount());
					}
				}
			}
		}
	}
	
}
