package com.steffbeard.totalwar.modules.npc.tasks;

import net.countercraft.movecraft.craft.Craft;
import nl.thewgbbroz.dtltraders.guis.tradegui.items.TradableGUIItem;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.steffbeard.totalwar.modules.npc.NpcMain;
import com.steffbeard.totalwar.modules.npc.utils.CargoUtils;

import java.util.List;

public class LoadTask extends CargoTask {
    
	public LoadTask(Craft craft, TradableGUIItem item){
        super(craft,item);
    }

    protected void execute(){
        List<Inventory> invs = CargoUtils.getInventoriesWithSpace(craft, item.getMainItem(), Material.CHEST, Material.TRAPPED_CHEST);
//        if (!CargoMain.isIsPre1_13()) {
//            invs.addAll(Utils.getInventoriesWithSpace(craft, item.getMainItem(), Material.BARREL));
//        }

        Inventory inv = invs.get(0);
        int loaded=0;
        for(int i =0; i < inv.getSize() ; i++)
            if(inv.getItem(i)==null || inv.getItem(i).getType()==Material.AIR || inv.getItem(i).isSimilar(item.getMainItem())){
                int maxCount = (inv.getItem(i)==null || inv.getItem(i).getType()==Material.AIR) ? item.getMainItem().getMaxStackSize() : inv.getItem(i).getMaxStackSize() - inv.getItem(i).getAmount();
                if(NpcMain.getEconomy().getBalance(originalPilot) > item.getTradePrice()*maxCount*(1+NpcMain.getLoadTax())){
                    loaded+=maxCount;
                    ItemStack tempItem = item.getMainItem().clone();
                    tempItem.setAmount(tempItem.getMaxStackSize());
                    inv.setItem(i,tempItem);
                    
                }else{
                    maxCount = (int)(NpcMain.getEconomy().getBalance(originalPilot)/(item.getTradePrice()*(1+NpcMain.getLoadTax())));
                    this.cancel();
                    NpcMain.getQue().remove(originalPilot);
                    originalPilot.sendMessage(NpcMain.SUCCESS_TAG + "You ran out of money!");
                    if(maxCount<=0){
                        if(NpcMain.isDebug()){
                            NpcMain.logger.info("Balance: " + NpcMain.getEconomy().getBalance(originalPilot) + ". maxCount: " + maxCount + ".");
                        }
                        originalPilot.sendMessage(NpcMain.SUCCESS_TAG + "Loaded " + loaded + " items worth $" + String.format("%.2f", loaded*item.getTradePrice()) + " took a tax of " + String.format("%.2f",NpcMain.getLoadTax()*loaded*item.getTradePrice()));
                        return;
                    }
                    ItemStack tempItem = item.getMainItem().clone();
                    if(inv.getItem(i)==null || inv.getItem(i).getType()==Material.AIR) 
                        tempItem.setAmount(maxCount);
                    else
                        tempItem.setAmount(inv.getItem(i).getAmount()+maxCount);
                    inv.setItem(i,tempItem);
                    loaded+=maxCount;
                    if(NpcMain.isDebug()){
                        NpcMain.logger.info("Balance: " + NpcMain.getEconomy().getBalance(originalPilot) + ". maxCount: " + maxCount + ". Actual stack-size: " + tempItem.getAmount());
                    }
                    originalPilot.sendMessage(NpcMain.SUCCESS_TAG + "Loaded " + loaded + " items worth $" + String.format("%.2f", loaded*item.getTradePrice()) + " took a tax of " + String.format("%.2f",NpcMain.getLoadTax()*loaded*item.getTradePrice()));
                    NpcMain.getEconomy().withdrawPlayer(originalPilot,loaded*item.getTradePrice()*(1+NpcMain.getLoadTax()));
                    return;
                }
                NpcMain.getEconomy().withdrawPlayer(originalPilot,maxCount*item.getTradePrice()*(1+NpcMain.getLoadTax()));
            }

        originalPilot.sendMessage(NpcMain.SUCCESS_TAG + "Loaded " + loaded + " items worth $" + String.format("%.2f", loaded*item.getTradePrice()) + " took a tax of " + String.format("%.2f",NpcMain.getLoadTax()*loaded*item.getTradePrice()));
        

        if(invs.size()<= 1){
            this.cancel();
            NpcMain.getQue().remove(originalPilot);
            originalPilot.sendMessage(NpcMain.SUCCESS_TAG + "All cargo loaded");
            return;
        }
        new ProcessingTask(originalPilot, item,invs.size()).runTaskTimer(NpcMain.getInstance(),0,20);
    }
}
