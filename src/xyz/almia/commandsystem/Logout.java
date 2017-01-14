package xyz.almia.commandsystem;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import xyz.almia.accountsystem.Account;
import xyz.almia.utils.Message;

public class Logout implements CommandExecutor{
	Plugin plugin;
	
	public Logout(Plugin plugin){
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String labe, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("You must be a player to use commands!");
		}
		Player player = (Player)sender;
		Account account = new Account(player);
		if(cmd.getName().equalsIgnoreCase("logout")){
			Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			Message.sendCenteredMessage(player, ChatColor.BOLD + "Account");
			Message.sendCenteredMessage(player, ChatColor.YELLOW+"Logging out in 5 seconds do not move.");
			Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			Location loc = player.getLocation();
			int oldx = loc.getBlockX();
			int oldy = loc.getBlockY();
			int oldz = loc.getBlockZ();
			for(int o = 0; o < 15; o++){
				player.sendMessage("");
			}
			new BukkitRunnable(){
				int i = 5;
				
				public void run() {
					
					int newx = player.getLocation().getBlockX();
					int newy = player.getLocation().getBlockY();
					int newz = player.getLocation().getBlockZ();
					
					if( (oldx != newx) || (oldy != newy) || (oldz != newz) ){
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(player, ChatColor.BOLD + "Account");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+"Moved cancelling logout.");
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						this.cancel();
					}
					
					if(!(i == 0)){
						Message.sendCenteredMessage(player, ChatColor.RED + "" + i +"...");
						i--;
					}else{
						Message.sendCenteredMessage(player, ChatColor.RED + "" + i +"...");
						account.logout();
						for(int o = 0; o < 15; o++){
							player.sendMessage("");
						}
						this.cancel();
					}
				}
			}.runTaskTimer(plugin, 0, 20);
			return true;
		}
		return true;
	}
}
