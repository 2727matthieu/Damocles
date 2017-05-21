package ca.damocles.utils;

import ca.damocles.potionsystem.PotionColors;

public class Color {
	
	public int Red;
	public int Blue;
	public int Green;
	
	public Color(PotionColors color){
		switch(color){
		case BLACK:
			Red = 0;
			Green = 0;
			Blue = 0;
			break;
		case CYAN:
			Red = 0;
			Green = 255;
			Blue = 255;
			break;
		case DARK_GREEN:
			Red = 0;
			Green = 102;
			Blue = 0;
			break;
		case DARK_RED:
			Red = 102;
			Green = 0;
			Blue = 0;
			break;
		case GREEN:
			Red = 0;
			Green = 255;
			Blue = 0;
			break;
		case GREY:
			Red = 128;
			Green = 128;
			Blue = 128;
			break;
		case MAGENTA:
			Red = 255;
			Green = 0;
			Blue = 255;
			break;
		case ORANGE:
			Red = 255;
			Green = 127;
			Blue = 0;
			break;
		case PINK:
			Red = 255;
			Green = 102;
			Blue = 178;
			break;
		case RED:
			Red = 255;
			Green = 0;
			Blue = 0;
			break;
		case SALMON:
			Red = 255;
			Green = 102;
			Blue = 102;
			break;
		case WHITE:
			Red = 255;
			Green = 255;
			Blue = 255;
			break;
		case YELLOW:
			Red = 255;
			Green = 255;
			Blue = 0;
			break;
		default:
			Red = 0;
			Green = 0;
			Blue = 0;
			break;
		}
	}
	
	public Color(int Red, int Green, int Blue){
		this.Red = Red;
		this.Green = Green;
		this.Blue = Blue;
	}
	
	public int getInt(){
		return (Red <<16) + (Green<<8) + Blue;
	}
	
	
}
