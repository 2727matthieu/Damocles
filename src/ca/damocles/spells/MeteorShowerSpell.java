package ca.damocles.spells;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import ca.damocles.accountsystem.Character;
import ca.damocles.cardinalsystem.Cardinal;
import ca.damocles.accountsystem.Account;
import ca.damocles.spellsystem.Spell;

public class MeteorShowerSpell extends Spell{
	
	Location loc;

	public MeteorShowerSpell(int id) {
		super(id);
	}

	public int getDamage() {
		return 0;
	}

	public int overSeconds() {
		return 10;
	}

	public SpellType getType() {
		return SpellType.AOE;
	}

	public Spells getSpell() {
		return Spells.METEOR_SHOWER;
	}

	public int getCost() {
		return 20;
	}

	public int getId() {
		return 3;
	}

	public int getCooldown() {
		return 40;
	}

	public int getRange() {
		return 30;
	}

	public Location getLocation() {
		return this.loc;
	}

	public void setLocation(Location loc) {
		this.loc = loc;
	}

	public void cast(Player source) {
		Account account = new Account(source);
		Character character = account.getLoadedCharacter();
		if(character.getMana() < getCost()){
			return;
		}
		character.setMana(character.getMana() - getCost());
		World world = source.getWorld();
		Location loc = source.getLocation();
		new BukkitRunnable(){
			int count = 0;
			public void run(){
				boolean d = false;
				for(int i = 0; i < 6; i++){
					int x = new Random().nextInt(getRange());
					int z = new Random().nextInt(getRange());
					
					if(d)
						x = x*-1;
					
					if(!d)
						z = z*-1;
					
					d = !d;
					loc.add(x, 30, z);
					Fireball ball = (Fireball)world.spawnEntity(loc, EntityType.FIREBALL);
					loc.subtract(x, 30, z);
					ball.setDirection(new Vector(0, -0.3, 0));
					ball.setYield(2);
				}
				count++;
				if(count > overSeconds())
					this.cancel();
			}
		}.runTaskTimer(Cardinal.getPlugin(), 0, 20);
		
		
		
	}

}
