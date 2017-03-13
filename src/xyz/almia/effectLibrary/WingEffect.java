package xyz.almia.effectLibrary;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import xyz.almia.cardinalsystem.Cardinal;
import xyz.almia.utils.ParticleUtil;

public class WingEffect {
	
	public WingEffect(Player player){
		new BukkitRunnable(){
			public void run(){
				Location loc = player.getEyeLocation().subtract(0.0D, 0.3D, 0.0D);
			    loc.setPitch(0.0F);
			    loc.setYaw(player.getEyeLocation().getYaw());
			    Vector v1 = loc.getDirection().normalize().multiply(-0.3D);
			    v1.setY(0);
			    loc.add(v1);
			    for (double i = -10.0D; i < 6.2D; i += 0.2){
			    	double var = Math.sin(i / 12.0D);
			        double x = Math.sin(i) * (Math.exp(Math.cos(i)) - 2.0D * Math.cos(4.0D * i) - Math.pow(var, 5.0D)) / 2.0D;
			        double z = Math.cos(i) * (Math.exp(Math.cos(i)) - 2.0D * Math.cos(4.0D * i) - Math.pow(var, 5.0D)) / 2.0D;
			        Vector v = new Vector(-x, 0.0D, -z);
			        rotateAroundAxisX(v, (loc.getPitch() + 90.0F) * 0.017453292F);
			        rotateAroundAxisY(v, -loc.getYaw() * 0.017453292F);
			        Location l = loc.clone().add(v);
				    new ParticleUtil().playRedstone(l, 1, 255, 0, 0);
				}
			}
		}.runTaskTimer(Cardinal.getPlugin(), 0, 5);
	}
	
	public static final Vector rotateAroundAxisX(Vector vector, double angle){
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		double y = vector.getY() * cos - vector.getZ() * sin;
		double z = vector.getY() * sin + vector.getZ() * cos;
		return vector.setY(y).setZ(z);
	}
	  
	  public static final Vector rotateAroundAxisY(Vector vector, double angle){
	    double cos = Math.cos(angle);
	    double sin = Math.sin(angle);
	    double x = vector.getX() * cos + vector.getZ() * sin;
	    double z = vector.getX() * -sin + vector.getZ() * cos;
	    return vector.setX(x).setZ(z);
	  }
	
}
