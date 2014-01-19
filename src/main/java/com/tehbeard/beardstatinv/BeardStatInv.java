package com.tehbeard.beardstatinv;

import net.dragonzone.promise.Delegate;
import net.dragonzone.promise.Promise;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.tehbeard.beardstat.BeardStat;
import com.tehbeard.beardstat.containers.EntityStatBlob;
import com.tehbeard.beardstat.containers.documents.DocumentRegistry;
import com.tehbeard.beardstat.manager.EntityStatManager;

public class BeardStatInv extends JavaPlugin implements Listener {
    
    private EntityStatManager manager;


    @Override
    public void onEnable() {
        DocumentRegistry.registerDocument(InventoryDocument.class);
        getServer().getPluginManager().registerEvents(this, this);
        manager = getPlugin(BeardStat.class).getStatManager();
    }
    
    
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        /**
         * Create and/or load the inventory document for each player who joins.
         */
        manager.getBlobForPlayerAsync(event.getPlayer()).onResolve(new Delegate<Void, Promise<EntityStatBlob>>() {
            
            public <P extends Promise<EntityStatBlob>> Void invoke(P params) {
                params.getValue().getDocument("bsinv", "inventory", InventoryDocument.class).getDocument();
                return null;
            }
        });
    }
    
    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        //Fire an update as they leave.
        manager.getBlobForPlayer(event.getPlayer()).getDocument("bsinv", "inventory", InventoryDocument.class).getDocument();
    }
  
}
