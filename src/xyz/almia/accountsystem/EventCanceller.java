package xyz.almia.accountsystem;

import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import xyz.almia.cardinalsystem.Cardinal;
import xyz.almia.enchantsystem.Enchantments;
import xyz.almia.enchantsystem.Rune;
import xyz.almia.utils.Message;

public class EventCanceller implements Listener{
	
	Plugin plugin = Cardinal.getPlugin();
	xyz.almia.enchantsystem.Enchantment enchantclass = new xyz.almia.enchantsystem.Enchantment();
	Rune rune = new Rune();
	
	@EventHandler
	public void respawnEvent(PlayerRespawnEvent event){
		Account account = new Account(event.getPlayer());
		try{
			account.getLoadedCharacter().setHealth(account.getLoadedCharacter().getMaxHealth());
		}catch(Exception e) {}
	}
	
	@EventHandler
	public void healthRegen(EntityRegainHealthEvent event){
		if(event.getRegainReason().equals(RegainReason.SATIATED)){
			event.setCancelled(true);
		}
		if(event.getRegainReason().equals(RegainReason.REGEN)){
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onRuneHit(EntityDamageByEntityEvent event){
		if(event.getEntity() instanceof EnderCrystal){
			if(event.getDamager() instanceof Player){
				event.setCancelled(true);
				event.getEntity().remove();
				Player player = (Player) event.getDamager();
				int rand = ThreadLocalRandom.current().nextInt(100);
				if(rand <= 50){
					World world = event.getEntity().getWorld();
					Location loc = event.getEntity().getLocation();
					world.createExplosion(loc.getX(), loc.getY(), loc.getZ(), 0, false, false);
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Message.sendCenteredMessage(player, ChatColor.BOLD + "Rune");
					Message.sendCenteredMessage(player, ChatColor.YELLOW + "You fail to harvest any runes.");
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					return;
				}else{
					Enchantments[] enchants = Enchantments.values();
					Enchantments ench = enchants[ThreadLocalRandom.current().nextInt(enchants.length - 1)];
					int level = ThreadLocalRandom.current().nextInt(enchantclass.getMaxLevel(ench));
					if(level == 0)
						level = 1;
					event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), rune.createRune(ench, level, ThreadLocalRandom.current().nextInt(100), ThreadLocalRandom.current().nextInt(100)));
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Message.sendCenteredMessage(player, ChatColor.BOLD + "Rune");
					Message.sendCenteredMessage(player, ChatColor.YELLOW + "You successefuly harvest some runes.");
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					return;
				}
			}
		}
	}
	
	@EventHandler
	public void runeCreation(PlayerInteractEntityEvent event){
		event.setCancelled(true);
		if(event.getRightClicked() instanceof EnderCrystal){
			
			if(event.getHand().equals(EquipmentSlot.OFF_HAND))
				return;
			
			event.getRightClicked().remove();
			Player player = event.getPlayer();
			int rand = ThreadLocalRandom.current().nextInt(100);
			if(rand <= 50){
				World world = event.getRightClicked().getWorld();
				Location loc = event.getRightClicked().getLocation();
				world.createExplosion(loc.getX(), loc.getY(), loc.getZ(), 2, false, false);
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				Message.sendCenteredMessage(player, ChatColor.BOLD + "Rune");
				Message.sendCenteredMessage(player, ChatColor.YELLOW + "You fail to harvest any runes.");
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				return;
			}else{
				Enchantments[] enchants = Enchantments.values();
				Enchantments ench = enchants[ThreadLocalRandom.current().nextInt(enchants.length - 1)];
				int level = ThreadLocalRandom.current().nextInt(enchantclass.getMaxLevel(ench) + 1);
				if(level == 0)
					level = 1;
				event.getRightClicked().getWorld().dropItem(event.getRightClicked().getLocation(), rune.createRune(ench, level, ThreadLocalRandom.current().nextInt(100), ThreadLocalRandom.current().nextInt(100)));
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				Message.sendCenteredMessage(player, ChatColor.BOLD + "Rune");
				Message.sendCenteredMessage(player, ChatColor.YELLOW + "You successefuly harvest some runes.");
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				return;
			}
		}
		return;
	}
	
	@EventHandler
	public void deathEvent(EntityDeathEvent event){
		if(ThreadLocalRandom.current().nextInt(100) <= 10){
			event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.ENDER_CRYSTAL);
		}
		event.setDroppedExp(0);
		if(event.getEntity() instanceof Creature){
			event.getDrops().add(createMoney(ThreadLocalRandom.current().nextInt(12)));
			return;
		}
		
		if(event.getEntity() instanceof Player){
			
		}
		
		return;
	}
	
	@EventHandler
	public void pickupItemEvent(PlayerPickupItemEvent event){
		if(event.getItem().getItemStack().getType().equals(Material.EMERALD)){
			if(event.getItem().getItemStack().getItemMeta().hasDisplayName()){
				if(event.getItem().getItemStack().getItemMeta().getDisplayName().contains("$")){
					String name = event.getItem().getItemStack().getItemMeta().getDisplayName();
					name = name.replace("$", "");
					new Account(event.getPlayer()).getLoadedCharacter().deposit(Integer.valueOf(name));
					event.getPlayer().sendMessage(ChatColor.YELLOW+"You have picked up $"+ Integer.valueOf(name)+" Dollars!");
		            event.setCancelled(true);
		            event.getItem().remove();
				}
			}
		}
	}
	
	
	public ItemStack createMoney(int amount){
		ItemStack money = new ItemStack(Material.EMERALD, 1);
		ItemMeta moneymeta = money.getItemMeta();
		moneymeta.setDisplayName(amount+"$");
		money.setItemMeta(moneymeta);
		return money;
	}
}
