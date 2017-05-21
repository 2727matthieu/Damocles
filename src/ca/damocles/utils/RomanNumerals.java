package ca.damocles.utils;

public class RomanNumerals {
	
	public static String intToRoman(int i){
		String roman = "";
		if(i == 1){
			roman = "I";
			return roman;
		}
		if(i == 2){
			roman = "II";
			return roman;
		}
		if(i == 3){
			roman = "III";
			return roman;
		}
		if(i == 4){
			roman = "IV";
			return roman;
		}
		if(i == 5){
			roman = "V";
			return roman;
		}
		if(i == 6){
			roman = "VI";
			return roman;
		}
		if(i == 7){
			roman = "VII";
			return roman;
		}
		if(i == 8){
			roman = "VIII";
			return roman;
		}
		if(i == 9){
			roman = "IX";
			return roman;
		}
		if(i == 10){
			roman = "X";
			return roman;
		}
		if(i == 11){
			roman = "XI";
			return roman;
		}
		if(i == 12){
			roman = "XII";
			return roman;
		}
		if(i == 13){
			roman = "XIII";
			return roman;
		}
		if(i == 14){
			roman = "XIV";
			return roman;
		}
		if(i == 15){
			roman = "XV";
			return roman;
		}
		if(i == 16){
			roman = "XVI";
			return roman;
		}
		if(i == 17){
			roman = "XVII";
			return roman;
		}
		if(i == 18){
			roman = "XVIII";
			return roman;
		}
		if(i == 19){
			roman = "XIX";
			return roman;
		}
		if(i == 20){
			roman = "XX";
			return roman;
		}
		if(i == 21){
			roman = "XXI";
			return roman;
		}
		if(i == 22){
			roman = "XXII";
			return roman;
		}
		if(i == 23){
			roman = "XXIII";
			return roman;
		}
		if(i == 24){
			roman = "XXIV";
			return roman;
		}
		if(i == 25){
			roman = "XXV";
			return roman;
		}
		if(i == 26){
			roman = "XXVI";
			return roman;
		}
		if(i == 27){
			roman = "XXVII";
			return roman;
		}
		if(i == 28){
			roman = "XXVIII";
			return roman;
		}
		if(i == 29){
			roman = "XXIX";
			return roman;
		}
		if(i == 30){
			roman = "XXX";
			return roman;
		}
		if(i == 31){
			roman = "XXXI";
			return roman;
		}
		if(i == 32){
			roman = "XXXII";
			return roman;
		}
		if(i == 33){
			roman = "XXXIII";
			return roman;
		}
		if(i == 34){
			roman = "XXXIV";
			return roman;
		}
		if(i == 35){
			roman = "XXXV";
			return roman;
		}
		if(i == 36){
			roman = "XXXVI";
			return roman;
		}
		if(i == 37){
			roman = "XXXVII";
			return roman;
		}
		if(i == 38){
			roman = "XXXVIII";
			return roman;
		}
		if(i == 39){
			roman = "XXXIX";
			return roman;
		}
		if(i == 40){
			roman = "XL";
			return roman;
		}
		if(i == 41){
			roman = "XLI";
			return roman;
		}
		if(i == 42){
			roman = "XLII";
			return roman;
		}
		if(i == 43){
			roman = "XLIII";
			return roman;
		}
		if(i == 44){
			roman = "XLIV";
			return roman;
		}
		if(i == 45){
			roman = "XLV";
			return roman;
		}
		if(i == 46){
			roman = "XLVI";
			return roman;
		}
		if(i == 47){
			roman = "XLVII";
			return roman;
		}
		if(i == 48){
			roman = "XLVIII";
			return roman;
		}
		if(i == 49){
			roman = "XLIX";
			return roman;
		}
		if(i == 50){
			roman = "L";
			return roman;
		}
		return roman;
	}
	
	public static int romanToInt(String s){
		int i = 0;
		if(s.equalsIgnoreCase("I")){
			i = 1;
		}else if(s.equalsIgnoreCase("II")){
			i = 2;
		}else if(s.equalsIgnoreCase("III")){
			i = 3;
		}else if(s.equalsIgnoreCase("IV")){
			i = 4;
		}else if(s.equalsIgnoreCase("V")){
			i = 5;
		}else if(s.equalsIgnoreCase("VI")){
			i = 6;
		}else if(s.equalsIgnoreCase("VII")){
			i = 7;
		}else if(s.equalsIgnoreCase("VIII")){
			i = 8;
		}else if(s.equalsIgnoreCase("IX")){
			i = 9;
		}else if(s.equalsIgnoreCase("X")){
			i = 10;
		}else if(s.equalsIgnoreCase("XI")){
			i = 11;
		}else if(s.equalsIgnoreCase("XII")){
			i = 12;
		}else if(s.equalsIgnoreCase("XIII")){
			i = 13;
		}else if(s.equalsIgnoreCase("XIV")){
			i = 14;
		}else if(s.equalsIgnoreCase("XV")){
			i = 15;
		}else if(s.equalsIgnoreCase("XVI")){
			i = 16;
		}else if(s.equalsIgnoreCase("XVII")){
			i = 17;
		}else if(s.equalsIgnoreCase("XVIII")){
			i = 18;
		}else if(s.equalsIgnoreCase("XIX")){
			i = 19;
		}else if(s.equalsIgnoreCase("XX")){
			i = 20;
		}else if(s.equalsIgnoreCase("XXI")){
			i = 21;
		}else if(s.equalsIgnoreCase("XXII")){
			i = 22;
		}else if(s.equalsIgnoreCase("XXIII")){
			i = 23;
		}else if(s.equalsIgnoreCase("XXIV")){
			i = 24;
		}else if(s.equalsIgnoreCase("XXV")){
			i = 25;
		}else if(s.equalsIgnoreCase("XXVI")){
			i = 26;
		}else if(s.equalsIgnoreCase("XXVII")){
			i = 27;
		}else if(s.equalsIgnoreCase("XXVIII")){
			i = 28;
		}else if(s.equalsIgnoreCase("XXIX")){
			i = 29;
		}else if(s.equalsIgnoreCase("XXX")){
			i = 30;
		}else if(s.equalsIgnoreCase("XXXI")){
			i = 31;
		}else if(s.equalsIgnoreCase("XXXII")){
			i = 32;
		}else if(s.equalsIgnoreCase("XXXIII")){
			i = 33;
		}else if(s.equalsIgnoreCase("XXXIV")){
			i = 34;
		}else if(s.equalsIgnoreCase("XXXV")){
			i = 35;
		}else if(s.equalsIgnoreCase("XXXVI")){
			i = 36;
		}else if(s.equalsIgnoreCase("XXXVII")){
			i = 37;
		}else if(s.equalsIgnoreCase("XXXVIII")){
			i = 38;
		}else if(s.equalsIgnoreCase("XXXIX")){
			i = 39;
		}else if(s.equalsIgnoreCase("XL")){
			i = 40;
		}else if(s.equalsIgnoreCase("XLI")){
			i = 41;
		}else if(s.equalsIgnoreCase("XLII")){
			i = 42;
		}else if(s.equalsIgnoreCase("XLIII")){
			i = 43;
		}else if(s.equalsIgnoreCase("XLIV")){
			i = 44;
		}else if(s.equalsIgnoreCase("XLV")){
			i = 45;
		}else if(s.equalsIgnoreCase("XLVI")){
			i = 46;
		}else if(s.equalsIgnoreCase("XLVII")){
			i = 47;
		}else if(s.equalsIgnoreCase("XLVIII")){
			i = 48;
		}else if(s.equalsIgnoreCase("XLIX")){
			i = 49;
		}else if(s.equalsIgnoreCase("L")){
			i = 50;
		}else{
			i = 0;
		}
		return i;
	}
}
