package xyz.almia.potionsystem;

public class PotionHandler {
	
	public PotionHandler(){}
	
	public String getName(PotionType type){
		switch(type){
		case HEAL:
			return "Healing";
		case MANA:
			return "Mana Regeneration";
		case NONE:
			return "Nothing";
		case BLIND:
			return "Blindness";
		case EXPLOSION:
			return "Explosion";
		case FIRE:
			return "Fire";
		case FIRE_RESIST:
			return "Fire Resistance";
		case HARM:
			return "Harming";
		case HUNGER:
			return "Hunger";
		case JUMP:
			return "Jumping";
		case LIGHTNING:
			return "Lightning";
		case NAUSEA:
			return "Sickness";
		case POISON:
			return "Poison";
		case REGEN:
			return "Regeneration";
		case RESISTANCE:
			return "Resistance";
		case SLOW:
			return "Slowness";
		case SPEED:
			return "Speed";
		case WEAKNESS:
			return "Weakness";
		case WITHER:
			return "Withering";
		}
		return "Nothing";
	}
	
	public String getLore(PotionType type){
		switch(type){
		case HEAL:
			return "Health";
		case MANA:
			return "Mana";
		case NONE:
			return "Nothing";
		case BLIND:
			return "Blindness";
		case EXPLOSION:
			return "Explosion";
		case FIRE:
			return "Fire";
		case FIRE_RESIST:
			return "Fire Resistance";
		case HARM:
			return "Harming";
		case HUNGER:
			return "Hunger";
		case JUMP:
			return "Jumping";
		case LIGHTNING:
			return "Smiting";
		case NAUSEA:
			return "Sickness";
		case POISON:
			return "Poison";
		case REGEN:
			return "Regenration";
		case RESISTANCE:
			return "Resistance";
		case SLOW:
			return "Slowness";
		case SPEED:
			return "Speed";
		case WEAKNESS:
			return "Weakness";
		case WITHER:
			return "Wither";
		}
		return "Nothing";
	}
	
	/*@EventHandler
	public void onPotionConsume(PlayerItemConsumeEvent event){
		ItemStack consumed = event.getItem();
		if(ItemHandler.getType(consumed).equals(ItemTypes.POTION)){
			Potion potion = new Potion(consumed);
			Account player = new Account(event.getPlayer());
			xyz.almia.accountsystem.Character character = player.getLoadedCharacter();
		}
	}*/
	
}
