package xyz.almia.effectLibrary;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.almia.cardinalsystem.Cardinal;
import xyz.almia.utils.ParticleUtil;

public class TornadoEffect {
	
	public TornadoEffect(int duration, Location location){
		new BukkitRunnable(){
			int count = 0;
			public void run() {
				double radius = 0.4;
				Location loc;
				for (double y = 0; y <= 10; y += 0.004) {
				    double x = (radius*=1.0015) * Math.cos(Math.pow(y, 2)*10);
				    double z = radius * Math.sin(Math.pow(y, 2)*10);
				    loc = new Location(location.getWorld(), x, y, z);
				    Location l = loc.clone().add(location);
				    new ParticleUtil().playRedstone(l, 1, 0, 0, 255);
				}
				count++;
				if(count >= duration*20)
					this.cancel();
			}
				
		}.runTaskTimer(Cardinal.getPlugin(), 0, 1);
	}
	
}
