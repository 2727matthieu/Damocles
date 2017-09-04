package ca.damocles.accountsystem;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import ca.damocles.tasks.KingPromotion;
import ca.damocles.tasks.PlayerData;
import ca.damocles.tasks.PlayerStatusChecker;
import ca.damocles.tasks.SlotChecker;
import ca.damocles.tasks.SoulUpdater;

public class Tasks{
	
	private Plugin plugin;
	private SlotChecker slotchecker = new SlotChecker(plugin);
	private KingPromotion kingpromoter = new KingPromotion(plugin);
	private PlayerData playerdata = new PlayerData(plugin);
	private PlayerStatusChecker playerchecker = new PlayerStatusChecker(plugin);
	private SoulUpdater soulupdater = new SoulUpdater(plugin);
	
	public Tasks(Plugin plugin) {
		this.plugin = plugin;
	}

	public SlotChecker getSlotChecker(){
		return slotchecker;
	}
	
	public KingPromotion getKingPromoter(){
		return kingpromoter;
	}
	
	public PlayerData getPlayerData(){
		return playerdata;
	}
	
	public PlayerStatusChecker getPlayerStatusChecker(){
		return playerchecker;
	}
	
	public SoulUpdater getSoulUpdater(){
		return soulupdater;
	}
	
	public void start(){
		this.getSlotChecker().runTaskTimer(plugin, 0, 1);
		this.getKingPromoter().runTaskTimer(plugin, 0, 1);
		this.getPlayerData().runTaskTimer(plugin, 0, 1);
		this.getPlayerStatusChecker().runTaskTimer(plugin, 0, 20);
		this.getSoulUpdater().runTaskTimer(plugin, 0, 1);
	}
	
	public void stop(){
		this.getSlotChecker().cancel();
		this.getKingPromoter().cancel();
		this.getPlayerData().cancel();
		this.getPlayerStatusChecker().cancel();
		this.getSoulUpdater().cancel();
	}
	
	public void disableRegen(){
		for(Player player : Bukkit.getOnlinePlayers()){
			Account account = new Account(player);
			if(account.getStatus().equals(AccountStatus.LOGGEDIN)){
				account.getLoadedCharacter().setRegening(false);
			}
		}
	}
	
}
