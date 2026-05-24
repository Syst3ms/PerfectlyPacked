package io.github.syst3ms.perfectlypacked.client.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.syst3ms.perfectlypacked.client.PerfectlyPacked;
import io.github.syst3ms.perfectlypacked.client.PerfectlyPackedConfig;
import io.github.syst3ms.perfectlypacked.client.SquarePackingRendering;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientBundleTooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.component.BundleContents;
import org.apache.commons.lang3.math.Fraction;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ClientBundleTooltip.class)
abstract class ClientBundleTooltipMixin implements ClientTooltipComponent {
	@Shadow
	@Final
	private static int PROGRESSBAR_MARGIN_Y;
	@Shadow
	@Final
	private static int PROGRESSBAR_HEIGHT;
	@Shadow
	@Final
	private static Identifier PROGRESSBAR_BORDER_SPRITE;
	@Shadow
	@Final
	private BundleContents contents;

	@Unique
    private int graphicalSlotSize;
	@Unique
    private int slotPadding;
	@Unique
    private int maxSpecialPackingSize;
	@Unique
    private boolean useRectangularRendering;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void initializeFromConfig(BundleContents contents, CallbackInfo ci) {
		this.slotPadding = PerfectlyPackedConfig.getInstance().paddingSize;
		this.graphicalSlotSize = PerfectlyPackedConfig.getInstance().slotSize + slotPadding * 2;
		this.maxSpecialPackingSize = PerfectlyPackedConfig.getInstance().maxSpecialPackingSize;
		this.useRectangularRendering = PerfectlyPackedConfig.getInstance().trivialPackingRendering == PerfectlyPackedConfig.TrivialPackingRendering.RECTANGULAR;
	}

	@Unique
	private boolean shouldRenderNonVanilla(int n) {
		return n != 0 && useRectangularRendering && n <= maxSpecialPackingSize;
	}

	@Shadow
	protected abstract void extractSlot(int slotNumber, int drawX, int drawY, List<ItemStackTemplate> shownItems, int slotIndex, Font font, GuiGraphicsExtractor graphics);

	@Shadow
	protected abstract List<ItemStackTemplate> getShownItems(int amountOfItemsToShow);

	@Shadow
	public abstract int getWidth(Font font);

	@Shadow
	private static Identifier getProgressBarTexture(Fraction weight) {
		throw new UnsupportedOperationException("Implemented via mixin");
	}

	@Shadow
	private static @Nullable Component getProgressBarFillText(Fraction weight) {
		throw new UnsupportedOperationException("Implemented via mixin");
	}

	@Shadow
	protected abstract void extractSelectedItemTooltip(Font font, GuiGraphicsExtractor graphics, int x, int y, int w);

	@ModifyConstant(
			method = "slotCount",
			constant = @Constant(intValue = 12)
	)
	private int slotCountUseConfig(int original) {
		return PerfectlyPackedConfig.getInstance().maxVanillaBundleSlotsToShow;
	}

	@ModifyConstant(
			method = "extractBundleWithItemsTooltip",
			constant = @Constant(intValue = 12)
	)
	private int isOverflowingUseConfig(int original) {
		return PerfectlyPackedConfig.getInstance().maxVanillaBundleSlotsToShow;
	}

	@Redirect(
			method = "extractBundleWithItemsTooltip",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/component/BundleContents;getNumberOfItemsToShow()I")
	)
	private int properNumberOfItemsToShow(BundleContents instance) {
		return PerfectlyPacked.bundleNumberOfItemsToShow(instance);
	}

	@ModifyExpressionValue(
			method = "backgroundHeight",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/tooltip/ClientBundleTooltip;itemGridHeight()I")
	)
	private int specialBackgroundHeight(int original) {
		int n = this.contents.size();
		return shouldRenderNonVanilla(n) ? SquarePackingRendering.getHeight(graphicalSlotSize, n) : original;
	}

	@ModifyReturnValue(
			method = "getWidth",
			at = @At("RETURN")
	)
	private int specialBackgroundWidth(int original) {
		int n = this.contents.size();
		return shouldRenderNonVanilla(n) ? SquarePackingRendering.getWidth(graphicalSlotSize, n) : original;
	}

	@Unique
    private static void properExtractProgressBar(final int x, final int y, final int progressBarWidth, final Font font, final GuiGraphicsExtractor graphics, final Fraction weight) {
		var maxFill = progressBarWidth - 2;
		var progressBarFill = Mth.clamp(Mth.mulAndTruncate(weight, maxFill), 0, maxFill);
		graphics.blitSprite(RenderPipelines.GUI_TEXTURED, getProgressBarTexture(weight), x + 1, y, progressBarFill, PROGRESSBAR_HEIGHT);
		graphics.blitSprite(RenderPipelines.GUI_TEXTURED, PROGRESSBAR_BORDER_SPRITE, x, y, progressBarWidth, PROGRESSBAR_HEIGHT);
		Component progressBarFillText = getProgressBarFillText(weight);
		if (progressBarFillText != null) {
			graphics.centeredText(font, progressBarFillText, x + progressBarWidth / 2, y + 3, 0xffffffff);
		}
	}

	@Inject(method = "extractBundleWithItemsTooltip", at = @At("HEAD"), cancellable = true)
	private void handleSpecialNumbers(Font font, int x, int y, int w, int h, GuiGraphicsExtractor graphics, Fraction weight, CallbackInfo ci) {
		var n = this.contents.size();

        if (SquarePackingRendering.SPECIAL_SLOT_COUNTS.contains(n)) {
            var allItems = this.getShownItems(this.contents.size());
            var squareSize = SquarePackingRendering.getSpecialSquareSize(graphicalSlotSize, n);
            var squareXOffset = (w - squareSize) / 2;
            SquarePackingRendering.drawSpecial(
                    n,
					graphicalSlotSize,
                    (x1, y1, index) -> ClientBundleTooltipMixin.this.extractSlot(
							n - index,
							x1 + slotPadding, y1 + slotPadding,
							allItems, index, font, graphics
					),
                    x + squareXOffset, y,
                    graphics.pose()
            );

			this.extractSelectedItemTooltip(font, graphics, x, y, w);
            properExtractProgressBar(x + squareXOffset, y + squareSize + PROGRESSBAR_MARGIN_Y, squareSize, font, graphics, weight);
            ci.cancel();
        } else if (shouldRenderNonVanilla(n)) {
			List<ItemStackTemplate> shownItems = this.contents.items();
			int gridSizeX = SquarePackingRendering.trivialPackingWidth(n);
			int gridSizeY = Mth.positiveCeilDiv(n, gridSizeX);
			int rectWidth = gridSizeX * graphicalSlotSize;
			int rectHeight = gridSizeY * graphicalSlotSize;
			int xStartPos = x + (w + rectWidth) / 2;
			int yStartPos = y + rectHeight;
			int slotNumber = 1;

			for(int rowNumber = 1; rowNumber <= gridSizeY; ++rowNumber) {
				for(int columnNumber = 1; columnNumber <= gridSizeX; ++columnNumber) {
					int drawX = xStartPos - columnNumber * graphicalSlotSize + slotPadding;
					int drawY = yStartPos - rowNumber * graphicalSlotSize + slotPadding;
					if (slotNumber <= n) {
						this.extractSlot(slotNumber, drawX, drawY, shownItems, slotNumber, font, graphics);
						++slotNumber;
					}
				}
			}

			this.extractSelectedItemTooltip(font, graphics, x, y, w);
			properExtractProgressBar(x + (w - rectHeight) / 2, y + rectHeight + 4, rectWidth, font, graphics, weight);

			ci.cancel();
        }
    }
}


