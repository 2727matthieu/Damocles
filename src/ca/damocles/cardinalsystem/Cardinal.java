package ca.damocles.cardinalsystem;

import java.lang.reflect.Field;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import com.connorlinfoot.actionbarapi.ActionBarAPI;
import ca.damocles.accountsystem.Character;
import ca.damocles.accountsystem.Account;
import ca.damocles.accountsystem.AccountStatus;
import ca.damocles.accountsystem.EventCanceller;
import ca.damocles.accountsystem.PlayerSetup;
import ca.damocles.accountsystem.Tasks;
import ca.damocles.anvilsystem.AnvilHandler;
import ca.damocles.arrowsystem.ArrowHandler;
import ca.damocles.castsystem.CastingManager;
import ca.damocles.commandsystem.Arrow;
import ca.damocles.commandsystem.Balance;
import ca.damocles.commandsystem.Debug;
import ca.damocles.commandsystem.Guild;
import ca.damocles.commandsystem.Heal;
import ca.damocles.commandsystem.Help;
import ca.damocles.commandsystem.Logout;
import ca.damocles.commandsystem.Money;
import ca.damocles.commandsystem.Party;
import ca.damocles.commandsystem.Potion;
import ca.damocles.commandsystem.Spell;
import ca.damocles.commandsystem.Stats;
import ca.damocles.commandsystem.Trade;
import ca.damocles.damagesystem.Damage;
import ca.damocles.enchantsystem.BatVision;
import ca.damocles.enchantsystem.BlankEnchant;
import ca.damocles.enchantsystem.BloodThirst;
import ca.damocles.enchantsystem.Eyepatch;
import ca.damocles.enchantsystem.Jump;
import ca.damocles.enchantsystem.Rune;
import ca.damocles.enchantsystem.Speed;
import ca.damocles.enchantsystem.Volley;
import ca.damocles.itemsystem.ItemHandler;
import ca.damocles.itemsystem.Soul;
import ca.damocles.menusystem.ClanMenu;
import ca.damocles.menusystem.PlayerMenu;
import ca.damocles.menusystem.SelectionMenu;
import ca.damocles.potionsystem.PotionHandler;
import ca.damocles.potionsystem.SplashPotionHandler;
import ca.damocles.professionssystem.Farming;
import ca.damocles.professionssystem.Fishing;
import ca.damocles.professionssystem.Mining;
import ca.damocles.professionssystem.Smelting;
import ca.damocles.soulsystem.SoulSystem;
import ca.damocles.spellsystem.SpellBookHandler;
import ca.damocles.storagesystem.EquipHandler;
import net.blitzcube.mlapi.MultiLineAPI;

public class Cardinal extends JavaPlugin{
	
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
							Character character = account.getLoadedCharacter();
							String name = "";
							if(character.getTargetEntity(30) == null){
								name = ChatColor.GRAY+"No Target";
							}else{
								name = task.getName(character.getTargetEntity(30));
							}
							
							ActionBarAPI.sendActionBar(player, ChatColor.DARK_RED+"❤"+ChatColor.RED+""+Math.round(character.getHealth())+"/"+Math.round(character.getMaxHealth())+
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
			new BukkitRunnable(){
				public void run(){
					for(Player player : Bukkit.getOnlinePlayers()){
						Account account = new Account(player);
						try{
							if(account.getStatus().equals(AccountStatus.LOGGEDIN)){
								MultiLineAPI.getName(player).setText(account.getLoadedCharacter().getUsername());
							}else{
								MultiLineAPI.getName(player).setText(player.getName());
							}
						}catch(IllegalArgumentException e){
							MultiLineAPI.enable(player);
							return;
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
		Bukkit.getPluginManager().registerEvents(new EquipHandler(), this);
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
		Bukkit.getPluginManager().registerEvents(new Damage(), this);
		Bukkit.getPluginManager().registerEvents(new SoulSystem(), this);
		Bukkit.getPluginManager().registerEvents(new SelectionMenu(), this);
		Bukkit.getPluginManager().registerEvents(new AnvilHandler(), this);
		Bukkit.getPluginManager().registerEvents(new Soul(), this);
		Bukkit.getPluginManager().registerEvents(new ArrowHandler(), this);
		Bukkit.getPluginManager().registerEvents(new PotionHandler(), this);
		Bukkit.getPluginManager().registerEvents(new SplashPotionHandler(), this);
		Bukkit.getPluginManager().registerEvents(new Volley(), this);
		Bukkit.getPluginManager().registerEvents(new SpellBookHandler(), this);
		Bukkit.getPluginManager().registerEvents(new CastingManager(), this);
	}
	
	public void registerCommands(){
		this.getCommand("spell").setExecutor(new Spell(this));
		this.getCommand("debuging").setExecutor(new Debug(this));
		this.getCommand("potion").setExecutor(new Potion(this));
		this.getCommand("arrow").setExecutor(new Arrow(this));
		this.getCommand("balance").setExecutor(new Balance(this));
		this.getCommand("cardinal").setExecutor(new ca.damocles.commandsystem.Cardinal(this));
		this.getCommand("clan").setExecutor(new ca.damocles.commandsystem.Clan(this));
		this.getCommand("guild").setExecutor(new Guild(this));
		this.getCommand("heal").setExecutor(new Heal(this));
		this.getCommand("logout").setExecutor(new Logout(this));
		this.getCommand("money").setExecutor(new Money(this));
		this.getCommand("party").setExecutor(new Party(this));
		this.getCommand("rank").setExecutor(new ca.damocles.commandsystem.Rank(this));
		this.getCommand("rune").setExecutor(new ca.damocles.commandsystem.Rune(this));
		this.getCommand("stats").setExecutor(new Stats(this));
		this.getCommand("trade").setExecutor(new Trade(this));
		this.getCommand("help").setExecutor(new Help(this));
		this.getCommand("cast").setExecutor(new ca.damocles.commandsystem.Cast(this));
	}
	
	public void registerEnchants(){
		new Eyepatch().checkForEyepatchEnchant();
		new BatVision().checkForBatEnchant();
		new Speed().checkForSpeedEnchant();
		new Jump().checkForJumpEnchant();
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
		registerGlow();
		task.runTasks();
		updateNameTag();
		updateActionBar();
	}
	
	public void onDisable(){
		plugin = null;
		task.disableRegen();
	}
	
}
