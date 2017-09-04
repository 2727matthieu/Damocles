package ca.damocles.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import ca.damocles.accountsystem.Account;
import ca.damocles.accountsystem.AccountStatus;
import ca.damocles.accountsystem.Character;
import ca.damocles.cardinalsystem.Cardinal;
import ca.damocles.menusystem.AccountMenu;

public class PlayerStatusChecker extends BukkitRunnable{

	@SuppressWarnings("unused")
	private final Plugin plugin;

    public PlayerStatusChecker(Plugin plugin) {
        this.plugin = plugin;
    }
	
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
			
			if(account.getStatus().equals(AccountStatus.LOGGEDIN)){
				
				Character character = account.getLoadedCharacter();
				
				if(!(character.getRegening())){
					character.setRegening(true);
					int regenrate = 3;
					new BukkitRunnable(){
						@Override
						public void run() {
							if(character.getRegening()){
								character.setHealth((int) (character.getHealth() + 1));//(int) (account.getHealth() + (1 + (account.getStat(Stats.HITPOINTS) * .2) ) ) );	
							}
						}
					}.runTaskTimer(Cardinal.getPlugin(), 0, (int)(regenrate  * 20));
					
					new BukkitRunnable(){
						public void run(){
							character.regenMana();
						}
					}.runTaskTimer(Cardinal.getPlugin(), 0, 5);
				}

			}
		}
	}

}
