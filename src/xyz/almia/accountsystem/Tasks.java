package xyz.almia.accountsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.BlockIterator;
import xyz.almia.configclasses.ConfigManager;
import xyz.almia.enchantsystem.Enchantments;
import xyz.almia.itemsystem.Armor;
import xyz.almia.itemsystem.ItemHandler;
import xyz.almia.itemsystem.ItemTypes;
import xyz.almia.itemsystem.Soul;
import xyz.almia.itemsystem.Weapon;
import xyz.almia.menu.AccountMenu;
import xyz.almia.selectionsystem.Selection;

public class Tasks{
	
	private Plugin plugin;
	
	public Tasks(Plugin plugin) {
		this.plugin = plugin;
	}
	
	public int getDefaultArmor(Material material){
		if(material.equals(Material.DIAMOND_HELMET)){
			return 3;
		}
		if(material.equals(Material.DIAMOND_CHESTPLATE)){
			return 8;
		}
		if(material.equals(Material.DIAMOND_LEGGINGS)){
			return 6;
		}
		if(material.equals(Material.DIAMOND_BOOTS)){
			return 3;
		}
		
		if(material.equals(Material.LEATHER_HELMET)){
			return 1;
		}
		if(material.equals(Material.LEATHER_CHESTPLATE)){
			return 3;
		}
		if(material.equals(Material.LEATHER_LEGGINGS)){
			return 2;
		}
		if(material.equals(Material.LEATHER_BOOTS)){
			return 1;
		}
		
		if(material.equals(Material.IRON_HELMET)){
			return 2;
		}
		if(material.equals(Material.IRON_CHESTPLATE)){
			return 6;
		}
		if(material.equals(Material.IRON_LEGGINGS)){
			return 5;
		}
		if(material.equals(Material.IRON_BOOTS)){
			return 2;
		}
		
		if(material.equals(Material.CHAINMAIL_HELMET)){
			return 2;
		}
		if(material.equals(Material.CHAINMAIL_CHESTPLATE)){
			return 5;
		}
		if(material.equals(Material.CHAINMAIL_LEGGINGS)){
			return 3;
		}
		if(material.equals(Material.CHAINMAIL_BOOTS)){
			return 1;
		}
		
		if(material.equals(Material.GOLD_HELMET)){
			return 2;
		}
		if(material.equals(Material.GOLD_CHESTPLATE)){
			return 5;
		}
		if(material.equals(Material.GOLD_LEGGINGS)){
			return 3;
		}
		if(material.equals(Material.GOLD_BOOTS)){
			return 1;
		}
		
		return 1;
	}
	
	public int getDefaultDamage(Material material){
		if(material.equals(Material.DIAMOND_SWORD)){
			return 7;
		}
		if(material.equals(Material.IRON_SWORD)){
			return 6;
		}
		if(material.equals(Material.WOOD_SWORD)){
			return 4;
		}
		if(material.equals(Material.GOLD_SWORD)){
			return 4;
		}
		if(material.equals(Material.STONE_SWORD)){
			return 5;
		}
		if(material.equals(Material.DIAMOND_AXE)){
			return 9;
		}
		if(material.equals(Material.IRON_AXE)){
			return 9;
		}
		if(material.equals(Material.WOOD_AXE)){
			return 7;
		}
		if(material.equals(Material.GOLD_AXE)){
			return 7;
		}
		if(material.equals(Material.STONE_AXE)){
			return 9;
		}
		if(material.equals(Material.DIAMOND_SPADE)){
			return 5;
		}
		if(material.equals(Material.IRON_SPADE)){
			return 4;
		}
		if(material.equals(Material.WOOD_SPADE)){
			return 2;
		}
		if(material.equals(Material.GOLD_SPADE)){
			return 2;
		}
		if(material.equals(Material.STONE_SPADE)){
			return 3;
		}
		if(material.equals(Material.DIAMOND_PICKAXE)){
			return 5;
		}
		if(material.equals(Material.IRON_PICKAXE)){
			return 4;
		}
		if(material.equals(Material.WOOD_PICKAXE)){
			return 2;
		}
		if(material.equals(Material.GOLD_PICKAXE)){
			return 2;
		}
		if(material.equals(Material.STONE_PICKAXE)){
			return 3;
		}
		return 1;
	}

	public int getDefaultWeight(ItemTypes type){
		switch(type){
		case ALL:
			break;
		case ARMOR:
			return 10;
		case BOW:
			return 4;
		case NONE:
			break;
		case POTION:
			return 0;
		case SHIELD:
			return 5;
		case TOOL:
			return 6;
		case WEAPON:
			return 7;
		default:
			break;
		}
		return 0;
	}
	
	public void addSlotsToItem(){
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {

			@Override
			public void run() {
				for(Player p : Bukkit.getOnlinePlayers()){
					for(ItemStack item : p.getInventory().getContents()){
						if(item != null){
							
							if(ItemHandler.getType(item).equals(ItemTypes.ARMOR)){
								Armor detailItem = new Armor(item);
								if(!(detailItem.isItemSet())){
									int slots = ThreadLocalRandom.current().nextInt(3);
									int reforges = ThreadLocalRandom.current().nextInt(2);
									int upgrades = ThreadLocalRandom.current().nextInt(4);
									int weight = getDefaultWeight(ItemTypes.ARMOR);
									int armor = getDefaultArmor(item.getType());
									int maxdurability = ThreadLocalRandom.current().nextInt(143) + 100;
									int durability = ThreadLocalRandom.current().nextInt(41) + 50;
									detailItem.setup(new HashMap<Enchantments, Integer>(), slots, 0, 0, 0, 0, armor, reforges, weight, upgrades, false, durability, maxdurability);
									for(int i = 0; i < 36; i++){
										if(p.getInventory().getItem(i) != null){
											if(p.getInventory().getItem(i).equals(item)){
												p.getInventory().setItem(i, detailItem.getItemStack());
											}
										}
									}
									p.updateInventory();
								}
							}
							if(ItemHandler.getType(item).equals(ItemTypes.WEAPON)){
								Weapon detailItem = new Weapon(item);
								if(!(detailItem.isItemSet())){
									int slots = ThreadLocalRandom.current().nextInt(3);
									int reforges = ThreadLocalRandom.current().nextInt(2);
									int upgrades = ThreadLocalRandom.current().nextInt(4);
									int maxdurability = ThreadLocalRandom.current().nextInt(143) + 100;
									int durability = ThreadLocalRandom.current().nextInt(41) + 50;
									int weight = getDefaultWeight(ItemTypes.WEAPON);
									int damage = getDefaultDamage(item.getType());
									detailItem.setup(new HashMap<Enchantments, Integer>(), slots, 0, 0, 0, 0, damage, reforges, weight, upgrades, false, durability, maxdurability);
									for(int i = 0; i < 36; i++){
										if(p.getInventory().getItem(i) != null){
											if(p.getInventory().getItem(i).equals(item)){
												p.getInventory().setItem(i, detailItem.getItemStack());
											}
										}
									}
									p.updateInventory();
								}
							}
							
						}
					}
				}
			}
        	
        }, 0, 1);
	}
	
	public void startCharacterRegen(Player player){
		Account account = new Account(player);
		if(account.getStatus().equals(AccountStatus.LOGGEDIN)){
			Character character = account.getLoadedCharacter();
			int regenrate = 6; //(int)(10 - (0.2 * account.getStat(Stats.HITPOINTS)));
			if(regenrate < 1){
				regenrate = 1;
			}
			
			new BukkitRunnable(){
				@Override
				public void run() {
					if(character.getRegening()){
						character.setHealth((int) (character.getHealth() + 1));//(int) (account.getHealth() + (1 + (account.getStat(Stats.HITPOINTS) * .2) ) ) );	
					}
				}
			}.runTaskTimer(plugin, 0, (int)(regenrate  * 20));
		}
	}

	public void runTasks(){
		
		new Selection().promoteToKing();
		addSlotsToItem();
		
		new BukkitRunnable(){
			@Override
			public void run() {
				for(Player player : Bukkit.getOnlinePlayers()){
					Account account = new Account(player);
					if(account.getStatus().equals(AccountStatus.LOGGINGIN)){
						Inventory inv = AccountMenu.getAccountMenu(player);
						if(!(player.isDead())){
							player.openInventory(inv);
						}
					}		
				}	
			}
		}.runTaskTimer(plugin, 0, 20);
		
		new BukkitRunnable(){
			@Override
			public void run() {
				for(Player player : Bukkit.getOnlinePlayers()){
					
					Account account = new Account(player);
					
					if(account.getStatus().equals(AccountStatus.LOGGEDIN)){
						
						Character character = account.getLoadedCharacter();
						
						character.getPlayer().getInventory().setItem(8, Soul.soulApple(character));
						
						if(character.getSouls() < 0)
							character.setSouls(0);
						
						if(character.getSouls() == 0){
							character.remove();
						}
						
						if(!(character.getRegening())){
							startCharacterRegen(player);
							character.setRegening(true);
						}
						
						
						player.setDisplayName(character.getUsername());
						player.setCustomName(character.getUsername());
						player.setPlayerListName(character.getUsername());
						player.setCustomNameVisible(true);
						
						double damage = (1 + (((double)(character.getStat(Stat.STRENGTH)) - 1) * (2 * (1/4) ) ) );
						character.setPhysicalDamage((int)damage);
						double mdamage = (1 + (((double)(character.getStat(Stat.INTELLIGENCE)) - 1) * (2 * (1/4) ) ) );
						character.setMagicalDamage((int)mdamage);
						
	            		player.setLevel(character.getLevel());
	            		double rate = ((double)character.getExp()) / ((double) (character.getLevel() * 1028));
	            		double exp = rate * (double)player.getExpToLevel();
	            		exp = exp/10;
	            		player.setExp((float)exp);
						
						character.regenMana();
						character.displayMana();
						
						player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(character.getMaxHealth());;
						if(!(player.isDead())){
							if(character.getHealth() < 0){
								character.setHealth(0);
							}
							player.setHealth(character.getHealth());
						}
						character.applySpeed();
						
						
					}
				}
			}
		}.runTaskTimer(plugin, 0, 1);
		

	}
	
	public String getName(Entity entity){
		switch(entity.getType()){
		case AREA_EFFECT_CLOUD:
			return ChatColor.GRAY+"AOE Cloud";
		case ARMOR_STAND:
			return ChatColor.GRAY+"Armor Stand";
		case ARROW:
			return ChatColor.GRAY+"Arrow";
		case BAT:
			return ChatColor.DARK_GRAY+"Bat";
		case BLAZE:
			return ChatColor.RED+"Blaze";
		case BOAT:
			return ChatColor.DARK_GRAY+"Boat";
		case CAVE_SPIDER:
			return ChatColor.LIGHT_PURPLE+"Cave Spider";
		case CHICKEN:
			return ChatColor.YELLOW+"Chicken";
		case COMPLEX_PART:
			return ChatColor.GRAY+"No Target";
		case COW:
			return ChatColor.DARK_GRAY+"Cow";
		case CREEPER:
			return ChatColor.GREEN+"Creeper";
		case DRAGON_FIREBALL:
			return ChatColor.RED+"Dragon Fireball";
		case DROPPED_ITEM:
			Item item = (Item)entity;
			return ChatColor.GRAY+item.getItemStack().getType().name();
		case EGG:
			return ChatColor.YELLOW+"Egg";
		case ENDERMAN:
			return ChatColor.DARK_PURPLE+"Enderman";
		case ENDERMITE:
			return ChatColor.DARK_PURPLE+"Endermite";
		case ENDER_CRYSTAL:
			return ChatColor.DARK_PURPLE+"Ender Crystal";
		case ENDER_DRAGON:
			return ChatColor.DARK_PURPLE+"Ender Dragon";
		case ENDER_PEARL:
			return ChatColor.DARK_PURPLE+"EnderPearl";
		case ENDER_SIGNAL:
			return ChatColor.DARK_PURPLE+"Ender Signal";
		case EXPERIENCE_ORB:
			return ChatColor.GRAY+"EXP Orb";
		case FALLING_BLOCK:
			return ChatColor.GRAY+"Falling Block";
		case FIREBALL:
			return ChatColor.RED+"Fireball";
		case FISHING_HOOK:
			return ChatColor.GRAY+"Fishing Hook";
		case GHAST:
			return ChatColor.GRAY+"Ghast";
		case GIANT:
			return ChatColor.GREEN+"Giant";
		case GUARDIAN:
			return ChatColor.AQUA+"Guardian";
		case HORSE:
			return ChatColor.YELLOW+"Horse";
		case IRON_GOLEM:
			return ChatColor.GRAY+"Iron Golem";
		case LIGHTNING:
			return ChatColor.YELLOW+"Lightning";
		case LINGERING_POTION:
			return ChatColor.GRAY+"Lingering Potion";
		case MAGMA_CUBE:
			return ChatColor.RED+"Magma Cube";
		case MINECART:
			return ChatColor.GRAY+"Minecart";
		case MINECART_CHEST:
			return ChatColor.GRAY+"Minecart";
		case MINECART_COMMAND:
			return ChatColor.GRAY+"Minecart";
		case MINECART_FURNACE:
			return ChatColor.GRAY+"Minecart";
		case MINECART_HOPPER:
			return ChatColor.GRAY+"Minecart";
		case MINECART_MOB_SPAWNER:
			return ChatColor.GRAY+"Minecart";
		case MINECART_TNT:
			return ChatColor.GRAY+"Minecart";
		case MUSHROOM_COW:
			return ChatColor.RED+"Mushroom Cow";
		case OCELOT:
			return ChatColor.YELLOW+"OCelot";
		case PIG:
			return ChatColor.RED+"Pig";
		case PIG_ZOMBIE:
			return ChatColor.GOLD+"Pig Zombie";
		case PLAYER:
			Player player = (Player) entity;
			if(new Account(player).getStatus().equals(AccountStatus.LOGGEDIN)){
				return ChatColor.GRAY+new Account(player).getLoadedCharacter().getUsername();
			}else{
				return player.getName();
			}
		case POLAR_BEAR:
			return ChatColor.GRAY+"Polar Bear";
		case PRIMED_TNT:
			return ChatColor.DARK_RED+"Lit TNT";
		case RABBIT:
			return ChatColor.GRAY+"Rabbit";
		case SHEEP:
			return ChatColor.GRAY+"Sheep";
		case SHULKER:
			return ChatColor.LIGHT_PURPLE+"Shulker";
		case SHULKER_BULLET:
			return ChatColor.LIGHT_PURPLE+"Shulker Bullet";
		case SILVERFISH:
			return ChatColor.GRAY+"Silverfish";
		case SKELETON:
			return ChatColor.GRAY+"Skeleton";
		case SLIME:
			return ChatColor.GREEN+"Slime";
		case SNOWBALL:
			return ChatColor.GRAY+"Snowball";
		case SNOWMAN:
			return ChatColor.GRAY+"Snowman";
		case SPECTRAL_ARROW:
			return ChatColor.GRAY+"Spectral Arrow";
		case SPIDER:
			return ChatColor.DARK_GRAY+"Spider";
		case SPLASH_POTION:
			return ChatColor.GRAY+"Splash Potion";
		case SQUID:
			return ChatColor.DARK_GRAY+"Squid";
		case THROWN_EXP_BOTTLE:
			return ChatColor.GREEN+"Thrown EXP Bottle";
		case TIPPED_ARROW:
			return ChatColor.GRAY+"Tipped Arrow";
		case UNKNOWN:
			return ChatColor.GRAY+"Unknown";
		case VILLAGER:
			return ChatColor.DARK_GRAY+"Villager";
		case WITCH:
			return ChatColor.LIGHT_PURPLE+"Witch";
		case WITHER:
			return ChatColor.DARK_GRAY+"Wither";
		case WITHER_SKULL:
			return ChatColor.DARK_GRAY+"Wither Skull";
		case WOLF:
			return ChatColor.GRAY+"Wolf";
		case ZOMBIE:
			return ChatColor.GREEN+"Zombie";
		default:
			return ChatColor.GRAY+"Unknown";
		
		}
	}
	
	public Entity getTarget(Player player, int range) {
		ConfigManager.load("blacklist.yml", "");
		List<String> blacklist = ConfigManager.get("blacklist.yml").getStringList("list");
		List<Material> materials = new ArrayList<Material>();
		for(String s : blacklist){
			materials.add(Material.valueOf(s));
		}
		BlockIterator bItr = new BlockIterator(player, range);
		Block block;
		Location loc;
		int bx, by, bz;
		double ex, ey, ez;
		// loop through player's line of sight
		while (bItr.hasNext()) {
			block = bItr.next();
			if(materials.contains(block.getType()))
				bItr.next();
			bx = block.getX();
			by = block.getY();
			bz = block.getZ();
			// check for entities near this block in the line of sight
			for (Entity e : player.getNearbyEntities(range, range, range)) {
				loc = e.getLocation();
				ex = loc.getX();
				ey = loc.getY();
				ez = loc.getZ();
				if ((bx - .75 <= ex && ex <= bx + 1.75) && (bz - .75 <= ez && ez <= bz + 1.75)
						&& (by - 1 <= ey && ey <= by + 2.5)) {
					// entity is close enough, set target and stop
					return e;
				}
			}
		}
		return null;
	}
	
}
