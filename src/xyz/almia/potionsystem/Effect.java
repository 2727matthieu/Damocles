package xyz.almia.potionsystem;

public class Effect {
	
	private PotionType type;
	private int amplifier;
	private int duration;
	
	public Effect(PotionType type, int amplifier, int duration){
		this.type = type;
		this.amplifier = amplifier;
		this.duration = duration;
	}
	
	public int getAmplifier(){
		return amplifier;
	}
	
	public int getDuration(){
		return duration;
	}
	
	public PotionType getType(){
		return type;
	}
	
}
