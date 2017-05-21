package ca.damocles.menusystem;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import ca.damocles.accountsystem.Account;
import ca.damocles.accountsystem.Stat;

public class AccountMenu {
	
	public static Inventory getAccountMenu(Player player){
		
		Inventory inv = Bukkit.getServer().createInventory(null, 9, player.getName() + "'s Characters");
		
		/*
		 * [  0,  1,  2,  3,  4,  5,  6,  7,  8  ]
		 */
		
		ItemStack emp = MenuItem.createItem("", "", Material.STAINED_GLASS_PANE);
		ItemStack lo = MenuItem.createItem(ChatColor.RED+"Logout", ChatColor.GRAY + "Click to exit the server.", Material.REDSTONE_BLOCK);
		
		Account account = new Account(player);
		
		ca.damocles.accountsystem.Character characterOne = account.getCharacterFromID(0);
		ca.damocles.accountsystem.Character characterTwo = account.getCharacterFromID(1);
		ca.damocles.accountsystem.Character characterThree = account.getCharacterFromID(2);
		
		ItemStack charOne = null;
		ItemStack charTwo = null;
		ItemStack charThree = null;
		
		if(characterOne.exists()){
			charOne = MenuItem.createBetterItem("Character Slot 1", Arrays.asList(new String[] {
					ChatColor.GRAY + "Username: " + characterOne.getUsername(),
					ChatColor.GRAY + "Rank: " + characterOne.getRank().toString(),
					"",
					ChatColor.GRAY + "Level: " + characterOne.getLevel(),
					ChatColor.GRAY+ "Exp: "+characterOne.getExp()+"/"+(characterOne.getLevel() * 1028),
					ChatColor.GRAY+ "Ability Points: "+characterOne.getAbilityPoints(),
					"",
					ChatColor.GRAY+ "Strength: "+characterOne.getStat(Stat.STRENGTH)+" Agility: "+characterOne.getStat(Stat.AGILITY),
					ChatColor.GRAY+ "Hitpoints: "+characterOne.getStat(Stat.HITPOINTS)+" Intelligence: "+characterOne.getStat(Stat.INTELLIGENCE),
					"",
					ChatColor.GRAY+"Max Health: "+(characterOne.getMaxHealth()),
					ChatColor.GRAY+"Max Mana: "+(characterOne.getMaxMana()),
					ChatColor.GRAY+"Speed: "+characterOne.getSpeed(),
					ChatColor.GRAY+"Base Physical Damage: "+characterOne.getPhysicalDamage() ,
					ChatColor.GRAY+"Base Magic Damage: "+characterOne.getMagicalDamage(),
					"",
					}), Material.EMERALD_BLOCK);
		}else{
			charOne = MenuItem.createBetterItem("Character Slot 1", Arrays.asList(new String[] {
					ChatColor.GRAY + "Username: UNKNOWN",
					ChatColor.GRAY + "Rank: PLAYER",
					"",
					ChatColor.GRAY + "Level: 1",
					ChatColor.GRAY+ "Exp: 0"+"/"+"1024",
					ChatColor.GRAY+ "Skillslots: 2",
					"",
					ChatColor.GRAY+ "Strength: 0"+" Agility: 0",
					ChatColor.GRAY+ "Hitpoints: 0"+" Intelligence: 0",
					ChatColor.GRAY+ "Ability Points: 4",
					"",
					ChatColor.GRAY+"Max Health: 6",
					ChatColor.GRAY+"Max Mana: 20",
					ChatColor.GRAY+"Speed: 0.2",
					ChatColor.GRAY+"Base Physical Damage: 1",
					ChatColor.GRAY+"Base Magic Damage: 1",
					"",
					}), Material.QUARTZ_BLOCK);
		}
		
		if(characterTwo.exists()){
			charTwo = MenuItem.createBetterItem("Character Slot 2", Arrays.asList(new String[] {
					ChatColor.GRAY + "Username: " + characterTwo.getUsername(),
					ChatColor.GRAY + "Rank: " + characterTwo.getRank().toString(),
					"",
					ChatColor.GRAY + "Level: " + characterTwo.getLevel(),
					ChatColor.GRAY+ "Exp: "+characterTwo.getExp()+"/"+(characterTwo.getLevel() * 1028),
					ChatColor.GRAY+ "Ability Points: "+characterTwo.getAbilityPoints(),
					"",
					ChatColor.GRAY+ "Strength: "+characterTwo.getStat(Stat.STRENGTH)+" Agility: "+characterTwo.getStat(Stat.AGILITY),
					ChatColor.GRAY+ "Hitpoints: "+characterTwo.getStat(Stat.HITPOINTS)+" Intelligence: "+characterTwo.getStat(Stat.INTELLIGENCE),
					"",
					ChatColor.GRAY+"Max Health: "+(characterTwo.getMaxHealth()),
					ChatColor.GRAY+"Max Mana: "+(characterTwo.getMaxMana()),
					ChatColor.GRAY+"Speed: "+characterTwo.getSpeed(),
					ChatColor.GRAY+"Base Physical Damage: "+characterTwo.getPhysicalDamage() ,
					ChatColor.GRAY+"Base Magic Damage: "+characterTwo.getMagicalDamage(),
					"",
					}), Material.EMERALD_BLOCK);
		}else{
			charTwo = MenuItem.createBetterItem("Character Slot 2", Arrays.asList(new String[] {
					ChatColor.GRAY + "Username: UNKNOWN",
					ChatColor.GRAY + "Rank: PLAYER",
					"",
					ChatColor.GRAY + "Level: 1",
					ChatColor.GRAY+ "Exp: 0"+"/"+"1024",
					ChatColor.GRAY+ "Skillslots: 2",
					"",
					ChatColor.GRAY+ "Strength: 0"+" Agility: 0",
					ChatColor.GRAY+ "Hitpoints: 0"+" Intelligence: 0",
					ChatColor.GRAY+ "Ability Points: 4",
					"",
					ChatColor.GRAY+"Max Health: 6",
					ChatColor.GRAY+"Max Mana: 20",
					ChatColor.GRAY+"Speed: 0.2",
					ChatColor.GRAY+"Base Physical Damage: 1",
					ChatColor.GRAY+"Base Magic Damage: 1",
					"",
					}), Material.QUARTZ_BLOCK);
		}
		
		if(characterThree.exists()){
			charThree = MenuItem.createBetterItem("Character Slot 3", Arrays.asList(new String[] {
					ChatColor.GRAY + "Username: " + characterThree.getUsername(),
					ChatColor.GRAY + "Rank: " + characterThree.getRank().toString(),
					"",
					ChatColor.GRAY + "Level: " + characterThree.getLevel(),
					ChatColor.GRAY+ "Exp: "+characterThree.getExp()+"/"+(characterThree.getLevel() * 1028),
					ChatColor.GRAY+ "Ability Points: "+characterThree.getAbilityPoints(),
					"",
					ChatColor.GRAY+ "Strength: "+characterThree.getStat(Stat.STRENGTH)+" Agility: "+characterThree.getStat(Stat.AGILITY),
					ChatColor.GRAY+ "Hitpoints: "+characterThree.getStat(Stat.HITPOINTS)+" Intelligence: "+characterThree.getStat(Stat.INTELLIGENCE),
					ChatColor.GRAY+ "Ability Points: "+characterThree.getAbilityPoints(),
					"",
					ChatColor.GRAY+"Max Health: "+(characterThree.getMaxHealth()),
					ChatColor.GRAY+"Max Mana: "+(characterThree.getMaxMana()),
					ChatColor.GRAY+"Speed: "+characterThree.getSpeed(),
					ChatColor.GRAY+"Base Physical Damage: "+characterThree.getPhysicalDamage() ,
					ChatColor.GRAY+"Base Magic Damage: "+characterThree.getMagicalDamage(),
					"",
					}), Material.EMERALD_BLOCK);
		}else{
			charThree = MenuItem.createBetterItem("Character Slot 3", Arrays.asList(new String[] {
					ChatColor.GRAY + "Username: UNKNOWN",
					ChatColor.GRAY + "Rank: PLAYER",
					"",
					ChatColor.GRAY + "Level: 1",
					ChatColor.GRAY+ "Exp: 0"+"/"+"1024",
					ChatColor.GRAY+ "Skillslots: 2",
					"",
					ChatColor.GRAY+ "Strength: 0"+" Agility: 0",
					ChatColor.GRAY+ "Hitpoints: 0"+" Intelligence: 0",
					ChatColor.GRAY+ "Ability Points: 4",
					"",
					ChatColor.GRAY+"Max Health: 6",
					ChatColor.GRAY+"Max Mana: 20",
					ChatColor.GRAY+"Speed: 0.2",
					ChatColor.GRAY+"Base Physical Damage: 1",
					ChatColor.GRAY+"Base Magic Damage: 1",
					"",
					}), Material.QUARTZ_BLOCK);
		}
		
		
		inv.setItem(0, emp);
		inv.setItem(1, charOne);
		inv.setItem(2, emp);//1
		inv.setItem(3, charTwo);
		inv.setItem(4, emp);//2
		inv.setItem(5, charThree);
		inv.setItem(6, emp);//3
		inv.setItem(7, emp);
		inv.setItem(8, lo);
		
		return inv;
	}
	
}
