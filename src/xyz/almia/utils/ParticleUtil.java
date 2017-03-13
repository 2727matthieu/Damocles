package xyz.almia.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_11_R1.EnumParticle;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldParticles;

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
	
}
