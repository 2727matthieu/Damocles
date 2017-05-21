package ca.damocles.castsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;

import ca.damocles.accountsystem.Account;
import ca.damocles.accountsystem.AccountStatus;
import ca.damocles.accountsystem.Character;
import ca.damocles.cardinalsystem.Cardinal;
import ca.damocles.castsystem.CastHandler.Combination;

public class CastingManager implements Listener{
	
	public static Map<String, List<Combination>> castingCombs = new HashMap<String, List<Combination>>();
	
	@EventHandler
	public void onInteractEvent(PlayerInteractEvent event){
		Player player = event.getPlayer();
		if(player.isSneaking()){
			Account account = new Account(player);
			if(account.getStatus().equals(AccountStatus.LOGGEDIN)){
				Character character = account.getLoadedCharacter();
				if(event.getHand().equals(EquipmentSlot.HAND)){
					CastHandler handler = new CastHandler(character);
					List<Combination> combinations = handler.getCombination();
					if(combinations.size() == 0){
						new BukkitRunnable(){
							public void run(){
								handler.setCombination(new ArrayList<Combination>());
							}
						}.runTaskLater(Cardinal.getPlugin(), 30);
					}
					if(combinations.size() == 3){
						handler.setCombination(new ArrayList<Combination>());
					}
					combinations = handler.getCombination();
					combinations.add(Combination.valueOf(actionToString(event.getAction()).toUpperCase()));
					handler.setCombination(combinations);
					String s = ClickCombFromCombs(combinations);
					player.sendTitle(ChatColor.YELLOW + s, ChatColor.GRAY+ "Spell Combination", 5, 20, 5);
					if(combinations.size() == 3){
						new Cast(getCastedSpellId(combinations), character);
					}
				}
			}
		}
	}
	
	public int getCastedSpellId(List<Combination> combinations){
		if(combinations.get(0).equals(Combination.LEFT) && combinations.get(1).equals(Combination.LEFT) && combinations.get(2).equals(Combination.LEFT)){
			return 0;
		}
		if(combinations.get(0).equals(Combination.RIGHT) && combinations.get(1).equals(Combination.LEFT) && combinations.get(2).equals(Combination.LEFT)){
			return 1;
		}
		if(combinations.get(0).equals(Combination.LEFT) && combinations.get(1).equals(Combination.RIGHT) && combinations.get(2).equals(Combination.LEFT)){
			return 2;
		}
		if(combinations.get(0).equals(Combination.LEFT) && combinations.get(1).equals(Combination.LEFT) && combinations.get(2).equals(Combination.RIGHT)){
			return 3;
		}
		if(combinations.get(0).equals(Combination.RIGHT) && combinations.get(1).equals(Combination.RIGHT) && combinations.get(2).equals(Combination.RIGHT)){
			return 4;
		}
		return -1;
	}
	
	public String ClickCombFromCombs(List<Combination> combinations){
		String s = "1-2-3";
		if(combinations.size() == 1){
			s = s.replace("1", combinations.get(0).toString());
			s = s.replace("2", "?");
			s = s.replace("3", "?");
		}
		if(combinations.size() == 2){
			s = s.replace("1", combinations.get(0).toString());
			s = s.replace("2", combinations.get(1).toString());
			s = s.replace("3", "?");
		}
		if(combinations.size() == 3){
			s = s.replace("1", combinations.get(0).toString());
			s = s.replace("2", combinations.get(1).toString());
			s = s.replace("3", combinations.get(2).toString());
		}
		return s;
	}
	
	public String ClickCombFromNumb(int i){
		if(i == 0){
			return "Left - Left - Left";
		}else if(i == 1){
			return "Right - Left - Left";
		}else if(i == 2){
			return "Left - Right - Left";
		}else if(i == 3){
			return "Left - Left - Right";
		}else if(i == 4){
			return "Right - Right - Right";
		}else{
			return "unknown";
		}
	}
	
	public String actionToString(Action action){
		if(action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)){
			return "right";
		}
		if(action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)){
			return "left";
		}
		return "";
	}
	
}
