package xyz.almia.menu;

import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import xyz.almia.accountsystem.Account;
import xyz.almia.accountsystem.Profession;
import xyz.almia.accountsystem.Stat;
import xyz.almia.storagesystem.Equips;
import xyz.almia.storagesystem.Treasury;

public class PlayerMenu implements Listener{
	
	public static Inventory createMenu(Player player){
		xyz.almia.accountsystem.Character character = new Account(player).getLoadedCharacter();
		Inventory inv = Bukkit.getServer().createInventory(null, 27, character.getUsername() + "'s Stats");
		
		ItemStack emp = MenuItem.createItem("Empty", "", Material.STAINED_GLASS_PANE);
		ItemStack i1 = MenuItem.createBetterItem(ChatColor.DARK_GRAY + "Player Data", Arrays.asList(new String[] {
				ChatColor.GRAY + "Name: " + character.getUsername(),
				ChatColor.GRAY + "Rank: " + character.getRank().toString(),
				"",
				ChatColor.GRAY + "Level: " + character.getLevel(),
				ChatColor.GRAY+ "Exp: "+character.getExp()+"/"+(character.getLevel() * 1028),
				ChatColor.GRAY+ "Ability Points: "+character.getAbilityPoints(),
				"",
				ChatColor.GRAY+"Max Health: "+(character.getMaxHealth()),
				ChatColor.GRAY+"Max Mana: "+(character.getMaxMana()),
				ChatColor.GRAY+"Speed: "+character.getSpeed(),
				ChatColor.GRAY+"Base Physical Damage: "+character.getPhysicalDamage() ,
				ChatColor.GRAY+"Base Magic Damage: "+character.getMagicalDamage(),
				"",
				}), Material.END_CRYSTAL);
		ItemStack prof = MenuItem.createBetterItem(ChatColor.DARK_GRAY + "Professions", Arrays.asList(new String[] { ChatColor.GRAY + "Click to open the Professions menu." }), Material.STONE_PICKAXE);
		ItemStack skills = MenuItem.createBetterItem(ChatColor.DARK_GRAY + "Skills", Arrays.asList(new String[] { ChatColor.GRAY + "Click to open the Skill menu."}), Material.ENCHANTED_BOOK);
		ItemStack money = MenuItem.createBetterItem(ChatColor.GREEN+"Balance", Arrays.asList(new String[] { ChatColor.GREEN+"$"+ChatColor.GRAY+""+ new Treasury(character).getBalance() }), Material.EMERALD);
		ItemStack friends = MenuItem.createBetterItem(ChatColor.RED+"Friends", Arrays.asList(new String[] {  }), Material.RED_ROSE);
		ItemStack ap = MenuItem.createEvenBetterItem(ChatColor.DARK_GRAY + "Ability Points", Arrays.asList(new String[] {ChatColor.GRAY + "Unallocated Points: " + new Account(player).getLoadedCharacter().getAbilityPoints(), ChatColor.GRAY + "Total Points: " + new Account(player).getLoadedCharacter().getLevel() * 3}), Material.EXP_BOTTLE, new Account(player).getLoadedCharacter().getAbilityPoints());
		ItemStack clan = MenuItem.createClanTag(player);
		ItemStack equip = MenuItem.createEvenBetterItem(ChatColor.DARK_GRAY + "Equips", Arrays.asList(ChatColor.GRAY + "Click to open your equip inventory.") , Material.DIAMOND_CHESTPLATE, 1);
		ItemMeta equipmeta = equip.getItemMeta();
		equipmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		equip.setItemMeta(equipmeta);
		
		
		ItemStack s1 = MenuItem.createEvenBetterItem(ChatColor.DARK_GRAY + "Strength", Arrays.asList(new String[] { ChatColor.GRAY + "Current Level: " + new Account(player).getLoadedCharacter().getStat(Stat.STRENGTH), ChatColor.GREEN+"Increases Base Physical-Damage." }), Material.BOOK, new Account(player).getLoadedCharacter().getStat(Stat.STRENGTH));
		ItemStack s2 = MenuItem.createEvenBetterItem(ChatColor.DARK_GRAY + "Agility", Arrays.asList(new String[] { ChatColor.GRAY + "Current Level: " + new Account(player).getLoadedCharacter().getStat(Stat.AGILITY), ChatColor.GREEN+"Increases Dodge-Chance and Base-Speed." }), Material.BOOK, new Account(player).getLoadedCharacter().getStat(Stat.AGILITY));
		ItemStack s3 = MenuItem.createEvenBetterItem(ChatColor.DARK_GRAY + "Hitpoints", Arrays.asList(new String[] { ChatColor.GRAY + "Current Level: " + new Account(player).getLoadedCharacter().getStat(Stat.HITPOINTS), ChatColor.GREEN+"Increases Base-Health." }), Material.BOOK, new Account(player).getLoadedCharacter().getStat(Stat.HITPOINTS));
		ItemStack s4 = MenuItem.createEvenBetterItem(ChatColor.DARK_GRAY+"Intelligence", Arrays.asList(new String[] { ChatColor.GRAY + "Current Level: " + new Account(player).getLoadedCharacter().getStat(Stat.INTELLIGENCE), ChatColor.GREEN+"Increases Max-Mana and Magic-Damage" }), Material.BOOK, new Account(player).getLoadedCharacter().getStat(Stat.INTELLIGENCE));
		
		/*
		 *     TODO LIST
		 *     - MAYBE ADD GLOBAL SHOP ACCESS IN MAIN MENU.
		 */
		
		
		/*
		 *  [  0,  1,  2,  3,  4,  5,  6,  7,  8, ]
		 *  [  9, 10, 11, 12, 13, 14, 15, 16, 17, ]
		 *  [ 18, 19, 20, 21, 22, 23, 24, 25, 26, ]
		 *  [ 27, 28, 29, 30, 31, 32, 33, 34, 35, ]
		 *  [ 36, 37, 38, 39, 40, 41, 42, 43, 44, ]
		 *  [ 45, 46, 47, 48, 49, 50, 51, 52, 53, ]
		 */
		
		
		inv.setItem(0, clan);
		inv.setItem(1, emp);
		inv.setItem(2, emp);
		inv.setItem(3, i1);
		inv.setItem(4, emp);
		inv.setItem(5, emp);
		inv.setItem(6, emp);
		inv.setItem(7, s4);
		inv.setItem(8, s1);
		inv.setItem(9, money);
		inv.setItem(10, emp);
		inv.setItem(11, prof);
		inv.setItem(12, emp);
		inv.setItem(13, skills);
		inv.setItem(14, emp);
		inv.setItem(15, emp);
		inv.setItem(16, s3);
		inv.setItem(17, s2);
		inv.setItem(18, friends);
		inv.setItem(19, emp);
		inv.setItem(20, emp);
		inv.setItem(21, equip);
		inv.setItem(22, emp);
		inv.setItem(23, emp);
		inv.setItem(24, emp);
		inv.setItem(25, emp);
		inv.setItem(26, ap);
		
		return inv;
	}
	
	public static Inventory createEconomyMenu(Player player){
		Inventory inv = Bukkit.getServer().createInventory(null, 9, "Economy Menu");
		
		/*
		 *     TODO LIST
		 *     - Create USAGE FOR THIS MENU IN MAIN MENU.
		 *     - CREATE PAY AND BANK METHODS FROM THIS MENU.
		 */
		
		ItemStack emp = MenuItem.createItem("Empty", "", Material.STAINED_GLASS_PANE);
		
		inv.setItem(0, emp);
		inv.setItem(1, emp);
		inv.setItem(2, emp);
		inv.setItem(3, emp);
		inv.setItem(4, emp);
		inv.setItem(5, emp);
		inv.setItem(6, emp);
		inv.setItem(7, emp);
		inv.setItem(8, emp);
		
		return inv;
	}
	
	public static Inventory createProfMenu(Player player){
		Inventory inv = Bukkit.getServer().createInventory(null, 9, "Profession Menu");
		
		ItemStack emp = MenuItem.createItem("Empty", "", Material.STAINED_GLASS_PANE);
		ItemStack mining = MenuItem.createBetterItem(ChatColor.DARK_GRAY + "Mining", Arrays.asList(new String[] { 
				ChatColor.GRAY + "Level: " + new Account(player).getLoadedCharacter().getPLevel(Profession.MINING),
				ChatColor.GRAY + "Exp: " + new Account(player).getLoadedCharacter().getPExp(Profession.MINING) + "/" + new Account(player).getLoadedCharacter().getPLevel(Profession.MINING) * 100
		}), Material.WOOD_PICKAXE);
		
		ItemStack smelting = MenuItem.createBetterItem(ChatColor.DARK_GRAY + "Smelting", Arrays.asList(new String[] { 
				ChatColor.GRAY + "Level: " + new Account(player).getLoadedCharacter().getPLevel(Profession.FORGING),
				ChatColor.GRAY + "Exp: " + new Account(player).getLoadedCharacter().getPExp(Profession.FORGING) + "/" + new Account(player).getLoadedCharacter().getPLevel(Profession.FORGING) * 100
		}), Material.COAL);
		
		ItemStack fishing = MenuItem.createBetterItem(ChatColor.DARK_GRAY + "Fishing", Arrays.asList(new String[] { 
				ChatColor.GRAY + "Level: " + new Account(player).getLoadedCharacter().getPLevel(Profession.FISHING),
				ChatColor.GRAY + "Exp: " + new Account(player).getLoadedCharacter().getPExp(Profession.FISHING) + "/" + new Account(player).getLoadedCharacter().getPLevel(Profession.FISHING) * 100
		}), Material.FISHING_ROD);
		
		ItemStack cooking = MenuItem.createBetterItem(ChatColor.DARK_GRAY + "Cooking", Arrays.asList(new String[] { 
				ChatColor.GRAY + "Level: " + new Account(player).getLoadedCharacter().getPLevel(Profession.COOKING),
				ChatColor.GRAY + "Exp: " + new Account(player).getLoadedCharacter().getPExp(Profession.COOKING) + "/" + new Account(player).getLoadedCharacter().getPLevel(Profession.COOKING) * 100
		}), Material.MUSHROOM_SOUP);
		
		ItemStack farming = MenuItem.createBetterItem(ChatColor.DARK_GRAY + "Herbalism", Arrays.asList(new String[] { 
				ChatColor.GRAY + "Level: " + new Account(player).getLoadedCharacter().getPLevel(Profession.HERBALISM),
				ChatColor.GRAY + "Exp: " + new Account(player).getLoadedCharacter().getPExp(Profession.HERBALISM) + "/" + new Account(player).getLoadedCharacter().getPLevel(Profession.HERBALISM) * 100
		}), Material.WOOD_HOE);
		
		inv.setItem(0, mining);
		inv.setItem(1, smelting); 
		inv.setItem(2, fishing); //i1
		inv.setItem(3, cooking); 
		inv.setItem(4, farming); //i2
		inv.setItem(5, emp); 
		inv.setItem(6, emp); //i3
		inv.setItem(7, emp);
		inv.setItem(8, emp);
		
		return inv;
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event){
		Player player = (Player)event.getWhoClicked();
		Account account = new Account(player);
		xyz.almia.accountsystem.Character character = account.getLoadedCharacter();
		
		if(event.getInventory().getName().toLowerCase().equals("profession menu")){
			event.setCancelled(true);
		}
		
		if(character == null){
			return;
		}
		
		if(event.getInventory().getName().equals(character.getUsername() + "'s Stats")){
			event.setCancelled(true);
			if(event.getCurrentItem() != null){
				
				if(event.getCurrentItem().getType().equals(Material.DIAMOND_CHESTPLATE)){
					player.closeInventory();
					player.openInventory(new Equips(character).getMenu());
				}
				
				if(event.getCurrentItem().getType().equals(Material.STONE_PICKAXE)){
					player.closeInventory();
					player.openInventory(createProfMenu(player));
				}
				if(event.getCurrentItem().getType().equals(Material.BOOK)){
					String string = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).toUpperCase();
					Stat stat = Stat.valueOf(string);
					if(new Account(player).getLoadedCharacter().getAbilityPoints() >= 1){
						new Account(player).getLoadedCharacter().setStat(stat, new Account(player).getLoadedCharacter().getStat(stat)+1);
						new Account(player).getLoadedCharacter().setAbilityPoints(new Account(player).getLoadedCharacter().getAbilityPoints()-1);
						player.closeInventory();
						player.openInventory(createMenu(player));
					}
				}
			}
		}
		
	}
	
}
