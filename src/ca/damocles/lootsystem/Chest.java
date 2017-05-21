package ca.damocles.lootsystem;

import org.bukkit.plugin.Plugin;

import ca.damocles.cardinalsystem.Cardinal;

public class Chest {
	
	Plugin plugin = Cardinal.getPlugin();
	
        		/*new BukkitRunnable(){
        			double t = 0;
        			public void run(){
        				t += Math.PI/8;
        				Location newLoc = loc;
        				for(double phi = 0; phi <= 2*Math.PI; phi += Math.PI/2){
        					double x = 0.2*(2*Math.PI - t) * Math.cos(t + phi);
        					double y = 0.3*t;
        					double z = 0.2*(2*Math.PI - t) * Math.sin(t + phi);
        					newLoc.add(x, y, z);
        					loc.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, newLoc, 1);
        					newLoc.subtract(x, y, z);
        					if( t >= 2*Math.PI){
        						this.cancel();
        					}
        				}
        			}
        		}.runTaskTimer(plugin, 0, 1);*/
}
