package xyz.almia.menu;

import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Wool;
import xyz.almia.accountsystem.Account;
import xyz.almia.clansystem.Clan;
import xyz.almia.clansystem.Clans;

public class MenuItem {
	
	public static ItemStack createItem(String name, Material material){
		ItemStack item = new ItemStack(material);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(name);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack createClanPane(Clans clan){
		Clan clanProfile = new Clan(clan);
		if(clan.equals(Clans.COLORLESS)){
			ItemStack item = new ItemStack(Material.THIN_GLASS, 1);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(ChatColor.DARK_GRAY+"Colorless Clan");
			im.setLore(Arrays.asList(
					ChatColor.GRAY+ "King: "+clanProfile.getKingName(),
					ChatColor.GRAY+ "Members: "+clanProfile.getClansmen().size()
					));
			item.setItemMeta(im);
			return item;
		}else{
			ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)paneID(clan));
			ItemMeta im = item.getItemMeta();
			String clanTag = clan.toString().toLowerCase();
			clanTag = clanTag.substring(0, 1).toUpperCase() + clanTag.substring(1);
			im.setDisplayName(ChatColor.valueOf(clan.toString().toUpperCase())+clanTag+ChatColor.DARK_GRAY+" Clan");
			im.setLore(Arrays.asList(
					ChatColor.GRAY+ "King: "+clanProfile.getKingName(),
					ChatColor.GRAY+ "Members: "+clanProfile.getClansmen().size()
					));
			item.setItemMeta(im);
			return item;
		}
	}
	
	public static int paneID(Clans clan){
		switch(clan){
			case WHITE:
				return 0;
			case BLACK:
				return 15;
			case RED:
				return 14;
			case BLUE:
				return 3;
			case GOLD:
				return 4;
			case GREEN:
				return 5;
			case COLORLESS:
				return 0;
			case UNCLANNED:
				break;
			default:
				break;
		}
		return 0;
	}
	
	public static ItemStack createClanTag(Player player){
		Account account = new Account(player);
		xyz.almia.accountsystem.Character character = account.getLoadedCharacter();
		switch(character.getClan()){
		case BLACK:
			ItemStack b = new Wool(DyeColor.BLACK).toItemStack(1);
			ItemMeta bm = b.getItemMeta();
			bm.setDisplayName(ChatColor.GRAY + "Clan: " + ChatColor.BLACK + "Black");
			bm.setLore(Arrays.asList(new String[] { ChatColor.DARK_GRAY +"Position: " + character.getClanRank().toString(),ChatColor.DARK_GRAY +"This player belongs to the "+ ChatColor.BLACK +"Black"+ChatColor.DARK_GRAY+" Clan." }));
			b.setItemMeta(bm);
			return b;
		case BLUE:
			ItemStack bl = new Wool(DyeColor.BLUE).toItemStack(1);
			ItemMeta blm = bl.getItemMeta();
			blm.setDisplayName(ChatColor.GRAY + "Clan: " + ChatColor.BLUE + "Blue");
			blm.setLore(Arrays.asList(new String[] { ChatColor.DARK_GRAY +"Position: " + character.getClanRank().toString(),ChatColor.DARK_GRAY +"This player belongs to the "+ ChatColor.BLUE +"Blue"+ChatColor.DARK_GRAY+" Clan." }));
			bl.setItemMeta(blm);
			return bl;
		case COLORLESS:
			ItemStack cl = new ItemStack(Material.GLASS, 1);
			ItemMeta clm = cl.getItemMeta();
			clm.setDisplayName(ChatColor.GRAY + "Clan: Colorless");
			clm.setLore(Arrays.asList(new String[] { ChatColor.DARK_GRAY +"Position: " + character.getClanRank().toString(),ChatColor.DARK_GRAY +"This player belongs to the Colorless Clan." }));
			cl.setItemMeta(clm);
			return cl;
		case GOLD:
			ItemStack g = new Wool(DyeColor.YELLOW).toItemStack(1);
			ItemMeta gm = g.getItemMeta();
			gm.setDisplayName(ChatColor.GRAY + "Clan: " + ChatColor.GOLD + "Gold");
			gm.setLore(Arrays.asList(new String[] { ChatColor.DARK_GRAY +"Position: " + character.getClanRank().toString(),ChatColor.DARK_GRAY +"This player belongs to the "+ ChatColor.GOLD +"Gold"+ChatColor.DARK_GRAY+" Clan." }));
			g.setItemMeta(gm);
			return g;
		case GREEN:
			ItemStack br = new Wool(DyeColor.GREEN).toItemStack(1);
			ItemMeta brm = br.getItemMeta();
			brm.setDisplayName(ChatColor.GRAY + "Clan: " + ChatColor.GREEN + "Green");
			brm.setLore(Arrays.asList(new String[] { ChatColor.DARK_GRAY +"Position: " + character.getClanRank().toString(),ChatColor.DARK_GRAY +"This player belongs to the "+ ChatColor.GREEN +"Green"+ChatColor.DARK_GRAY+" Clan." }));
			br.setItemMeta(brm);
			return br;
		case RED:
			ItemStack r = new Wool(DyeColor.RED).toItemStack(1);
			ItemMeta rm = r.getItemMeta();
			rm.setDisplayName(ChatColor.GRAY + "Clan: " + ChatColor.RED + "Red");
			rm.setLore(Arrays.asList(new String[] { ChatColor.DARK_GRAY +"Position: " + character.getClanRank().toString(),ChatColor.DARK_GRAY +"This player belongs to the "+ ChatColor.RED +"Red"+ChatColor.DARK_GRAY+" Clan." }));
			r.setItemMeta(rm);
			return r;
		case UNCLANNED:
			ItemStack u = new Wool(DyeColor.GRAY).toItemStack(1);
			ItemMeta um = u.getItemMeta();
			um.setDisplayName(ChatColor.GRAY + "Clan: Not currently in a clan.");
			um.setLore(Arrays.asList(new String[] { ChatColor.DARK_GRAY +"This player is not in a Clan." }));
			u.setItemMeta(um);
			return u;
		case WHITE:
			ItemStack w = new Wool(DyeColor.WHITE).toItemStack(1);
			ItemMeta wm = w.getItemMeta();
			wm.setDisplayName(ChatColor.GRAY + "Clan: " + ChatColor.WHITE + "White");
			wm.setLore(Arrays.asList(new String[] { ChatColor.DARK_GRAY +"Position: " + character.getClanRank().toString(),ChatColor.DARK_GRAY +"This player belongs to the "+ ChatColor.WHITE +"White"+ChatColor.DARK_GRAY+" Clan." }));
			w.setItemMeta(wm);
			return w;
		default:
			break;
		}
		return null;
	}
	
	public static ItemStack createItem(String name, String lore, Material m) {
		ItemStack i = new MaterialData(m).toItemStack(1);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		im.setLore(Arrays.asList(new String[] { lore }));
		i.setItemMeta(im);
		return i;
	}
	
	public static ItemStack createBetterItem(String name, List<String> lore, Material m){
		ItemStack i = new MaterialData(m).toItemStack(1);
		ItemMeta im = i.getItemMeta();				
		im.setDisplayName(name);
		im.setLore(lore);
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		i.setItemMeta(im);
		return i;
	}
	
	public static ItemStack createEvenBetterItem(String name, List<String> lore, Material m, int amount){
		ItemStack i = new MaterialData(m).toItemStack(amount);
		ItemMeta im = i.getItemMeta();				
		im.setDisplayName(name);
		im.setLore(lore);
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		i.setItemMeta(im);
		return i;
	}
	
    public static String serializeLoc(Location l){
        return l.getWorld().getName()+","+l.getBlockX()+","+l.getBlockY()+","+l.getBlockZ();
    }
    
    public static Location deserializeLoc(String s){
        String[] st = s.split(",");
        return new Location(Bukkit.getWorld(st[0]), Integer.parseInt(st[1]), Integer.parseInt(st[2]), Integer.parseInt(st[3]));
    }
    
}
