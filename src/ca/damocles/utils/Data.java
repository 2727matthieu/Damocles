package ca.damocles.utils;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import ca.damocles.accountsystem.Account;
import ca.damocles.accountsystem.AccountStatus;
import ca.damocles.itemsystem.ItemType.ItemTypes;

public class Data {
	
	static Data data = new Data();
	
	public static Data getData(){
		return data;
	}
	
	public int getDefaultWeight(ItemTypes type){
		switch(type){
		case ARMOR:
			return 10;
		case BOW:
			return 4;
		case NONE:
			break;
		case POTION:
			break;
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
	
	@SuppressWarnings("deprecation")
	public String getItemName(Material material){
		String s = material.toString();
		s = s.toLowerCase();
		s = s.replace("_", " ");
		s = StringUtils.capitaliseAllWords(s);
		return s;
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
		case DONKEY:
			return ChatColor.GRAY+"Donkey";
		case ELDER_GUARDIAN:
			return ChatColor.GRAY+"Elder Guardian";
		case EVOKER:
			return ChatColor.GRAY+"Evoker";
		case EVOKER_FANGS:
			return ChatColor.GRAY+"Evoker Fangs";
		case FIREWORK:
			return ChatColor.GRAY+"Fireworks";
		case HUSK:
			return ChatColor.GRAY+"Husk";
		case ILLUSIONER:
			return ChatColor.GRAY+"Illusioner";
		case ITEM_FRAME:
			return ChatColor.GRAY+"A Framed Item";
		case LEASH_HITCH:
			return ChatColor.GRAY+"Leash";
		case LLAMA:
			return ChatColor.GRAY+"Llama";
		case LLAMA_SPIT:
			return ChatColor.GRAY+"Llama Spit";
		case MULE:
			return ChatColor.GRAY+"Mule";
		case PAINTING:
			return ChatColor.GRAY+"Painting";
		case PARROT:
			return ChatColor.GRAY+"Parrot";
		case SKELETON_HORSE:
			return ChatColor.GRAY+"Skeleton Horse";
		case SMALL_FIREBALL:
			return ChatColor.GRAY+"Fireball";
		case STRAY:
			return ChatColor.GRAY+"Stray";
		case VEX:
			return ChatColor.GRAY+"Vex";
		case VINDICATOR:
			return ChatColor.GRAY+"Vindicator";
		case WEATHER:
			return ChatColor.GRAY+"Weather";
		case WITHER_SKELETON:
			return ChatColor.GRAY+"Wither Skeleton";
		case ZOMBIE_HORSE:
			return ChatColor.GRAY+"Zombie Horse";
		case ZOMBIE_VILLAGER:
			return ChatColor.GRAY+"Zombie Villager";
		default:
			return ChatColor.GRAY+"Unknown";
		}
	}
	
	/*
	 * @author DeadlyScone
	 * 
	 * Returns an encoded string that appears invisible to the client.
	 * 
	 */
	
 	public static String decodeItemData(String str){
 		try {
 			String[] hiddenData = str.split("(?:\\w{2,}|\\d[0-9A-Fa-f])+");
 			String returnData = "";
 			if(hiddenData == null){
 				hiddenData = str.split("§");
 				for(int i = 0; i < hiddenData.length; i++){
                 returnData += hiddenData[i];
 			}
 			return returnData;
 			}else{
 				String[] d = hiddenData[hiddenData.length-1].split("§");
 				for(int i = 1; i < d.length; i++){
                 returnData += d[i];
             }
             return returnData;
         }
 
 		}catch (Exception e){
 			e.printStackTrace();
 			return null;
 		}	
 	}
	
	public static String encodeItemData(String str){
		 try{
		 	String hiddenData = "";
		 	for(char c : str.toCharArray()){
		 		hiddenData += "§" + c;
		 	}
			return hiddenData;
		 }catch (Exception e){
		 	e.printStackTrace();
		 	return null;
		 }
	}

}
