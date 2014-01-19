package com.tehbeard.beardstatinv;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.google.gson.annotations.Expose;
import com.tehbeard.beardstat.containers.EntityStatBlob;
import com.tehbeard.beardstat.containers.documents.IStatDocument;
import com.tehbeard.beardstat.containers.documents.IStatDynamicDocument;
import com.tehbeard.beardstat.containers.documents.StatDocument;
import com.tehbeard.beardstat.containers.documents.docfile.DocumentFile;
import com.tehbeard.beardstat.dataproviders.IStatDataProvider;

@StatDocument(value="inventory",singleInstance=true)
public class InventoryDocument implements IStatDynamicDocument{

    @Expose
    private List<JsonItem> inventory = new ArrayList<JsonItem>();

    @Expose
    private JsonItem helmet = null;

    @Expose
    private JsonItem chestplate = null;

    @Expose
    private JsonItem leggings = null;

    @Expose
    private JsonItem boots = null;

    public IStatDocument mergeDocument(DocumentFile file) {
        // Overwrite document
        return this;
    }

    public void updateDocument(EntityStatBlob blob) {
        if(!Bukkit.isPrimaryThread()){
            if(blob == null){return;}
            Player p = Bukkit.getPlayerExact(blob.getName());
            
            if(p != null){
                inventory.clear();
                //Update player inventory
                ItemStack[] inv = p.getInventory().getContents();
                for(int i = 0;i<inv.length;i++){
                    if(inv[i]!=null){
                        inventory.add(new JsonItem(i, inv[i]));
                    }
                }
                if(p.getInventory().getHelmet()!=null){
                    helmet = new JsonItem(-1,p.getInventory().getHelmet());
                }

                if(p.getInventory().getChestplate()!=null){
                    helmet = new JsonItem(-1,p.getInventory().getChestplate());
                }

                if(p.getInventory().getLeggings()!=null){
                    helmet = new JsonItem(-1,p.getInventory().getLeggings());
                }

                if(p.getInventory().getBoots()!=null){
                    helmet = new JsonItem(-1,p.getInventory().getBoots());
                }
            }
        }

    }

}
