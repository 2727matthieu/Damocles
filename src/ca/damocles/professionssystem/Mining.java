package ca.damocles.professionssystem;

import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import ca.damocles.accountsystem.Account;
import ca.damocles.accountsystem.Profession;
import ca.damocles.cardinalsystem.Cardinal;
import ca.damocles.expsystem.MiningExp;

public class Mining implements Listener{
	
	/*
	 *    CHANGELOG:
	 *    - Added dropAmount/bonus' depending on level.
	 */
	
	Plugin plugin = Cardinal.getPlugin();
	MiningExp miningexp = new MiningExp();
	
	@EventHandler
	public void onPlayerOrePlace(BlockPlaceEvent event){
		Block b = event.getBlock();
		b.setMetadata("natural", new FixedMetadataValue(plugin, "false"));
	}
	
	public int dropAmount(Player player){
		int level = new Account(player).getLoadedCharacter().getPLevel(Profession.MINING);
		int rand = new Random().nextInt(100);
		if(rand < level){
			return 2;
		}
		return 1;
	}
	
	@EventHandler
	public void onPlayerMine(BlockBreakEvent event){
		
		Block b = event.getBlock();
		
		if(!(b.hasMetadata("natural"))){
			Player p = event.getPlayer();
			if(event.getBlock().getType().equals(Material.STONE)){
				new Account(p).getLoadedCharacter().addPExp(Profession.MINING, miningexp.stone());
			}
			
			if(event.getBlock().getType().equals(Material.DIAMOND_ORE)){
				event.setCancelled(true);
				event.getBlock().setType(Material.AIR);
				ItemStack i = new ItemStack(Material.DIAMOND_ORE, 1 * dropAmount(p));
				ItemMeta im = i.getItemMeta();
				im.setDisplayName(ChatColor.WHITE + "Diamond Ore");
				i.setItemMeta(im);
				p.getWorld().dropItemNaturally(event.getBlock().getLocation(), i);
				new Account(p).getLoadedCharacter().addPExp(Profession.MINING, miningexp.diamond());
			}
			
			if(event.getBlock().getType().equals(Material.IRON_ORE)){
				event.setCancelled(true);
				event.getBlock().setType(Material.AIR);
				ItemStack i = new ItemStack(Material.IRON_ORE, 1 * dropAmount(p));
				ItemMeta im = i.getItemMeta();
				im.setDisplayName(ChatColor.WHITE + "Steel Ore");
				i.setItemMeta(im);
				p.getWorld().dropItemNaturally(event.getBlock().getLocation(), i);
				new Account(p).getLoadedCharacter().addPExp(Profession.MINING, miningexp.iron());
			}
			
			if(event.getBlock().getType().equals(Material.EMERALD_ORE)){
				event.setCancelled(true);
				event.getBlock().setType(Material.AIR);
				ItemStack i = new ItemStack(Material.EMERALD_ORE, 1 * dropAmount(p));
				ItemMeta im = i.getItemMeta();
				im.setDisplayName(ChatColor.WHITE + "Crystal Ore");
				i.setItemMeta(im);
				p.getWorld().dropItemNaturally(event.getBlock().getLocation(), i);
				new Account(p).getLoadedCharacter().addPExp(Profession.MINING, miningexp.emerald());
			}
			
			if(event.getBlock().getType().equals(Material.COAL_ORE)){
				event.setCancelled(true);
				event.getBlock().setType(Material.AIR);
				ItemStack i = new ItemStack(Material.COAL_ORE, 1 * dropAmount(p));
				ItemMeta im = i.getItemMeta();
				im.setDisplayName(ChatColor.WHITE + "Coal Ore");
				i.setItemMeta(im);
				p.getWorld().dropItemNaturally(event.getBlock().getLocation(), i);
				new Account(p).getLoadedCharacter().addPExp(Profession.MINING, miningexp.coal());
			}
			
			if(event.getBlock().getType().equals(Material.GOLD_ORE)){
				event.setCancelled(true);
				event.getBlock().setType(Material.AIR);
				ItemStack i = new ItemStack(Material.GOLD_ORE, 1 * dropAmount(p));
				ItemMeta im = i.getItemMeta();
				im.setDisplayName(ChatColor.WHITE + "Gold Ore");
				i.setItemMeta(im);
				p.getWorld().dropItemNaturally(event.getBlock().getLocation(), i);
				new Account(p).getLoadedCharacter().addPExp(Profession.MINING, miningexp.gold());
			}
		}else{
			
			Player p = event.getPlayer();
			if(event.getBlock().getType().equals(Material.DIAMOND_ORE)){
				event.setCancelled(true);
				event.getBlock().setType(Material.AIR);
				ItemStack i = new ItemStack(Material.DIAMOND_ORE, 1);
				ItemMeta im = i.getItemMeta();
				im.setDisplayName(ChatColor.WHITE + "Diamond Ore");
				i.setItemMeta(im);
				p.getWorld().dropItemNaturally(event.getBlock().getLocation(), i);
			}
			
			if(event.getBlock().getType().equals(Material.IRON_ORE)){
				event.setCancelled(true);
				event.getBlock().setType(Material.AIR);
				ItemStack i = new ItemStack(Material.IRON_ORE, 1);
				ItemMeta im = i.getItemMeta();
				im.setDisplayName(ChatColor.WHITE + "Steel Ore");
				i.setItemMeta(im);
				p.getWorld().dropItemNaturally(event.getBlock().getLocation(), i);
			}
			
			if(event.getBlock().getType().equals(Material.EMERALD_ORE)){
				event.setCancelled(true);
				event.getBlock().setType(Material.AIR);
				ItemStack i = new ItemStack(Material.EMERALD_ORE, 1);
				ItemMeta im = i.getItemMeta();
				im.setDisplayName(ChatColor.WHITE + "Crystal Ore");
				i.setItemMeta(im);
				p.getWorld().dropItemNaturally(event.getBlock().getLocation(), i);
			}
			
			if(event.getBlock().getType().equals(Material.COAL_ORE)){
				event.setCancelled(true);
				event.getBlock().setType(Material.AIR);
				ItemStack i = new ItemStack(Material.COAL_ORE, 1);
				ItemMeta im = i.getItemMeta();
				im.setDisplayName(ChatColor.WHITE + "Coal Ore");
				i.setItemMeta(im);
				p.getWorld().dropItemNaturally(event.getBlock().getLocation(), i);
			}
			
			if(event.getBlock().getType().equals(Material.GOLD_ORE)){
				event.setCancelled(true);
				event.getBlock().setType(Material.AIR);
				ItemStack i = new ItemStack(Material.GOLD_ORE, 1);
				ItemMeta im = i.getItemMeta();
				im.setDisplayName(ChatColor.WHITE + "Gold Ore");
				i.setItemMeta(im);
				p.getWorld().dropItemNaturally(event.getBlock().getLocation(), i);
			}
		}
	}
}
