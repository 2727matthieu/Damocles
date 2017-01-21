package xyz.almia.storagesystem;

import org.bukkit.configuration.file.FileConfiguration;

import xyz.almia.configclasses.ConfigManager;

public class Treasury {
	
	xyz.almia.accountsystem.Character character;
	FileConfiguration characterBank;
	FileConfiguration bank;
	double taxrate = 0.05;
	
	public Treasury(xyz.almia.accountsystem.Character character){
		this.character = character;
		ConfigManager.load(character.uuid+";bank;"+character.characterID+".yml", "bank/"+character.uuid);
		ConfigManager.load("central.yml", "bank");
		
		this.bank = ConfigManager.get("central.yml");
		this.characterBank = ConfigManager.get(character.uuid+";bank;"+character.characterID+".yml");
		
	}
	
	public void setupBankAccount(){
		characterBank.set("balance", 0.0);
		characterBank.set("most-owned", 0.0);
		characterBank.set("tax-money-payed", 0.0);
		characterBank.set("amount-lost", 0.0);
		ConfigManager.save(character.uuid+";bank;"+character.characterID+".yml", "bank/"+character.uuid);
	}
	
	public double getBalance(){
		return characterBank.getDouble("balance");
	}
	
	public void setBalance(double value){
		characterBank.set("balance", value);
		ConfigManager.save(character.uuid+";bank;"+character.characterID+".yml", "bank/"+character.uuid);
	}
	
	public boolean withdraw(double value){
		if((getBalance() - value) >= 0){
			setBalance(getBalance() - value);
			return true;
		}else{
			return false;
		}
	}
	
	public void deposit(double value){
		double tax = value*taxrate;
		depositCentralBank(tax);
		setBalance(getBalance() + (value-tax));
	}
	
	public void depositCentralBank(double value){
		setCentralBank(getCentralBank() + value);
	}
	
	public double getCentralBank(){
		return bank.getDouble("balance");
	}
	
	public void setCentralBank(double value){
		bank.set("balance", value);
		ConfigManager.save("central.yml", "bank");
	}
	
	
}
