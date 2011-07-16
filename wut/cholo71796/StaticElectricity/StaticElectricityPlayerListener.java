/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wut.cholo71796.StaticElectricity;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 *
 * @author Cole Erickson
 */
public class StaticElectricityPlayerListener extends PlayerListener{
    
    private Map<Player, Double> playerDistances = new HashMap<Player, Double>();
    
    public static StaticElectricity plugin;
    public StaticElectricityPlayerListener(StaticElectricity instance){
        plugin = instance;
    }
    
    Player player;
    Location from;
    Location to;
    int woolCheck;
    
    @Override
    public void onPlayerMove(PlayerMoveEvent event){
        
        player = event.getPlayer();
        if(!playerDistances.containsKey(player))
            playerDistances.put(player, 0.0);
        from = event.getFrom();
        to = event.getTo();
        
        woolCheck = from.getBlock().getRelative(0, -1, 0).getTypeId();
        
        if (hasPermission(player, "staticelectricity.exempt"))
            return;        
        
        if (woolCheck == Material.WOOL.getId())
            playerDistances.put(player, playerDistances.get(player) + (from.distance(to)*2));
        else if (woolCheck != Material.AIR.getId())
            playerDistances.put(player, 0.0);
        if ( ((playerDistances.get(player)) > StaticElectricity.steps) && playerDistances.get(player) > 0){
            if(StaticElectricity.doDamage)
                player.getWorld().strikeLightning(to);
            else
                player.getWorld().strikeLightningEffect(to);
            playerDistances.put(player, 0.0);
        }
        //StaticElectricity.log(player.toString() + "'s voltage: " + playerDistances.get(player).toString(), 1);
    }
    
    private boolean hasPermission(Player player, String permnode) {
        if (player.isOp())
            return true;
        else
            return StaticElectricity.permissionHandler.has(player, permnode);
    }
}