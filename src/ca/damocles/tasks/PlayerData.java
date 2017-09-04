package ca.damocles.tasks;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import ca.damocles.accountsystem.Account;
import ca.damocles.accountsystem.AccountStatus;
import ca.damocles.accountsystem.Character;
import ca.damocles.accountsystem.Stat;
import ca.damocles.itemblueprints.Armor;
import ca.damocles.storagesystem.Equips.Slot;

public class PlayerData extends BukkitRunnable{

	@SuppressWarnings("unused")
	private final Plugin plugin;

    public PlayerData(Plugin plugin) {
        this.plugin = plugin;
    }
	
	@Override
	public void run() {
		for(Player player : Bukkit.getOnlinePlayers()){
			
			Account account = new Account(player);
			
			if(account.getStatus().equals(AccountStatus.LOGGEDIN)){
				
				Character character = account.getLoadedCharacter();
				
				character.setEquip(Slot.HELMET, player.getInventory().getHelmet());
				player.getInventory().setHelmet(character.getEquip(Slot.HELMET));
				
				character.setEquip(Slot.CHESTPLATE, player.getInventory().getChestplate());
				player.getInventory().setChestplate(character.getEquip(Slot.CHESTPLATE));
				
				character.setEquip(Slot.LEGGINGS, player.getInventory().getLeggings());
				player.getInventory().setLeggings(character.getEquip(Slot.LEGGINGS));
				
				character.setEquip(Slot.BOOTS, player.getInventory().getBoots());
				player.getInventory().setBoots(character.getEquip(Slot.BOOTS));
				
				double i = 0.0;
				i = i + new Armor(player.getInventory().getHelmet()).getArmor();
				i = i + new Armor(player.getInventory().getChestplate()).getArmor();
				i = i + new Armor(player.getInventory().getLeggings()).getArmor();
				i = i + new Armor(player.getInventory().getBoots()).getArmor();
				player.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(i);
				
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
	

}
