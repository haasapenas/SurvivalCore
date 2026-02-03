package com.haas.easyhunger.systems;

import com.haas.easyhunger.EasyHunger;
import com.haas.easyhunger.EasyHungerUtils;
import com.haas.easyhunger.components.HungerComponent;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.ComponentAccessor;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.entity.LivingEntityInventoryChangeEvent;
import com.hypixel.hytale.server.core.inventory.transaction.Transaction;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.hypixel.hytale.server.core.asset.type.item.config.Item;

public class EasyHungerFoodHandler {



    private static final Pattern QUANTITY_BEFORE_PATTERN = Pattern.compile("slotBefore=ItemStack\\{[^}]*quantity=(\\d+)");
    private static final Pattern QUANTITY_AFTER_PATTERN = Pattern.compile("slotAfter=ItemStack\\{[^}]*quantity=(\\d+)");
    private static final Pattern ITEM_ID_PATTERN = Pattern.compile("itemId=([^,}]+)");
    
    private final com.hypixel.hytale.component.ComponentType<com.hypixel.hytale.server.core.universe.world.storage.EntityStore, com.haas.easyhunger.components.HungerComponent> hungerComponentType;

    public EasyHungerFoodHandler(com.hypixel.hytale.component.ComponentType<com.hypixel.hytale.server.core.universe.world.storage.EntityStore, com.haas.easyhunger.components.HungerComponent> hungerComponentType) {
        this.hungerComponentType = hungerComponentType;
    }

    @SuppressWarnings("unchecked")
    public void handleInventoryChange(LivingEntityInventoryChangeEvent event) {
        if (this.hungerComponentType == null) {
             return;
        }
        try {
            Object entity = event.getEntity();
            // Only players have hunger
            if (!(entity instanceof Player)) return;
            Player player = (Player) entity;

            Transaction transaction = event.getTransaction();
            if (transaction == null) return;

            String transactionInfo = transaction.toString();
            String lowerInfo = transactionInfo.toLowerCase();
            
            // RESTORED FILTERS (User Request: "Use same logic as before")
            // 1. Filter out MoveTransactions (Drops, Moves, Swaps)
            if (transactionInfo.contains("MoveTransaction")) {
                 // EasyHunger.logInfo("[Debug] Ignored: MoveTransaction detected.");
                 return; 
            }

             // 3. Filter null queries
            if (transactionInfo.contains("query=null")) {
                // EasyHunger.logInfo("[Debug] Ignored: query=null");
                return;
            }

            // EasyHunger.logInfo("[Debug] Filters passed. Checking LastItem logic...");

            // 4. Check for Consumption (Decrease by 1)
            boolean consumption = matchesConsumption(transaction);
            // EasyHunger.logInfo("[Debug] matchesLastItemConsumption result: " + lastItem);

        if (consumption) {
            String itemId = extractItemIdFromTransaction(transaction);
            
            // Refactored Food Detection: Use Item Asset definition
            boolean isFood = false;
            Item itemAsset = Item.getAssetMap().getAsset(itemId);
            if (itemAsset != null && itemAsset.isConsumable()) {
                 isFood = true;
            }

            if (isFood) {
                float hungerRestore = EasyHunger.get().getFoodsConfig().getFoodValue(itemId);
                
                if (hungerRestore > 0) {
                     try {
                         var store = player.getReference().getStore();
                         
                         // Helper variable for Accessor (Safe cast attempts)
                         ComponentAccessor accessor = null;
                         if (store instanceof ComponentAccessor) {
                             accessor = (ComponentAccessor) store;
                         } else {
                             // Force cast attempt or log
                             accessor = (ComponentAccessor) (Object) store; // Blind trust
                         }

                         HungerComponent hunger = (HungerComponent) accessor.getComponent(
                             (Ref) player.getReference(), 
                             (ComponentType) (Object) this.hungerComponentType
                         );

                        if (hunger != null) {
                            float current = hunger.getHungerLevel();
                            float maxHunger = EasyHunger.get().getConfig().getMaxHunger();
                            
                            if (current < maxHunger) { 
                               hunger.feed(hungerRestore);
                               
                               // Update HUD (Immediate)
                               try {
                                   if (accessor != null) {
                                       com.hypixel.hytale.server.core.universe.PlayerRef playerRef = 
                                            (com.hypixel.hytale.server.core.universe.PlayerRef) accessor.getComponent(
                                               (Ref) player.getReference(), 
                                               (ComponentType) (Object) com.hypixel.hytale.server.core.universe.PlayerRef.getComponentType()
                                            );
                                            
                                       if (playerRef != null) {
                                           com.haas.easyhunger.ui.EasyHungerHud.updatePlayerHungerLevel(
                                               playerRef, 
                                               hunger.getHungerLevel()
                                           );
                                       }
                                   }
                               } catch (Exception e) {
                                   // EasyHunger.logInfo("[Debug] Failed to update HUD: " + e.getMessage());
                               }
                            }
                        }
                     } catch (Exception e) {
                          EasyHunger.get().getLogger().at(Level.SEVERE).log("Error applying hunger: " + e.toString());
                          e.printStackTrace();
                     }
                }
            }
        }

        } catch (Throwable e) {
            EasyHunger.get().getLogger().at(Level.SEVERE).log("Error in EasyHungerFoodHandler: " + e.getMessage());
        }
    }

    private boolean matchesConsumption(Transaction transaction) {
        // Case 1: Single Slot Transaction (Direct)
        if (transaction instanceof com.hypixel.hytale.server.core.inventory.transaction.ItemStackSlotTransaction) {
             com.hypixel.hytale.server.core.inventory.transaction.ItemStackSlotTransaction slotTrans = 
                 (com.hypixel.hytale.server.core.inventory.transaction.ItemStackSlotTransaction) transaction;
             
             return checkSlotConsumption(slotTrans.getSlotBefore(), slotTrans.getSlotAfter());
        }

        // Case 2: Multi-Slot Transaction (Container)
        if (transaction instanceof com.hypixel.hytale.server.core.inventory.transaction.ItemStackTransaction) {
            com.hypixel.hytale.server.core.inventory.transaction.ItemStackTransaction stackTransaction = 
                (com.hypixel.hytale.server.core.inventory.transaction.ItemStackTransaction) transaction;
    
            for (Object slotTransObj : stackTransaction.getSlotTransactions()) {
                if (slotTransObj instanceof com.hypixel.hytale.server.core.inventory.transaction.SlotTransaction) {
                     com.hypixel.hytale.server.core.inventory.transaction.SlotTransaction slotTrans = 
                         (com.hypixel.hytale.server.core.inventory.transaction.SlotTransaction) slotTransObj;
                     
                     if (checkSlotConsumption(slotTrans.getSlotBefore(), slotTrans.getSlotAfter())) {
                         return true;
                     }
                }
            }
        }
        return false;
    }

    private boolean checkSlotConsumption(com.hypixel.hytale.server.core.inventory.ItemStack before, com.hypixel.hytale.server.core.inventory.ItemStack after) {
        if (before == null) return false;
        
        int quantityBefore = before.getQuantity();
        int quantityAfter = (after != null) ? after.getQuantity() : 0;

        // Detect consumption: Quantity decreases by exactly 1
        return (quantityBefore - quantityAfter) == 1;
    }

    private String extractItemIdFromTransaction(Transaction transaction) {
        // Robust Regex Extraction (Matches User's old mod logic)
        // Works for both ItemStackSlotTransaction and ItemStackTransaction strings
        String info = transaction.toString();
        Matcher matcher = ITEM_ID_PATTERN.matcher(info);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "unknown";
    }


}
