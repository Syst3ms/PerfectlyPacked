package io.github.syst3ms.perfectlypacked.client.mixin;

import io.github.syst3ms.perfectlypacked.client.PerfectlyPacked;
import net.minecraft.client.gui.BundleMouseActions;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BundleMouseActions.class)
public class BundleMouseActionsMixin {
    @Redirect(
            method = "onMouseScrolled",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/BundleItem;getNumberOfItemsToShow(Lnet/minecraft/world/item/ItemStack;)I")
    )
    private int fixNumberOfItems_scroll(ItemStack stack) {
        return PerfectlyPacked.bundleNumberOfItemsToShow(stack.getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY));
    }

    @Redirect(
            method = "toggleSelectedBundleItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/BundleItem;getNumberOfItemsToShow(Lnet/minecraft/world/item/ItemStack;)I")
    )
    private int fixNumberOfItems_toggle(ItemStack stack) {
        return PerfectlyPacked.bundleNumberOfItemsToShow(stack.getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY));
    }
}
