package ca.damocles.spellsystem;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import ca.damocles.accountsystem.Character;
import ca.damocles.accountsystem.Account;
import ca.damocles.accountsystem.AccountStatus;

public class Cast {
	
	private final Player player;
	private final Spell spell;
	private Character character;
	private int currentMana = character.getMana();
	private final int COST;
	private final int DAMAGE;
	private final long COOLDOWN;
	private List<Entity> targets;
	private CastLog log;
	
	public Cast(Player player, Spell spell){
		this.player = player;
		this.spell = spell;
		COST = this.spell.getCost();
		DAMAGE = this.spell.getDamage();
		COOLDOWN = this.spell.getCooldown();
		log =  new CastLog(character, spell, System.currentTimeMillis() + COOLDOWN);
		if(authenticateUser()){ player.sendMessage( cast().getDesc()); }
	}

	public CastReason cast(){
		if(CastHandler.getInstance().check(character, spell)){
			if(currentMana >= COST){
				targets = new Target(character, spell).getTarget();
				if(!(targets.isEmpty())){
					character.setMana(currentMana-COST);
					CastHandler.getInstance().add(log);
					return CastReason.SUCCESS;
				}else{ return CastReason.NO_TARGET; }
			}else{ return CastReason.NOT_ENOUGH_MANA; }
		}else { return CastReason.ON_COOLDOWN; }
	}
	
	public List<Entity> getTargets(){
		return this.targets;
	}
	
	public CastLog getLog(){
		return this.log;
	}
	
	public int getDamage(){
		return this.DAMAGE;
	}
	
	private boolean authenticateUser() {
		Account account = new Account(this.player);
		if(account.getStatus() == AccountStatus.LOGGEDIN){
			this.character = account.getLoadedCharacter();
			return true;
		}
		this.character = null;
		return false;
	}
	
	public enum CastReason{
		SUCCESS(ChatColor.GREEN + "Successfully casted!"),
		NO_TARGET(ChatColor.RED + "You have no valid target!"),
		ON_COOLDOWN(ChatColor.RED + "This spell is on cooldown!"),
		NOT_ENOUGH_MANA(ChatColor.RED + "You do not have enough mana!"),
		FAILED(ChatColor.RED + "Cast has failed!");
		private final String n;
		CastReason(String n){ this.n = n; }
		public String getDesc() { return this.n; }
	}
	
}
