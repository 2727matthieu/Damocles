package ca.damocles.spells;

import org.bukkit.Location;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ca.damocles.accountsystem.Account;
import ca.damocles.accountsystem.Character;
import ca.damocles.cardinalsystem.Cardinal;
import ca.damocles.damagesystem.Damage;
import ca.damocles.damagesystem.DamageType;
import ca.damocles.spellsystem.Spell;
import ca.damocles.utils.ParticleUtil;

public class TransfusionSpell extends Spell{
	
	Location location;

	public TransfusionSpell(int id) {
		super(id);
	}

	public int getDamage() {
		return 4;
	}

	public int overSeconds() {
		return 2;
	}

	public SpellType getType() {
		return SpellType.DOT;
	}

	public Spells getSpell() {
		return Spells.TRANSFUSION;
	}

	public int getCost() {
		return 10;
	}
	
	public int getId() {
		return 5;
	}

	public int getCooldown() {
		return 0;
	}

	public int getRange() {
		return 20;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location loc) {
		this.location = loc;
	}

	public void cast(Player source) {
		Account account = new Account(source);
		Character character = account.getLoadedCharacter();
		Entity target = character.getTargetEntity(getRange());

		if (target != null) {
			if (character.getMana() < getCost()) {
				return;
			}
			character.setMana(character.getMana() - getCost());
			if (target instanceof Creature) {
				for (int i = 0; i < 4; i++) {
					new BukkitRunnable() {
						public void run() {
							new ParticleUtil().playTransfusionEffectPart1(source, target.getLocation());
							character.setHealth(character.getHealth() + 1);
							new Damage().playerDamageCreature((Creature) target, source,
									source.getInventory().getItemInMainHand(), 1, DamageType.MAGICAL);
							this.cancel();
						}
					}.runTaskLater(Cardinal.getPlugin(), i * 40);
				}
			}
			if (target instanceof Player) {
				for (int i = 0; i < 4; i++) {
					new BukkitRunnable() {
						public void run() {
							//new ParticleUtil().playTransfusionEffect(source, target.getLocation());
							character.setHealth(character.getHealth() + 1);
							new Damage().playerDamagePlayer((Player) target, source,
									source.getInventory().getItemInMainHand(), 1, DamageType.MAGICAL);
							this.cancel();
						}
					}.runTaskLater(Cardinal.getPlugin(), i * 40);
				}
			}

		}

	}

}
