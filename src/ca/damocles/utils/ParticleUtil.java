package ca.damocles.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import ca.damocles.cardinalsystem.Cardinal;
import ca.damocles.damagesystem.DamageInstance;
import ca.damocles.damagesystem.DamageInstance.DamageType;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;

public class ParticleUtil {
	
	public ParticleUtil(){}
	
	public void play(EnumParticle particle, Location l, int amount){
		for(Player display : Bukkit.getOnlinePlayers()){
	        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particle, true, (float) l.getX(), (float) l.getY(), (float) l.getZ(), (float) 0,(float) 0,(float) 0,(float) amount, 0);
	        ((CraftPlayer)display).getHandle().playerConnection.sendPacket(packet);
	    }
	}
	
	public void playRedstone(Location l, int amount, int r, int g, int b){
		for(Player display : Bukkit.getOnlinePlayers()){
	        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.REDSTONE, true, (float) l.getX(), (float) l.getY(), (float) l.getZ(), (float) r,(float) g,(float) b,(float) amount, 0);
	        ((CraftPlayer)display).getHandle().playerConnection.sendPacket(packet);
	    }
	}
	
	public static final Vector rotateAroundAxisX(Vector vector, double angle) {
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		double y = vector.getY() * cos - vector.getZ() * sin;
		double z = vector.getY() * sin + vector.getZ() * cos;
		return vector.setY(y).setZ(z);
	}

	public static final Vector rotateAroundAxisY(Vector vector, double angle) {
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		double x = vector.getX() * cos + vector.getZ() * sin;
		double z = vector.getX() * -sin + vector.getZ() * cos;
		return vector.setX(x).setZ(z);
	}
	
	/*
	 * Draws a line of particles from one Location to Another at a specified rate and frquency.
	 * Particle is the particle effect being produced.
	 * Amount is the amount of particles being spawned at each location.
	 * p1 is the origin location.
	 * p2 is the destination location.
	 * Speed is the number in ticks each iteration spawns at.
	 * Frequency is the rate each particle spawns at, 1.0 appearing ontop of destinations, 0.1 appearing one tenth of the distance each iteration.
	 */
	public void drawLine(EnumParticle particle, int amount, Location p1, Location p2, int speed, double frequency){
		new BukkitRunnable(){
			double t = 0;
			Vector direction = p2.toVector().subtract(p1.toVector());
			public void run(){
				t += frequency;
				double x = direction.getX()*t;
				double y = direction.getY()*t;
				double z = direction.getZ()*t;
				p1.add(x, y, z);
				play(particle, p1, amount);
				if(p1.toVector().distance(p2.toVector()) <= 0.5)
					this.cancel();
				p1.subtract(x, y, z);
			}
		}.runTaskTimer(Cardinal.getPlugin(), 0, speed);
	}
	
	public void playPrayerEffect(Player source){
		new BukkitRunnable(){
            double phi = 0;
            public void run(){
                    phi = phi + Math.PI;
                    double x, y, z;
                    Location location = source.getLocation();
                    for (double t = 0; t <= 2*Math.PI; t = t + Math.PI/16){
                    	for (double i = 0; i <= 1; i = i + 1){
                    		x = 0.4*(2*Math.PI-t)*0.5*Math.cos(t + phi + i*Math.PI);
                    		y = 0.5*t;
                    		z = 0.4*(2*Math.PI-t)*0.5*Math.sin(t + phi + i*Math.PI);
                    		location.add(x, y, z);
                    		play(EnumParticle.FIREWORKS_SPARK, location, 1);
                    		location.subtract(x,y,z);
                        }
                    }
                    if(phi > Math.PI){
                    	this.cancel();
                    }
            }      
		}.runTaskTimer(Cardinal.getPlugin(), 0, 3);
	}
	
	public void drawLineFromPlayer(EnumParticle particle, int amount, Player player, Location p2, int speed, double frequency){
		new BukkitRunnable(){
			double t = 0;
			Vector direction;
			Location p1;
			public void run(){
				p1 = player.getLocation();
				direction = p2.toVector().subtract(p1.toVector());
				t += frequency;
				double x = direction.getX()*t;
				double y = direction.getY()*t;
				double z = direction.getZ()*t;
				p1.add(x, y, z);
				play(particle, p1, amount);
				if(p1.toVector().distance(p2.toVector()) <= 0.5)
					this.cancel();
				p1.subtract(x, y, z);
			}
		}.runTaskTimer(Cardinal.getPlugin(), 0, speed);
	}
	
	public void drawLineToPlayer(EnumParticle particle, int amount, Player player, Location p2, int speed, double frequency){
		new BukkitRunnable(){
			double t = 0;
			Vector direction;
			Location p1;
			public void run(){
				p1 = player.getLocation();
				direction = p1.toVector().subtract(p2.toVector());
				t += frequency;
				double x = direction.getX()*t;
				double y = direction.getY()*t;
				double z = direction.getZ()*t;
				p2.add(x, y, z);
				play(particle, p2, amount);
				if(p2.toVector().distance(p1.toVector()) <= 0.5)
					this.cancel();
				p2.subtract(x, y, z);
			}
		}.runTaskTimer(Cardinal.getPlugin(), 0, speed);
	}
	
	public void playTornadoEffect(int duration, Location location){
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
	
	public void playWingEffect(Player player, int duration){
		new BukkitRunnable() {
			int count = 0;
			public void run() {
				count += (1/4);
				Location loc = player.getEyeLocation().subtract(0.0D, 0.3D, 0.0D);
				loc.setPitch(0.0F);
				loc.setYaw(player.getEyeLocation().getYaw());
				Vector v1 = loc.getDirection().normalize().multiply(-0.3D);
				v1.setY(0);
				loc.add(v1);
				for (double i = -10.0D; i < 6.2D; i += 0.2) {
					double var = Math.sin(i / 12.0D);
					double x = Math.sin(i) * (Math.exp(Math.cos(i)) - 2.0D * Math.cos(4.0D * i) - Math.pow(var, 5.0D))
							/ 2.0D;
					double z = Math.cos(i) * (Math.exp(Math.cos(i)) - 2.0D * Math.cos(4.0D * i) - Math.pow(var, 5.0D))
							/ 2.0D;
					Vector v = new Vector(-x, 0.0D, -z);
					rotateAroundAxisX(v, (loc.getPitch() + 90.0F) * 0.017453292F);
					rotateAroundAxisY(v, -loc.getYaw() * 0.017453292F);
					Location l = loc.clone().add(v);
					playRedstone(l, 1, 255, 0, 0);
				}
				if(count == duration)
					this.cancel();
			}
		}.runTaskTimer(Cardinal.getPlugin(), 0, 5);
	}
	
	public void playArcaneMissileEffect(Player source, double damage){
		new BukkitRunnable(){
			double t = 0;
			Vector dir = source.getLocation().getDirection();
			Location loc = source.getLocation();
			public void run(){
				t += 0.5;
				double x = dir.getX()*t;
				double y = dir.getY()*t + 1.5;
				double z = dir.getZ()*t;
				loc.add(x, y, z);
				play(EnumParticle.CRIT_MAGIC, loc, 1);
				for(Entity e : loc.getChunk().getEntities()){
					if(e.getLocation().distance(loc) < 2.0){
						if(!e.equals(source)){
							if(e instanceof LivingEntity){
								new DamageInstance((LivingEntity)e, source, damage, DamageType.MAGICAL);
							}
							this.cancel();
						}
					}
				}
				loc.subtract(x, y, z);
				if(t > 40)
					this.cancel();
			}
		}.runTaskTimer(Cardinal.getPlugin(), 0, 1);
	}
	
	public void playTransfusionEffectPart1(Player player, Location p2, LivingEntity target, double damage){
		new BukkitRunnable(){
			double t = 0;
			Vector direction;
			Location p1;
			public void run(){
				p1 = player.getLocation();
				direction = p2.toVector().subtract(p1.toVector());
				t += 0.025;
				double x = direction.getX()*t;
				double y = direction.getY()*t;
				double z = direction.getZ()*t;
				p1.add(x, y, z);
				play(EnumParticle.SNOW_SHOVEL, p1, 1);
				if(p1.toVector().distance(p2.toVector()) <= 0.5){
					//damage
					new DamageInstance(target, player, damage, DamageType.MAGICAL);
					playTransfusionEffectPart2(EnumParticle.FLAME, 1, player, p2, 1, 0.1);
					this.cancel();
				}
				p1.subtract(x, y, z);
			}
		}.runTaskTimer(Cardinal.getPlugin(), 0, 1);
	}
	
	public void playTransfusionEffectPart2(EnumParticle particle, int amount, Player player, Location p2, int speed, double frequency){
		new BukkitRunnable(){
			double t = 0;
			Vector direction;
			Location p1;
			public void run(){
				p1 = player.getLocation();
				direction = p1.toVector().subtract(p2.toVector());
				t += frequency;
				double x = direction.getX()*t;
				double y = direction.getY()*t;
				double z = direction.getZ()*t;
				p2.add(x, y, z);
				play(particle, p2, amount);
				if(p2.toVector().distance(p1.toVector()) <= 0.5)
					this.cancel();
				p2.subtract(x, y, z);
			}
		}.runTaskTimer(Cardinal.getPlugin(), 0, speed);
	}
	
}
