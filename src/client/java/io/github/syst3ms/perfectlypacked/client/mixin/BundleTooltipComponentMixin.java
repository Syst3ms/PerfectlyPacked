package io.github.syst3ms.perfectlypacked.client.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.syst3ms.perfectlypacked.client.SquarePackingRendering;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.BundleTooltipComponent;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

@Mixin(BundleTooltipComponent.class)
abstract class BundleTooltipComponentMixin implements TooltipComponent {
	@Shadow
	@Final
	private static Identifier BACKGROUND_TEXTURE;
	@Shadow
	@Final
	private DefaultedList<ItemStack> inventory;

	@Shadow
	protected abstract void drawSlot(int x, int y, int index, boolean shouldBlock, DrawContext context,
									 TextRenderer textRenderer);

	@Shadow @Final private int occupancy;

	@Unique
	private int size() {
		return this.inventory.size() + 1;
	}

	@ModifyReturnValue(method = "getColumns", at = @At("RETURN"))
	private int useSquareColumns(int original) {
		return SquarePackingRendering.trivialPackingSide(size());
	}

	@ModifyReturnValue(method = "getRows", at = @At("RETURN"))
	private int useSquareRows(int original) {
		return SquarePackingRendering.trivialPackingSide(size());
	}

	@ModifyReturnValue(method = "getHeight", at = @At("RETURN"))
	private int handleSpecialHeight(int original) {
		return SquarePackingRendering.getSquareSize(size(), 20) + 4;
	}

	@ModifyReturnValue(method = "getWidth", at = @At("RETURN"))
	private int handleSpecialWidth(int original) {
		return SquarePackingRendering.getSquareSize(size());
	}

	@Inject(method = "drawItems", at = @At("HEAD"), cancellable = true)
	private void handleSpecialNumbers(TextRenderer textRenderer, int x, int y, DrawContext context, CallbackInfo ci) {
		boolean full = occupancy >= 64;

		if (SquarePackingRendering.tryDrawSpecial(
				size(),
				size -> context.drawGuiTexture(BACKGROUND_TEXTURE, x, y, size, size),
				(x1, y1, index) -> BundleTooltipComponentMixin.this.drawSlot(x1, y1, index, full, context, textRenderer),
				x, y,
				context.getMatrices()
		)) {
			ci.cancel();
		}
	}
}


