package io.github.syst3ms.perfectlypacked.client.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.apache.commons.lang3.math.Fraction;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
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
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

@Mixin(BundleTooltipComponent.class)
abstract class BundleTooltipComponentMixin implements TooltipComponent {
	@Unique
	private static final int SLOT_SIZE = 18;
	@Shadow
	@Final
	private static Identifier BACKGROUND_TEXTURE;
	@Shadow
	@Final
	private BundleContentsComponent field_49537;

	@Shadow
	protected abstract void drawSlot(int x, int y, int index, boolean shouldBlock, DrawContext context,
									 TextRenderer textRenderer);

	@Unique
	private static int trivialPackingSide(int n) {
		return (int) Math.ceil(Math.sqrt(n));
	}

	@Unique
	private int size() {
		return Math.min(this.field_49537.size() + 1, 64);
	}

	@ModifyReturnValue(method = "getColumns", at = @At("RETURN"))
	private int useSquareColumns(int original) {
		return trivialPackingSide(size());
	}

	@ModifyReturnValue(method = "getRows", at = @At("RETURN"))
	private int useSquareRows(int original) {
		return trivialPackingSide(size());
	}

	@Unique
	private static int getSquareSize(int n) {
		return getSquareSize(n, SLOT_SIZE);
	}

	@Unique
	private static int getSquareSize(int n, int trivialRowHeight) {
		return (int) switch (n) {
			case 5 -> Math.ceil(SLOT_SIZE * 2.70710678f);
			case 10 -> Math.ceil(SLOT_SIZE * 3.70710678f);
			case 11 -> Math.ceil(SLOT_SIZE * 3.87708359f);
			case 17 -> Math.ceil(SLOT_SIZE * 4.67553009f);
			case 18 -> Math.ceil(SLOT_SIZE * 4.82287565f);
			case 19 -> Math.ceil(SLOT_SIZE * 4.88561808f);
			case 26 -> Math.ceil(SLOT_SIZE * 5.62132034f);
			case 27 -> Math.ceil(SLOT_SIZE * 5.70710678f);
			case 28 -> Math.ceil(SLOT_SIZE * 5.82842712f);
			case 29 -> Math.ceil(SLOT_SIZE * 5.93434180f);
			case 37 -> Math.ceil(SLOT_SIZE * 6.59861960f);
			case 38 -> Math.ceil(SLOT_SIZE * 6.70710678f);
			case 39 -> Math.ceil(SLOT_SIZE * 6.81880916f);
			case 40 -> Math.ceil(SLOT_SIZE * 6.82287565f);
			case 41 -> Math.ceil(SLOT_SIZE * 6.93786550f);
			case 50 -> Math.ceil(SLOT_SIZE * 7.59861960f);
			case 51 -> Math.ceil(SLOT_SIZE * 7.70435372f);
			case 52 -> Math.ceil(SLOT_SIZE * 7.70710678f);
			case 53 -> Math.ceil(SLOT_SIZE * 7.82303789f);
			case 54 -> Math.ceil(SLOT_SIZE * 7.84666719f);
			case 55 -> Math.ceil(SLOT_SIZE * 7.95424222f);
			default -> trivialPackingSide(n) * trivialRowHeight;
		};
	}

	@ModifyReturnValue(method = "getHeight", at = @At("RETURN"))
	private int handleSpecialHeight(int original) {
		return getSquareSize(size(), 20) + 4;
	}

	@ModifyReturnValue(method = "getWidth", at = @At("RETURN"))
	private int handleSpecialWidth(int original) {
		return getSquareSize(size());
	}

	@Inject(method = "drawItems", at = @At("HEAD"), cancellable = true)
	private void handleSpecialNumbers(TextRenderer textRenderer, int x, int y, DrawContext context, CallbackInfo ci) {
		int count = size();
		boolean full = this.field_49537.getOccupancy().compareTo(Fraction.ONE) >= 0;
		boolean special = true;
		switch (count) {
			case 5 -> drawFive(textRenderer, x, y, context, full);
			case 10 -> drawTen(textRenderer, x, y, context, full);
			case 11 -> drawEleven(textRenderer, x, y, context, full);
			case 17 -> drawSeventeen(textRenderer, x, y, context, full);
			case 18 -> drawEighteen(textRenderer, x, y, context, full);
			case 19 -> drawNineteen(textRenderer, x, y, context, full);
			case 26 -> drawTwentySix(textRenderer, x, y, context, full);
			case 27 -> drawTwentySeven(textRenderer, x, y, context, full);
			case 28 -> drawTwentyEight(textRenderer, x, y, context, full);
			case 29 -> drawTwentyNine(textRenderer, x, y, context, full);
			case 37 -> drawThirtySeven(textRenderer, x, y, context, full);
			case 38 -> drawThirtyEight(textRenderer, x, y, context, full);
			case 39 -> drawThirtyNine(textRenderer, x, y, context, full);
			case 40 -> drawForty(textRenderer, x, y, context, full);
			case 41 -> drawFortyOne(textRenderer, x, y, context, full);
			case 50 -> drawFifty(textRenderer, x, y, context, full);
			case 51 -> drawFiftyOne(textRenderer, x, y, context, full);
			case 52 -> drawFiftyTwo(textRenderer, x, y, context, full);
			case 53 -> drawFiftyThree(textRenderer, x, y, context, full);
			case 54 -> drawFiftyFour(textRenderer, x, y, context, full);
			case 55 -> drawFiftyFive(textRenderer, x, y, context, full);
			default -> special = false;
		}

		if (special) {
			ci.cancel();
		}
	}

	@Unique
	private void drawFive(TextRenderer textRenderer, int x, int y, DrawContext ctx, boolean full) {
		int squareSize = getSquareSize(5) + 2; // margin
		int straightOffset = 31;
		ctx.drawGuiTexture(BACKGROUND_TEXTURE, x, y, squareSize, squareSize);
		this.drawSlot(x + 1, y + 1, 0, full, ctx, textRenderer);
		this.drawSlot(x + straightOffset + 1, y + 1, 1, full, ctx, textRenderer);

		var matrices = ctx.getMatrices();
		matrices.push();
		matrices.translate(x+SLOT_SIZE, y + SLOT_SIZE, 0);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		matrices.translate(0f, -SLOT_SIZE/2f, 0f);
		this.drawSlot(1, 1, 2, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x + 1, y + straightOffset + 1, 3, full, ctx, textRenderer);
		this.drawSlot(x + straightOffset + 1, y + straightOffset + 1, 4, full, ctx, textRenderer);
	}

	@Unique
	private void drawTen(TextRenderer textRenderer, int x, int y, DrawContext ctx, boolean full) {
		int i = 0;
		int squareSize = getSquareSize(10) + 2;
		int gap = 31;
		ctx.drawGuiTexture(BACKGROUND_TEXTURE, x, y, squareSize, squareSize);
		this.drawSlot(x + 1,                   y + 1, i++, full, ctx, textRenderer);
		this.drawSlot(x + gap + 1,             y + 1, i++, full, ctx, textRenderer);
		this.drawSlot(x + gap + SLOT_SIZE + 1, y + 1, i++, full, ctx, textRenderer);
		this.drawSlot(x + 1,                   y + SLOT_SIZE + 1, i++, full, ctx, textRenderer);
		this.drawSlot(x + gap + 1,             y + SLOT_SIZE + 1, i++, full, ctx, textRenderer);
		this.drawSlot(x + gap + SLOT_SIZE + 1, y + SLOT_SIZE + 1, i++, full, ctx, textRenderer);

		var matrices = ctx.getMatrices();
		matrices.push();
		matrices.translate(x + SLOT_SIZE, y + SLOT_SIZE * 2, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		matrices.translate(0, -SLOT_SIZE/2f, 0);
		this.drawSlot(1, 1, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x + 1,                   y + SLOT_SIZE + gap + 1, i++, full, ctx, textRenderer);
		this.drawSlot(x + gap + 1,             y + SLOT_SIZE + gap + 1, i++, full, ctx, textRenderer);
		this.drawSlot(x + gap + SLOT_SIZE + 1, y + SLOT_SIZE + gap + 1, i++, full, ctx, textRenderer);
	}

	@Unique
	private void drawEleven(TextRenderer textRenderer, int x, int y, DrawContext ctx, boolean full) {
		int i = 0;
		int squareSize = getSquareSize(11);
		ctx.drawGuiTexture(BACKGROUND_TEXTURE, x, y, squareSize + 2, squareSize + 2);
		x++;
		y++;
		var matrices = ctx.getMatrices();
		this.drawSlot(x, y, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE, y, i++, full, ctx, textRenderer);

		Matrix4f baseTransform;
		matrices.push();
		matrices.translate(x + SLOT_SIZE, y + SLOT_SIZE, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(40.18193729f));
		matrices.translate(0f, SLOT_SIZE * -0.32990859f, 0f);
		baseTransform = new Matrix4f(matrices.peek().getPositionMatrix());
		matrices.translate(SLOT_SIZE * .02487453f, -SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.11878261f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.33377596f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.11878260f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x, y + squareSize - SLOT_SIZE * 2, i++, full, ctx, textRenderer);
		this.drawSlot(x, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 2.03255831f, y + SLOT_SIZE * 2.87708359f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();
	}

	@Unique
	private void drawSeventeen(TextRenderer textRenderer, int x, int y, DrawContext ctx, boolean full) {
		int i = 0;
		float normSize = 4.67553009f;
		int squareSize = getSquareSize(17);
		ctx.drawGuiTexture(BACKGROUND_TEXTURE, x, y, squareSize + 2, squareSize + 2);
		x++;
		y++;
		var matrices = ctx.getMatrices();
		this.drawSlot(x, y, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE, y, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE * 2, y, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE, y, i++, full, ctx, textRenderer);
		this.drawSlot(x, y + SLOT_SIZE, i++, full, ctx, textRenderer);

		Matrix4f baseTransform;

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 0.64017619f, y + SLOT_SIZE * 2, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(39.80495897f));
		baseTransform = new Matrix4f(matrices.peek().getPositionMatrix());
		matrices.translate(SLOT_SIZE * 0.40447869f, SLOT_SIZE * -2, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x + squareSize - SLOT_SIZE, y + SLOT_SIZE, i++, full, ctx, textRenderer);

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		matrices.translate(SLOT_SIZE * 0.27642676f, -SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		matrices.translate(SLOT_SIZE * 1.40447869f, SLOT_SIZE * -1.94316130f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		matrices.translate(SLOT_SIZE * 1.27642676f, SLOT_SIZE * -0.94316130, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * (normSize - 1), y + SLOT_SIZE * 2.11346013f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.05683896f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 2.84732482f, y + SLOT_SIZE * (normSize - 1), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-36.62378638f));
		matrices.translate(0f, SLOT_SIZE * -0.50592742f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + (int) Math.ceil(SLOT_SIZE * 1.84732482f), y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
	}

	@Unique
	private void drawEighteen(TextRenderer textRenderer, int x, int y, DrawContext ctx, boolean full) {
		int i = 0;
		int squareSize = getSquareSize(18);
		ctx.drawGuiTexture(BACKGROUND_TEXTURE, x, y, squareSize + 2, squareSize + 2);
		x++;
		y++;
		var matrices = ctx.getMatrices();
		this.drawSlot(x, y, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE * 3, y, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE * 2, y, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE, y, i++, full, ctx, textRenderer);
		this.drawSlot(x, y + SLOT_SIZE, i++, full, ctx, textRenderer);

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 1.51429728f, y + SLOT_SIZE * 0.86070271f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(24.29518894f));
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x + squareSize - SLOT_SIZE * 2, y + SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE, y + SLOT_SIZE, i++, full, ctx, textRenderer);

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 1.51429728f, y + SLOT_SIZE * 0.86070271f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(24.29518894f));
		matrices.translate(0, SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 2.30857837f, y + SLOT_SIZE * 3.96217294f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(24.29518894f));
		matrices.translate(-SLOT_SIZE, SLOT_SIZE * -2, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x, y + squareSize - SLOT_SIZE * 2, i++, full, ctx, textRenderer);

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 2.30857837f, y + SLOT_SIZE * 3.96217294f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(24.29518894f));
		matrices.translate(-SLOT_SIZE, -SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x + squareSize - SLOT_SIZE * 2, y + squareSize - SLOT_SIZE * 2, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE * 2, i++, full, ctx, textRenderer);
		this.drawSlot(x, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE * 2, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
	}

	@Unique
	private void drawNineteen(TextRenderer textRenderer, int x, int y, DrawContext ctx, boolean full) {
		int i = 0;
		float normSize = 4.88561808f;
		int squareSize = getSquareSize(19);
		ctx.drawGuiTexture(BACKGROUND_TEXTURE, x, y, squareSize + 2, squareSize + 2);
		x++;
		y++;
		var matrices = ctx.getMatrices();
		this.drawSlot(x, y, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE * 3, y, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE * 2, y, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE, y, i++, full, ctx, textRenderer);

		Matrix4f baseTransform;

		matrices.push();
		matrices.translate(x + SLOT_SIZE * (normSize / 2f - 1), y + SLOT_SIZE * (3 - normSize / 2f), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		baseTransform = new Matrix4f(matrices.peek().getPositionMatrix());
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(0f, SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * -0.04044011f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x + squareSize - SLOT_SIZE * 2, y + SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE, y + SLOT_SIZE, i++, full, ctx, textRenderer);

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.95955989f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 3 * (normSize / 2f - 1), y + SLOT_SIZE * (normSize / 2f + 1), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		matrices.translate(SLOT_SIZE * -2, SLOT_SIZE * -0.04044011f, 0f);
		baseTransform = new Matrix4f(matrices.peek().getPositionMatrix());
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x + squareSize - SLOT_SIZE, y + SLOT_SIZE * 2, i++, full, ctx, textRenderer);
		this.drawSlot(x, y + squareSize - SLOT_SIZE * 2, i++, full, ctx, textRenderer);

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		matrices.translate(0f, SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * -0.95955989f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 1.04044011f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
	}

	@Unique
	private void drawTwentySix(TextRenderer textRenderer, int x, int y, DrawContext ctx, boolean full) {
		int i = 0;
		float normSize = 5.62132034f;
		int squareSize = getSquareSize(26);
		ctx.drawGuiTexture(BACKGROUND_TEXTURE, x, y, squareSize + 2, squareSize + 2);
		x++;
		y++;
		var matrices = ctx.getMatrices();
		this.drawSlot(x, y, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE, y, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE * 3, y, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE * 2, y, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE, y, i++, full, ctx, textRenderer);
		this.drawSlot(x, y + SLOT_SIZE, i++, full, ctx, textRenderer);

		Matrix4f baseTransform;

		matrices.push();
		matrices.translate(x + SLOT_SIZE * (normSize - 1) / 2, y + SLOT_SIZE * normSize / 2, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		matrices.translate(SLOT_SIZE * -1.5f, SLOT_SIZE * -1.5f, 0f);
		baseTransform = new Matrix4f(matrices.peek().getPositionMatrix());
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x + squareSize - SLOT_SIZE * 2, y + SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE, y + SLOT_SIZE, i++, full, ctx, textRenderer);

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		matrices.translate(0f, SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, -SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(-SLOT_SIZE, SLOT_SIZE * 2, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, -SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, -SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize / 2 - SLOT_SIZE / 2, i++, full, ctx, textRenderer);

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 2, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, -SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x, y + squareSize - SLOT_SIZE * 2, i++, full, ctx, textRenderer);

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		matrices.translate(SLOT_SIZE * 2, SLOT_SIZE * 2, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x + squareSize - SLOT_SIZE * 2, y + squareSize - SLOT_SIZE * 2, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE * 2, i++, full, ctx, textRenderer);
		this.drawSlot(x, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE * 3, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE * 2, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
	}

	@Unique
	private void drawTwentySeven(TextRenderer textRenderer, int x, int y, DrawContext ctx, boolean full) {
		int i = 0;
		var fiveOffset = (int) Math.floor(SLOT_SIZE * 1.70710678f);
		int squareSize = getSquareSize(27);
		ctx.drawGuiTexture(BACKGROUND_TEXTURE, x, y, squareSize + 2, squareSize + 2);
		x++;
		y++;
		var matrices = ctx.getMatrices();
		this.drawSlot(x, y, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE * 4, y, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE * 3, y, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE * 2, y, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE, y, i++, full, ctx, textRenderer);

		matrices.push();
		matrices.translate(x + SLOT_SIZE, y + SLOT_SIZE, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		matrices.translate(0f, SLOT_SIZE * -0.5f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x + squareSize - SLOT_SIZE * 3, y + SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE * 2, y + SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE, y + SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x, y + fiveOffset, i++, full, ctx, textRenderer);
		this.drawSlot(x + fiveOffset, y + fiveOffset, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE * 2, y + SLOT_SIZE * 2, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE, y + SLOT_SIZE * 2, i++, full, ctx, textRenderer);
		this.drawSlot(x, y + squareSize - SLOT_SIZE * 3, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE, y + squareSize - SLOT_SIZE * 3, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE * 2, y + squareSize - SLOT_SIZE * 3, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE * 3, y + SLOT_SIZE * 3, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE * 3 + fiveOffset, y + SLOT_SIZE * 3, i++, full, ctx, textRenderer);
		this.drawSlot(x, y + squareSize - SLOT_SIZE * 2, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE, y + squareSize - SLOT_SIZE * 2, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE * 2, y + squareSize - SLOT_SIZE * 2, i++, full, ctx, textRenderer);

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 4, y + SLOT_SIZE * 4, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		matrices.translate(0f, SLOT_SIZE * -0.5f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE * 2, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE * 3, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE * 3 + fiveOffset, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
	}

	@Unique
	private void drawTwentyEight(TextRenderer textRenderer, int x, int y, DrawContext ctx, boolean full) {
		int i = 0;
		int squareSize = getSquareSize(28);
		int centerOffset = (squareSize - SLOT_SIZE) / 2;
		ctx.drawGuiTexture(BACKGROUND_TEXTURE, x, y, squareSize + 2, squareSize + 2);
		x++;
		y++;
		var matrices = ctx.getMatrices();
		this.drawSlot(x, y, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE, y, i++, full, ctx, textRenderer);
		this.drawSlot(x + centerOffset, y, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE * 2, y, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE, y, i++, full, ctx, textRenderer);
		this.drawSlot(x, y + SLOT_SIZE, i++, full, ctx, textRenderer);

		Matrix4f baseTransform;

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 1.5f, y + SLOT_SIZE * 1.5f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		baseTransform = new Matrix4f(matrices.peek().getPositionMatrix());
		matrices.translate(0f, -SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, -SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x + squareSize - SLOT_SIZE, y + SLOT_SIZE, i++, full, ctx, textRenderer);

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, -SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, -SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x, y + centerOffset, i++, full, ctx, textRenderer);

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		matrices.translate(SLOT_SIZE, 0f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, -SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x + squareSize - SLOT_SIZE, y + centerOffset, i++, full, ctx, textRenderer);

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		matrices.translate(SLOT_SIZE, SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, -SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, -SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x, y + squareSize - SLOT_SIZE * 2, i++, full, ctx, textRenderer);

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		matrices.translate(SLOT_SIZE * 2, SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, -SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE * 2, i++, full, ctx, textRenderer);
		this.drawSlot(x, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + centerOffset, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE * 2, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
	}

	@Unique
	private void drawTwentyNine(TextRenderer textRenderer, int x, int y, DrawContext ctx, boolean full) {
		int i = 0;
		int squareSize = getSquareSize(29);
		ctx.drawGuiTexture(BACKGROUND_TEXTURE, x, y, squareSize + 2, squareSize + 2);
		x++;
		y++;
		var matrices = ctx.getMatrices();
		this.drawSlot(x, y, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE, y, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE * 3, y, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE * 2, y, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE, y, i++, full, ctx, textRenderer);

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 2, y + SLOT_SIZE, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(25.99204718f));
		matrices.translate(0f, SLOT_SIZE * -0.40947192f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 5.58822412f, y + SLOT_SIZE * 2.93434180, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(20.25003828f));
		matrices.translate(SLOT_SIZE * -3, SLOT_SIZE * -0.95501732f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * -0.04498268f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, 0f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x, y + SLOT_SIZE, i++, full, ctx, textRenderer);

		matrices.push();
		matrices.translate(x + SLOT_SIZE, y + SLOT_SIZE * 2, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(21.62093977f));
		matrices.translate(0f, SLOT_SIZE * -0.67231250f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 4.93434180f, y + SLOT_SIZE * 2.93434180, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(20.25540431f));
		matrices.translate(-SLOT_SIZE * 3, SLOT_SIZE * -0.11130742f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * -0.05050338f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, 0f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE * 3, i++, full, ctx, textRenderer);

		Matrix4f baseTransform;

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 2.93434180f, y + SLOT_SIZE * 4.93434180, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(18.17734536f));
		matrices.translate(-SLOT_SIZE, SLOT_SIZE * -0.70852340f, 0f);
		baseTransform = new Matrix4f(matrices.peek().getPositionMatrix());
		matrices.translate(SLOT_SIZE * -2.36186381f, SLOT_SIZE * -1.12411661f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.10208347f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.02203314f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, 0f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x + squareSize - SLOT_SIZE * 2, y + squareSize - SLOT_SIZE * 2, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE * 2, i++, full, ctx, textRenderer);

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		matrices.translate(SLOT_SIZE * -2, SLOT_SIZE * -0.02203314f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.02203314f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, 0f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE * 3, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE * 2, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
	}

	@Unique
	private void drawThirtySeven(TextRenderer textRenderer, int x, int y, DrawContext ctx, boolean full) {
		int i = 0;
		float normSize = 6.59861960f;
		int squareSize = getSquareSize(37);
		ctx.drawGuiTexture(BACKGROUND_TEXTURE, x, y, squareSize + 2, squareSize + 2);
		x++;
		y++;
		var matrices = ctx.getMatrices();
		this.drawSlot(x, y, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE, y, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE * 3, y, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE * 2, y, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE, y, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE * 2, y + SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE, y + SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE, y + SLOT_SIZE * 2, i++, full, ctx, textRenderer);

		double offset = 0.07187936f;

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 0.70223662f, y + SLOT_SIZE * 2.32969614f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(42.08660748f));
		matrices.translate(SLOT_SIZE * offset, SLOT_SIZE * -3, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * offset, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 2, y + SLOT_SIZE * (normSize - 2.70710678f), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		matrices.translate(0f, SLOT_SIZE * -3f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * (normSize - 2.32969614f), y + SLOT_SIZE * (normSize - 0.70223662f), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(47.91339252f));
		matrices.translate(SLOT_SIZE * -(2 + offset), SLOT_SIZE * -(3 - offset), 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * -offset, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x, y + SLOT_SIZE, i++, full, ctx, textRenderer);

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 0.70223662f, y + SLOT_SIZE * 2.32969614f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(42.08660748f));
		matrices.translate(SLOT_SIZE * offset, SLOT_SIZE * -2, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * -offset, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 2, y + SLOT_SIZE * (normSize - 2.70710678f), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		matrices.translate(0f, SLOT_SIZE * -2, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * (normSize - 2.32969614f), y + SLOT_SIZE * (normSize - 0.70223662f), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(47.91339252f));
		matrices.translate(SLOT_SIZE * -(2 + offset), SLOT_SIZE * -(2 - offset), 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * -offset, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE * 2, i++, full, ctx, textRenderer);

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 0.70223662f, y + SLOT_SIZE * 2.32969614f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(42.08660748f));
		matrices.translate(0f, -SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * offset, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 2, y + SLOT_SIZE * (normSize - 2.70710678f), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		matrices.translate(0f, -SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * (normSize - 2.32969614f), y + SLOT_SIZE * (normSize - 0.70223662f), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(47.91339252f));
		matrices.translate(SLOT_SIZE * -2, SLOT_SIZE * -(1 - offset), 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * -offset, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x, y + squareSize - SLOT_SIZE * 4, i++, full, ctx, textRenderer);
		this.drawSlot(x, y + squareSize - SLOT_SIZE * 3, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE, y + squareSize - SLOT_SIZE * 3, i++, full, ctx, textRenderer);
		this.drawSlot(x, y + squareSize - SLOT_SIZE * 2, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE, y + squareSize - SLOT_SIZE * 2, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE * 2, y + squareSize - SLOT_SIZE * 2, i++, full, ctx, textRenderer);
		this.drawSlot(x, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE * 2, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE * 3, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE * 2, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
	}

	@Unique
	private void drawThirtyEight(TextRenderer textRenderer, int x, int y, DrawContext ctx, boolean full) {
		int i = 0;
		var fiveOffset = (int) Math.floor(SLOT_SIZE * 1.70710678f);
		int squareSize = getSquareSize(38);
		ctx.drawGuiTexture(BACKGROUND_TEXTURE, x, y, squareSize + 2, squareSize + 2);
		x++;
		y++;
		var matrices = ctx.getMatrices();
		this.drawSlot(x, y, i++, full, ctx, textRenderer);
		for (int j = 5; j >= 1; j--) {
			this.drawSlot(x + squareSize - SLOT_SIZE * j, y, i++, full, ctx, textRenderer);
		}

		matrices.push();
		matrices.translate(x + SLOT_SIZE, y + SLOT_SIZE, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		matrices.translate(0f, SLOT_SIZE * -0.5f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x, y + squareSize - SLOT_SIZE * 5, i++, full, ctx, textRenderer);
		this.drawSlot(x + fiveOffset, y + squareSize - SLOT_SIZE * 5, i++, full, ctx, textRenderer);

		for (int j = 4; j >= 1; j--) {
			this.drawSlot(x + squareSize - SLOT_SIZE * j, y + SLOT_SIZE, i++, full, ctx, textRenderer);
		}

		this.drawSlot(x + squareSize - SLOT_SIZE * 2, y + SLOT_SIZE * 2, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE, y + SLOT_SIZE * 2, i++, full, ctx, textRenderer);

		this.drawSlot(x, y + squareSize - SLOT_SIZE * 4, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE, y + squareSize - SLOT_SIZE * 4, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE * 2, y + squareSize - SLOT_SIZE * 4, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE * 3, y + squareSize - SLOT_SIZE * 4, i++, full, ctx, textRenderer);

		this.drawSlot(x + squareSize - SLOT_SIZE * 2, y + SLOT_SIZE * 3, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE, y + SLOT_SIZE * 3, i++, full, ctx, textRenderer);

		this.drawSlot(x, y + squareSize - SLOT_SIZE * 3, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE, y + squareSize - SLOT_SIZE * 3, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE * 2, y + squareSize - SLOT_SIZE * 3, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE * 3, y + squareSize - SLOT_SIZE * 3, i++, full, ctx, textRenderer);

		this.drawSlot(x + SLOT_SIZE * 4, y + SLOT_SIZE * 4, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE * 4 + fiveOffset, y + SLOT_SIZE * 4, i++, full, ctx, textRenderer);

		this.drawSlot(x, y + squareSize - SLOT_SIZE * 2, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE, y + squareSize - SLOT_SIZE * 2, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE * 2, y + squareSize - SLOT_SIZE * 2, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE * 3, y + squareSize - SLOT_SIZE * 2, i++, full, ctx, textRenderer);

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 5, y + SLOT_SIZE * 5, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		matrices.translate(0f, SLOT_SIZE * -0.5f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE * 2, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE * 3, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE * 4, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
	}

	@Unique
	private void drawThirtyNine(TextRenderer textRenderer, int x, int y, DrawContext ctx, boolean full) {
		int i = 0;
		float normSize = 6.81880916f;
		int squareSize = getSquareSize(39);
		ctx.drawGuiTexture(BACKGROUND_TEXTURE, x, y, squareSize + 2, squareSize + 2);
		x++;
		y++;
		var matrices = ctx.getMatrices();

		this.drawSlot(x, y, i++, full, ctx, textRenderer);
		for (int k = 5; k >= 1; k--) {
			this.drawSlot(x + squareSize - SLOT_SIZE * k, y, i++, full, ctx, textRenderer);
		}
		for (int k = 3; k >= 1; k--) {
			this.drawSlot(x + squareSize - SLOT_SIZE * k, y + SLOT_SIZE, i++, full, ctx, textRenderer);
		}
		this.drawSlot(x, y + squareSize - SLOT_SIZE * 5, i++, full, ctx, textRenderer);

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 0.60617891f, y + SLOT_SIZE * 1.29752487f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-37.07040322f));
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE * 0.12360160f, SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.23145647f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE * -0.13060668f, SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x + squareSize - SLOT_SIZE * 2, y + SLOT_SIZE * 2, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE, y + SLOT_SIZE * 2, i++, full, ctx, textRenderer);
		for (int k = 0; k <= 2; k++) {
			this.drawSlot(x + SLOT_SIZE * k, y + squareSize - SLOT_SIZE * 4, i++, full, ctx, textRenderer);
		}
		this.drawSlot(x + squareSize - SLOT_SIZE, y + SLOT_SIZE * 3, i++, full, ctx, textRenderer);
		for (int k = 0; k <= 3; k++) {
			this.drawSlot(x + SLOT_SIZE * k, y + squareSize - SLOT_SIZE * 3, i++, full, ctx, textRenderer);
		}
		this.drawSlot(x + squareSize - SLOT_SIZE, y + SLOT_SIZE * 4, i++, full, ctx, textRenderer);
		for (int k = 0; k <= 3; k++) {
			this.drawSlot(x + SLOT_SIZE * k, y + squareSize - SLOT_SIZE * 2, i++, full, ctx, textRenderer);
		}
		for (int k = 0; k <= 4; k++) {
			this.drawSlot(x + SLOT_SIZE * k, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		}

		matrices.push();
		matrices.translate(x + SLOT_SIZE * (normSize - 1.29752487f), y + SLOT_SIZE * (normSize - 0.60617891f), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(37.07040322f));
		matrices.translate(SLOT_SIZE * -3.12360160f, SLOT_SIZE * -1.99299492f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * -0.12360160f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE * 0.23145647f, SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.13060668f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
	}

	@Unique
	private void drawForty(TextRenderer textRenderer, int x, int y, DrawContext ctx, boolean full) {
		int i = 0;
		int squareSize = getSquareSize(40);
		ctx.drawGuiTexture(BACKGROUND_TEXTURE, x, y, squareSize + 2, squareSize + 2);
		x++;
		y++;
		var matrices = ctx.getMatrices();

		for (int j = 0; j < 3; j++) {
			for (int k = 0; k < 3 - j; k++) {
				this.drawSlot(x + SLOT_SIZE * k, y + SLOT_SIZE * j, i++, full, ctx, textRenderer);
			}
		}
		for (int j = 0; j < 3; j++) {
			for (int k = 3 - j; k >= 1; k--) {
				this.drawSlot(x + squareSize - SLOT_SIZE * k, y + SLOT_SIZE * j, i++, full, ctx, textRenderer);
			}
		}

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 2, y + SLOT_SIZE * 2, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		matrices.translate(0f, SLOT_SIZE * -2, 0f);

		for (int j = 0; j < 4; j++) {
			for (int k = 0; k < 4; k++) {
				this.drawSlot(SLOT_SIZE * k, SLOT_SIZE * j, i++, full, ctx, textRenderer);
			}
		}

		matrices.pop();

		for (int j = 3; j >= 1; j--) {
			for (int k = 0; k < 4 - j; k++) {
				this.drawSlot(x + SLOT_SIZE * k, y + squareSize - SLOT_SIZE * j, i++, full, ctx, textRenderer);
			}
		}
		for (int j = 3; j >= 1; j--) {
			for (int k = 4 - j; k >= 1; k--) {
				this.drawSlot(x + squareSize - SLOT_SIZE * k, y + squareSize - SLOT_SIZE * j, i++, full, ctx, textRenderer);
			}
		}
	}

	@Unique
	private void drawFortyOne(TextRenderer textRenderer, int x, int y, DrawContext ctx, boolean full) {
		int i = 0;
		int squareSize = getSquareSize(41);
		ctx.drawGuiTexture(BACKGROUND_TEXTURE, x, y, squareSize + 2, squareSize + 2);
		x++;
		y++;
		var matrices = ctx.getMatrices();

		for (int j = 0; j < 3; j++) {
			for (int k = 0; k < 3 - j; k++) {
				this.drawSlot(x + SLOT_SIZE * k, y + SLOT_SIZE * j, i++, full, ctx, textRenderer);
			}
		}

		for (int j = 3; j >= 1; j--) {
			this.drawSlot(x + squareSize - SLOT_SIZE * j, y, i++, full, ctx, textRenderer);
		}

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 3.07655182f, y + SLOT_SIZE * 0.74322162f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(16.60058754f));

		for (int j = 0; j < 4; j++) {
			this.drawSlot(SLOT_SIZE * j, 0, i++, full, ctx, textRenderer);
		}

		for (int k = 0; k < 3; k++) {
			matrices.translate(SLOT_SIZE * -0.67262145f, SLOT_SIZE, 0f);
			for (int j = 0; j < 5; j++) {
				this.drawSlot(SLOT_SIZE * j, 0, i++, full, ctx, textRenderer);
			}
		}

		matrices.translate(SLOT_SIZE * 0.32737854f, SLOT_SIZE, 0f);

		for (int j = 0; j < 4; j++) {
			this.drawSlot(SLOT_SIZE * j, 0, i++, full, ctx, textRenderer);
		}

		matrices.pop();

		for (int j = 0; j < 3; j++) {
			this.drawSlot(x + SLOT_SIZE * j, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		}

		for (int j = 3; j >= 1; j--) {
			for (int k = 4 - j; k >= 1; k--) {
				this.drawSlot(x + squareSize - SLOT_SIZE * k, y + squareSize - SLOT_SIZE * j, i++, full, ctx, textRenderer);
			}
		}
	}

	@Unique
	private void drawFifty(TextRenderer textRenderer, int x, int y, DrawContext ctx, boolean full) {
		int i = 0;
		float normSize = 7.59861960f;
		int squareSize = getSquareSize(50);
		ctx.drawGuiTexture(BACKGROUND_TEXTURE, x, y, squareSize + 2, squareSize + 2);
		x++;
		y++;
		var matrices = ctx.getMatrices();
		this.drawSlot(x, y, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE, y, i++, full, ctx, textRenderer);
		this.drawSlot(x, y + SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE, y + SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x, y + SLOT_SIZE * 2, i++, full, ctx, textRenderer);


		for (int j = 0; j < 5; j++) {
			for (int k = 5 - j; k >= 1; k--) {
				this.drawSlot(x + squareSize - SLOT_SIZE * k, y + SLOT_SIZE * j, i++, full, ctx, textRenderer);
			}
		}

		double offset = 0.07187936f;

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 0.70223662f, y + SLOT_SIZE * 3.32969614f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(42.08660748f));
		matrices.translate(SLOT_SIZE * offset, SLOT_SIZE * -3, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * offset, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 2, y + SLOT_SIZE * (normSize - 2.70710678f), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		matrices.translate(0f, SLOT_SIZE * -3f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * (normSize - 3.32969614f), y + SLOT_SIZE * (normSize - 0.70223662f), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(47.91339252f));
		matrices.translate(SLOT_SIZE * -(2 + offset), SLOT_SIZE * -(3 - offset), 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * -offset, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 0.70223662f, y + SLOT_SIZE * 3.32969614f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(42.08660748f));
		matrices.translate(SLOT_SIZE * offset, SLOT_SIZE * -2, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * -offset, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 2, y + SLOT_SIZE * (normSize - 2.70710678f), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		matrices.translate(0f, SLOT_SIZE * -2, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * (normSize - 3.32969614f), y + SLOT_SIZE * (normSize - 0.70223662f), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(47.91339252f));
		matrices.translate(SLOT_SIZE * -(2 + offset), SLOT_SIZE * -(2 - offset), 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * -offset, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 0.70223662f, y + SLOT_SIZE * 3.32969614f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(42.08660748f));
		matrices.translate(0f, -SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * offset, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 2, y + SLOT_SIZE * (normSize - 2.70710678f), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		matrices.translate(0f, -SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * (normSize - 3.32969614f), y + SLOT_SIZE * (normSize - 0.70223662f), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(47.91339252f));
		matrices.translate(SLOT_SIZE * -2, SLOT_SIZE * -(1 - offset), 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * -offset, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		for (int j = 4; j >= 1; j--) {
			for (int k = 0; k <= 4 - j; k++) {
				this.drawSlot(x + SLOT_SIZE * k, y + squareSize - SLOT_SIZE * j, i++, full, ctx, textRenderer);
			}
		}

		this.drawSlot(x + squareSize - SLOT_SIZE * 2, y + squareSize - SLOT_SIZE * 2, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE * 2, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE * 3, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE * 2, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
	}

	@Unique
	private void drawFiftyOne(TextRenderer textRenderer, int x, int y, DrawContext ctx, boolean full) {
		int i = 0;
		float normSize = 7.70435372f;
		int squareSize = getSquareSize(51);
		ctx.drawGuiTexture(BACKGROUND_TEXTURE, x, y, squareSize + 2, squareSize + 2);
		x++;
		y++;
		var matrices = ctx.getMatrices();
		this.drawSlot(x, y, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE, y, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE * 2, y, i++, full, ctx, textRenderer);

		matrices.push();
		matrices.translate(x + SLOT_SIZE * (normSize - 4.58444800f), y, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x, y + SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE, y + SLOT_SIZE, i++, full, ctx, textRenderer);
		this.drawSlot(x, y + SLOT_SIZE * 2, i++, full, ctx, textRenderer);

		for (int j = 0; j < 4; j++) {
			for (int k = 3 - j/2; k >= 1; k--) {
				this.drawSlot(x + squareSize - SLOT_SIZE * k, y + SLOT_SIZE * j, i++, full, ctx, textRenderer);
			}
		}

		double offset = 0.08471829f;
		Matrix4f transform1, transform2, transform3, transform4;

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 0.69479500f, y + SLOT_SIZE * 3.17645419f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-30.03434357f));
		transform1 = new Matrix4f(matrices.peek().getPositionMatrix());
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * (normSize - 4), y + SLOT_SIZE * (normSize - 1), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-22.08119070f));
		matrices.translate(SLOT_SIZE * offset * -5, SLOT_SIZE * -5, 0f);
		transform2 = new Matrix4f(matrices.peek().getPositionMatrix());
		matrices.translate(SLOT_SIZE * 0.02639259f, 0f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 3, y + SLOT_SIZE, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-22.08119070f));
		matrices.translate(SLOT_SIZE * (offset - 1), 0f, 0f);
		transform3 = new Matrix4f(matrices.peek().getPositionMatrix());
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(transform1);
		matrices.translate(SLOT_SIZE * -0.077f, SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(transform2);
		matrices.translate(SLOT_SIZE * (offset + 0.00501247f), SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(transform3);
		matrices.translate(SLOT_SIZE * (offset - 0.00501247f), SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * (normSize - 1.69479500f), y + SLOT_SIZE * (normSize - 3.17645419f), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-30.03434357f));
		matrices.translate(SLOT_SIZE * -0.804f, SLOT_SIZE * -4, 0f);
		transform4 = new Matrix4f(matrices.peek().getPositionMatrix());
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(transform1);
		matrices.translate(SLOT_SIZE * -0.13531248f, SLOT_SIZE * 2, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(transform2);
		matrices.translate(SLOT_SIZE * offset * 2, SLOT_SIZE * 2, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(transform3);
		matrices.translate(SLOT_SIZE * offset * 2, SLOT_SIZE * 2, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(transform4);
		matrices.translate(SLOT_SIZE * -0.06068752f, SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(transform1);
		matrices.translate(SLOT_SIZE * -0.196f, SLOT_SIZE * 3, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(transform2);
		matrices.translate(SLOT_SIZE * (offset * 3 - 0.00501247f), SLOT_SIZE * 3, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(transform3);
		matrices.translate(SLOT_SIZE * (offset * 3 + 0.00501247f), SLOT_SIZE * 3, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(transform4);
		matrices.translate(SLOT_SIZE * -0.119f, SLOT_SIZE * 2, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(transform2);
		matrices.translate(SLOT_SIZE * (offset * 4 - 0.02639259f), SLOT_SIZE * 4, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(transform3);
		matrices.translate(SLOT_SIZE * (offset * 4 + 0.02639259f), SLOT_SIZE * 4, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(transform4);
		matrices.translate(SLOT_SIZE * -0.196f, SLOT_SIZE * 3, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		for (int j = 0; j < 4; j++) {
			for (int k = 0; k <= j/2; k++) {
				this.drawSlot(x + SLOT_SIZE * k, y + squareSize - SLOT_SIZE * (4 - j), i++, full, ctx, textRenderer);
			}
		}

		this.drawSlot(x + squareSize - SLOT_SIZE * 2, y + squareSize - SLOT_SIZE * 3, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE * 3, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE * 3, y + squareSize - SLOT_SIZE * 2, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE * 2, y + squareSize - SLOT_SIZE * 2, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE * 2, i++, full, ctx, textRenderer);

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 2.58444800f, y + SLOT_SIZE * (normSize - 1), 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		for (int j = 4; j >= 1; j--) {
			this.drawSlot(x + squareSize - SLOT_SIZE * j, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		}
	}

	@Unique
	private void drawFiftyTwo(TextRenderer textRenderer, int x, int y, DrawContext ctx, boolean full) {
		int i = 0;
		int squareSize = getSquareSize(52);
		ctx.drawGuiTexture(BACKGROUND_TEXTURE, x, y, squareSize + 2, squareSize + 2);
		x++;
		y++;
		var matrices = ctx.getMatrices();
		this.drawSlot(x, y, i++, full, ctx, textRenderer);

		for (int j = 6; j >= 1; j--) {
			this.drawSlot(x + squareSize - SLOT_SIZE * j, y, i++, full, ctx, textRenderer);
		}

		Matrix4f mat;

		matrices.push();
		matrices.translate(x + SLOT_SIZE, y + SLOT_SIZE, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		matrices.translate(0f, SLOT_SIZE * -0.5f, 0f);
		mat = new Matrix4f(matrices.peek().getPositionMatrix());
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		for (int j = 5; j >= 1; j--) {
			this.drawSlot(x + squareSize - SLOT_SIZE * j, y + SLOT_SIZE, i++, full, ctx, textRenderer);
		}

		this.drawSlot(x, y + squareSize - SLOT_SIZE * 6, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE * 6, y + squareSize - SLOT_SIZE * 6, i++, full, ctx, textRenderer);
		this.drawSlot(x + SLOT_SIZE * 3, y + SLOT_SIZE * 2, i++, full, ctx, textRenderer);

		for (int j = 3; j >= 1; j--) {
			this.drawSlot(x + squareSize - SLOT_SIZE * j, y + SLOT_SIZE * 2, i++, full, ctx, textRenderer);
		}
		for (int j = 0; j < 3; j++) {
			this.drawSlot(x + SLOT_SIZE * j, y + squareSize - SLOT_SIZE * 5, i++, full, ctx, textRenderer);
		}

		matrices.push();
		matrices.translate(SLOT_SIZE * 3, SLOT_SIZE * 2, 0f);
		matrices.multiplyPositionMatrix(mat);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		for (int j = 2; j >= 1; j--) {
			this.drawSlot(x + squareSize - SLOT_SIZE * j, y + SLOT_SIZE * 3, i++, full, ctx, textRenderer);
		}

		for (int j = 0; j < 4; j++) {
			this.drawSlot(x + SLOT_SIZE * j, y + squareSize - SLOT_SIZE * 4, i++, full, ctx, textRenderer);
		}

		this.drawSlot(x + squareSize - SLOT_SIZE * 3, y + squareSize - SLOT_SIZE * 4, i++, full, ctx, textRenderer);

		for (int j = 2; j >= 1; j--) {
			this.drawSlot(x + squareSize - SLOT_SIZE * j, y + SLOT_SIZE * 4, i++, full, ctx, textRenderer);
		}

		for (int j = 0; j < 5; j++) {
			this.drawSlot(x + SLOT_SIZE * j, y + squareSize - SLOT_SIZE * 3, i++, full, ctx, textRenderer);
		}

		this.drawSlot(x + SLOT_SIZE * 5, y + SLOT_SIZE * 5, i++, full, ctx, textRenderer);
		this.drawSlot(x + squareSize - SLOT_SIZE, y + SLOT_SIZE * 5, i++, full, ctx, textRenderer);

		for (int j = 0; j < 5; j++) {
			this.drawSlot(x + SLOT_SIZE * j, y + squareSize - SLOT_SIZE * 2, i++, full, ctx, textRenderer);
		}

		matrices.push();
		matrices.translate(SLOT_SIZE * 5, SLOT_SIZE * 5, 0f);
		matrices.multiplyPositionMatrix(mat);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		for (int j = 0; j < 6; j++) {
			this.drawSlot(x + SLOT_SIZE * j, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		}

		this.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
	}

	@Unique
	private int rowFiftyThree(TextRenderer textRenderer, DrawContext ctx, boolean full, int i, MatrixStack matrices) {
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * -0.48051357f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * -0.4805137f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		this.drawSlot(SLOT_SIZE, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE * 2, SLOT_SIZE * -0.48051375f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * -0.4805137f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * -0.48051375f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		return i;
	}

	@Unique
	private void drawFiftyThree(TextRenderer textRenderer, int x, int y, DrawContext ctx, boolean full) {
		int i = 0;
		int squareSize = getSquareSize(53);
		ctx.drawGuiTexture(BACKGROUND_TEXTURE, x, y, squareSize + 2, squareSize + 2);
		x++;
		y++;
		var matrices = ctx.getMatrices();

		for (int j = 0; j < 5; j++) {
			for (int k = 0; k < 5 - j; k++) {
				this.drawSlot(x + SLOT_SIZE * k, y + SLOT_SIZE * j, i++, full, ctx, textRenderer);
			}
		}

		this.drawSlot(x, y + squareSize - SLOT_SIZE * 2, i++, full, ctx, textRenderer);

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 5.09470432f, y + SLOT_SIZE * 0.42478198f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-25.13686477f));
		matrices.translate(SLOT_SIZE * -6, SLOT_SIZE * 2.40256844f, 0f);
		i = rowFiftyThree(textRenderer, ctx, full, i, matrices);
		matrices.pop();

		this.drawSlot(x, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 5.09470432f, y + SLOT_SIZE * 0.42478198f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-25.13686477f));
		matrices.translate(SLOT_SIZE * -6, SLOT_SIZE * 3.40256844f, 0f);
		i = rowFiftyThree(textRenderer, ctx, full, i, matrices);
		matrices.pop();

		this.drawSlot(x + squareSize - SLOT_SIZE, y, i++, full, ctx, textRenderer);

		for (int j = 6; j >= 1; j--) {
			for (int k = 7 - j; k >= 1; k--) {
				this.drawSlot(x + squareSize - SLOT_SIZE * k, y + squareSize - SLOT_SIZE * j, i++, full, ctx, textRenderer);
			}
		}
	}

	@Unique
	private void drawFiftyFour(TextRenderer textRenderer, int x, int y, DrawContext ctx, boolean full) {
		int i = 0;
		float normSize = 7.84666719f;
		int squareSize = getSquareSize(54);
		ctx.drawGuiTexture(BACKGROUND_TEXTURE, x, y, squareSize + 2, squareSize + 2);
		x++;
		y++;
		var matrices = ctx.getMatrices();

		int[] lengths = {6,5,4,2,1};
		for (int j = 0; j < lengths.length; j++) {
			for (int k = 0; k < lengths[j]; k++) {
				this.drawSlot(x + SLOT_SIZE * k, y + SLOT_SIZE * j, i++, full, ctx, textRenderer);
			}
		}

		this.drawSlot(x + squareSize - SLOT_SIZE, y, i++, full, ctx, textRenderer);

		Quaternionf rot = RotationAxis.POSITIVE_Z.rotationDegrees(34.73490026f);

		matrices.push();
		matrices.translate(x + SLOT_SIZE * (normSize - 0.75f), y + SLOT_SIZE * (normSize - 6.67333359f), 0f);
		matrices.multiply(rot);
		matrices.translate(-SLOT_SIZE, 0f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * normSize, y + SLOT_SIZE * (normSize - 6), 0f);
		matrices.multiply(rot);
		matrices.translate(-SLOT_SIZE, 0f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * (normSize - 1.52688698f), y + SLOT_SIZE * (normSize - 5.99513072f), 0f);
		matrices.multiply(rot);
		matrices.translate(-SLOT_SIZE, 0f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * (normSize - 0.77688698f), y + SLOT_SIZE * (normSize - 5.32179712f), 0f);
		matrices.multiply(rot);
		matrices.translate(-SLOT_SIZE, 0f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * (normSize - 1.55377397f), y + SLOT_SIZE * (normSize - 4.64359425f), 0f);
		matrices.multiply(rot);
		matrices.translate(SLOT_SIZE * -2, SLOT_SIZE * -0.07728492f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.07728492f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(-SLOT_SIZE, SLOT_SIZE * 0.92271507f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.07728492f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 1.55377397f, y + SLOT_SIZE * 4.64359425f, 0f);
		matrices.multiply(rot);
		matrices.translate(0f, SLOT_SIZE * -2, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.07728492f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(-SLOT_SIZE, SLOT_SIZE * 0.92271507f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.07728492f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 0.77688698f, y + SLOT_SIZE * 5.32179712f, 0f);
		matrices.multiply(rot);
		matrices.translate(0f, -SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 1.52688698f, y + SLOT_SIZE * 5.99513072f, 0f);
		matrices.multiply(rot);
		matrices.translate(0f, -SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.translate(x, y + SLOT_SIZE * 6, 0f);
		matrices.multiply(rot);
		matrices.translate(0f, -SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 0.75f, y + SLOT_SIZE * 6.67333359f, 0f);
		matrices.multiply(rot);
		matrices.translate(0f, -SLOT_SIZE, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		this.drawSlot(x, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);

		for (int j = lengths.length; j >= 1; j--) {
			for (int k = lengths[j - 1]; k >= 1; k--) {
				this.drawSlot(x + squareSize - SLOT_SIZE * k, y + squareSize - SLOT_SIZE * j, i++, full, ctx, textRenderer);
			}
		}
	}

	@Unique
	private void drawFiftyFive(TextRenderer textRenderer, int x, int y, DrawContext ctx, boolean full) {
		int i = 0;
		int squareSize = getSquareSize(55);
		ctx.drawGuiTexture(BACKGROUND_TEXTURE, x, y, squareSize + 2, squareSize + 2);
		x++;
		y++;
		var matrices = ctx.getMatrices();

		for (int j = 0; j < 3; j++) {
			for (int k = 0; k < 3 - j; k++) {
				this.drawSlot(x + SLOT_SIZE * k, y + SLOT_SIZE * j, i++, full, ctx, textRenderer);
			}
		}

		for (int j = 4; j >= 1; j--) {
			this.drawSlot(x + squareSize - SLOT_SIZE * j, y, i++, full, ctx, textRenderer);
		}

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 3.13009105f, y + SLOT_SIZE * 0.67256345f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(21.66800178f));
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		Matrix4f mat1;

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 3.16066671f, y + SLOT_SIZE * 5.96465671f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(13.30405178f));
		mat1 = new Matrix4f(matrices.peek().getPositionMatrix());
		matrices.translate(SLOT_SIZE * -0.25819987f, SLOT_SIZE * -5.04051442, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.04051442f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		this.drawSlot(SLOT_SIZE, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE * 2, SLOT_SIZE * -0.00696516f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 2, y + SLOT_SIZE * 2, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(15.87748802f));
		matrices.translate(0f, SLOT_SIZE * -0.55282875f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		Matrix4f mat2;

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 3.09209914f, y + SLOT_SIZE * 1.81565139f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(14.57170350f));
		mat2 = new Matrix4f(matrices.peek().getPositionMatrix());
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(mat1);
		matrices.translate(SLOT_SIZE * -0.02173524f, SLOT_SIZE * -4, 0f);
		for (int j = 0; j < 3; j++) {
			this.drawSlot(SLOT_SIZE * j, 0, i++, full, ctx, textRenderer);
		}
		matrices.translate(SLOT_SIZE * 3, SLOT_SIZE * -0.00696516f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(mat2);
		matrices.translate(SLOT_SIZE * -1.72683179f, SLOT_SIZE * 0.90693694f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.09306304f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(mat1);
		matrices.translate(SLOT_SIZE * -0.77086806f, SLOT_SIZE * -3, 0f);
		for (int j = 0; j < 4; j++) {
			this.drawSlot(SLOT_SIZE * j, 0, i++, full, ctx, textRenderer);
		}
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 1.18415667f, y + SLOT_SIZE * 3.40017871f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(13.79356726f));
		matrices.translate(-SLOT_SIZE, SLOT_SIZE * -0.10665350f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.10665350f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(mat1);
		matrices.translate(SLOT_SIZE * -1.5139121f, SLOT_SIZE * -2, 0f);
		for (int j = 0; j < 4; j++) {
			this.drawSlot(SLOT_SIZE * j, 0, i++, full, ctx, textRenderer);
		}
		matrices.translate(SLOT_SIZE * -1.74304404f, SLOT_SIZE * 0.96140677f, 0f);
		this.drawSlot(0, 0, i++, full, ctx, textRenderer);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.03859322f, 0f);
		for (int j = 0; j < 4; j++) {
			this.drawSlot(SLOT_SIZE * j, 0, i++, full, ctx, textRenderer);
		}
		matrices.translate(SLOT_SIZE * -0.74304404f, SLOT_SIZE, 0f);
		for (int j = 0; j < 4; j++) {
			this.drawSlot(SLOT_SIZE * j, 0, i++, full, ctx, textRenderer);
		}
		matrices.pop();

		for (int j = 0; j < 3; j++) {
			this.drawSlot(x + SLOT_SIZE * j, y + squareSize - SLOT_SIZE, i++, full, ctx, textRenderer);
		}

		for (int j = 4; j >= 1; j--) {
			for (int k = 5 - j; k >= 1; k--) {
				this.drawSlot(x + squareSize - SLOT_SIZE * k, y + squareSize - SLOT_SIZE * j, i++, full, ctx, textRenderer);
			}
		}
	}
}


