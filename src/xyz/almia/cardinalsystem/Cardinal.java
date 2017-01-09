package xyz.almia.cardinalsystem;

import java.lang.reflect.Field;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import mkremins.fanciful.FancyMessage;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import net.minecraft.server.v1_11_R1.NBTTagInt;
import xyz.almia.abilities.DarkMagic;
import xyz.almia.abilities.Teleport;
import xyz.almia.accountsystem.Account;
import xyz.almia.accountsystem.EventCanceller;
import xyz.almia.accountsystem.PlayerSetup;
import xyz.almia.accountsystem.Rank;
import xyz.almia.accountsystem.Tasks;
import xyz.almia.chatsystem.ChatSystem;
import xyz.almia.clansystem.Clan;
import xyz.almia.clansystem.Clans;
import xyz.almia.damagesystem.DamageSystem;
import xyz.almia.enchantlistener.BatVision;
import xyz.almia.enchantlistener.BloodThirst;
import xyz.almia.enchantlistener.Eyepatch;
import xyz.almia.enchantlistener.Jump;
import xyz.almia.enchantlistener.Speed;
import xyz.almia.enchantsystem.BlankEnchant;
import xyz.almia.enchantsystem.Enchant;
import xyz.almia.enchantsystem.Enchantments;
import xyz.almia.enchantsystem.Rune;
import xyz.almia.itemsystem.CardinalDrops;
import xyz.almia.itemsystem.ItemHandler;
import xyz.almia.itemsystem.Items;
import xyz.almia.itemsystem.Soul;
import xyz.almia.menu.ClanMenu;
import xyz.almia.menu.PlayerMenu;
import xyz.almia.menu.SelectionMenu;
import xyz.almia.potionsystem.Potion;
import xyz.almia.potionsystem.PotionTypes;
import xyz.almia.professionssystem.Farming;
import xyz.almia.professionssystem.Fishing;
import xyz.almia.professionssystem.Mining;
import xyz.almia.professionssystem.Smelting;
import xyz.almia.soulsystem.SoulSystem;
import xyz.almia.utils.Message;

public class Cardinal extends JavaPlugin implements Listener{
	
	public BlankEnchant ench = new BlankEnchant(69);
	public static Plugin plugin;
	private PlayerSetup playersetup = new PlayerSetup();
	private xyz.almia.enchantsystem.Enchantment enchantclass = new xyz.almia.enchantsystem.Enchantment();
	private Rune rune = new Rune();
	
	public static Plugin getPlugin() {
		return plugin;
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
		
		String main = "Kings";
		getConfig().addDefault(main, null);

		String white = "Kings.white";
		getConfig().addDefault(white, null);
		String whiteking = "Kings.white.king";
		getConfig().addDefault(whiteking, "unknown");
		String whiteofficer = "Kings.white.officer";
		getConfig().addDefault(whiteofficer, "unkown");

		String gold = "Kings.gold";
		getConfig().addDefault(gold, null);
		String goldking = "Kings.gold.king";
		getConfig().addDefault(goldking, "unknown");
		String goldofficer = "Kings.gold.officer";
		getConfig().addDefault(goldofficer, "unkown");

		String blue = "Kings.blue";
		getConfig().addDefault(blue, null);
		String blueking = "Kings.blue.king";
		getConfig().addDefault(blueking, "unknown");
		String blueofficer = "Kings.blue.officer";
		getConfig().addDefault(blueofficer, "unknown");

		String red = "Kings.red";
		getConfig().addDefault(red, null);
		String redking = "Kings.red.king";
		getConfig().addDefault(redking, "unknown");
		String redofficer = "Kings.red.officer";
		getConfig().addDefault(redofficer, "unknown");

		String black = "Kings.black";
		getConfig().addDefault(black, null);
		String blackking = "Kings.black.king";
		getConfig().addDefault(blackking, "unknown");
		String blackofficer = "Kings.black.officer";
		getConfig().addDefault(blackofficer, "unkown");

		String green = "Kings.green";
		getConfig().addDefault(green, null);
		String greenking = "Kings.green.king";
		getConfig().addDefault(greenking, "unknown");
		String greenofficer = "Kings.green.officer";
		getConfig().addDefault(greenofficer, "unknown");

		String colorless = "Kings.colorless";
		getConfig().addDefault(colorless, null);
		String colorlessking = "Kings.colorless.king";
		getConfig().addDefault(colorlessking, "unknown");
		String colorlessofficer = "Kings.colorless.officer";
		getConfig().addDefault(colorlessofficer, "unknown");


		getConfig().addDefault("Kings.white.clansmen", new ArrayList<String>());
		getConfig().addDefault("Kings.gold.clansmen", new ArrayList<String>());
		getConfig().addDefault("Kings.blue.clansmen", new ArrayList<String>());
		getConfig().addDefault("Kings.red.clansmen", new ArrayList<String>());
		getConfig().addDefault("Kings.black.clansmen", new ArrayList<String>());
		getConfig().addDefault("Kings.green.clansmen", new ArrayList<String>());
		getConfig().addDefault("Kings.colorless.clansmen", new ArrayList<String>());
		getConfig().addDefault("Kings.exiled.clansmen", new ArrayList<String>());
		
		getConfig().addDefault("Kings.white.rejected", new ArrayList<String>());
		getConfig().addDefault("Kings.gold.rejected", new ArrayList<String>());
		getConfig().addDefault("Kings.blue.rejected", new ArrayList<String>());
		getConfig().addDefault("Kings.red.rejected", new ArrayList<String>());
		getConfig().addDefault("Kings.black.rejected", new ArrayList<String>());
		getConfig().addDefault("Kings.green.rejected", new ArrayList<String>());
		getConfig().addDefault("Kings.colorless.rejected", new ArrayList<String>());
		
		getConfig().addDefault("Kings.white.proposed", "unknown");
		getConfig().addDefault("Kings.black.proposed", "unknown");
		getConfig().addDefault("Kings.blue.proposed", "unknown");
		getConfig().addDefault("Kings.red.proposed", "unknown");
		getConfig().addDefault("Kings.gold.proposed", "unknown");
		getConfig().addDefault("Kings.green.proposed", "unknown");
		getConfig().addDefault("Kings.colorless.proposed", "unknown");
		
		getConfig().addDefault("players", new ArrayList<String>());
		
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
		Bukkit.getPluginManager().registerEvents(new Enchant(), this);
		Bukkit.getPluginManager().registerEvents(new Fishing(), this);
		Bukkit.getPluginManager().registerEvents(new Farming(), this);
		Bukkit.getPluginManager().registerEvents(new DamageSystem(), this);
		Bukkit.getPluginManager().registerEvents(new SoulSystem(), this);
		Bukkit.getPluginManager().registerEvents(new ChatSystem(), this); 
		Bukkit.getPluginManager().registerEvents(new Teleport(), this);
		Bukkit.getPluginManager().registerEvents(new DarkMagic(), this);
		Bukkit.getPluginManager().registerEvents(new SelectionMenu(), this);
		Bukkit.getPluginManager().registerEvents(new AnvilHandler(), this);
		Bukkit.getPluginManager().registerEvents(new Soul(), this);
	}
	
	public void registerEnchants(){
		new Eyepatch().checkForEyepatchEnchant();
		new BatVision().checkForBatEnchant();
		new Speed().checkForSpeedEnchant();
		new Jump().checkForJumpEnchant();
		new DarkMagic().darkMagic();
	}

	public void onEnable(){
		plugin = this;
		registerConfig();
		registerEvents();
		registerEnchants();
		new Tasks().runTasks();
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
	
	public void onDisable(){
		plugin = null;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(!(sender instanceof Player)){
			sender.sendMessage("You must be a player to use commands!");
		}
		Player player = (Player)sender;
		Account account = new Account(player);
		xyz.almia.accountsystem.Character character = account.getLoadedCharacter();
		Clans whatClan = playersetup.getClan(character);
		Clan clan = new Clan(whatClan);
		
		if(cmd.getName().equalsIgnoreCase("logout")){
			Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			Message.sendCenteredMessage(player, ChatColor.BOLD + "Account");
			Message.sendCenteredMessage(player, ChatColor.YELLOW+"Logging out in 5 seconds do not move.");
			Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			Location loc = player.getLocation();
			int oldx = loc.getBlockX();
			int oldy = loc.getBlockY();
			int oldz = loc.getBlockZ();
			for(int o = 0; o < 15; o++){
				player.sendMessage("");
			}
			new BukkitRunnable(){
				int i = 5;
				
				public void run() {
					
					int newx = player.getLocation().getBlockX();
					int newy = player.getLocation().getBlockY();
					int newz = player.getLocation().getBlockZ();
					
					if( (oldx != newx) || (oldy != newy) || (oldz != newz) ){
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(player, ChatColor.BOLD + "Account");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+"Moved cancelling logout.");
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						this.cancel();
					}
					
					if(!(i == 0)){
						Message.sendCenteredMessage(player, ChatColor.RED + "" + i +"...");
						i--;
					}else{
						Message.sendCenteredMessage(player, ChatColor.RED + "" + i +"...");
						account.logout();
						for(int o = 0; o < 15; o++){
							player.sendMessage("");
						}
						this.cancel();
					}
				}
			}.runTaskTimer(getPlugin(), 0, 20);
			return true;
		}
		
		if(cmd.getName().equalsIgnoreCase("help")){
			if(character.getRank().equals(Rank.GAMEMASTER)){
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				Message.sendCenteredMessage(player, ChatColor.BOLD + "Help");
				Message.sendCenteredMessage(player, " ");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Rank "+ChatColor.GOLD+ ": For all the Rank Commands.");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Money "+ChatColor.GOLD+ ": Admin money command.");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Cardinal "+ChatColor.GOLD+ ": generates custom weapons.");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Rune "+ChatColor.GOLD+ ": For all the Enchant Commands.");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Heal <Character>"+ChatColor.GOLD+ ": Heals specified player.");
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			}else{
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				Message.sendCenteredMessage(player, ChatColor.BOLD + "Help");
				Message.sendCenteredMessage(player, " ");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"/logout "+ChatColor.GOLD+ ": Logs out of your character.");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"/balance "+ChatColor.GOLD+ ": Displays users bank balance.");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Clan "+ChatColor.GOLD+ ": For all the Clan Commands.");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Stats "+ChatColor.GOLD+ ": For your players Stats.");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Guild "+ChatColor.GOLD+ ": Not implemented.");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Party "+ChatColor.GOLD+ ": Not implemented.");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Trade "+ChatColor.GOLD+ ": Not implemented.");
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				return true;
			}
		}
		
		if(cmd.getName().equals("balance")){
			if(args.length == 0){
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				Message.sendCenteredMessage(player, ChatColor.BOLD + "Balance");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"Your balance is "+ChatColor.GREEN+"$"+character.getBankBalance());
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				return true;
			}else{
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				Message.sendCenteredMessage(player, ChatColor.BOLD + "Balance");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"Error: Proper usage is /balance");
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				return true;
			}
		}
		
		if(cmd.getName().equalsIgnoreCase("money")){
			
			if(!(character.getRank().equals(Rank.GAMEMASTER))){
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				Message.sendCenteredMessage(player, ChatColor.BOLD + "Help");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"Must be a GAMEMASTER to use /money");
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				return true;
			}
			
			if(args.length == 0){
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				Message.sendCenteredMessage(player, ChatColor.BOLD + "Money Commands");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"/money set <Player> <Amount>");
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				return true;
			}else if(args.length == 3){
				if(args[0].equalsIgnoreCase("set")){
					try{
						xyz.almia.accountsystem.Character target = playersetup.getCharacterFromUsername(args[1]);
						try{
							int amount = Integer.valueOf(args[2]);
							
							target.setBankBalance(amount);
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							Message.sendCenteredMessage(player, ChatColor.BOLD + "Money");
							Message.sendCenteredMessage(player, ChatColor.YELLOW+ target.getUsername() + "'s balance has been set to " + args[2]);
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							Message.sendCenteredMessage(target.getPlayer(), ChatColor.GREEN+"----------------------------------------------------");
							Message.sendCenteredMessage(target.getPlayer(), ChatColor.BOLD + "Money");
							Message.sendCenteredMessage(target.getPlayer(), ChatColor.YELLOW+ target.getUsername() + "'s balance has been set to " + args[2]);
							Message.sendCenteredMessage(target.getPlayer(), ChatColor.GREEN+"----------------------------------------------------");
							return true;
							
						}catch(Exception e){
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							Message.sendCenteredMessage(player, ChatColor.BOLD + "Money");
							Message.sendCenteredMessage(player, ChatColor.YELLOW+ args[2] + " is not a number.");
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							return true;
						}
					}catch(Exception e) {
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(player, ChatColor.BOLD + "Money");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+ args[1] + " does not exist or is offline.");
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						return true;
					}
				}else{
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Message.sendCenteredMessage(player, ChatColor.BOLD + "Help");
					Message.sendCenteredMessage(player, ChatColor.YELLOW+"Improper command usage, use /money set <Player> <Amount>");
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					return true;
				}
			}else{
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				Message.sendCenteredMessage(player, ChatColor.BOLD + "Help");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"Improper command usage, use /money set <Player> <Amount>");
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				return true;
			}
		}
		
		if(cmd.getName().equalsIgnoreCase("add")){
			ItemStack item = new ItemStack(Material.POTION);
			net.minecraft.server.v1_11_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
	        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
	        compound.set("CustomPotionColor", new NBTTagInt(16446520));
			nmsStack.setTag(compound);
			player.getInventory().addItem(CraftItemStack.asBukkitCopy(nmsStack));
			
			ItemStack arrow = new ItemStack(Material.TIPPED_ARROW, 64);
			net.minecraft.server.v1_11_R1.ItemStack nmsStackArrow = CraftItemStack.asNMSCopy(arrow);
	        NBTTagCompound arrowcompound = (nmsStackArrow.hasTag()) ? nmsStackArrow.getTag() : new NBTTagCompound();
	        arrowcompound.set("CustomPotionColor", new NBTTagInt(16446520));
			nmsStackArrow.setTag(arrowcompound);
			player.getInventory().addItem(CraftItemStack.asBukkitCopy(nmsStackArrow));
			
			/*ItemStack spawner = new ItemStack(Material.MOB_SPAWNER, 1);
			net.minecraft.server.v1_11_R1.ItemStack nmsSpawner = CraftItemStack.asNMSCopy(spawner);
			NBTTagCompound spawnercompound = (nmsSpawner.hasTag()) ? nmsSpawner.getTag() : new NBTTagCompound();
			NBTTagCompound BlockEntityTag = new NBTTagCompound();
			NBTTagList SpawnPotentials = new NBTTagList();
			NBTTagCompound zombie = new NBTTagCompound();
			zombie.set("Type", new NBTTagString("zombie"));
			zombie.set("Weight", new NBTTagInt(1));
			SpawnPotentials.add(zombie);
			BlockEntityTag.set("SpawnPotentials", SpawnPotentials);
			spawnercompound.set("BlockEntityTag", BlockEntityTag);
			nmsSpawner.setTag(spawnercompound);
			player.getInventory().addItem(CraftItemStack.asBukkitCopy(nmsSpawner));*/
			
			 
			
			return true;
		}
		
		if(cmd.getName().equalsIgnoreCase("heal")){
			if(character.getRank().equals(Rank.GAMEMASTER)){
				if(args.length == 0){
					character.setHealth(character.getMaxHealth());
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Message.sendCenteredMessage(player, ChatColor.BOLD + "Help");
					Message.sendCenteredMessage(player, ChatColor.YELLOW+"You have been healed!");
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					return true;
				}else if(args.length == 1){
					try{
						xyz.almia.accountsystem.Character target = playersetup.getCharacterFromUsername(args[0]);
						target.setHealth(target.getMaxHealth());
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(player, ChatColor.BOLD + "Help");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+"You have healed " + target.getUsername() + " !");
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(target.getPlayer(), ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(target.getPlayer(), ChatColor.BOLD + "Help");
						Message.sendCenteredMessage(target.getPlayer(), ChatColor.YELLOW+"You have been healed!");
						Message.sendCenteredMessage(target.getPlayer(), ChatColor.GREEN+"----------------------------------------------------");
						return true;
					}catch(Exception e){
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(player, ChatColor.BOLD + "Help");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+"Player " + args[0] + " does not exist or is offline.");
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						return true;
					}
				}else{
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Message.sendCenteredMessage(player, ChatColor.BOLD + "Help");
					Message.sendCenteredMessage(player, ChatColor.YELLOW+"Improper command usage, use /heal <Player>");
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					return true;
				}
			}else{
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				Message.sendCenteredMessage(player, ChatColor.BOLD + "Rank");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"Only GameMasters can use this command!");
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				return true;
			}
		}
		
		if(cmd.getName().equalsIgnoreCase("cardinal")){

			
			Inventory inv = Bukkit.createInventory(null, 18, "items");
			CardinalDrops drop = new CardinalDrops();
			
			for(Items item : Items.values()){
				inv.addItem(drop.getItem(item));
			}
			inv.addItem(new Potion(new ItemStack(Material.POTION)).create(PotionTypes.HEALING, 1));
			inv.addItem(new Potion(new ItemStack(Material.POTION)).create(PotionTypes.MANA, 1));
			
			player.openInventory(inv);
			
			return true;
		}
		
		if(cmd.getName().equalsIgnoreCase("rank")){
			
			if(player.isOp() || (new Account(player).getLoadedCharacter().getRank().equals(xyz.almia.accountsystem.Rank.GAMEMASTER))){
			
			if(args.length == 0){
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				Message.sendCenteredMessage(player, ChatColor.BOLD + "Rank Help");
				Message.sendCenteredMessage(player, " ");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Rank set <Player> <Rank>");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Rank get <Player>");
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				return true;
			}
			if(args[0].equalsIgnoreCase("help")){
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				Message.sendCenteredMessage(player, ChatColor.BOLD + "Rank Help");
				Message.sendCenteredMessage(player, " ");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Rank set <Player> <Rank>");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Rank get <Player>");
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				return true;
			}
			if(args[0].equalsIgnoreCase("set")){
				if(args.length == 3){
					try{
						xyz.almia.accountsystem.Character chara = playersetup.getCharacterFromUsername(args[1]);
						try{
							xyz.almia.accountsystem.Rank rank = xyz.almia.accountsystem.Rank.valueOf(args[2].toUpperCase());
							chara.setRank(rank);
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							Message.sendCenteredMessage(player, ChatColor.BOLD + "Rank");
							Message.sendCenteredMessage(player, ChatColor.YELLOW+"You have set "+args[1]+" to a "+args[2]+"!");
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							return true;
						}catch(Exception e){
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							Message.sendCenteredMessage(player, ChatColor.BOLD + "Rank Help");
							Message.sendCenteredMessage(player, ChatColor.YELLOW+"Invalid Arguments : Unknown Rank");
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							return true;
						}
					}catch(Exception e){
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(player, ChatColor.BOLD + "Rank Help");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+"Invalid Arguments : Unknown Player");
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						return true;
					}
				}else{
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Message.sendCenteredMessage(player, ChatColor.BOLD + "Rank Help");
					Message.sendCenteredMessage(player, ChatColor.YELLOW+"Invalid Arguments : /Rank set <Player> <Rank>");
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					return true;
				}
			}
			if(args[0].equalsIgnoreCase("get")){
				if(args.length == 2){
					try{
						xyz.almia.accountsystem.Character target = playersetup.getCharacterFromUsername(args[1]);
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(player, ChatColor.BOLD + "Rank");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+args[1]+" is a "+ target.getRank()+"!");
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						return true;
					}catch(Exception e){
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(player, ChatColor.BOLD + "Rank Help");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+"Invalid Arguments : Unknown Player");
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						return true;
					}
				}else{
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Message.sendCenteredMessage(player, ChatColor.BOLD + "Rank Help");
					Message.sendCenteredMessage(player, ChatColor.YELLOW+"Invalid Arguments : /Rank get <Player>");
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					return true;
				}
			}
			Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			Message.sendCenteredMessage(player, ChatColor.BOLD + "Rank Help");
			Message.sendCenteredMessage(player, ChatColor.YELLOW+"Unknown Rank command!");
			Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			return true;
		}else{
			Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			Message.sendCenteredMessage(player, ChatColor.BOLD + "Rank Help");
			Message.sendCenteredMessage(player, ChatColor.YELLOW+"You must be a GameMaster to use the Rank command!");
			Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			return true;
		}
		}
		
		if(cmd.getName().equalsIgnoreCase("stats")){
			Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			Message.sendCenteredMessage(player, ChatColor.BOLD + character.getUsername() + " Stats");
			Message.sendCenteredMessage(player, ChatColor.YELLOW+ "Your current Level is " + ChatColor.GOLD + character.getLevel());
			Message.sendCenteredMessage(player, ChatColor.YELLOW+ "Your current xp is " + ChatColor.GOLD + character.getExp()+ ChatColor.YELLOW+ " / "+ ChatColor.GOLD + (character.getLevel() * 1028));
			Message.sendCenteredMessage(player, ChatColor.YELLOW+ "You currently belong to the "+ playersetup.getClan(character).toString().toLowerCase().substring(0, 1).toUpperCase() + playersetup.getClan(character).toString().toLowerCase().substring(1) + " clan.");
			Message.sendCenteredMessage(player, ChatColor.YELLOW+ "your position is " + ChatColor.GRAY + playersetup.getClanRank(character).toString().toLowerCase().substring(0, 1).toUpperCase() + playersetup.getClanRank(character).toString().toLowerCase().substring(1));	
			Message.sendCenteredMessage(player, " ");
			Message.sendCenteredMessage(player, ChatColor.BOLD + "No Active Bonus' !");
			Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			player.openInventory(PlayerMenu.createMenu(player));
			return true;
		}
		
		if(cmd.getName().equalsIgnoreCase("clan")){
			
		      if (args.length == 0){
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Help");
					Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Clan help");
					Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Clan choose");
					Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Clan info "+ChatColor.GOLD+"[Player]"+ChatColor.YELLOW+" or "+ChatColor.GOLD+"[Clan]");
					//Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Clan join"+ChatColor.GOLD+" [Color]");
					Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Clan promote"+ChatColor.GOLD+" [Player]");
					Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Clan leave");
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		            return true;
		      }
		      
		      if(args[0].equalsIgnoreCase("help")){
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Help");
					Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Clan help");
					Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Clan choose");
					Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Clan info "+ChatColor.GOLD+"[Player]"+ChatColor.YELLOW+" or "+ChatColor.GOLD+"[Clan]");
					//Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Clan join"+ChatColor.GOLD+" [Color]");
					Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Clan promote"+ChatColor.GOLD+" [Player]");
					Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Clan leave");
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		            return true;
		      }
		      
		      if(args[0].equalsIgnoreCase("leave")){
		    	  if(playersetup.isInClan(character)){
		    		  
		    		  if(clan.getProposed().getUsername().equalsIgnoreCase(character.getUsername()))
		    			  clan.setProposed(null);
		    		  
		    		  xyz.almia.clansystem.Rank rank = playersetup.getClanRank(character);
		    		  switch(rank){
					case CLANSMEN:
						clan.removeClansmen(character);
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+"You have left "+whatClan.toString()+" Clan!");
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    			return true;
					case KING:
						clan.setKing(null);
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+"You have left "+whatClan.toString()+" Clan!");
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    			  return true;
					case NONE:
						break;
					default:
						break;
		    		  }
		    	  }else{
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+"You are not in a Clan!");
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
	    			  return true;
		    	  }
		      }
		      
		      /*
		      if(args[0].equalsIgnoreCase("invite")){
		    	  if(args.length == 2){
			    	  Clans clan = Clan.getManager().getClan(player);
			    	  if(clan == Clans.UNCLANNED){
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
							Message.sendCenteredMessage(player, ChatColor.YELLOW+"You are not in a Clan!");
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    			  return true;
			    	  }
			    	  Player target = Bukkit.getPlayer(args[1]);
			    	  if(player == target){
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
							Message.sendCenteredMessage(player, ChatColor.YELLOW+"You cannot invite yourself to a Clan!");
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			    		  return true;
			    	  }
			    	  boolean canInvite = false;
			    	  if(Clan.getManager().getKing(clan).equalsIgnoreCase(player.getUniqueId()+""))
			    		  canInvite = true;
			    	  if(Clan.getManager().getOfficer(clan).equalsIgnoreCase(player.getUniqueId()+""))
			    		  canInvite = true;
			    	  if(canInvite){
			    		  if(target != null){
			    			  Clan.getManager().addInvitedMember(target, clan);
			    			  return true;
			    		  }else{
								Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
								Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
								Message.sendCenteredMessage(player, ChatColor.YELLOW+"The player ( "+args[1]+" ) does not exist or is offline!");
								Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			    			  return true;
			    		  }
			    	  }else{
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
							Message.sendCenteredMessage(player, ChatColor.YELLOW+"You are not a King or an Officer!");
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			    		  return true;
			    	  }
		    	  }else{
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+"Invalid usage, check /sevenkings help for correct command usage!");
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    		  return true;
		    	  }
		      }
		      */
		      
		      if(args[0].equalsIgnoreCase("choose")){
		    	  
		    	  if(character.getLevel() >= 5){
			    	  if(!(playersetup.isInClan(character))){
			    		  player.openInventory(SelectionMenu.getInstance().generateSelectionInventory());
			    		  return true;
			    	  }else{
			    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			    		  Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
			    		  Message.sendCenteredMessage(player, ChatColor.YELLOW+"ERROR: You are already in a clan!");
			    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			    		  return true;
			    	  } 
		    	  }else{
		    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    		  Message.sendCenteredMessage(player, ChatColor.BOLD + "Game Info");
		    		  Message.sendCenteredMessage(player, ChatColor.YELLOW+"You must be Level 5 to choose a clan!");
		    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    		  return true;
		    	  }
		    	  
		      }
		      
		      if(args[0].equalsIgnoreCase("accept")){
		    	  
		    	  if(whatClan.equals(Clans.UNCLANNED))
		    		  return true;
		    	  
		    	  if(whatClan.equals(Clans.EXILED))
		    		  return true;
		    	  
		    	  if(clan.getProposed().getUsername().equalsIgnoreCase(character.getUsername())){
		    		  if(clan.getKing() == null){
		    			  clan.removeClansmen(character);
		    			  clan.setProposed(null);
		    			  clan.setKing(character);
		    			  for(Player target : Bukkit.getOnlinePlayers()){
								if(whatClan.equals(Clans.COLORLESS)){
									Message.sendCenteredMessage(target, ChatColor.GREEN+"----------------------------------------------------");
									Message.sendCenteredMessage(target, ChatColor.BOLD + "Game Info");
									Message.sendCenteredMessage(target, ChatColor.YELLOW+character.getUsername()+" has become the " + ChatColor.DARK_GRAY + whatClan.toString().toLowerCase().substring(0, 1).toUpperCase() + whatClan.toString().toLowerCase().substring(1) + ChatColor.YELLOW + " King!");
									Message.sendCenteredMessage(target, ChatColor.GREEN+"----------------------------------------------------");
								}else{
									Message.sendCenteredMessage(target, ChatColor.GREEN+"----------------------------------------------------");
									Message.sendCenteredMessage(target, ChatColor.BOLD + "Game Info");
									Message.sendCenteredMessage(target, ChatColor.YELLOW+character.getUsername()+" has become the " + ChatColor.valueOf(whatClan.toString().toUpperCase()) + whatClan.toString().toLowerCase().substring(0, 1).toUpperCase() + whatClan.toString().toLowerCase().substring(1) + ChatColor.YELLOW + " King!");
									Message.sendCenteredMessage(target, ChatColor.GREEN+"----------------------------------------------------");
								}
							}
		    		  }
		    		  return true;
		    	  }
		    	  
		      }
		      
		      if(args[0].equalsIgnoreCase("reject")){
		    	  
		    	  if(whatClan.equals(Clans.UNCLANNED))
		    		  return true;
		    	  
		    	  if(whatClan.equals(Clans.EXILED))
		    		  return true;
		    	  
		    	  if(clan.getProposed().getUsername().equalsIgnoreCase(character.getUsername())){
		    		  if(clan.getKing() == null){
		    			  clan.addRejected(character);
							if(whatClan.equals(Clans.COLORLESS)){
								Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
								Message.sendCenteredMessage(player, ChatColor.BOLD + "Game Info");
								Message.sendCenteredMessage(player, ChatColor.YELLOW+"You have rejected becoming the " + ChatColor.DARK_GRAY + whatClan.toString().toLowerCase().substring(0, 1).toUpperCase() + whatClan.toString().toLowerCase().substring(1) + ChatColor.YELLOW + " King!");
								Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							}else{
								Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
								Message.sendCenteredMessage(player, ChatColor.BOLD + "Game Info");
								Message.sendCenteredMessage(player, ChatColor.YELLOW+"You have rejected becoming the " + ChatColor.valueOf(whatClan.toString().toUpperCase()) + whatClan.toString().toLowerCase().substring(0, 1).toUpperCase() + whatClan.toString().toLowerCase().substring(1) + ChatColor.YELLOW + " King!");
								Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							}
		    		  }
		    		  return true;
		    	  }
		    	  
		      }
		      
		      /*
		      if(args[0].equalsIgnoreCase("kick")){
		    	  if(args.length == 2){
			    	  if(Clan.getManager().isInClan(player)){
			    		  if(Clan.getManager().getRank(player) == Rank.KING || Clan.getManager().getRank(player) == Rank.OFFICER){
			    			  Player target = Bukkit.getPlayer(args[1]);
			    			  if(target != null){
			    				  if(Clan.getManager().getClan(target).equals(Clan.getManager().getClan(player))){
			    					  if(Clan.getManager().getRank(target) == Rank.CLANSMEN){
			    						  Clan.getManager().removeClanMember(target, Clan.getManager().getClan(player));
			    							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			    							Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
			    							Message.sendCenteredMessage(player, ChatColor.YELLOW+"INFO: You have kicked "+args[1]+" from the Clan!");
			    							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			    							Message.sendCenteredMessage(target, ChatColor.GREEN+"----------------------------------------------------");
			    							Message.sendCenteredMessage(target, ChatColor.BOLD + "Clan Info");
			    							Message.sendCenteredMessage(target, ChatColor.YELLOW+"INFO: You have been kicked out of your clan!");
			    							Message.sendCenteredMessage(target, ChatColor.GREEN+"----------------------------------------------------");
			    			    		  return true;
			    					  }else{
			    							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			    							Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
			    							Message.sendCenteredMessage(player, ChatColor.YELLOW+"ERROR: A King/Officer cannot kick a King/Officer!");
			    							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			    			    		  return true;
			    					  }
			    				  }else{
		    							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    							Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
		    							Message.sendCenteredMessage(player, ChatColor.YELLOW+"ERROR: "+ args[1]+" is not in your clan.");
		    							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			    		    		  return true;
			    				  }
			    			  }else{
	    							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
	    							Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
	    							Message.sendCenteredMessage(player, ChatColor.YELLOW+"ERROR: Player " + args[1] +" does not exist or is offline.");
	    							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					    		  return true;
			    			  }
			    		  }else{
  							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
  							Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
  							Message.sendCenteredMessage(player, ChatColor.YELLOW+"ERROR: Only the King/Officer can kick people!");
  							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				    		  return true;
			    		  }
			    	  }else{
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
							Message.sendCenteredMessage(player, ChatColor.YELLOW+"ERROR: You are not in a Clan!");
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			    		  return true;
			    	  }
		    	  }else{
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+"ERROR: Invalid usage, check /sevenkings help for correct command usage!");
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    		  return true;
		    	  }
		      }
		      */
		     
		      if(args[0].equalsIgnoreCase("join")){
		    	  if(args.length == 2){
			    	  if(!(playersetup.isInClan(character))){
			    		  try{
			    			  Clans clanchoice = Clans.valueOf(args[1].toUpperCase());
			    			  Clan theClan = new Clan(clanchoice);
			    			  theClan.addClansmen(character);
	    						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
	    						Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
	    						Message.sendCenteredMessage(player, ChatColor.YELLOW+"INFO: You have joined the "+args[1]+" Clan!");
	    						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
	    						return true;
			    		  }catch(Exception IllegalArgumentException){
  							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
  							Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
  							Message.sendCenteredMessage(player, ChatColor.YELLOW+"ERROR: "+args[1]+ " is not a valid Clan!");
  							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				    		  return true;
			    		  }
			    	  }else{
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
							Message.sendCenteredMessage(player, ChatColor.YELLOW+"ERROR: You are already in a Clan!");
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			    		  return true; 
			    	  }  
		    	  }else{
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+"ERROR: Invalid usage, check /sevenkings help for correct command usage!");
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    		  return true;
		    	  }
		      }
	
		      
		      if(args[0].equalsIgnoreCase("info")){
		    	  if(args.length == 2){
		    		  for(Clans clans : Clans.values()){
		    			  if(!clan.equals(Clans.UNCLANNED)){
		    				  if(args[1].equalsIgnoreCase(clan.toString().toLowerCase())){
		    					  player.openInventory(ClanMenu.generateClanMenu(clans));
		  						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    					  new FancyMessage("You have opened the ")
		    					  .color(ChatColor.YELLOW)
		    					  .then(clan.toString().toLowerCase())
		    					  .color(ChatColor.valueOf(clan.toString().toUpperCase()))
		    					  .then("'s Clan Menu. Click ")
		    					  .color(ChatColor.YELLOW)
		    					  .then("here")
		    					  .command("/clan info "+args[1])
		    					  .color(ChatColor.GOLD)
		    					  .style(ChatColor.BOLD)
		    					  .then(" to reopen the menu.")
		    					  .color(ChatColor.YELLOW)
		    					  .send(player);
		  						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    					  
		    				  }
		    			  }
		    		  }
		    		  //Get player menu here.
		    	  }else{
		    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    		  Message.sendCenteredMessage(player, ChatColor.YELLOW+"Invalid Arguemnts for proper usage use /Help");
		    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    		  return true;
		    	  }
		      }
			
		}
		
		if(cmd.getName().equalsIgnoreCase("rune")){
			
			if(args.length == 0){
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				Message.sendCenteredMessage(player, ChatColor.BOLD + "Rune Help");
				Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Rune list "+ChatColor.GOLD+": for a list of Runes.");
				Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Rune <Enchantment> <level> <Success Rate> <Destroy Rate>");
				Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Rune slot <Amount> "+ChatColor.GOLD+": gives a Slot Rune.");
				Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Rune protection "+ChatColor.GOLD+": gives a Protection Rune.");
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("list")){
				if(args.length == 1){
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Message.sendCenteredMessage(player, ChatColor.BOLD + "Runes");
					
					String enchants = "";
					for(Enchantments enchant : Enchantments.values()){
						enchants = enchants + ChatColor.GOLD+ enchantclass.getName(enchant) + ChatColor.YELLOW+ ", ";
					}
					Message.sendCenteredMessage(player, enchants);
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					return true;
				}else{
		    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    		  Message.sendCenteredMessage(player, ChatColor.YELLOW+"Invalid Arguemnts for proper usage use /Rune");
		    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    		  return true;
				}
			}else if(args[0].equalsIgnoreCase("slot")){
				if(args.length == 2){
					try{
						int slots = Integer.valueOf(args[1]);
						player.getInventory().addItem(rune.createSlotRune(slots));
						return true;
					}catch(Exception e){
			    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			    		  Message.sendCenteredMessage(player, ChatColor.YELLOW+args[1]+" is not a whole number or your inventory is full!");
			    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			    		  return true;
					}
				}else{
		    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    		  Message.sendCenteredMessage(player, ChatColor.YELLOW+"Invalid Arguemnts for proper usage use /Rune");
		    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    		  return true;
				}
			}else if(args[0].equalsIgnoreCase("protection")){
				if(args.length == 1){
					try{
						player.getInventory().addItem(rune.createProtectionRune());
						return true;
					}catch(Exception e){
			    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			    		  Message.sendCenteredMessage(player, ChatColor.YELLOW+"Your inventory is full.");
			    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			    		  return true;
					}
				}else{
		    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    		  Message.sendCenteredMessage(player, ChatColor.YELLOW+"Invalid Arguemnts for proper usage use /Rune");
		    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    		  return true;
				}
			}
			
			
			for(Enchantments enchant : Enchantments.values()){
				if(args[0].equalsIgnoreCase(enchant.toString())){
					try{
						int level = Integer.valueOf(args[1]);
						int success = Integer.valueOf(args[2]);
						int destroy = Integer.valueOf(args[3]);
						if(level > enchantclass.getMaxLevel(enchant))
						if(level > enchantclass.getMaxLevel(enchant)){
							player.sendMessage(ChatColor.YELLOW + "Error: The max level for " + enchantclass.getName(enchant) + " is " + enchantclass.getMaxLevel(enchant));
							return true;
						}
						player.getInventory().addItem(rune.createRune(enchant, level, success, destroy));
						return true;
					}catch(NumberFormatException e) {
				  		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				  		  Message.sendCenteredMessage(player, ChatColor.YELLOW+args[1]+ ", "+args[2]+", "+args[3]+" One or allof these are not whole numbers!");
				  		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				  		  return true;
					}
				}
			}
			
  		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
  		  Message.sendCenteredMessage(player, ChatColor.YELLOW+args[0]+" is not a rune, or a valid sub-command of /rune");
  		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
  		  return true;

		}
				
    	return true;
    }
	
}
