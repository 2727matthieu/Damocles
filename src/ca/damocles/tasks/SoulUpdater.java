package ca.damocles.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import ca.damocles.accountsystem.Account;
import ca.damocles.accountsystem.AccountStatus;
import ca.damocles.accountsystem.Character;
import ca.damocles.itemsystem.Soul;

public class SoulUpdater extends BukkitRunnable{

	@SuppressWarnings("unused")
	private final Plugin plugin;

    public SoulUpdater(Plugin plugin) {
        this.plugin = plugin;
    }
	
	@Override
	public void run() {
		for(Player player : Bukkit.getOnlinePlayers()){
			Account account = new Account(player);
			if(account.getStatus().equals(AccountStatus.LOGGEDIN)){
				Character character = account.getLoadedCharacter();
				if(character.getPlayer().getInventory().getItem(8) == null || (!(character.getPlayer().getInventory().getItem(8).isSimilar(Soul.soulApple(character))))){
					character.getPlayer().getInventory().setItem(8, Soul.soulApple(character));
				}
				
				if(character.getSouls() < 0)
					character.setSouls(0);
				
				if(character.getSouls() == 0){
					character.remove();
				}
				
			}
		}
	}

}
