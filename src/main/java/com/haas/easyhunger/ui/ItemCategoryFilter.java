package com.haas.easyhunger.ui;

import java.util.Set;
import java.util.HashSet;

/**
 * Utility class to categorize items by their source mod.
 * This allows filtering items in the UI by mod origin.
 */
public class ItemCategoryFilter {
    
    // Category names
    public static final String VANILLA = "vanilla";
    public static final String SNIP3 = "snip3";
    public static final String HIDDENS = "hiddens";
    public static final String NOCUBE = "nocube";
    public static final String ALE = "ale";
    public static final String CHEFF = "cheff";
    public static final String GALORE = "galore";
    public static final String CONNOR = "connor";
    public static final String OTHER = "other";
    
    // HiddensHarvestDelights - exact item IDs from mod files
    private static final Set<String> HIDDENS_ITEMS = new HashSet<>();
    static {
        HIDDENS_ITEMS.add("Avacado_Toast");
        HIDDENS_ITEMS.add("Beef_Well_Don");
        HIDDENS_ITEMS.add("Berry_Glazed_Cod");
        HIDDENS_ITEMS.add("Birthday_Cake");
        HIDDENS_ITEMS.add("Burger_Fries");
        HIDDENS_ITEMS.add("Charcuterie_Board");
        HIDDENS_ITEMS.add("Cheese_Steak");
        HIDDENS_ITEMS.add("Chicken_Buttered");
        HIDDENS_ITEMS.add("Chicken_Dumplings");
        HIDDENS_ITEMS.add("Chocolate_Swissroll");
        HIDDENS_ITEMS.add("Completo_Plate");
        HIDDENS_ITEMS.add("Egg_Benny_Toast");
        HIDDENS_ITEMS.add("Egg_Toast");
        HIDDENS_ITEMS.add("Fajita_Skillet");
        HIDDENS_ITEMS.add("Fish_Tacos");
        HIDDENS_ITEMS.add("Fish_Tacos_Corn");
        HIDDENS_ITEMS.add("Fried_Chicken_Dinner");
        HIDDENS_ITEMS.add("General_Whos_Chicken");
        HIDDENS_ITEMS.add("Hamburger_Steak");
        HIDDENS_ITEMS.add("HiddenIsme_Pico");
        HIDDENS_ITEMS.add("Hot_Dogs");
        HIDDENS_ITEMS.add("Ingredient_Butter");
        HIDDENS_ITEMS.add("Ingredient_Sugar");
        HIDDENS_ITEMS.add("Junk_Cake");
        HIDDENS_ITEMS.add("Kebab_Beef");
        HIDDENS_ITEMS.add("Mac_Cheese");
        HIDDENS_ITEMS.add("Mango_Juice");
        HIDDENS_ITEMS.add("Meat_Toast");
        HIDDENS_ITEMS.add("Minced_Meat_Cutlet");
        HIDDENS_ITEMS.add("Omelette_Fried_Rice");
        HIDDENS_ITEMS.add("Pizza_Flatbread");
        HIDDENS_ITEMS.add("Pork_Cutlet_Sandwhich");
        HIDDENS_ITEMS.add("Potato_Nachos");
        HIDDENS_ITEMS.add("Regular_Cheesecake");
        HIDDENS_ITEMS.add("Saucy_Katsudon");
        HIDDENS_ITEMS.add("Shepps_Pie");
        HIDDENS_ITEMS.add("Spaghetti");
        HIDDENS_ITEMS.add("Steak_Dinner");
        HIDDENS_ITEMS.add("Steak_Tower");
        HIDDENS_ITEMS.add("Sushi_Platter");
        HIDDENS_ITEMS.add("Three_Elote");
        HIDDENS_ITEMS.add("Torta_Milanesa");
        HIDDENS_ITEMS.add("Tostada_Homemade");
        HIDDENS_ITEMS.add("Tuna_Rice");
        HIDDENS_ITEMS.add("Twice_Baked_Potato");
        HIDDENS_ITEMS.add("Veggie_Pizza_Flatbread");
        HIDDENS_ITEMS.add("Wings_And_Fries");
    }
    
    // Food Galore - exact item IDs from mod files
    private static final Set<String> GALORE_ITEMS = new HashSet<>();
    static {
        GALORE_ITEMS.add("Burger");
        GALORE_ITEMS.add("CoffeeCup");
        GALORE_ITEMS.add("Cup");
        GALORE_ITEMS.add("EggToast");
        GALORE_ITEMS.add("FoodOrb");
        GALORE_ITEMS.add("FriedEgg");
        GALORE_ITEMS.add("GoldenApple");
        GALORE_ITEMS.add("GoldenCarrot");
        GALORE_ITEMS.add("IceCream");
        GALORE_ITEMS.add("MilkBox");
        GALORE_ITEMS.add("Omelet");
        GALORE_ITEMS.add("Onigiri");
        GALORE_ITEMS.add("Pizza");
        GALORE_ITEMS.add("Plate");
        GALORE_ITEMS.add("Pudding");
        GALORE_ITEMS.add("Sandwich");
        GALORE_ITEMS.add("SpeedCup");
        GALORE_ITEMS.add("SprinkleIceCream");
        GALORE_ITEMS.add("Sushi");
        GALORE_ITEMS.add("Toast");
    }
    
    // SNIP3 FoodPack - exact item IDs from mod files
    private static final Set<String> SNIP3_ITEMS = new HashSet<>();
    static {
        // Foods (68 items total)
        SNIP3_ITEMS.add("Food_Bibimbap");
        SNIP3_ITEMS.add("Food_Cheese_Burger");
        SNIP3_ITEMS.add("Food_Cheese_Cake");
        SNIP3_ITEMS.add("Food_Chicken_Burger");
        SNIP3_ITEMS.add("Food_Chicken_Nuggets");
        SNIP3_ITEMS.add("Food_Chicken_Tenders");
        SNIP3_ITEMS.add("Food_Chocolate_Cake");
        SNIP3_ITEMS.add("Food_Completo");
        SNIP3_ITEMS.add("Food_Dumpling_Gyoza");
        SNIP3_ITEMS.add("Food_Dumpling_Samosa");
        SNIP3_ITEMS.add("Food_Egg_Bacon");
        SNIP3_ITEMS.add("Food_Falafel_Bread");
        SNIP3_ITEMS.add("Food_Fish_Chip");
        SNIP3_ITEMS.add("Food_Fried_Egg");
        SNIP3_ITEMS.add("Food_Fried_Potato");
        SNIP3_ITEMS.add("Food_Grilled_Aubergine");
        SNIP3_ITEMS.add("Food_Gyudon");
        SNIP3_ITEMS.add("Food_Ham_Burger");
        SNIP3_ITEMS.add("Food_Hot_Dog");
        SNIP3_ITEMS.add("Food_Kebab_Bread");
        SNIP3_ITEMS.add("Food_Mud_Cake");
        SNIP3_ITEMS.add("Food_Oat_Porridge");
        SNIP3_ITEMS.add("Food_Oat_Porridge_Berry");
        SNIP3_ITEMS.add("Food_Omelette");
        SNIP3_ITEMS.add("Food_Pasta");
        SNIP3_ITEMS.add("Food_Pasta_Bologonese");
        SNIP3_ITEMS.add("Food_Pasta_Carbonara");
        SNIP3_ITEMS.add("Food_Pasta_Mushroom");
        SNIP3_ITEMS.add("Food_Pasta_Tomato");
        SNIP3_ITEMS.add("Food_Pizza_Cheese");
        SNIP3_ITEMS.add("Food_Pizza_Kebab");
        SNIP3_ITEMS.add("Food_Pizza_Mushroom");
        SNIP3_ITEMS.add("Food_Pizza_Pepperoni");
        SNIP3_ITEMS.add("Food_Ramen_Pork");
        SNIP3_ITEMS.add("Food_Red_Velvet_Cake");
        SNIP3_ITEMS.add("Food_Roasted_Cauliflower");
        SNIP3_ITEMS.add("Food_Sandwich_Fried_Egg");
        SNIP3_ITEMS.add("Food_Sandwich_Steak_Cheese");
        SNIP3_ITEMS.add("Food_Sauce_Dog");
        SNIP3_ITEMS.add("Food_Soup_BeetRoot");
        SNIP3_ITEMS.add("Food_Soup_Meat");
        SNIP3_ITEMS.add("Food_Soup_Mushroom");
        SNIP3_ITEMS.add("Food_Special_Cake");
        SNIP3_ITEMS.add("Food_Steak_Chip");
        SNIP3_ITEMS.add("Food_Sushi_Egg");
        SNIP3_ITEMS.add("Food_Sushi_Roll");
        SNIP3_ITEMS.add("Food_Sushi_Salmon");
        SNIP3_ITEMS.add("Food_Sushi_Tuna");
        SNIP3_ITEMS.add("Food_Taco");
        SNIP3_ITEMS.add("Food_Takoyaki");
        // Jams
        SNIP3_ITEMS.add("Food_Jam_Apple");
        SNIP3_ITEMS.add("Food_Jam_Azure");
        SNIP3_ITEMS.add("Food_Jam_Berry");
        SNIP3_ITEMS.add("Food_Jam_Mango");
        SNIP3_ITEMS.add("Food_Jam_PinkBerry");
        SNIP3_ITEMS.add("Food_Jam_PoisonTree");
        SNIP3_ITEMS.add("Food_Jam_SpiralTree");
        SNIP3_ITEMS.add("Food_Jam_WindWillow");
        // Juices (drinks)
        SNIP3_ITEMS.add("Food_Juice_Apple");
        SNIP3_ITEMS.add("Food_Juice_Azure");
        SNIP3_ITEMS.add("Food_Juice_Berry");
        SNIP3_ITEMS.add("Food_Juice_Carrot");
        SNIP3_ITEMS.add("Food_Juice_Mango");
        SNIP3_ITEMS.add("Food_Juice_Pinkberry");
        SNIP3_ITEMS.add("Food_Juice_PoisonTree");
        SNIP3_ITEMS.add("Food_Juice_SpiralTree");
        SNIP3_ITEMS.add("Food_Juice_WindWillow");
        // Ingredients
        SNIP3_ITEMS.add("Ingredient_Raw_Fries_Potato");
        SNIP3_ITEMS.add("Ingredient_Raw_Pasta");
    }
    
    // AleAndHearth - Food items that would be confused with Vanilla
    private static final Set<String> ALE_ITEMS = new HashSet<>();
    static {
        ALE_ITEMS.add("Food_Plate_Egg");
        ALE_ITEMS.add("Food_Plate_Sausages");
        ALE_ITEMS.add("Food_Sandwich");
    }
    
    // Connor More Food Stuff - exact item IDs from mod files
    private static final Set<String> CONNOR_ITEMS = new HashSet<>();
    static {
        CONNOR_ITEMS.add("Food_Bread_Slices");
        CONNOR_ITEMS.add("Food_Butter");
        CONNOR_ITEMS.add("Food_Drink_Apple");
        CONNOR_ITEMS.add("Food_Drink_Berry");
        CONNOR_ITEMS.add("Food_Drink_Berry_Shake");
        CONNOR_ITEMS.add("Food_Drink_Coconut_Milk");
        CONNOR_ITEMS.add("Food_Drink_Mango");
        CONNOR_ITEMS.add("Food_Drink_Milk");
        CONNOR_ITEMS.add("Food_Mango_Pie");
        CONNOR_ITEMS.add("Food_Pie_Azure");
        CONNOR_ITEMS.add("Food_Pie_Berry");
        CONNOR_ITEMS.add("Food_Pie_Pink_Berry");
        CONNOR_ITEMS.add("Food_Salad_Corn");
    }
    
    /**
     * Gets the category for an item based on its ID.
     * @param itemId The item ID to categorize
     * @return The category name (vanilla, snip3, nocube, ale, cheff, other)
     */
    public static String getCategory(String itemId) {
        if (itemId == null || itemId.isEmpty()) {
            return OTHER;
        }
        
        String id = itemId.toLowerCase();
        
        // Check exact SNIP3 items first
        if (SNIP3_ITEMS.contains(itemId)) {
            return SNIP3;
        }

        // Check exact HIDDENS items
        if (HIDDENS_ITEMS.contains(itemId)) {
            return HIDDENS;
        }
        
        // Check exact GALORE items
        if (GALORE_ITEMS.contains(itemId)) {
            return GALORE;
        }
        
        // Check exact CONNOR items
        if (CONNOR_ITEMS.contains(itemId)) {
            return CONNOR;
        }
        
        // NoCube items
        if (id.startsWith("nocube_")) {
            return NOCUBE;
        }
        
        // Ale & Hearth / Brewery items
        if (id.startsWith("bowl_") || id.startsWith("brewery_") || id.contains("aleandhearth") || ALE_ITEMS.contains(itemId)) {
            return ALE;
        }
        
        // AndiemgCheff items (both prefixes)
        if (id.startsWith("andiemgcheff_") || id.startsWith("andiechef_")) {
            return CHEFF;
        }
        
        // Vanilla items (Hytale base game)
        if (id.startsWith("food_") || id.startsWith("plant_") || id.startsWith("ingredient_") 
            || id.startsWith("halloween_") || id.startsWith("christmas_")) {
            return VANILLA;
        }
        
        // EasyHunger mod items
        if (id.startsWith("easyhunger_")) {
            return OTHER;
        }
        
        return OTHER;
    }
    
    /**
     * Checks if an item matches a specific category.
     * @param itemId The item ID to check
     * @param category The category to match against
     * @return true if the item belongs to the category
     */
    public static boolean matchesCategory(String itemId, String category) {
        if (category == null || category.equals("all")) {
            return true;
        }
        return getCategory(itemId).equals(category);
    }
    
    /**
     * Checks if an item is from the SNIP3 FoodPack mod.
     */
    public static boolean isSnip3Item(String itemId) {
        return SNIP3_ITEMS.contains(itemId);
    }

    /**
     * Checks if an item is from the HiddensHarvestDelights mod.
     */
    public static boolean isHiddensItem(String itemId) {
        return HIDDENS_ITEMS.contains(itemId);
    }

    /**
     * Checks if an item is from the Food Galore mod.
     */
    public static boolean isGaloreItem(String itemId) {
        return GALORE_ITEMS.contains(itemId);
    }
}
