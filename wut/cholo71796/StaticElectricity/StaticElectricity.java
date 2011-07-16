/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wut.cholo71796.StaticElectricity;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

/**
 *
 * @author Cole Erickson
 */
public class StaticElectricity extends JavaPlugin{
    private final StaticElectricityPlayerListener playerListener = new StaticElectricityPlayerListener(this);
    private static final Logger logger = Logger.getLogger(StaticElectricity.class.getName());
    
    public static Plugin plugin;
    PluginDescriptionFile pdfFile;
    public Configuration config;
    
    static public double steps;
    static public boolean doDamage;

    @Override
    public void onDisable() {
        //This plugin is so hip it doesn't do ANYTHING when it shuts off.
    }
    
    @Override
    public void onEnable() {        
        config = getConfiguration(); //Sets the public config to the /plugins/PluginFolder/config.yml
        
        File configfile = new File(getDataFolder(), "config.yml");
        if (!configfile.exists()){
            getDataFolder().mkdirs();
            try {
                configfile.createNewFile();
            } catch (IOException ex) {
                log("Error creating config " + ex.getMessage(), 3);
            }            
            config.setProperty("doDamage", false);
            config.setProperty("steps", 5.0);
        }
        config.load();
        doDamage = config.getBoolean("doDamage", false);
        steps = config.getDouble("steps", 5.0);
        config.save();
        
        plugin = this;
        
        pdfFile = this.getDescription();
        
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Event.Priority.Normal, this);
        
        log("enabled.", 1);
    }
    
    
    
    public static void log(String message, int type){
        message = new StringBuilder("[").append("StaticElectricity").append("] ").append(message).toString();
        switch (type) {
            case 1:
                logger.info(message);
                break;
            case 2:
                logger.warning(message);
                break;
            case 3:
                logger.severe(message);
                break;
        }
    }
}
