package xyz.almia.accountsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import net.blitzcube.score.secondlineapi.manager.SecondLineManager;
import xyz.almia.cardinalsystem.Cardinal;
import xyz.almia.clansystem.Clan;
import xyz.almia.clansystem.Clans;
import xyz.almia.utils.Message;
import xyz.almia.utils.Swears;

public class PlayerSetup implements Listener{
	
	
	Plugin plugin = Cardinal.getPlugin();
	
	public PlayerSetup(){}
	
	public xyz.almia.clansystem.Rank getClanRank(Character character){
		Clans clan = getClan(character);
		Clan clanProfile = new Clan(clan);
		
		if(clan.equals(Clans.UNCLANNED))
			return xyz.almia.clansystem.Rank.NONE;
		
		if(clan.equals(Clans.EXILED))
			return xyz.almia.clansystem.Rank.CLANSMEN;
		
		if(clanProfile.getKing() != null){
			if(clanProfile.getKing().getUsername().equals(character.getUsername())){
				return xyz.almia.clansystem.Rank.KING;
			}
		}
		
		for(Character chara : clanProfile.getClansmen()){
			if(chara.getUsername().equals(character.getUsername())){
				return xyz.almia.clansystem.Rank.CLANSMEN;
			}
		}
		
		return xyz.almia.clansystem.Rank.NONE;
		
	}
	
	public Clans getClan(Character character){
		for(Clans clan : Clans.values()){
			Clan clanProfile = new Clan(clan);
			
			
			for(Character chara : clanProfile.getClansmen()){
				if(chara.getUsername().equals(character.getUsername())){
					return clan;
				}
			}
			
			if(!(clan.equals(Clans.UNCLANNED)) || !(clan.equals(Clans.EXILED))){
				try{
					if(clanProfile.getKing().getUsername().equals(character.getUsername())){
						return clan;
					}
				}catch(Exception e) {}
			}
			
		}
		return Clans.UNCLANNED;
	}
	
	public boolean isInClan(Character character){
		if(getClan(character).equals(Clans.UNCLANNED)){
			return false;
		}else{
			return true;
		}
	}
	
	public List<Character> getOnlineCharacters(){
		List<Character> onlinePlayers = new ArrayList<Character>();
		for(Player player : Bukkit.getOnlinePlayers()){
			Account account = new Account(player);
			try{
				Character character = account.getLoadedCharacter();
				onlinePlayers.add(character);
			} catch(Exception e) {}
		}
		return onlinePlayers;
	}
	
	public List<Character> getOfflineCharacters(){
		List<Character> onlineCharacters = getOnlineCharacters();
		List<Character> allCharacters = new Players().getCharacters();
		List<Character> offlineCharacters = allCharacters;
		for(Character character : allCharacters){
			for(Character chara : onlineCharacters){
				if(character.equals(chara)){
					offlineCharacters.remove(chara);
				}
			}
		}
		return offlineCharacters;
	}
	
	public Character getCharacterFromUsername(String username){
		if(username.equalsIgnoreCase("unknown"))
			return null;
		List<Character> characters = getOnlineCharacters();
		for(Character character : characters){
			if(character.getUsername().equalsIgnoreCase(username)){
				return character;
			}
		}
		return null;
	}
	
	public List<String> getCharacterNames(){
		List<Character> chars = new Players().getCharacters();
		List<String> names = new ArrayList<String>();
		for(Character chara : chars){
			names.add(chara.getUsername());
		}
		return names;
	}
	
	public Character deserializeCharacter(String s){
		String[] args = s.split(";");
		try{
			Player player = Bukkit.getPlayer(UUID.fromString(args[0]));
			try{
				Character character = new Character(player, Integer.valueOf(args[2]));
				return character;
			}catch(Exception e){}
		} catch (Exception e) {}
		
		return null;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event){
		Player player = (Player) event.getWhoClicked();
		Account account = new Account(player);
		if(event.getInventory().getName().contains(player.getName())){
			if(event.getCurrentItem() != null){
				event.setCancelled(true);
				
				if(event.getCurrentItem().hasItemMeta()){
					
					if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED+"Logout")){
						player.kickPlayer("You have exited Damocles!");
						return;
					}
					
					
					if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("Character Slot 1")){
						account.loadCharacter(0);
						player.closeInventory();
						player.teleport(account.getLoadedCharacter().getLastLocation());
						try{
							account.getLoadedCharacter().applyInventory();
						}catch(Exception e) {}
						for(int i=0; i < 16;){
							player.sendMessage("");
							i++;
						}
						if(!(account.getLoadedCharacter().getUsername().equalsIgnoreCase("unknown"))){
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							Message.sendCenteredMessage(player, ChatColor.BOLD + "Logging in!");
							Message.sendCenteredMessage(player, ChatColor.YELLOW+ "Logged into "+account.getLoadedCharacter().getUsername()+"!");
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						}else{
							sendNameSelectionProcess(player);
						}
					}
					
					if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("Character Slot 2")){
						account.loadCharacter(1);
						player.closeInventory();
						player.teleport(account.getLoadedCharacter().getLastLocation());
						try{
							account.getLoadedCharacter().applyInventory();
						}catch(Exception e) {}
						for(int i=0; i < 16;){
							player.sendMessage("");
							i++;
						}
						if(!(account.getLoadedCharacter().getUsername().equalsIgnoreCase("unknown"))){
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							Message.sendCenteredMessage(player, ChatColor.BOLD + "Logging in!");
							Message.sendCenteredMessage(player, ChatColor.YELLOW+ "Logged into "+account.getLoadedCharacter().getUsername()+"!");
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						}else{
							sendNameSelectionProcess(player);
						}
					}
					
					if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("Character Slot 3")){
						account.loadCharacter(2);
						player.closeInventory();
						player.teleport(account.getLoadedCharacter().getLastLocation());
						try{
							account.getLoadedCharacter().applyInventory();
						}catch(Exception e) {}
						for(int i=0; i < 16;){
							player.sendMessage("");
							i++;
						}
						if(!(account.getLoadedCharacter().getUsername().equalsIgnoreCase("unknown"))){
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							Message.sendCenteredMessage(player, ChatColor.BOLD + "Logging in!");
							Message.sendCenteredMessage(player, ChatColor.YELLOW+ "Logged into "+account.getLoadedCharacter().getUsername()+"!");
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						}else{
							sendNameSelectionProcess(player);
						}
					}
					
				}
				
			}
			
		}
	}
	
	public UserReason nameSelection(String name){
		List<String> alreadyexists = getCharacterNames();
		
		for(String s : alreadyexists){
			if(s.equalsIgnoreCase(name)){
				return UserReason.NAMETAKEN;
			}
		}
		
		if(name.contains(" ")){
			return UserReason.SPACE;
		}
		
		if(name.contains("king") || name.contains("damocles")){
			return UserReason.KING;
		}
		
		for(Swears swear : Swears.values()){
			if(name.contains(swear.toString().toLowerCase())){
				return UserReason.PROFANITY;
			}
		}
		
		return UserReason.NONE;
		
	}
	
	public void sendNameSelectionProcess(Player player){
		for(int i=0; i < 16;){
			player.sendMessage("");
			i++;
		}
		Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		Message.sendCenteredMessage(player, ChatColor.BOLD + "Please type what you would like to name your character into chat!");
		Message.sendCenteredMessage(player, ChatColor.YELLOW+ "No profanity, No spaces, or the use of 'king' or 'damocles' in your name.");
		Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
	}
	
	@EventHandler
	public void onLogout(PlayerQuitEvent event){
		Player player = event.getPlayer();
		Account account = new Account(player);
		if(account.getStatus().equals(AccountStatus.LOGGEDIN)){
			account.logout();
		}
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event){
		Player player = event.getPlayer();
		try{
			Character fchar = new Account(player).getLoadedCharacter();
			
			if(fchar.getCharacterStatus().equals(CharacterStatus.CHOOSE_USERNAME)){
				event.setCancelled(true);
				
				if(nameSelection(event.getMessage()).equals(UserReason.NONE)){
					
					for(int i=0; i < 16;){
						player.sendMessage("");
						i++;
					}
					
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Message.sendCenteredMessage(player, ChatColor.BOLD + "Name available!");
					Message.sendCenteredMessage(player, ChatColor.YELLOW+ "You have been logged in.");
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					
					fchar.setCharacterStatus(CharacterStatus.NORMAL);
					fchar.setUsername(event.getMessage());
					
				}else{
					
					for(int i=0; i < 16;){
						player.sendMessage("");
						i++;
					}
					
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Message.sendCenteredMessage(player, ChatColor.BOLD + "Name Unavailable");
					Message.sendCenteredMessage(player, ChatColor.YELLOW+ nameSelection(event.getMessage()).toString() + " Error.");
					Message.sendCenteredMessage(player, ChatColor.YELLOW+ "No profanity, No spaces, or the use of 'king' or 'damocles' in your name.");
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					
				}
				
			}
			
		}catch(NullPointerException exception){}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event){
		Player player = event.getPlayer();
		Account account = new Account(player);
		Character character = account.getLoadedCharacter();
		
		if(account.getStatus().equals(AccountStatus.LOGGINGIN)){
			event.setCancelled(true);
		}else{
			if(character.getCharacterStatus().equals(CharacterStatus.CHOOSE_USERNAME)){
				event.setCancelled(true);
			}
		}
		
	}
	
	@EventHandler
	public void onPlayersJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		Account account = new Account(player);
		try{
			SecondLineManager.getInstance(plugin).add(player);
		}catch(NoClassDefFoundError e) {}
		
		try{
			@SuppressWarnings("unused")
			AccountStatus status = account.getStatus();
		}catch(Exception e){
			new Account(player).firstTimeSetup();
		}
		
		Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		Message.sendCenteredMessage(player, ChatColor.BOLD + "Welcome " + player.getName() + "!");
		Message.sendCenteredMessage(player, ChatColor.YELLOW+ "Please choose a character.");
		Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
	}
	
}
