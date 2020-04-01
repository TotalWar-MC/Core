package com.steffbeard.totalwar.modules.npc.tasks;

import net.countercraft.movecraft.craft.Craft;
import nl.thewgbbroz.dtltraders.guis.tradegui.items.TradableGUIItem;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import com.steffbeard.totalwar.modules.npc.NpcMain;
import com.steffbeard.totalwar.modules.npc.utils.CargoUtils;

import java.util.List;

public class UnloadTask extends CargoTask {
    
	public UnloadTask(Craft craft, TradableGUIItem item){
        super(craft,item);
    }

    public void execute(){
        List<Inventory> invs = CargoUtils.getInventories(craft, item.getMainItem(), Material.CHEST, Material.TRAPPED_CHEST);
//        if (!CargoMain.isIsPre1_13()) {
//            invs.addAll(CargoUtils.getInventoriesWithSpace(craft, item.getMainItem(), Material.BARREL));
//        }
        Inventory inv = invs.get(0);
        int count = 0;
        for(int i = 0; i<inv.getSize();i++){
            if(inv.getItem(i) != null && inv.getItem(i).isSimilar(item.getMainItem())){
                count+=inv.getItem(i).getAmount();
                inv.setItem(i,null);
            }
        }
        originalPilot.sendMessage(NpcMain.SUCCESS_TAG + "Unloaded " + count + " worth $" + String.format("%.2f", count*item.getTradePrice()) + " took a tax of " + String.format("%.2f",NpcMain.getUnloadTax()*count*item.getTradePrice()));
        NpcMain.getEconomy().depositPlayer(originalPilot,count*item.getTradePrice()*(1-NpcMain.getUnloadTax()));

        if(invs.size()<=1){
            this.cancel();
            NpcMain.getQue().remove(originalPilot);
            originalPilot.sendMessage(NpcMain.SUCCESS_TAG + "All cargo unloaded");
            return;
        }
        new ProcessingTask(originalPilot, item,invs.size()).runTaskTimer(NpcMain.getInstance(),0,20);
    }
}
