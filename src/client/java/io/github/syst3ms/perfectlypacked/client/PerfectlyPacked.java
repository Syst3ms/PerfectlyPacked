package io.github.syst3ms.perfectlypacked.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.world.item.component.BundleContents;

public class PerfectlyPacked implements ClientModInitializer {
    public static final String VERSION = "2.0.0";
    public static final String MODID = "perfectlypacked";

    @Override
    public void onInitializeClient() {
        PerfectlyPackedConfig.HANDLER.load();
    }

    public static int bundleNumberOfItemsToShow(BundleContents contents) {
        int n = contents.size();
        int maxSlots = PerfectlyPackedConfig.getInstance().maxVanillaBundleSlotsToShow;
        if (n > maxSlots) {
            return maxSlots - 1;
        } else {
            return n;
        }
    }
}
