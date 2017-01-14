package xyz.almia.accountsystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Bukkit;
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
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import mkremins.fanciful.FancyMessage;
import xyz.almia.arrowsystem.CustomArrow;
import xyz.almia.cardinalsystem.Cardinal;
import xyz.almia.enchantsystem.Enchantments;
import xyz.almia.enchantsystem.Rune;
import xyz.almia.utils.Color;
import xyz.almia.utils.Message;

public class EventCanceller implements Listener{
	
	Plugin plugin = Cardinal.getPlugin();
	xyz.almia.enchantsystem.Enchantment enchantclass = new xyz.almia.enchantsystem.Enchantment();
	Rune rune = new Rune();
	
	@EventHandler
	public void onRightClickEnchantmentTable(PlayerInteractEvent event){
		Player player = event.getPlayer();
		if(event.getClickedBlock() != null){
			if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
				if(event.getClickedBlock().getType().equals(Material.ENCHANTMENT_TABLE)){
					event.setCancelled(true);
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Message.sendCenteredMessage(player, ChatColor.BOLD + "Alter");
					Message.sendCenteredMessage(player, ChatColor.YELLOW+"An omnious feeling begins to creep over you.");
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				}
			}
		}
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event){
		
		event.getPlayer().setDisplayName(new Account(event.getPlayer()).getLoadedCharacter().getTitle()+new Account(event.getPlayer()).getLoadedCharacter().getUsername());
		
		if(event.getMessage().contains("@hand")){
			if(event.getPlayer().getInventory().getItemInMainHand() != null){
				event.setCancelled(true);
				String message = event.getMessage();
				message = message.replace("@hand", "~");
				List<String> parts = new ArrayList<String>();
				parts = Arrays.asList(message.split("~"));
				if(parts.size() == 1){
					for(Player player : Bukkit.getOnlinePlayers()){
						new FancyMessage(ChatColor.WHITE+"<"+event.getPlayer().getDisplayName()+ChatColor.WHITE+"> ")
						.then(parts.get(0))
						.color(ChatColor.WHITE)
						.then("SHOW")
						.color(ChatColor.YELLOW)
						.style(ChatColor.BOLD)
						.style(ChatColor.UNDERLINE)
						.itemTooltip(event.getPlayer().getInventory().getItemInMainHand())
						.send(player);
					}
				}else if(parts.size() == 2){
					for(Player player : Bukkit.getOnlinePlayers()){
						new FancyMessage(ChatColor.WHITE+"<"+event.getPlayer().getDisplayName()+ChatColor.WHITE+"> ")
						.then(parts.get(0))
						.color(ChatColor.WHITE)
						.then("SHOW")
						.color(ChatColor.YELLOW)
						.style(ChatColor.BOLD)
						.style(ChatColor.UNDERLINE)
						.itemTooltip(event.getPlayer().getInventory().getItemInMainHand())
						.then(parts.get(1))
						.color(ChatColor.WHITE)
						.send(player);
					}
				}else{
					return;
				}
			}
		}
	}
	
	@EventHandler
	public void respawnEvent(PlayerRespawnEvent event){
		Account account = new Account(event.getPlayer());
		try{
			account.getLoadedCharacter().setHealth(account.getLoadedCharacter().getMaxHealth());
		}catch(Exception e) {}
	}
	
	public static Inventory getArrowColors(){
		Inventory inventory = Bukkit.createInventory(null, 36, "Colors");
		inventory.setItem(0, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(102,0,0).getInt()).getSimple());
		inventory.setItem(1, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(102,0,51).getInt()).getSimple());
		inventory.setItem(2, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(102,0,102).getInt()).getSimple());
		inventory.setItem(3, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(0,0,102).getInt()).getSimple());
		inventory.setItem(4, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(0,102,102).getInt()).getSimple());
		inventory.setItem(5, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(0,102,0).getInt()).getSimple());
		inventory.setItem(6, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(102,102,0).getInt()).getSimple());
		inventory.setItem(7, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(102,51,0).getInt()).getSimple());
		inventory.setItem(8, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(0,0,0).getInt()).getSimple());
		
		inventory.setItem(9, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(255,0,0).getInt()).getSimple());
		inventory.setItem(10, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(255,0,128).getInt()).getSimple());
		inventory.setItem(11, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(255,0,255).getInt()).getSimple());
		inventory.setItem(12, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(0,0,255).getInt()).getSimple());
		inventory.setItem(13, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(0,255,255).getInt()).getSimple());
		inventory.setItem(14, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(0,255,0).getInt()).getSimple());
		inventory.setItem(15, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(255,255,0).getInt()).getSimple());
		inventory.setItem(16, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(255,127,0).getInt()).getSimple());
		inventory.setItem(17, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(128,128,128).getInt()).getSimple());
		
		inventory.setItem(18, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(255,102,102).getInt()).getSimple());
		inventory.setItem(19, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(255,102,178).getInt()).getSimple());
		inventory.setItem(20, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(255,102,255).getInt()).getSimple());
		inventory.setItem(21, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(102,102,255).getInt()).getSimple());
		inventory.setItem(22, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(102,255,255).getInt()).getSimple());
		inventory.setItem(23, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(102,255,102).getInt()).getSimple());
		inventory.setItem(24, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(255,255,102).getInt()).getSimple());
		inventory.setItem(25, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(255,178,102).getInt()).getSimple());
		inventory.setItem(26, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(192,192,192).getInt()).getSimple());
		
		inventory.setItem(27, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(255,204,229).getInt()).getSimple());
		inventory.setItem(28, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(255,204,204).getInt()).getSimple());
		inventory.setItem(29, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(255,204,255).getInt()).getSimple());
		inventory.setItem(30, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(204,204,255).getInt()).getSimple());
		inventory.setItem(31, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(204,255,255).getInt()).getSimple());
		inventory.setItem(32, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(204,255,204).getInt()).getSimple());
		inventory.setItem(33, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(255,204,204).getInt()).getSimple());
		inventory.setItem(34, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(255,229,204).getInt()).getSimple());
		inventory.setItem(35, new CustomArrow(ChatColor.GRAY+"Arrow", 1, new Color(255,255,255).getInt()).getSimple());
		
		return inventory;
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
	public void onEntityExplode(EntityExplodeEvent event){
		if(event.getEntity() instanceof EnderCrystal){
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
		if(ThreadLocalRandom.current().nextInt(100) <= 100){
			event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.ENDER_CRYSTAL);
		}
		event.setDroppedExp(0);
		if(event.getEntity() instanceof Creature){
			event.getDrops().add(createMoney(ThreadLocalRandom.current().nextInt(12)));
			return;
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
