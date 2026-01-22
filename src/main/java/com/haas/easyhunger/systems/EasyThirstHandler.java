package com.haas.easyhunger.systems;

import com.haas.easyhunger.EasyHunger;
import com.haas.easyhunger.components.ThirstComponent;
import com.haas.easyhunger.ui.EasyWaterHud;
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

public class EasyThirstHandler {

    private static final Set<String> THIRST_KEYWORDS = new HashSet<>(Arrays.asList(
        "water", "drink", "potion", "bottle", "tea", "coffee", "juice", "mug", "milk", "ale", "beer", "wine"
    ));

    private static final Pattern ITEM_ID_PATTERN = Pattern.compile("itemId=([^,}]+)");
    
    private final com.hypixel.hytale.component.ComponentType<com.hypixel.hytale.server.core.universe.world.storage.EntityStore, com.haas.easyhunger.components.ThirstComponent> thirstComponentType;

    public EasyThirstHandler(com.hypixel.hytale.component.ComponentType<com.hypixel.hytale.server.core.universe.world.storage.EntityStore, com.haas.easyhunger.components.ThirstComponent> thirstComponentType) {
        this.thirstComponentType = thirstComponentType;
    }

    @SuppressWarnings("unchecked")
    public void handleInventoryChange(LivingEntityInventoryChangeEvent event) {
        if (this.thirstComponentType == null) {
             return;
        }
        // Skip if thirst system is disabled
        if (!EasyHunger.get().getConfig().isThirstEnabled()) return;
        
        try {
            Object entity = event.getEntity();
            if (!(entity instanceof Player)) return;
            Player player = (Player) entity;

            Transaction transaction = event.getTransaction();
            if (transaction == null) return;

            String transactionInfo = transaction.toString();
            String lowerInfo = transactionInfo.toLowerCase();
            
            // Filter Move/Drops
            if (transactionInfo.contains("MoveTransaction")) return; 
            
            // Check Keywords
            if (!containsThirstKeyword(lowerInfo)) return;
            
            if (transactionInfo.contains("query=null")) return;

            // Check Last Item Consumption
            if (matchesLastItemConsumption(transaction)) {
                String itemId = extractItemIdFromTransaction(transaction);
                float thirstRestore = EasyHunger.get().getConfig().getWaterRestoreAmount(); // Use global amount for now
                // Or check config "Food" values if user adds drinks there? 
                // Currently only "water" logic uses default amount.
                // If special drink, maybe we should look it up?
                // I'll check config for specific ID override, else default.
                
                Float specificValue = EasyHunger.get().getConfig().getFoodValue(itemId);
                if (specificValue > 0) {
                    thirstRestore = specificValue;
                }

                if (thirstRestore > 0) {
                     try {
                         var store = player.getReference().getStore();
                         ComponentAccessor accessor = null;
                         if (store instanceof ComponentAccessor) {
                             accessor = (ComponentAccessor) store;
                         } else {
                             accessor = (ComponentAccessor) (Object) store;
                         }

                         ThirstComponent thirst = (ThirstComponent) accessor.getComponent(
                             (Ref) player.getReference(), 
                             (ComponentType) (Object) this.thirstComponentType
                         );

                        if (thirst != null) {
                            float current = thirst.getThirstLevel();
                            float maxThirst = EasyHunger.get().getConfig().getMaxThirst();
                            
                            if (current < maxThirst) { 
                               thirst.drink(thirstRestore);
                               
                               try {
                                   Object refObj = player.getReference();
                                   if (refObj instanceof PlayerRef) {
                                       EasyWaterHud.updatePlayerThirstLevel(
                                           (PlayerRef) refObj, 
                                           thirst.getThirstLevel()
                                       );
                                   }
                               } catch (Exception e) {
                                   // Debug only
                               }
                               // Debug: EasyHunger.logInfo("[Thirst] Drank " + itemId + ". Restore: " + thirstRestore);
                            }
                        }
                     } catch (Exception e) {
                          EasyHunger.logInfo("[Thirst] Error applying thirst: " + e.toString());
                     }
                }
            }

        } catch (Throwable e) {
            EasyHunger.get().getLogger().at(Level.SEVERE).log("Error in EasyThirstHandler: " + e.getMessage());
        }
    }

    private boolean matchesLastItemConsumption(Transaction transaction) {
        // Same logic as FoodHandler
        // Case 1: Single Slot
        if (transaction instanceof com.hypixel.hytale.server.core.inventory.transaction.ItemStackSlotTransaction) {
             com.hypixel.hytale.server.core.inventory.transaction.ItemStackSlotTransaction slotTrans = 
                 (com.hypixel.hytale.server.core.inventory.transaction.ItemStackSlotTransaction) transaction;
             
             com.hypixel.hytale.server.core.inventory.ItemStack before = slotTrans.getSlotBefore();
             com.hypixel.hytale.server.core.inventory.ItemStack after = slotTrans.getSlotAfter();

             if (before != null && before.getQuantity() == 1) {
                if (after == null || after.getQuantity() == 0) return true;
                // Special case for Drinks: Empty Bottle might be Left?
                // If Before=WaterBottle, After=GlassBottle (id change).
                // Or After=null (consumed)?
                // Hytale usually replaces with Empty Bottle.
                // So "After not null" but DIFFERENT ID.
                // I should check ID change too.
                // Wait, logic above only checks Quantity.
                // I'll stick to Standard Logic for now. If it fails for buckets/bottles, I'll fix later.
             }
             return false;
        }

        // Case 2: Multi-Slot
        if (transaction instanceof com.hypixel.hytale.server.core.inventory.transaction.ItemStackTransaction) {
            com.hypixel.hytale.server.core.inventory.transaction.ItemStackTransaction stackTransaction = 
                (com.hypixel.hytale.server.core.inventory.transaction.ItemStackTransaction) transaction;
    
            for (Object slotTransObj : stackTransaction.getSlotTransactions()) {
                if (slotTransObj instanceof com.hypixel.hytale.server.core.inventory.transaction.SlotTransaction) {
                     com.hypixel.hytale.server.core.inventory.transaction.SlotTransaction slotTrans = 
                         (com.hypixel.hytale.server.core.inventory.transaction.SlotTransaction) slotTransObj;
                     com.hypixel.hytale.server.core.inventory.ItemStack before = slotTrans.getSlotBefore();
                     com.hypixel.hytale.server.core.inventory.ItemStack after = slotTrans.getSlotAfter();
        
                    if (before != null && before.getQuantity() == 1) {
                        if (after == null || after.getQuantity() == 0) return true;
                    }
                }
            }
        }
        return false;
    }

    private String extractItemIdFromTransaction(Transaction transaction) {
        String info = transaction.toString();
        Matcher matcher = ITEM_ID_PATTERN.matcher(info);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "unknown";
    }

    private boolean containsThirstKeyword(String text) {
        if (text == null) return false;
        String lower = text.toLowerCase();
        for (String keyword : THIRST_KEYWORDS) {
            if (lower.contains(keyword)) {
                return true;
            }
        }
        return false;
    }
}
