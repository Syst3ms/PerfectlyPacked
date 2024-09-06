package io.github.syst3ms.perfectlypacked.client.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.syst3ms.perfectlypacked.client.SquarePackingRendering;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.tooltip.BundleTooltipComponent;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

@Mixin(BundleTooltipComponent.class)
abstract class BundleTooltipComponentMixin implements TooltipComponent {
	@Unique
	private static final int SLOT_SIZE = 18;
	@Shadow
	@Final
	private DefaultedList<ItemStack> inventory;
	@Unique
	private static boolean SPECIAL = false;

	@Shadow @Final private int occupancy;

	@Shadow protected abstract void drawSlot(int x, int y, int index, boolean shouldBlock, TextRenderer textRenderer, MatrixStack matrices, ItemRenderer itemRenderer, int z);

	@Unique
	private int size() {
		return this.inventory.size() + 1;
	}

	@ModifyArg(
			method = "draw",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawableHelper;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIFFIIII)V"),
			index = 7
	)
	private int correctHeightForSpecial(int height) {
		return SPECIAL ? SLOT_SIZE : height;
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

	@Unique
	private static void drawBackground(MatrixStack matrices, int x, int y, int squareSize) {
		DrawableHelper.fill(matrices, x, y, x + squareSize, y + squareSize, 0xFFC6C6C6);
		DrawableHelper.fill(matrices, x, y + squareSize - 1, x + squareSize, y + squareSize, 0xFF8B8B8B);
	}

	@Inject(method = "drawItems", at = @At("HEAD"), cancellable = true)
	private void handleSpecialNumbers(TextRenderer textRenderer, int x, int y, MatrixStack matrices, ItemRenderer itemRenderer, int z, CallbackInfo ci) {
		boolean full = occupancy >= 64;
		SPECIAL = true;
		MatrixStack tempMatrices = new MatrixStack();
		SquarePackingRendering.TEMP_MATRICES = tempMatrices;

		if (SquarePackingRendering.tryDrawSpecial(
				size(),
				size -> drawBackground(tempMatrices, x, y, size),
				(x1, y1, index) -> BundleTooltipComponentMixin.this.drawSlot(x1, y1, index, full, textRenderer, tempMatrices, itemRenderer, z),
				x, y,
				tempMatrices
		)) {
			ci.cancel();
		} else {
			SPECIAL = false;
		}

		SquarePackingRendering.TEMP_MATRICES = null;
	}
}


