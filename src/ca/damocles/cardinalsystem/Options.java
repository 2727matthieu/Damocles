package ca.damocles.cardinalsystem;

import org.bukkit.configuration.file.FileConfiguration;

import ca.damocles.configclasses.ConfigManager;

public class Options {
    
    FileConfiguration config;
    boolean firstTimeLoaded;
    
    public Options(){
        firstTimeLoaded = ConfigManager.load("options.yml", "");
        this.config = ConfigManager.get("options.yml");
        if(firstTimeLoaded)
            firstTimeSetup();
    }
    
    public boolean getOptionEnabled(OptionType option){
        return config.getBoolean("Config."+option.toString().toUpperCase()+".Enabled");
    }
    
    public boolean toggleOption(OptionType option){
        boolean status = !getOptionEnabled(option);
        config.set("Options."+option.toString().toUpperCase()+".Enabled", status);
        ConfigManager.save("options.yml", "");
        return status;
    }
    
    private void firstTimeSetup(){
        for(OptionType option : OptionType.values()){
            config.set("Options."+option.toString().toUpperCase()+".Enabled", true);
        }
        ConfigManager.save("options.yml", "");
    }
    
    public enum OptionType{
        CLANSYSTEM, SOULSYSTEM, DEFAULT_ITEMS; // GUILDS, TRADING;
    }
    
}