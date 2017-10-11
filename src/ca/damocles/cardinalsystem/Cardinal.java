package ca.damocles.cardinalsystem;

import java.io.IOException;
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
import ca.damocles.cardinalsystem.Options.OptionType;
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
import ca.damocles.spellsystem.SpellBookHandler;
import ca.damocles.storagesystem.EquipHandler;
import ca.damocles.utils.ASCIIArtGenerator;
import ca.damocles.utils.ASCIIArtGenerator.ASCIIArtFont;
import ca.damocles.utils.Data;
import net.blitzcube.mlapi.MultiLineAPI;

public class Cardinal extends JavaPlugin{
	
	public BlankEnchant ench = new BlankEnchant(69);
	public static Plugin plugin;
	public Tasks task;
	public Options options;
	
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
								name = Data.getData().getName(character.getTargetEntity(30));
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
	
	public boolean startUpTasks(){
		task.getSlotChecker().runTaskTimer(plugin, 0, 1);
		if(options.getOptionEnabled(OptionType.CLANSYSTEM))
			task.getKingPromoter().runTaskTimer(plugin, 0, 1);
		task.getPlayerData().runTaskTimer(plugin, 0, 1);
		task.getPlayerStatusChecker().runTaskTimer(plugin, 0, 20);
		if(options.getOptionEnabled(OptionType.SOULSYSTEM))
			task.getSoulUpdater().runTaskTimer(plugin, 0, 1);
		return true;
	}
	
	public void onEnable(){
		plugin = this;
		task = new Tasks(plugin);
		registerCommands();
		registerConfig();
		registerEvents();
		registerEnchants();
		registerGlow();
		updateNameTag();
		updateActionBar();
		
		options = new Options();
		
		
		new BukkitRunnable(){
			public void run(){
				ASCIIArtGenerator artGen = new ASCIIArtGenerator();
				
				try {
					new ProcessBuilder("cmd", "/c", "cls", "clear").inheritIO().start().waitFor();
				} catch (InterruptedException | IOException e1) { e1.printStackTrace(); }
				
				System.out.println("----------------------------------------------------------------------------------------");
				try {
					artGen.printTextArt("Damocles", ASCIIArtGenerator.ART_SIZE_MEDIUM, ASCIIArtFont.ART_FONT_MONO,"@");
				} catch (Exception e) { e.printStackTrace(); }
				System.out.println("----------------------------------------------------------------------------------------");
				
				System.out.println("For first time setup please read the README.txt file for detailed usage.");
				System.out.println(" ");
				System.out.println("Currently using Spigot Version: " + Bukkit.getBukkitVersion());
				System.out.println(" ");
				System.out.println("-Currently Enabled Plugins-");
				
				for(Plugin plugin : Bukkit.getPluginManager().getPlugins()){
					System.out.println("  - " + plugin.getDescription().getName() + " : " + plugin.getDescription().getVersion());
				}
				
				System.out.println("----------------------------------------------------------------------------------------");
				
				this.cancel();
			}
		}.runTaskLaterAsynchronously(this, 20);
		
		if(!startUpTasks())
			Bukkit.shutdown();
	}
	
	public void onDisable(){
		task.disableRegen();
		plugin = null;
	}
	
}
