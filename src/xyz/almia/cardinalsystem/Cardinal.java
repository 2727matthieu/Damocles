package xyz.almia.cardinalsystem;

import java.lang.reflect.Field;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import com.connorlinfoot.actionbarapi.ActionBarAPI;
import net.blitzcube.score.secondlineapi.manager.SecondLineManager;
import xyz.almia.abilities.DarkMagic;
import xyz.almia.abilities.Teleport;
import xyz.almia.accountsystem.Account;
import xyz.almia.accountsystem.AccountStatus;
import xyz.almia.accountsystem.EventCanceller;
import xyz.almia.accountsystem.PlayerSetup;
import xyz.almia.accountsystem.Tasks;
import xyz.almia.anvilsystem.AnvilHandler;
import xyz.almia.arrowsystem.ArrowHandler;
import xyz.almia.commandsystem.Arrow;
import xyz.almia.commandsystem.Balance;
import xyz.almia.commandsystem.Guild;
import xyz.almia.commandsystem.Heal;
import xyz.almia.commandsystem.Help;
import xyz.almia.commandsystem.Logout;
import xyz.almia.commandsystem.Money;
import xyz.almia.commandsystem.Party;
import xyz.almia.commandsystem.Potion;
import xyz.almia.commandsystem.Stats;
import xyz.almia.commandsystem.Trade;
import xyz.almia.damagesystem.DamageSystem;
import xyz.almia.enchantsystem.BatVision;
import xyz.almia.enchantsystem.BlankEnchant;
import xyz.almia.enchantsystem.BloodThirst;
import xyz.almia.enchantsystem.Eyepatch;
import xyz.almia.enchantsystem.Jump;
import xyz.almia.enchantsystem.Rune;
import xyz.almia.enchantsystem.Speed;
import xyz.almia.itemsystem.ItemHandler;
import xyz.almia.itemsystem.Soul;
import xyz.almia.menu.ClanMenu;
import xyz.almia.menu.PlayerMenu;
import xyz.almia.menu.SelectionMenu;
import xyz.almia.potionsystem.PotionHandler;
import xyz.almia.potionsystem.SplashPotionHandler;
import xyz.almia.professionssystem.Farming;
import xyz.almia.professionssystem.Fishing;
import xyz.almia.professionssystem.Mining;
import xyz.almia.professionssystem.Smelting;
import xyz.almia.soulsystem.SoulSystem;

public class Cardinal extends JavaPlugin implements Listener{
	
	public BlankEnchant ench = new BlankEnchant(69);
	public static Plugin plugin;
	public Tasks task;
	
	public static Plugin getPlugin() {
		return plugin;
	}
	
	public void updateActionBar(){
		
		try{
			
			@SuppressWarnings("unused")
			Class<ActionBarAPI> api = ActionBarAPI.class;
			
			new BukkitRunnable(){
				public void run(){
					for(Player player : Bukkit.getOnlinePlayers()){
						Account account = new Account(player);
						if(account.getStatus().equals(AccountStatus.LOGGEDIN)){
							
							String name = "";
							if(task.getTarget(player, 30) == null){
								name = ChatColor.GRAY+"No Target";
							}else{
								name = task.getName(task.getTarget(player, 30));
							}
							
							xyz.almia.accountsystem.Character character = account.getLoadedCharacter();
							ActionBarAPI.sendActionBar(player, ChatColor.DARK_RED+"❤"+ChatColor.RED+""+character.getHealth()+"/"+character.getMaxHealth()+
									"   "+ name +"  "+ChatColor.BLUE+"✦"+ChatColor.AQUA+""+character.getMana()+"/"+character.getMaxMana());
						}
					}
				}
			}.runTaskTimer(getPlugin(), 0, 1);
		}catch(NoClassDefFoundError e){
			System.out.println(String.format("[%s] - No ActionBarAPI dependency found!", getDescription().getName()));
		}
	}
	
	public void updateNameTag(){
		try{
			
			SecondLineManager.getInstance(getPlugin());
			
			new BukkitRunnable(){
				public void run(){
					for(Player player : Bukkit.getOnlinePlayers()){
						Account account = new Account(player);
							if(account.getStatus().equals(AccountStatus.LOGGEDIN)){
								SecondLineManager.getInstance(getPlugin()).setName(player, account.getLoadedCharacter().getUsername());
							}else{
								SecondLineManager.getInstance(getPlugin()).setName(player, player.getName());
							}
					}
				}
				
			}.runTaskTimer(plugin, 0, 1);
		}catch(NoClassDefFoundError e) {
			System.out.println(String.format("[%s] - No SecondLineAPI dependency found!", getDescription().getName()));
		}
	}
	
	public void registerConfig(){
		getConfig().addDefault("Cardinal", null);
		getConfig().addDefault("Cardinal.professions.mining.coal", 5);
		getConfig().addDefault("Cardinal.professions.mining.iron", 15);
		getConfig().addDefault("Cardinal.professions.mining.gold", 35);
		getConfig().addDefault("Cardinal.professions.mining.emerald", 40);
		getConfig().addDefault("Cardinal.professions.mining.diamond", 25);
		getConfig().addDefault("Cardinal.professions.mining.stone", 1);
		getConfig().addDefault("Cardinal.professions.forging.coal", 5);
		getConfig().addDefault("Cardinal.professions.forging.iron", 15);
		getConfig().addDefault("Cardinal.professions.forging.gold", 35);
		getConfig().addDefault("Cardinal.professions.forging.emerald", 40);
		getConfig().addDefault("Cardinal.professions.forging.diamond", 25);
		getConfig().addDefault("Cardinal.professions.cooking.bacon", 200);
		getConfig().addDefault("Cardinal.professions.cooking.steak", 200);
		getConfig().addDefault("Cardinal.professions.cooking.rabbit", 100);
		getConfig().addDefault("Cardinal.professions.cooking.trout", 150);
		getConfig().addDefault("Cardinal.professions.cooking.mutton", 100);
		getConfig().addDefault("Cardinal.professions.cooking.potato", 50);
		
		getConfig().addDefault("Cardinal.professions.enchanting.sword", 200);
		getConfig().addDefault("Cardinal.professions.enchanting.tool", 125);
		getConfig().addDefault("Cardinal.professions.enchanting.rod", 50);
		getConfig().addDefault("Cardinal.professions.enchanting.bow", 150);
		getConfig().addDefault("Cardinal.professions.enchanting.helmet", 200);
		getConfig().addDefault("Cardinal.professions.enchanting.chestplate", 300);
		getConfig().addDefault("Cardinal.professions.enchanting.boots", 175);
		getConfig().addDefault("Cardinal.professions.enchanting.leggings", 300);
		
		getConfig().addDefault("Cardinal.professions.farming.sugarcane", 50);
		getConfig().addDefault("Cardinal.professions.farming.wheat", 50);
		getConfig().addDefault("Cardinal.professions.farming.carrot", 25);
		getConfig().addDefault("Cardinal.professions.farming.potato", 25);
		getConfig().addDefault("Cardinal.professions.farming.pumpkin", 50);
		getConfig().addDefault("Cardinal.professions.farming.melon", 75);
		
		getConfig().addDefault("Cardinal.enchant.ZOMBIE", 120);
		getConfig().addDefault("Cardinal.enchant.CAVE_SPIDER", 56);
		getConfig().addDefault("Cardinal.enchant.MUSHROOM_COW", 30);
		getConfig().addDefault("Cardinal.enchant.ENDERMAN", 140);
		getConfig().addDefault("Cardinal.enchant.BLAZE", 80);
		getConfig().addDefault("Cardinal.enchant.CREEPER", 110);
		getConfig().addDefault("Cardinal.enchant.ENDERMITE", 30);
		getConfig().addDefault("Cardinal.enchant.GHAST", 60);
		getConfig().addDefault("Cardinal.enchant.GIANT", 140);
		getConfig().addDefault("Cardinal.enchant.GUARDIAN", 72);
		getConfig().addDefault("Cardinal.enchant.IRON_GOLEM", 140);
		getConfig().addDefault("Cardinal.enchant.MAGMA_CUBE", 74);
		getConfig().addDefault("Cardinal.enchant.PIG_ZOMBIE", 110);
		getConfig().addDefault("Cardinal.enchant.SILVERFISH", 48);
		getConfig().addDefault("Cardinal.enchant.SKELETON", 120);
		getConfig().addDefault("Cardinal.enchant.SLIME", 45);
		getConfig().addDefault("Cardinal.enchant.SPIDER", 100);
		getConfig().addDefault("Cardinal.enchant.SQUID", 60);
		getConfig().addDefault("Cardinal.enchant.WITCH", 85);
		getConfig().addDefault("Cardinal.enchant.PIG", 20);
		getConfig().addDefault("Cardinal.enchant.COW", 20);
		getConfig().addDefault("Cardinal.enchant.CHICKEN", 17);
		getConfig().addDefault("Cardinal.enchant.BAT", 17);
		getConfig().addDefault("Cardinal.enchant.HORSE", 43);
		getConfig().addDefault("Cardinal.enchant.PLAYER", 70);
		getConfig().addDefault("Cardinal.enchant.SHEEP", 20);
		getConfig().addDefault("Cardinal.enchant.RABBIT", 17);
		
		getConfig().addDefault("Cardinal.Anvil.rate", 3);
		
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	public void registerEvents(){
		Bukkit.getPluginManager().registerEvents(this, this);
		Bukkit.getPluginManager().registerEvents(new EventCanceller(), this);
		Bukkit.getPluginManager().registerEvents(new ItemHandler(), this);
		Bukkit.getPluginManager().registerEvents(new ClanMenu(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerSetup(), this);
		Bukkit.getPluginManager().registerEvents(new Rune(), this);
		Bukkit.getPluginManager().registerEvents(new BloodThirst(), this);
		Bukkit.getPluginManager().registerEvents(new Smelting(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerMenu(), this);
		Bukkit.getPluginManager().registerEvents(new Mining(), this);
		Bukkit.getPluginManager().registerEvents(new Fishing(), this);
		Bukkit.getPluginManager().registerEvents(new Farming(), this);
		Bukkit.getPluginManager().registerEvents(new DamageSystem(), this);
		Bukkit.getPluginManager().registerEvents(new SoulSystem(), this);
		Bukkit.getPluginManager().registerEvents(new Teleport(), this);
		Bukkit.getPluginManager().registerEvents(new DarkMagic(), this);
		Bukkit.getPluginManager().registerEvents(new SelectionMenu(), this);
		Bukkit.getPluginManager().registerEvents(new AnvilHandler(), this);
		Bukkit.getPluginManager().registerEvents(new Soul(), this);
		Bukkit.getPluginManager().registerEvents(new ArrowHandler(), this);
		Bukkit.getPluginManager().registerEvents(new PotionHandler(), this);
		Bukkit.getPluginManager().registerEvents(new SplashPotionHandler(), this);
	}
	
	public void registerCommands(){
		this.getCommand("potion").setExecutor(new Potion(this));
		this.getCommand("arrow").setExecutor(new Arrow(this));
		this.getCommand("balance").setExecutor(new Balance(this));
		this.getCommand("cardinal").setExecutor(new xyz.almia.commandsystem.Cardinal(this));
		this.getCommand("clan").setExecutor(new xyz.almia.commandsystem.Clan(this));
		this.getCommand("guild").setExecutor(new Guild(this));
		this.getCommand("heal").setExecutor(new Heal(this));
		this.getCommand("logout").setExecutor(new Logout(this));
		this.getCommand("money").setExecutor(new Money(this));
		this.getCommand("party").setExecutor(new Party(this));
		this.getCommand("rank").setExecutor(new xyz.almia.commandsystem.Rank(this));
		this.getCommand("rune").setExecutor(new xyz.almia.commandsystem.Rune(this));
		this.getCommand("stats").setExecutor(new Stats(this));
		this.getCommand("trade").setExecutor(new Trade(this));
		this.getCommand("help").setExecutor(new Help(this));
	}
	
	public void registerEnchants(){
		new Eyepatch().checkForEyepatchEnchant();
		new BatVision().checkForBatEnchant();
		new Speed().checkForSpeedEnchant();
		new Jump().checkForJumpEnchant();
		new DarkMagic().darkMagic();
	}

	public void registerGlow(){
		try {
			Field f = Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		try{
			Enchantment.registerEnchantment(ench);
		}catch(Exception e){
			
		}
	}
	
	public void onEnable(){
		plugin = this;
		task = new Tasks(plugin);
		registerCommands();
		registerConfig();
		registerEvents();
		registerEnchants();
		task.runTasks();
		registerGlow();
		updateNameTag();
		updateActionBar();
	}
	
	public void onDisable(){
		plugin = null;
	}
	
}
