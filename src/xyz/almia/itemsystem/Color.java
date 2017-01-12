package xyz.almia.itemsystem;

public class Color {
	
	int Red;
	int Blue;
	int Green;
	
	public Color(int Red, int Green, int Blue){
		this.Red = Red;
		this.Green = Green;
		this.Blue = Blue;
	}
	
	public int getInt(){
		return (Red <<16) + (Green<<8) + Blue;
	}
	
	
}
