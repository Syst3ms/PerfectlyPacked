package io.github.syst3ms.perfectlypacked.client;

import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.util.Mth;
import org.joml.Matrix3x2f;
import org.joml.Matrix3x2fStack;

import java.util.function.IntSupplier;

public class SquarePackingRendering {
	public static IntSet SPECIAL_SLOT_COUNTS = IntSet.of(
		5, 10, 11, 17, 18, 19, 26, 27, 28, 29, 37, 38, 39, 40, 41,
		50, 51, 52, 53, 54, 55//, 65
	);

	public static int trivialPackingWidth(int n) {
		return (int) Math.ceil(Math.sqrt(n));
	}

	public static int trivialPackingHeight(int n) {
		return Mth.positiveCeilDiv(n, trivialPackingWidth(n));
	}

	public static int getSquareSize(int slotSize, int n, IntSupplier def) {
		return (int) switch (n) {
			case 5 -> Math.ceil(slotSize * 2.70710678f);
			case 10 -> Math.ceil(slotSize * 3.70710678f);
			case 11 -> Math.ceil(slotSize * 3.87708359f);
			case 17 -> Math.ceil(slotSize * 4.67553009f);
			case 18 -> Math.ceil(slotSize * 4.82287565f);
			case 19 -> Math.ceil(slotSize * 4.88561808f);
			case 26 -> Math.ceil(slotSize * 5.62132034f);
			case 27 -> Math.ceil(slotSize * 5.70710678f);
			case 28 -> Math.ceil(slotSize * 5.82842712f);
			case 29 -> Math.ceil(slotSize * 5.93434180f);
			case 37 -> Math.ceil(slotSize * 6.59861960f);
			case 38 -> Math.ceil(slotSize * 6.70710678f);
			case 39 -> Math.ceil(slotSize * 6.81880916f);
			case 40 -> Math.ceil(slotSize * 6.82287565f);
			case 41 -> Math.ceil(slotSize * 6.93786550f);
			case 50 -> Math.ceil(slotSize * 7.59861960f);
			case 51 -> Math.ceil(slotSize * 7.70435372f);
			case 52 -> Math.ceil(slotSize * 7.70710678f);
			case 53 -> Math.ceil(slotSize * 7.82303789f);
			case 54 -> Math.ceil(slotSize * 7.84666719f);
			case 55 -> Math.ceil(slotSize * 7.95424222f);
			// case 65 -> Math.ceil(slotSize * 8.53553390f);
			default -> def.getAsInt();
		};
	}

	public static int getSpecialSquareSize(int slotSize, int n) {
		return getSquareSize(slotSize, n, () -> {throw new IllegalStateException();});
	}

	public static int getWidth(int slotSize, int n) {
		return getSquareSize(slotSize, n, () -> slotSize * trivialPackingWidth(n));
	}

	public static int getHeight(int slotSize, int n) {
		return getSquareSize(slotSize, n, () -> slotSize * trivialPackingHeight(n));
	}

	@FunctionalInterface
	public interface Slots {
		void drawSlot(int x, int y, int index);
	}

	public static void drawSpecial(int n, int slotSize, Slots drawer, int x, int y, Matrix3x2fStack pose) {
		switch (n) {
			case 5 -> drawFive(slotSize, drawer, x, y, pose);
			case 10 -> drawTen(slotSize, drawer, x, y, pose);
			case 11 -> drawEleven(slotSize, drawer, x, y, pose);
			case 17 -> drawSeventeen(slotSize, drawer, x, y, pose);
			case 18 -> drawEighteen(slotSize, drawer, x, y, pose);
			case 19 -> drawNineteen(slotSize, drawer, x, y, pose);
			case 26 -> drawTwentySix(slotSize, drawer, x, y, pose);
			case 27 -> drawTwentySeven(slotSize, drawer, x, y, pose);
			case 28 -> drawTwentyEight(slotSize, drawer, x, y, pose);
			case 29 -> drawTwentyNine(slotSize, drawer, x, y, pose);
			case 37 -> drawThirtySeven(slotSize, drawer, x, y, pose);
			case 38 -> drawThirtyEight(slotSize, drawer, x, y, pose);
			case 39 -> drawThirtyNine(slotSize, drawer, x, y, pose);
			case 40 -> drawForty(slotSize, drawer, x, y, pose);
			case 41 -> drawFortyOne(slotSize, drawer, x, y, pose);
			case 50 -> drawFifty(slotSize, drawer, x, y, pose);
			case 51 -> drawFiftyOne(slotSize, drawer, x, y, pose);
			case 52 -> drawFiftyTwo(slotSize, drawer, x, y, pose);
			case 53 -> drawFiftyThree(slotSize, drawer, x, y, pose);
			case 54 -> drawFiftyFour(slotSize, drawer, x, y, pose);
			case 55 -> drawFiftyFive(slotSize, drawer, x, y, pose);
			// case 65 -> drawSixtyFive(slotSize, drawer, x, y, pose);
		}
	}

	public static void drawFive(int slotSize, Slots drawer, int x, int y, Matrix3x2fStack pose) {
		x++;
		y++;

		int straightOffset = Mth.ceil(slotSize * 1.70710678f);
		drawer.drawSlot(x, y, 0);
		drawer.drawSlot(x + straightOffset, y, 1);
		
		pose.pushMatrix();
		pose.translate(x + slotSize, y + slotSize);
		pose.rotate(Mth.DEG_TO_RAD * 45);
		pose.translate(0f, slotSize * -0.5f);
		drawer.drawSlot(0, 0, 2);
		pose.popMatrix();

		drawer.drawSlot(x, y + straightOffset, 3);
		drawer.drawSlot(x + straightOffset, y + straightOffset, 4);
	}

	public static void drawTen(int slotSize, Slots drawer, int x, int y, Matrix3x2fStack pose) {
		int i = 0;
		int gap = Mth.ceil(slotSize * 1.70710678f);
		x++;
		y++;
		
		drawer.drawSlot(x, y, i++);
		drawer.drawSlot(x + gap, y, i++);
		drawer.drawSlot(x + gap + slotSize, y, i++);
		drawer.drawSlot(x, y + slotSize, i++);
		drawer.drawSlot(x + gap, y + slotSize, i++);
		drawer.drawSlot(x + gap + slotSize, y + slotSize, i++);

		pose.pushMatrix();
		pose.translate(x + slotSize, y + slotSize * 2);
		pose.rotate(Mth.DEG_TO_RAD * 45);
		pose.translate(0, -slotSize/2f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x, y + slotSize + gap, i++);
		drawer.drawSlot(x + gap, y + slotSize + gap, i++);
		drawer.drawSlot(x + gap + slotSize, y + slotSize + gap, i);
	}

	public static void drawEleven(int slotSize, Slots drawer, int x, int y, Matrix3x2fStack pose) {
		int i = 0;
		int squareSize = getSpecialSquareSize(slotSize, 11);
		x++;
		y++;
		drawer.drawSlot(x, y, i++);
		drawer.drawSlot(x + squareSize - slotSize, y, i++);

		Matrix3x2f baseTransform;
		pose.pushMatrix();
		pose.translate(x + slotSize, y + slotSize);
		pose.rotate(Mth.DEG_TO_RAD * 40.18193729f);
		pose.translate(0f, slotSize * -0.32990859f);
		baseTransform = new Matrix3x2f(pose);
		pose.translate(slotSize * .02487453f, -slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * 0.11878261f);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * 0.33377596f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();
		
		pose.pushMatrix();
		pose.mul(baseTransform);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * 0.11878260f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x, y + squareSize - slotSize * 2, i++);
		drawer.drawSlot(x, y + squareSize - slotSize, i++);
		drawer.drawSlot(x + slotSize, y + squareSize - slotSize, i++);

		pose.pushMatrix();
		pose.translate(x + slotSize * 2.03255831f, y + slotSize * 2.87708359f);
		drawer.drawSlot(0, 0, i);
		pose.popMatrix();
	}

	public static void drawSeventeen(int slotSize, Slots drawer, int x, int y, Matrix3x2fStack pose) {
		int i = 0;
		float normSize = 4.67553009f;
		int squareSize = getSpecialSquareSize(slotSize, 17);
		x++;
		y++;
		drawer.drawSlot(x, y, i++);
		drawer.drawSlot(x + slotSize, y, i++);
		drawer.drawSlot(x + squareSize - slotSize * 2, y, i++);
		drawer.drawSlot(x + squareSize - slotSize, y, i++);
		drawer.drawSlot(x, y + slotSize, i++);

		Matrix3x2f baseTransform;

		pose.pushMatrix();
		pose.translate(x + slotSize * 0.64017619f, y + slotSize * 2);
		pose.rotate(Mth.DEG_TO_RAD * 39.80495897f);
		baseTransform = new Matrix3x2f(pose);
		pose.translate(slotSize * 0.40447869f, slotSize * -2);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x + squareSize - slotSize, y + slotSize, i++);

		pose.pushMatrix();
		pose.mul(baseTransform);
		pose.translate(slotSize * 0.27642676f, -slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.mul(baseTransform);
		pose.translate(slotSize * 1.40447869f, slotSize * -1.94316130f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.mul(baseTransform);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.mul(baseTransform);
		pose.translate(slotSize * 1.27642676f, slotSize * -0.94316130f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.translate(x + slotSize * (normSize - 1), y + slotSize * 2.11346013f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.mul(baseTransform);
		pose.translate(slotSize, slotSize * 0.05683896f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.translate(x + slotSize * 2.84732482f, y + slotSize * (normSize - 1));
		pose.rotate(Mth.DEG_TO_RAD * -36.62378638f);
		pose.translate(0f, slotSize * -0.50592742f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x, y + squareSize - slotSize, i++);
		drawer.drawSlot(x + (int) Math.ceil(slotSize * 1.84732482f), y + squareSize - slotSize, i++);
		drawer.drawSlot(x + squareSize - slotSize, y + squareSize - slotSize, i);
	}

	public static void drawEighteen(int slotSize, Slots drawer, int x, int y, Matrix3x2fStack pose) {
		int i = 0;
		int squareSize = getSpecialSquareSize(slotSize, 18);
		x++;
		y++;
		drawer.drawSlot(x, y, i++);
		drawer.drawSlot(x + squareSize - slotSize * 3, y, i++);
		drawer.drawSlot(x + squareSize - slotSize * 2, y, i++);
		drawer.drawSlot(x + squareSize - slotSize, y, i++);
		drawer.drawSlot(x, y + slotSize, i++);

		pose.pushMatrix();
		pose.translate(x + slotSize * 1.51429728f, y + slotSize * 0.86070271f);
		pose.rotate(Mth.DEG_TO_RAD * 24.29518894f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x + squareSize - slotSize * 2, y + slotSize, i++);
		drawer.drawSlot(x + squareSize - slotSize, y + slotSize, i++);

		pose.pushMatrix();
		pose.translate(x + slotSize * 1.51429728f, y + slotSize * 0.86070271f);
		pose.rotate(Mth.DEG_TO_RAD * 24.29518894f);
		pose.translate(0, slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.translate(x + slotSize * 2.30857837f, y + slotSize * 3.96217294f);
		pose.rotate(Mth.DEG_TO_RAD * 24.29518894f);
		pose.translate(-slotSize, slotSize * -2);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x, y + squareSize - slotSize * 2, i++);

		pose.pushMatrix();
		pose.translate(x + slotSize * 2.30857837f, y + slotSize * 3.96217294f);
		pose.rotate(Mth.DEG_TO_RAD * 24.29518894f);
		pose.translate(-slotSize, -slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x + squareSize - slotSize * 2, y + squareSize - slotSize * 2, i++);
		drawer.drawSlot(x + squareSize - slotSize, y + squareSize - slotSize * 2, i++);
		drawer.drawSlot(x, y + squareSize - slotSize, i++);
		drawer.drawSlot(x + slotSize, y + squareSize - slotSize, i++);
		drawer.drawSlot(x + squareSize - slotSize * 2, y + squareSize - slotSize, i++);
		drawer.drawSlot(x + squareSize - slotSize, y + squareSize - slotSize, i);
	}

	public static void drawNineteen(int slotSize, Slots drawer, int x, int y, Matrix3x2fStack pose) {
		int i = 0;
		float normSize = 4.88561808f;
		int squareSize = getSpecialSquareSize(slotSize, 19);
		x++;
		y++;
		
		drawer.drawSlot(x, y, i++);
		drawer.drawSlot(x + squareSize - slotSize * 3, y, i++);
		drawer.drawSlot(x + squareSize - slotSize * 2, y, i++);
		drawer.drawSlot(x + squareSize - slotSize, y, i++);

		Matrix3x2f baseTransform;

		pose.pushMatrix();
		pose.translate(x + slotSize * (normSize / 2f - 1), y + slotSize * (3 - normSize / 2f));
		pose.rotate(Mth.DEG_TO_RAD * 45);
		baseTransform = new Matrix3x2f(pose);
		drawer.drawSlot(0, 0, i++);
		pose.translate(0f, slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.mul(baseTransform);
		pose.translate(slotSize, slotSize * -0.04044011f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x + squareSize - slotSize * 2, y + slotSize, i++);
		drawer.drawSlot(x + squareSize - slotSize, y + slotSize, i++);

		pose.pushMatrix();
		pose.mul(baseTransform);
		pose.translate(slotSize, slotSize * 0.95955989f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.translate(x + slotSize * 3 * (normSize / 2f - 1), y + slotSize * (normSize / 2f + 1));
		pose.rotate(Mth.DEG_TO_RAD * 45);
		pose.translate(slotSize * -2, slotSize * -0.04044011f);
		baseTransform = new Matrix3x2f(pose);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x + squareSize - slotSize, y + slotSize * 2, i++);
		drawer.drawSlot(x, y + squareSize - slotSize * 2, i++);

		pose.pushMatrix();
		pose.mul(baseTransform);
		pose.translate(0f, slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * -0.95955989f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x, y + squareSize - slotSize, i++);
		drawer.drawSlot(x + slotSize, y + squareSize - slotSize, i++);

		pose.pushMatrix();
		pose.mul(baseTransform);
		pose.translate(slotSize, slotSize * 1.04044011f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x + squareSize - slotSize, y + squareSize - slotSize, i);
	}

	public static void drawTwentySix(int slotSize, Slots drawer, int x, int y, Matrix3x2fStack pose) {
		int i = 0;
		float normSize = 5.62132034f;
		int squareSize = getSpecialSquareSize(slotSize, 26);
		x++;
		y++;

		drawer.drawSlot(x, y, i++);
		drawer.drawSlot(x + slotSize, y, i++);
		drawer.drawSlot(x + squareSize - slotSize * 3, y, i++);
		drawer.drawSlot(x + squareSize - slotSize * 2, y, i++);
		drawer.drawSlot(x + squareSize - slotSize, y, i++);
		drawer.drawSlot(x, y + slotSize, i++);

		Matrix3x2f baseTransform;

		pose.pushMatrix();
		pose.translate(x + slotSize * (normSize - 1) / 2, y + slotSize * normSize / 2);
		pose.rotate(Mth.DEG_TO_RAD * 45);
		pose.translate(slotSize * -1.5f, slotSize * -1.5f);
		baseTransform = new Matrix3x2f(pose);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x + squareSize - slotSize * 2, y + slotSize, i++);
		drawer.drawSlot(x + squareSize - slotSize, y + slotSize, i++);

		pose.pushMatrix();
		pose.mul(baseTransform);
		pose.translate(0f, slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, -slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.translate(-slotSize, slotSize * 2);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, -slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, -slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x + squareSize - slotSize, y + squareSize / 2 - slotSize / 2, i++);

		pose.pushMatrix();
		pose.mul(baseTransform);
		pose.translate(slotSize, slotSize * 2);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, -slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x, y + squareSize - slotSize * 2, i++);

		pose.pushMatrix();
		pose.mul(baseTransform);
		pose.translate(slotSize * 2, slotSize * 2);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x + squareSize - slotSize * 2, y + squareSize - slotSize * 2, i++);
		drawer.drawSlot(x + squareSize - slotSize, y + squareSize - slotSize * 2, i++);
		drawer.drawSlot(x, y + squareSize - slotSize, i++);
		drawer.drawSlot(x + slotSize, y + squareSize - slotSize, i++);
		drawer.drawSlot(x + squareSize - slotSize * 3, y + squareSize - slotSize, i++);
		drawer.drawSlot(x + squareSize - slotSize * 2, y + squareSize - slotSize, i++);
		drawer.drawSlot(x + squareSize - slotSize, y + squareSize - slotSize, i);
	}

	public static void drawTwentySeven(int slotSize, Slots drawer, int x, int y, Matrix3x2fStack pose) {
		int i = 0;
		var fiveOffset = (int) Math.floor(slotSize * 1.70710678f);
		int squareSize = getSpecialSquareSize(slotSize, 27);
		x++;
		y++;

		drawer.drawSlot(x, y, i++);
		drawer.drawSlot(x + squareSize - slotSize * 4, y, i++);
		drawer.drawSlot(x + squareSize - slotSize * 3, y, i++);
		drawer.drawSlot(x + squareSize - slotSize * 2, y, i++);
		drawer.drawSlot(x + squareSize - slotSize, y, i++);

		pose.pushMatrix();
		pose.translate(x + slotSize, y + slotSize);
		pose.rotate(Mth.DEG_TO_RAD * 45);
		pose.translate(0f, slotSize * -0.5f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x + squareSize - slotSize * 3, y + slotSize, i++);
		drawer.drawSlot(x + squareSize - slotSize * 2, y + slotSize, i++);
		drawer.drawSlot(x + squareSize - slotSize, y + slotSize, i++);
		drawer.drawSlot(x, y + fiveOffset, i++);
		drawer.drawSlot(x + fiveOffset, y + fiveOffset, i++);
		drawer.drawSlot(x + squareSize - slotSize * 2, y + slotSize * 2, i++);
		drawer.drawSlot(x + squareSize - slotSize, y + slotSize * 2, i++);
		drawer.drawSlot(x, y + squareSize - slotSize * 3, i++);
		drawer.drawSlot(x + slotSize, y + squareSize - slotSize * 3, i++);
		drawer.drawSlot(x + slotSize * 2, y + squareSize - slotSize * 3, i++);
		drawer.drawSlot(x + slotSize * 3, y + slotSize * 3, i++);
		drawer.drawSlot(x + slotSize * 3 + fiveOffset, y + slotSize * 3, i++);
		drawer.drawSlot(x, y + squareSize - slotSize * 2, i++);
		drawer.drawSlot(x + slotSize, y + squareSize - slotSize * 2, i++);
		drawer.drawSlot(x + slotSize * 2, y + squareSize - slotSize * 2, i++);

		pose.pushMatrix();
		pose.translate(x + slotSize * 4, y + slotSize * 4);
		pose.rotate(Mth.DEG_TO_RAD * 45);
		pose.translate(0f, slotSize * -0.5f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x, y + squareSize - slotSize, i++);
		drawer.drawSlot(x + slotSize, y + squareSize - slotSize, i++);
		drawer.drawSlot(x + slotSize * 2, y + squareSize - slotSize, i++);
		drawer.drawSlot(x + slotSize * 3, y + squareSize - slotSize, i++);
		drawer.drawSlot(x + slotSize * 3 + fiveOffset, y + squareSize - slotSize, i);
	}

	public static void drawTwentyEight(int slotSize, Slots drawer, int x, int y, Matrix3x2fStack pose) {
		int i = 0;
		int squareSize = getSpecialSquareSize(slotSize, 28);
		int centerOffset = (squareSize - slotSize) / 2;
		x++;
		y++;
		
		drawer.drawSlot(x, y, i++);
		drawer.drawSlot(x + slotSize, y, i++);
		drawer.drawSlot(x + centerOffset, y, i++);
		drawer.drawSlot(x + squareSize - slotSize * 2, y, i++);
		drawer.drawSlot(x + squareSize - slotSize, y, i++);
		drawer.drawSlot(x, y + slotSize, i++);

		Matrix3x2f baseTransform;

		pose.pushMatrix();
		pose.translate(x + slotSize * 1.5f, y + slotSize * 1.5f);
		pose.rotate(Mth.DEG_TO_RAD * 45);
		baseTransform = new Matrix3x2f(pose);
		pose.translate(0f, -slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, -slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x + squareSize - slotSize, y + slotSize, i++);

		pose.pushMatrix();
		pose.mul(baseTransform);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, -slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, -slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x, y + centerOffset, i++);

		pose.pushMatrix();
		pose.mul(baseTransform);
		pose.translate(slotSize, 0f);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, -slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x + squareSize - slotSize, y + centerOffset, i++);

		pose.pushMatrix();
		pose.mul(baseTransform);
		pose.translate(slotSize, slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, -slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, -slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x, y + squareSize - slotSize * 2, i++);

		pose.pushMatrix();
		pose.mul(baseTransform);
		pose.translate(slotSize * 2, slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, -slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x + squareSize - slotSize, y + squareSize - slotSize * 2, i++);
		drawer.drawSlot(x, y + squareSize - slotSize, i++);
		drawer.drawSlot(x + slotSize, y + squareSize - slotSize, i++);
		drawer.drawSlot(x + centerOffset, y + squareSize - slotSize, i++);
		drawer.drawSlot(x + squareSize - slotSize * 2, y + squareSize - slotSize, i++);
		drawer.drawSlot(x + squareSize - slotSize, y + squareSize - slotSize, i);
	}

	public static void drawTwentyNine(int slotSize, Slots drawer, int x, int y, Matrix3x2fStack pose) {
		int i = 0;
		int squareSize = getSpecialSquareSize(slotSize, 29);
		x++;
		y++;
		
		drawer.drawSlot(x, y, i++);
		drawer.drawSlot(x + slotSize, y, i++);
		drawer.drawSlot(x + squareSize - slotSize * 3, y, i++);
		drawer.drawSlot(x + squareSize - slotSize * 2, y, i++);
		drawer.drawSlot(x + squareSize - slotSize, y, i++);

		pose.pushMatrix();
		pose.translate(x + slotSize * 2, y + slotSize);
		pose.rotate(Mth.DEG_TO_RAD * 25.99204718f);
		pose.translate(0f, slotSize * -0.40947192f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.translate(x + slotSize * 5.58822412f, y + slotSize * 2.93434180f);
		pose.rotate(Mth.DEG_TO_RAD * 20.25003828f);
		pose.translate(slotSize * -3, slotSize * -0.95501732f);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * -0.04498268f);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, 0f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x, y + slotSize, i++);

		pose.pushMatrix();
		pose.translate(x + slotSize, y + slotSize * 2);
		pose.rotate(Mth.DEG_TO_RAD * 21.62093977f);
		pose.translate(0f, slotSize * -0.67231250f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.translate(x + slotSize * 4.93434180f, y + slotSize * 2.93434180f);
		pose.rotate(Mth.DEG_TO_RAD * 20.25540431f);
		pose.translate(-slotSize * 3, slotSize * -0.11130742f);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * -0.05050338f);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, 0f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x + squareSize - slotSize, y + squareSize - slotSize * 3, i++);

		Matrix3x2f baseTransform;

		pose.pushMatrix();
		pose.translate(x + slotSize * 2.93434180f, y + slotSize * 4.93434180f);
		pose.rotate(Mth.DEG_TO_RAD * 18.17734536f);
		pose.translate(-slotSize, slotSize * -0.70852340f);
		baseTransform = new Matrix3x2f(pose);
		pose.translate(slotSize * -2.36186381f, slotSize * -1.12411661f);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * 0.10208347f);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * 0.02203314f);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, 0f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x + squareSize - slotSize * 2, y + squareSize - slotSize * 2, i++);
		drawer.drawSlot(x + squareSize - slotSize, y + squareSize - slotSize * 2, i++);

		pose.pushMatrix();
		pose.mul(baseTransform);
		pose.translate(slotSize * -2, slotSize * -0.02203314f);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * 0.02203314f);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, 0f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x, y + squareSize - slotSize, i++);
		drawer.drawSlot(x + slotSize, y + squareSize - slotSize, i++);
		drawer.drawSlot(x + squareSize - slotSize * 3, y + squareSize - slotSize, i++);
		drawer.drawSlot(x + squareSize - slotSize * 2, y + squareSize - slotSize, i++);
		drawer.drawSlot(x + squareSize - slotSize, y + squareSize - slotSize, i);
	}

	public static void drawThirtySeven(int slotSize, Slots drawer, int x, int y, Matrix3x2fStack pose) {
		int i = 0;
		float normSize = 6.59861960f;
		int squareSize = getSpecialSquareSize(slotSize, 37);
		x++;
		y++;

		drawer.drawSlot(x, y, i++);
		drawer.drawSlot(x + slotSize, y, i++);
		drawer.drawSlot(x + squareSize - slotSize * 3, y, i++);
		drawer.drawSlot(x + squareSize - slotSize * 2, y, i++);
		drawer.drawSlot(x + squareSize - slotSize, y, i++);
		drawer.drawSlot(x + squareSize - slotSize * 2, y + slotSize, i++);
		drawer.drawSlot(x + squareSize - slotSize, y + slotSize, i++);
		drawer.drawSlot(x + squareSize - slotSize, y + slotSize * 2, i++);

		float offset = 0.07187936f;

		pose.pushMatrix();
		pose.translate(x + slotSize * 0.70223662f, y + slotSize * 2.32969614f);
		pose.rotate(Mth.DEG_TO_RAD * 42.08660748f);
		pose.translate(slotSize * offset, slotSize * -3);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * offset);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.translate(x + slotSize * 2, y + slotSize * (normSize - 2.70710678f));
		pose.rotate(Mth.DEG_TO_RAD * 45);
		pose.translate(0f, slotSize * -3f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.translate(x + slotSize * (normSize - 2.32969614f), y + slotSize * (normSize - 0.70223662f));
		pose.rotate(Mth.DEG_TO_RAD * 47.91339252f);
		pose.translate(slotSize * -(2 + offset), slotSize * -(3 - offset));
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * -offset);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x, y + slotSize, i++);

		pose.pushMatrix();
		pose.translate(x + slotSize * 0.70223662f, y + slotSize * 2.32969614f);
		pose.rotate(Mth.DEG_TO_RAD * 42.08660748f);
		pose.translate(slotSize * offset, slotSize * -2);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * -offset);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.translate(x + slotSize * 2, y + slotSize * (normSize - 2.70710678f));
		pose.rotate(Mth.DEG_TO_RAD * 45);
		pose.translate(0f, slotSize * -2);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.translate(x + slotSize * (normSize - 2.32969614f), y + slotSize * (normSize - 0.70223662f));
		pose.rotate(Mth.DEG_TO_RAD * 47.91339252f);
		pose.translate(slotSize * -(2 + offset), slotSize * -(2 - offset));
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * -offset);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x + squareSize - slotSize, y + squareSize - slotSize * 2, i++);

		pose.pushMatrix();
		pose.translate(x + slotSize * 0.70223662f, y + slotSize * 2.32969614f);
		pose.rotate(Mth.DEG_TO_RAD * 42.08660748f);
		pose.translate(0f, -slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * offset);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.translate(x + slotSize * 2, y + slotSize * (normSize - 2.70710678f));
		pose.rotate(Mth.DEG_TO_RAD * 45);
		pose.translate(0f, -slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.translate(x + slotSize * (normSize - 2.32969614f), y + slotSize * (normSize - 0.70223662f));
		pose.rotate(Mth.DEG_TO_RAD * 47.91339252f);
		pose.translate(slotSize * -2, slotSize * -(1 - offset));
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * -offset);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x, y + squareSize - slotSize * 4, i++);
		drawer.drawSlot(x, y + squareSize - slotSize * 3, i++);
		drawer.drawSlot(x + slotSize, y + squareSize - slotSize * 3, i++);
		drawer.drawSlot(x, y + squareSize - slotSize * 2, i++);
		drawer.drawSlot(x + slotSize, y + squareSize - slotSize * 2, i++);
		drawer.drawSlot(x + slotSize * 2, y + squareSize - slotSize * 2, i++);
		drawer.drawSlot(x, y + squareSize - slotSize, i++);
		drawer.drawSlot(x + slotSize, y + squareSize - slotSize, i++);
		drawer.drawSlot(x + slotSize * 2, y + squareSize - slotSize, i++);
		drawer.drawSlot(x + slotSize * 3, y + squareSize - slotSize, i++);
		drawer.drawSlot(x + squareSize - slotSize * 2, y + squareSize - slotSize, i++);
		drawer.drawSlot(x + squareSize - slotSize, y + squareSize - slotSize, i);
	}

	public static void drawThirtyEight(int slotSize, Slots drawer, int x, int y, Matrix3x2fStack pose) {
		int i = 0;
		var fiveOffset = (int) Math.floor(slotSize * 1.70710678f);
		int squareSize = getSpecialSquareSize(slotSize, 38);
		x++;
		y++;
		
		drawer.drawSlot(x, y, i++);
		for (int j = 5; j >= 1; j--) {
			drawer.drawSlot(x + squareSize - slotSize * j, y, i++);
		}

		pose.pushMatrix();
		pose.translate(x + slotSize, y + slotSize);
		pose.rotate(Mth.DEG_TO_RAD * 45);
		pose.translate(0f, slotSize * -0.5f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x, y + squareSize - slotSize * 5, i++);
		drawer.drawSlot(x + fiveOffset, y + squareSize - slotSize * 5, i++);

		for (int j = 4; j >= 1; j--) {
			drawer.drawSlot(x + squareSize - slotSize * j, y + slotSize, i++);
		}

		drawer.drawSlot(x + squareSize - slotSize * 2, y + slotSize * 2, i++);
		drawer.drawSlot(x + squareSize - slotSize, y + slotSize * 2, i++);

		drawer.drawSlot(x, y + squareSize - slotSize * 4, i++);
		drawer.drawSlot(x + slotSize, y + squareSize - slotSize * 4, i++);
		drawer.drawSlot(x + slotSize * 2, y + squareSize - slotSize * 4, i++);
		drawer.drawSlot(x + slotSize * 3, y + squareSize - slotSize * 4, i++);

		drawer.drawSlot(x + squareSize - slotSize * 2, y + slotSize * 3, i++);
		drawer.drawSlot(x + squareSize - slotSize, y + slotSize * 3, i++);

		drawer.drawSlot(x, y + squareSize - slotSize * 3, i++);
		drawer.drawSlot(x + slotSize, y + squareSize - slotSize * 3, i++);
		drawer.drawSlot(x + slotSize * 2, y + squareSize - slotSize * 3, i++);
		drawer.drawSlot(x + slotSize * 3, y + squareSize - slotSize * 3, i++);

		drawer.drawSlot(x + slotSize * 4, y + slotSize * 4, i++);
		drawer.drawSlot(x + slotSize * 4 + fiveOffset, y + slotSize * 4, i++);

		drawer.drawSlot(x, y + squareSize - slotSize * 2, i++);
		drawer.drawSlot(x + slotSize, y + squareSize - slotSize * 2, i++);
		drawer.drawSlot(x + slotSize * 2, y + squareSize - slotSize * 2, i++);
		drawer.drawSlot(x + slotSize * 3, y + squareSize - slotSize * 2, i++);

		pose.pushMatrix();
		pose.translate(x + slotSize * 5, y + slotSize * 5);
		pose.rotate(Mth.DEG_TO_RAD * 45);
		pose.translate(0f, slotSize * -0.5f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x, y + squareSize - slotSize, i++);
		drawer.drawSlot(x + slotSize, y + squareSize - slotSize, i++);
		drawer.drawSlot(x + slotSize * 2, y + squareSize - slotSize, i++);
		drawer.drawSlot(x + slotSize * 3, y + squareSize - slotSize, i++);
		drawer.drawSlot(x + slotSize * 4, y + squareSize - slotSize, i++);
		drawer.drawSlot(x + squareSize - slotSize, y + squareSize - slotSize, i);
	}

	public static void drawThirtyNine(int slotSize, Slots drawer, int x, int y, Matrix3x2fStack pose) {
		int i = 0;
		float normSize = 6.81880916f;
		int squareSize = getSpecialSquareSize(slotSize, 39);
		x++;
		y++;

		drawer.drawSlot(x, y, i++);
		for (int k = 5; k >= 1; k--) {
			drawer.drawSlot(x + squareSize - slotSize * k, y, i++);
		}
		for (int k = 3; k >= 1; k--) {
			drawer.drawSlot(x + squareSize - slotSize * k, y + slotSize, i++);
		}
		drawer.drawSlot(x, y + squareSize - slotSize * 5, i++);

		pose.pushMatrix();
		pose.translate(x + slotSize * 0.60617891f, y + slotSize * 1.29752487f);
		pose.rotate(Mth.DEG_TO_RAD * -37.07040322f);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize * 0.12360160f, slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * 0.23145647f);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize * -0.13060668f, slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x + squareSize - slotSize * 2, y + slotSize * 2, i++);
		drawer.drawSlot(x + squareSize - slotSize, y + slotSize * 2, i++);
		for (int k = 0; k <= 2; k++) {
			drawer.drawSlot(x + slotSize * k, y + squareSize - slotSize * 4, i++);
		}
		drawer.drawSlot(x + squareSize - slotSize, y + slotSize * 3, i++);
		for (int k = 0; k <= 3; k++) {
			drawer.drawSlot(x + slotSize * k, y + squareSize - slotSize * 3, i++);
		}
		drawer.drawSlot(x + squareSize - slotSize, y + slotSize * 4, i++);
		for (int k = 0; k <= 3; k++) {
			drawer.drawSlot(x + slotSize * k, y + squareSize - slotSize * 2, i++);
		}
		for (int k = 0; k <= 4; k++) {
			drawer.drawSlot(x + slotSize * k, y + squareSize - slotSize, i++);
		}

		pose.pushMatrix();
		pose.translate(x + slotSize * (normSize - 1.29752487f), y + slotSize * (normSize - 0.60617891f));
		pose.rotate(Mth.DEG_TO_RAD * 37.07040322f);
		pose.translate(slotSize * -3.12360160f, slotSize * -1.99299492f);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * -0.12360160f);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize * 0.23145647f, slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * 0.13060668f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x + squareSize - slotSize, y + squareSize - slotSize, i);
	}

	public static void drawForty(int slotSize, Slots drawer, int x, int y, Matrix3x2fStack pose) {
		int i = 0;
		int squareSize = getSpecialSquareSize(slotSize, 40);
		x++;
		y++;

		for (int j = 0; j < 3; j++) {
			for (int k = 0; k < 3 - j; k++) {
				drawer.drawSlot(x + slotSize * k, y + slotSize * j, i++);
			}
		}
		for (int j = 0; j < 3; j++) {
			for (int k = 3 - j; k >= 1; k--) {
				drawer.drawSlot(x + squareSize - slotSize * k, y + slotSize * j, i++);
			}
		}

		pose.pushMatrix();
		pose.translate(x + slotSize * 2, y + slotSize * 2);
		pose.rotate(Mth.DEG_TO_RAD * 45);
		pose.translate(0f, slotSize * -2);

		for (int j = 0; j < 4; j++) {
			for (int k = 0; k < 4; k++) {
				drawer.drawSlot(slotSize * k, slotSize * j, i++);
			}
		}

		pose.popMatrix();

		for (int j = 3; j >= 1; j--) {
			for (int k = 0; k < 4 - j; k++) {
				drawer.drawSlot(x + slotSize * k, y + squareSize - slotSize * j, i++);
			}
		}
		for (int j = 3; j >= 1; j--) {
			for (int k = 4 - j; k >= 1; k--) {
				drawer.drawSlot(x + squareSize - slotSize * k, y + squareSize - slotSize * j, i++);
			}
		}
	}

	public static void drawFortyOne(int slotSize, Slots drawer, int x, int y, Matrix3x2fStack pose) {
		int i = 0;
		int squareSize = getSpecialSquareSize(slotSize, 41);
		x++;
		y++;

		for (int j = 0; j < 3; j++) {
			for (int k = 0; k < 3 - j; k++) {
				drawer.drawSlot(x + slotSize * k, y + slotSize * j, i++);
			}
		}

		for (int j = 3; j >= 1; j--) {
			drawer.drawSlot(x + squareSize - slotSize * j, y, i++);
		}

		pose.pushMatrix();
		pose.translate(x + slotSize * 3.07655182f, y + slotSize * 0.74322162f);
		pose.rotate(Mth.DEG_TO_RAD * 16.60058754f);

		for (int j = 0; j < 4; j++) {
			drawer.drawSlot(slotSize * j, 0, i++);
		}

		for (int k = 0; k < 3; k++) {
			pose.translate(slotSize * -0.67262145f, slotSize);
			for (int j = 0; j < 5; j++) {
				drawer.drawSlot(slotSize * j, 0, i++);
			}
		}

		pose.translate(slotSize * 0.32737854f, slotSize);

		for (int j = 0; j < 4; j++) {
			drawer.drawSlot(slotSize * j, 0, i++);
		}

		pose.popMatrix();

		for (int j = 0; j < 3; j++) {
			drawer.drawSlot(x + slotSize * j, y + squareSize - slotSize, i++);
		}

		for (int j = 3; j >= 1; j--) {
			for (int k = 4 - j; k >= 1; k--) {
				drawer.drawSlot(x + squareSize - slotSize * k, y + squareSize - slotSize * j, i++);
			}
		}
	}

	public static void drawFifty(int slotSize, Slots drawer, int x, int y, Matrix3x2fStack pose) {
		int i = 0;
		float normSize = 7.59861960f;
		int squareSize = getSpecialSquareSize(slotSize, 50);
		x++;
		y++;

		drawer.drawSlot(x, y, i++);
		drawer.drawSlot(x + slotSize, y, i++);
		drawer.drawSlot(x, y + slotSize, i++);
		drawer.drawSlot(x + slotSize, y + slotSize, i++);
		drawer.drawSlot(x, y + slotSize * 2, i++);


		for (int j = 0; j < 5; j++) {
			for (int k = 5 - j; k >= 1; k--) {
				drawer.drawSlot(x + squareSize - slotSize * k, y + slotSize * j, i++);
			}
		}

		float offset = 0.07187936f;

		pose.pushMatrix();
		pose.translate(x + slotSize * 0.70223662f, y + slotSize * 3.32969614f);
		pose.rotate(Mth.DEG_TO_RAD * 42.08660748f);
		pose.translate(slotSize * offset, slotSize * -3);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * offset);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.translate(x + slotSize * 2, y + slotSize * (normSize - 2.70710678f));
		pose.rotate(Mth.DEG_TO_RAD * 45);
		pose.translate(0f, slotSize * -3f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.translate(x + slotSize * (normSize - 3.32969614f), y + slotSize * (normSize - 0.70223662f));
		pose.rotate(Mth.DEG_TO_RAD * 47.91339252f);
		pose.translate(slotSize * -(2 + offset), slotSize * -(3 - offset));
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * -offset);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.translate(x + slotSize * 0.70223662f, y + slotSize * 3.32969614f);
		pose.rotate(Mth.DEG_TO_RAD * 42.08660748f);
		pose.translate(slotSize * offset, slotSize * -2);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * -offset);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.translate(x + slotSize * 2, y + slotSize * (normSize - 2.70710678f));
		pose.rotate(Mth.DEG_TO_RAD * 45);
		pose.translate(0f, slotSize * -2);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.translate(x + slotSize * (normSize - 3.32969614f), y + slotSize * (normSize - 0.70223662f));
		pose.rotate(Mth.DEG_TO_RAD * 47.91339252f);
		pose.translate(slotSize * -(2 + offset), slotSize * -(2 - offset));
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * -offset);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.translate(x + slotSize * 0.70223662f, y + slotSize * 3.32969614f);
		pose.rotate(Mth.DEG_TO_RAD * 42.08660748f);
		pose.translate(0f, -slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * offset);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.translate(x + slotSize * 2, y + slotSize * (normSize - 2.70710678f));
		pose.rotate(Mth.DEG_TO_RAD * 45);
		pose.translate(0f, -slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.translate(x + slotSize * (normSize - 3.32969614f), y + slotSize * (normSize - 0.70223662f));
		pose.rotate(Mth.DEG_TO_RAD * 47.91339252f);
		pose.translate(slotSize * -2, slotSize * -(1 - offset));
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * -offset);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		for (int j = 4; j >= 1; j--) {
			for (int k = 0; k <= 4 - j; k++) {
				drawer.drawSlot(x + slotSize * k, y + squareSize - slotSize * j, i++);
			}
		}

		drawer.drawSlot(x + squareSize - slotSize * 2, y + squareSize - slotSize * 2, i++);
		drawer.drawSlot(x + squareSize - slotSize, y + squareSize - slotSize * 2, i++);
		drawer.drawSlot(x + squareSize - slotSize * 3, y + squareSize - slotSize, i++);
		drawer.drawSlot(x + squareSize - slotSize * 2, y + squareSize - slotSize, i++);
		drawer.drawSlot(x + squareSize - slotSize, y + squareSize - slotSize, i);
	}

	public static void drawFiftyOne(int slotSize, Slots drawer, int x, int y, Matrix3x2fStack pose) {
		int i = 0;
		float normSize = 7.70435372f;
		int squareSize = getSpecialSquareSize(slotSize, 51);
		x++;
		y++;
		
		drawer.drawSlot(x, y, i++);
		drawer.drawSlot(x + slotSize, y, i++);
		drawer.drawSlot(x + slotSize * 2, y, i++);

		pose.pushMatrix();
		pose.translate(x + slotSize * (normSize - 4.58444800f), y);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x, y + slotSize, i++);
		drawer.drawSlot(x + slotSize, y + slotSize, i++);
		drawer.drawSlot(x, y + slotSize * 2, i++);

		for (int j = 0; j < 4; j++) {
			for (int k = 3 - j/2; k >= 1; k--) {
				drawer.drawSlot(x + squareSize - slotSize * k, y + slotSize * j, i++);
			}
		}

		float offset = 0.08471829f;
		Matrix3x2f transform1, transform2, transform3, transform4;

		pose.pushMatrix();
		pose.translate(x + slotSize * 0.69479500f, y + slotSize * 3.17645419f);
		pose.rotate(Mth.DEG_TO_RAD * -30.03434357f);
		transform1 = new Matrix3x2f(pose);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.translate(x + slotSize * (normSize - 4), y + slotSize * (normSize - 1));
		pose.rotate(Mth.DEG_TO_RAD * -22.08119070f);
		pose.translate(slotSize * offset * -5, slotSize * -5);
		transform2 = new Matrix3x2f(pose);
		pose.translate(slotSize * 0.02639259f, 0f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.translate(x + slotSize * 3, y + slotSize);
		pose.rotate(Mth.DEG_TO_RAD * -22.08119070f);
		pose.translate(slotSize * (offset - 1), 0f);
		transform3 = new Matrix3x2f(pose);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.mul(transform1);
		pose.translate(slotSize * -0.077f, slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.mul(transform2);
		pose.translate(slotSize * (offset + 0.00501247f), slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.mul(transform3);
		pose.translate(slotSize * (offset - 0.00501247f), slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.translate(x + slotSize * (normSize - 1.69479500f), y + slotSize * (normSize - 3.17645419f));
		pose.rotate(Mth.DEG_TO_RAD * -30.03434357f);
		pose.translate(slotSize * -0.804f, slotSize * -4);
		transform4 = new Matrix3x2f(pose);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.mul(transform1);
		pose.translate(slotSize * -0.13531248f, slotSize * 2);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.mul(transform2);
		pose.translate(slotSize * offset * 2, slotSize * 2);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.mul(transform3);
		pose.translate(slotSize * offset * 2, slotSize * 2);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.mul(transform4);
		pose.translate(slotSize * -0.06068752f, slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.mul(transform1);
		pose.translate(slotSize * -0.196f, slotSize * 3);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.mul(transform2);
		pose.translate(slotSize * (offset * 3 - 0.00501247f), slotSize * 3);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.mul(transform3);
		pose.translate(slotSize * (offset * 3 + 0.00501247f), slotSize * 3);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.mul(transform4);
		pose.translate(slotSize * -0.119f, slotSize * 2);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.mul(transform2);
		pose.translate(slotSize * (offset * 4 - 0.02639259f), slotSize * 4);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.mul(transform3);
		pose.translate(slotSize * (offset * 4 + 0.02639259f), slotSize * 4);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.mul(transform4);
		pose.translate(slotSize * -0.196f, slotSize * 3);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		for (int j = 0; j < 4; j++) {
			for (int k = 0; k <= j/2; k++) {
				drawer.drawSlot(x + slotSize * k, y + squareSize - slotSize * (4 - j), i++);
			}
		}

		drawer.drawSlot(x + squareSize - slotSize * 2, y + squareSize - slotSize * 3, i++);
		drawer.drawSlot(x + squareSize - slotSize, y + squareSize - slotSize * 3, i++);
		drawer.drawSlot(x + squareSize - slotSize * 3, y + squareSize - slotSize * 2, i++);
		drawer.drawSlot(x + squareSize - slotSize * 2, y + squareSize - slotSize * 2, i++);
		drawer.drawSlot(x + squareSize - slotSize, y + squareSize - slotSize * 2, i++);

		pose.pushMatrix();
		pose.translate(x + slotSize * 2.58444800f, y + slotSize * (normSize - 1));
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		for (int j = 4; j >= 1; j--) {
			drawer.drawSlot(x + squareSize - slotSize * j, y + squareSize - slotSize, i++);
		}
	}

	public static void drawFiftyTwo(int slotSize, Slots drawer, int x, int y, Matrix3x2fStack pose) {
		int i = 0;
		int squareSize = getSpecialSquareSize(slotSize, 52);
		x++;
		y++;

		drawer.drawSlot(x, y, i++);

		for (int j = 6; j >= 1; j--) {
			drawer.drawSlot(x + squareSize - slotSize * j, y, i++);
		}

		Matrix3x2f mat;

		pose.pushMatrix();
		pose.translate(x + slotSize, y + slotSize);
		pose.rotate(Mth.DEG_TO_RAD * 45);
		pose.translate(0f, slotSize * -0.5f);
		mat = new Matrix3x2f(pose);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		for (int j = 5; j >= 1; j--) {
			drawer.drawSlot(x + squareSize - slotSize * j, y + slotSize, i++);
		}

		drawer.drawSlot(x, y + squareSize - slotSize * 6, i++);
		drawer.drawSlot(x + squareSize - slotSize * 6, y + squareSize - slotSize * 6, i++);
		drawer.drawSlot(x + slotSize * 3, y + slotSize * 2, i++);

		for (int j = 3; j >= 1; j--) {
			drawer.drawSlot(x + squareSize - slotSize * j, y + slotSize * 2, i++);
		}
		for (int j = 0; j < 3; j++) {
			drawer.drawSlot(x + slotSize * j, y + squareSize - slotSize * 5, i++);
		}

		pose.pushMatrix();
		pose.translate(slotSize * 3, slotSize * 2);
		pose.mul(mat);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		for (int j = 2; j >= 1; j--) {
			drawer.drawSlot(x + squareSize - slotSize * j, y + slotSize * 3, i++);
		}

		for (int j = 0; j < 4; j++) {
			drawer.drawSlot(x + slotSize * j, y + squareSize - slotSize * 4, i++);
		}

		drawer.drawSlot(x + squareSize - slotSize * 3, y + squareSize - slotSize * 4, i++);

		for (int j = 2; j >= 1; j--) {
			drawer.drawSlot(x + squareSize - slotSize * j, y + slotSize * 4, i++);
		}

		for (int j = 0; j < 5; j++) {
			drawer.drawSlot(x + slotSize * j, y + squareSize - slotSize * 3, i++);
		}

		drawer.drawSlot(x + slotSize * 5, y + slotSize * 5, i++);
		drawer.drawSlot(x + squareSize - slotSize, y + slotSize * 5, i++);

		for (int j = 0; j < 5; j++) {
			drawer.drawSlot(x + slotSize * j, y + squareSize - slotSize * 2, i++);
		}

		pose.pushMatrix();
		pose.translate(slotSize * 5, slotSize * 5);
		pose.mul(mat);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		for (int j = 0; j < 6; j++) {
			drawer.drawSlot(x + slotSize * j, y + squareSize - slotSize, i++);
		}

		drawer.drawSlot(x + squareSize - slotSize, y + squareSize - slotSize, i);
	}

	private static int rowFiftyThree(int slotSize, Slots drawer, int i, Matrix3x2fStack pose) {
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * -0.48051357f);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * -0.4805137f);
		drawer.drawSlot(0, 0, i++);
		drawer.drawSlot(slotSize, 0, i++);
		pose.translate(slotSize * 2, slotSize * -0.48051375f);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * -0.4805137f);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * -0.48051375f);
		drawer.drawSlot(0, 0, i++);
		return i;
	}

	public static void drawFiftyThree(int slotSize, Slots drawer, int x, int y, Matrix3x2fStack pose) {
		int i = 0;
		int squareSize = getSpecialSquareSize(slotSize, 53);
		x++;
		y++;

		for (int j = 0; j < 5; j++) {
			for (int k = 0; k < 5 - j; k++) {
				drawer.drawSlot(x + slotSize * k, y + slotSize * j, i++);
			}
		}

		drawer.drawSlot(x, y + squareSize - slotSize * 2, i++);

		pose.pushMatrix();
		pose.translate(x + slotSize * 5.09470432f, y + slotSize * 0.42478198f);
		pose.rotate(Mth.DEG_TO_RAD * -25.13686477f);
		pose.translate(slotSize * -6, slotSize * 2.40256844f);
		i = rowFiftyThree(slotSize, drawer, i, pose);
		pose.popMatrix();

		drawer.drawSlot(x, y + squareSize - slotSize, i++);

		pose.pushMatrix();
		pose.translate(x + slotSize * 5.09470432f, y + slotSize * 0.42478198f);
		pose.rotate(Mth.DEG_TO_RAD * -25.13686477f);
		pose.translate(slotSize * -6, slotSize * 3.40256844f);
		i = rowFiftyThree(slotSize, drawer, i, pose);
		pose.popMatrix();

		drawer.drawSlot(x + squareSize - slotSize, y, i++);

		for (int j = 6; j >= 1; j--) {
			for (int k = 7 - j; k >= 1; k--) {
				drawer.drawSlot(x + squareSize - slotSize * k, y + squareSize - slotSize * j, i++);
			}
		}
	}

	public static void drawFiftyFour(int slotSize, Slots drawer, int x, int y, Matrix3x2fStack pose) {
		int i = 0;
		float normSize = 7.84666719f;
		int squareSize = getSpecialSquareSize(slotSize, 54);
		x++;
		y++;

		int[] lengths = {6,5,4,2,1};
		for (int j = 0; j < lengths.length; j++) {
			for (int k = 0; k < lengths[j]; k++) {
				drawer.drawSlot(x + slotSize * k, y + slotSize * j, i++);
			}
		}

		drawer.drawSlot(x + squareSize - slotSize, y, i++);

		float rotAngle = Mth.DEG_TO_RAD * 34.73490026f;

		pose.pushMatrix();
		pose.translate(x + slotSize * (normSize - 0.75f), y + slotSize * (normSize - 6.67333359f));
		pose.rotate(rotAngle);
		pose.translate(-slotSize, 0f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.translate(x + slotSize * normSize, y + slotSize * (normSize - 6));
		pose.rotate(rotAngle);
		pose.translate(-slotSize, 0f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.translate(x + slotSize * (normSize - 1.52688698f), y + slotSize * (normSize - 5.99513072f));
		pose.rotate(rotAngle);
		pose.translate(-slotSize, 0f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.translate(x + slotSize * (normSize - 0.77688698f), y + slotSize * (normSize - 5.32179712f));
		pose.rotate(rotAngle);
		pose.translate(-slotSize, 0f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.translate(x + slotSize * (normSize - 1.55377397f), y + slotSize * (normSize - 4.64359425f));
		pose.rotate(rotAngle);
		pose.translate(slotSize * -2, slotSize * -0.07728492f);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * 0.07728492f);
		drawer.drawSlot(0, 0, i++);
		pose.translate(-slotSize, slotSize * 0.92271507f);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * 0.07728492f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.translate(x + slotSize * 1.55377397f, y + slotSize * 4.64359425f);
		pose.rotate(rotAngle);
		pose.translate(0f, slotSize * -2);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * 0.07728492f);
		drawer.drawSlot(0, 0, i++);
		pose.translate(-slotSize, slotSize * 0.92271507f);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * 0.07728492f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.translate(x + slotSize * 0.77688698f, y + slotSize * 5.32179712f);
		pose.rotate(rotAngle);
		pose.translate(0f, -slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.translate(x + slotSize * 1.52688698f, y + slotSize * 5.99513072f);
		pose.rotate(rotAngle);
		pose.translate(0f, -slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.translate(x, y + slotSize * 6);
		pose.rotate(rotAngle);
		pose.translate(0f, -slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.translate(x + slotSize * 0.75f, y + slotSize * 6.67333359f);
		pose.rotate(rotAngle);
		pose.translate(0f, -slotSize);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		drawer.drawSlot(x, y + squareSize - slotSize, i++);

		for (int j = lengths.length; j >= 1; j--) {
			for (int k = lengths[j - 1]; k >= 1; k--) {
				drawer.drawSlot(x + squareSize - slotSize * k, y + squareSize - slotSize * j, i++);
			}
		}
	}

	public static void drawFiftyFive(int slotSize, Slots drawer, int x, int y, Matrix3x2fStack pose) {
		int i = 0;
		int squareSize = getSpecialSquareSize(slotSize, 55);
		x++;
		y++;

		for (int j = 0; j < 3; j++) {
			for (int k = 0; k < 3 - j; k++) {
				drawer.drawSlot(x + slotSize * k, y + slotSize * j, i++);
			}
		}

		for (int j = 4; j >= 1; j--) {
			drawer.drawSlot(x + squareSize - slotSize * j, y, i++);
		}

		pose.pushMatrix();
		pose.translate(x + slotSize * 3.13009105f, y + slotSize * 0.67256345f);
		pose.rotate(Mth.DEG_TO_RAD * 21.66800178f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		Matrix3x2f mat1;

		pose.pushMatrix();
		pose.translate(x + slotSize * 3.16066671f, y + slotSize * 5.96465671f);
		pose.rotate(Mth.DEG_TO_RAD * 13.30405178f);
		mat1 = new Matrix3x2f(pose);
		pose.translate(slotSize * -0.25819987f, slotSize * -5.04051442f);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * 0.04051442f);
		drawer.drawSlot(0, 0, i++);
		drawer.drawSlot(slotSize, 0, i++);
		pose.translate(slotSize * 2, slotSize * -0.00696516f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.translate(x + slotSize * 2, y + slotSize * 2);
		pose.rotate(Mth.DEG_TO_RAD * 15.87748802f);
		pose.translate(0f, slotSize * -0.55282875f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		Matrix3x2f mat2;

		pose.pushMatrix();
		pose.translate(x + slotSize * 3.09209914f, y + slotSize * 1.81565139f);
		pose.rotate(Mth.DEG_TO_RAD * 14.57170350f);
		mat2 = new Matrix3x2f(pose);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.mul(mat1);
		pose.translate(slotSize * -0.02173524f, slotSize * -4);
		for (int j = 0; j < 3; j++) {
			drawer.drawSlot(slotSize * j, 0, i++);
		}
		pose.translate(slotSize * 3, slotSize * -0.00696516f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.mul(mat2);
		pose.translate(slotSize * -1.72683179f, slotSize * 0.90693694f);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * 0.09306304f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.mul(mat1);
		pose.translate(slotSize * -0.77086806f, slotSize * -3);
		for (int j = 0; j < 4; j++) {
			drawer.drawSlot(slotSize * j, 0, i++);
		}
		pose.popMatrix();

		pose.pushMatrix();
		pose.translate(x + slotSize * 1.18415667f, y + slotSize * 3.40017871f);
		pose.rotate(Mth.DEG_TO_RAD * 13.79356726f);
		pose.translate(-slotSize, slotSize * -0.10665350f);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * 0.10665350f);
		drawer.drawSlot(0, 0, i++);
		pose.popMatrix();

		pose.pushMatrix();
		pose.mul(mat1);
		pose.translate(slotSize * -1.5139121f, slotSize * -2);
		for (int j = 0; j < 4; j++) {
			drawer.drawSlot(slotSize * j, 0, i++);
		}
		pose.translate(slotSize * -1.74304404f, slotSize * 0.96140677f);
		drawer.drawSlot(0, 0, i++);
		pose.translate(slotSize, slotSize * 0.03859322f);
		for (int j = 0; j < 4; j++) {
			drawer.drawSlot(slotSize * j, 0, i++);
		}
		pose.translate(slotSize * -0.74304404f, slotSize);
		for (int j = 0; j < 4; j++) {
			drawer.drawSlot(slotSize * j, 0, i++);
		}
		pose.popMatrix();

		for (int j = 0; j < 3; j++) {
			drawer.drawSlot(x + slotSize * j, y + squareSize - slotSize, i++);
		}

		for (int j = 4; j >= 1; j--) {
			for (int k = 5 - j; k >= 1; k--) {
				drawer.drawSlot(x + squareSize - slotSize * k, y + squareSize - slotSize * j, i++);
			}
		}
	}

	/*
	private static int tiltedRowSixtyFive(Slots drawer, int i, Matrix3x2fStack pose, float middleOffset) {
		drawer.drawSlot(0, 0, i++);
		drawer.drawSlot(slotSize, 0, i++);

		pose.translate(0f, middleOffset);
		drawer.drawSlot(slotSize * 2, 0, i++);
		pose.translate(0f, -middleOffset);

		drawer.drawSlot(slotSize * 3, 0, i++);
		drawer.drawSlot(slotSize * 4, 0, i++);

		return i;
	}

	public static void drawSixtyFive(int slotSize, Slots drawer, int x, int y, Matrix3x2fStack pose) {
		int i = 0;
		int squareSize = getSpecialSquareSize(slotSize, 65);
		x++;
		y++;

		for (int j = 0; j < 4; j++) {
			for (int k = 0; k < 4 - j; k++) {
				drawer.drawSlot(x + slotSize * k, y + slotSize * j, i++);
			}
		}

		for (int j = 0; j < 4; j++) {
			for (int k = 4 - j; k >= 1; k--) {
				drawer.drawSlot(x + squareSize - slotSize * k, y + slotSize * j, i++);
			}
		}

		drawer.drawSlot(x + squareSize / 2 - slotSize / 2, y + squareSize / 2 - slotSize / 2, i++);

		float middleOffset = slotSize * 0.20710678f;

		pose.pushMatrix();
		pose.translate(x + slotSize * 2.5f, y + slotSize * 2.5f);
		pose.rotate(Mth.DEG_TO_RAD * 45);
		pose.translate(0f, slotSize * -2.5f);
		i = tiltedRowSixtyFive(drawer, i, pose, -middleOffset);
		pose.translate(0f, slotSize);
		i = tiltedRowSixtyFive(drawer, i, pose, -middleOffset);

		pose.translate(-middleOffset, slotSize);
		drawer.drawSlot(0, 0, i++);
		drawer.drawSlot(slotSize, 0, i++);
		pose.translate(slotSize * 3 + middleOffset * 2, 0f);
		drawer.drawSlot(0, 0, i++);
		drawer.drawSlot(slotSize, 0, i++);

		pose.translate(slotSize * -3 - middleOffset, slotSize);
		i = tiltedRowSixtyFive(drawer, i, pose, middleOffset);
		pose.translate(0f, slotSize);
		i = tiltedRowSixtyFive(drawer, i, pose, middleOffset);

		pose.popMatrix();

		for (int j = 4; j >= 1; j--) {
			for (int k = 0; k < 5 - j; k++) {
				drawer.drawSlot(x + slotSize * k, y + squareSize - slotSize * j, i++);
			}
		}

		for (int j = 4; j >= 1; j--) {
			for (int k = 5 - j; k >= 1; k--) {
				drawer.drawSlot(x + squareSize - slotSize * k, y + squareSize - slotSize * j, i++);
			}
		}
	}*/
}