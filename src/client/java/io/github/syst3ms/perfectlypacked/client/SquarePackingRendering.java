package io.github.syst3ms.perfectlypacked.client;

import org.joml.Matrix4f;
import org.joml.Quaternionf;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;

public class SquarePackingRendering {
	public static final int SLOT_SIZE = 18;

	public static int trivialPackingSide(int n) {
		return (int) Math.ceil(Math.sqrt(n));
	}

	public static int getSquareSize(int n) {
		return getSquareSize(n, SLOT_SIZE);
	}

	public static int getSquareSize(int n, int trivialRowHeight) {
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
			case 65 -> Math.ceil(SLOT_SIZE * 8.53553390f);
			default -> trivialPackingSide(n) * trivialRowHeight;
		};
	}
	
	@FunctionalInterface
	public interface Background {
		void drawBackground(int size);
	}
	
	@FunctionalInterface
	public interface Slots {
		void drawSlot(int x, int y, int index);
	}

	public static boolean tryDrawSpecial(int n, Background bgDrawer, Slots drawer, int x, int y, MatrixStack matrices) {
		switch (n) {
			case 5 -> drawFive(bgDrawer, drawer, x, y, matrices);
			case 10 -> drawTen(bgDrawer, drawer, x, y, matrices);
			case 11 -> drawEleven(bgDrawer, drawer, x, y, matrices);
			case 17 -> drawSeventeen(bgDrawer, drawer, x, y, matrices);
			case 18 -> drawEighteen(bgDrawer, drawer, x, y, matrices);
			case 19 -> drawNineteen(bgDrawer, drawer, x, y, matrices);
			case 26 -> drawTwentySix(bgDrawer, drawer, x, y, matrices);
			case 27 -> drawTwentySeven(bgDrawer, drawer, x, y, matrices);
			case 28 -> drawTwentyEight(bgDrawer, drawer, x, y, matrices);
			case 29 -> drawTwentyNine(bgDrawer, drawer, x, y, matrices);
			case 37 -> drawThirtySeven(bgDrawer, drawer, x, y, matrices);
			case 38 -> drawThirtyEight(bgDrawer, drawer, x, y, matrices);
			case 39 -> drawThirtyNine(bgDrawer, drawer, x, y, matrices);
			case 40 -> drawForty(bgDrawer, drawer, x, y, matrices);
			case 41 -> drawFortyOne(bgDrawer, drawer, x, y, matrices);
			case 50 -> drawFifty(bgDrawer, drawer, x, y, matrices);
			case 51 -> drawFiftyOne(bgDrawer, drawer, x, y, matrices);
			case 52 -> drawFiftyTwo(bgDrawer, drawer, x, y, matrices);
			case 53 -> drawFiftyThree(bgDrawer, drawer, x, y, matrices);
			case 54 -> drawFiftyFour(bgDrawer, drawer, x, y, matrices);
			case 55 -> drawFiftyFive(bgDrawer, drawer, x, y, matrices);
			case 65 -> drawSixtyFive(bgDrawer, drawer, x, y, matrices);
			default -> {
				return false;
			}
		}

		return true;
	}

	public static void drawFive(Background bgDrawer, Slots drawer, int x, int y, MatrixStack matrices) {
		bgDrawer.drawBackground(getSquareSize(5) + 2);
		x++;
		y++;

		int straightOffset = 31;
		drawer.drawSlot(x, y, 0);
		drawer.drawSlot(x + straightOffset, y, 1);

		matrices.push();
		matrices.translate(x + SLOT_SIZE, y + SLOT_SIZE, 0);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		matrices.translate(0f, SLOT_SIZE * 0.5f, 0f);
		drawer.drawSlot(0, 0, 2);
		matrices.pop();

		drawer.drawSlot(x, y + straightOffset, 3);
		drawer.drawSlot(x + straightOffset, y + straightOffset, 4);
	}

	public static void drawTen(Background bgDrawer, Slots drawer, int x, int y, MatrixStack matrices) {
		int i = 0;
		int gap = 31;
		bgDrawer.drawBackground(getSquareSize(10) + 2);
		x++;
		y++;
		
		drawer.drawSlot(x, y, i++);
		drawer.drawSlot(x + gap, y, i++);
		drawer.drawSlot(x + gap + SLOT_SIZE, y, i++);
		drawer.drawSlot(x, y + SLOT_SIZE, i++);
		drawer.drawSlot(x + gap, y + SLOT_SIZE, i++);
		drawer.drawSlot(x + gap + SLOT_SIZE, y + SLOT_SIZE, i++);

		matrices.push();
		matrices.translate(x + SLOT_SIZE, y + SLOT_SIZE * 2, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		matrices.translate(0, -SLOT_SIZE/2f, 0);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x, y + SLOT_SIZE + gap, i++);
		drawer.drawSlot(x + gap, y + SLOT_SIZE + gap, i++);
		drawer.drawSlot(x + gap + SLOT_SIZE, y + SLOT_SIZE + gap, i);
	}

	public static void drawEleven(Background bgDrawer, Slots drawer, int x, int y, MatrixStack matrices) {
		int i = 0;
		int squareSize = getSquareSize(11);
		bgDrawer.drawBackground(squareSize + 2);
		x++;
		y++;
		drawer.drawSlot(x, y, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y, i++);

		Matrix4f baseTransform;
		matrices.push();
		matrices.translate(x + SLOT_SIZE, y + SLOT_SIZE, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(40.18193729f));
		matrices.translate(0f, SLOT_SIZE * -0.32990859f, 0f);
		baseTransform = new Matrix4f(matrices.peek().getPositionMatrix());
		matrices.translate(SLOT_SIZE * .02487453f, -SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.11878261f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.33377596f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.11878260f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x, y + squareSize - SLOT_SIZE * 2, i++);
		drawer.drawSlot(x, y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + SLOT_SIZE, y + squareSize - SLOT_SIZE, i++);

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 2.03255831f, y + SLOT_SIZE * 2.87708359f, 0f);
		drawer.drawSlot(0, 0, i);
		matrices.pop();
	}

	public static void drawSeventeen(Background bgDrawer, Slots drawer, int x, int y, MatrixStack matrices) {
		int i = 0;
		float normSize = 4.67553009f;
		int squareSize = getSquareSize(17);
		bgDrawer.drawBackground(squareSize + 2);
		x++;
		y++;
		drawer.drawSlot(x, y, i++);
		drawer.drawSlot(x + SLOT_SIZE, y, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE * 2, y, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y, i++);
		drawer.drawSlot(x, y + SLOT_SIZE, i++);

		Matrix4f baseTransform;

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 0.64017619f, y + SLOT_SIZE * 2, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(39.80495897f));
		baseTransform = new Matrix4f(matrices.peek().getPositionMatrix());
		matrices.translate(SLOT_SIZE * 0.40447869f, SLOT_SIZE * -2, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + SLOT_SIZE, i++);

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		matrices.translate(SLOT_SIZE * 0.27642676f, -SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		matrices.translate(SLOT_SIZE * 1.40447869f, SLOT_SIZE * -1.94316130f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		matrices.translate(SLOT_SIZE * 1.27642676f, SLOT_SIZE * -0.94316130, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * (normSize - 1), y + SLOT_SIZE * 2.11346013f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.05683896f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 2.84732482f, y + SLOT_SIZE * (normSize - 1), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-36.62378638f));
		matrices.translate(0f, SLOT_SIZE * -0.50592742f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x, y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + (int) Math.ceil(SLOT_SIZE * 1.84732482f), y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE, i);
	}

	public static void drawEighteen(Background bgDrawer, Slots drawer, int x, int y, MatrixStack matrices) {
		int i = 0;
		int squareSize = getSquareSize(18);
		bgDrawer.drawBackground(squareSize + 2);
		x++;
		y++;
		drawer.drawSlot(x, y, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE * 3, y, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE * 2, y, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y, i++);
		drawer.drawSlot(x, y + SLOT_SIZE, i++);

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 1.51429728f, y + SLOT_SIZE * 0.86070271f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(24.29518894f));
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x + squareSize - SLOT_SIZE * 2, y + SLOT_SIZE, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + SLOT_SIZE, i++);

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 1.51429728f, y + SLOT_SIZE * 0.86070271f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(24.29518894f));
		matrices.translate(0, SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 2.30857837f, y + SLOT_SIZE * 3.96217294f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(24.29518894f));
		matrices.translate(-SLOT_SIZE, SLOT_SIZE * -2, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x, y + squareSize - SLOT_SIZE * 2, i++);

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 2.30857837f, y + SLOT_SIZE * 3.96217294f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(24.29518894f));
		matrices.translate(-SLOT_SIZE, -SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x + squareSize - SLOT_SIZE * 2, y + squareSize - SLOT_SIZE * 2, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE * 2, i++);
		drawer.drawSlot(x, y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + SLOT_SIZE, y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE * 2, y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE, i);
	}

	public static void drawNineteen(Background bgDrawer, Slots drawer, int x, int y, MatrixStack matrices) {
		int i = 0;
		float normSize = 4.88561808f;
		int squareSize = getSquareSize(19);
		bgDrawer.drawBackground(squareSize + 2);
		x++;
		y++;
		
		drawer.drawSlot(x, y, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE * 3, y, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE * 2, y, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y, i++);

		Matrix4f baseTransform;

		matrices.push();
		matrices.translate(x + SLOT_SIZE * (normSize / 2f - 1), y + SLOT_SIZE * (3 - normSize / 2f), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		baseTransform = new Matrix4f(matrices.peek().getPositionMatrix());
		drawer.drawSlot(0, 0, i++);
		matrices.translate(0f, SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * -0.04044011f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x + squareSize - SLOT_SIZE * 2, y + SLOT_SIZE, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + SLOT_SIZE, i++);

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.95955989f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 3 * (normSize / 2f - 1), y + SLOT_SIZE * (normSize / 2f + 1), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		matrices.translate(SLOT_SIZE * -2, SLOT_SIZE * -0.04044011f, 0f);
		baseTransform = new Matrix4f(matrices.peek().getPositionMatrix());
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + SLOT_SIZE * 2, i++);
		drawer.drawSlot(x, y + squareSize - SLOT_SIZE * 2, i++);

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		matrices.translate(0f, SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * -0.95955989f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x, y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + SLOT_SIZE, y + squareSize - SLOT_SIZE, i++);

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 1.04044011f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE, i);
	}

	public static void drawTwentySix(Background bgDrawer, Slots drawer, int x, int y, MatrixStack matrices) {
		int i = 0;
		float normSize = 5.62132034f;
		int squareSize = getSquareSize(26);
		bgDrawer.drawBackground(squareSize + 2);
		x++;
		y++;

		drawer.drawSlot(x, y, i++);
		drawer.drawSlot(x + SLOT_SIZE, y, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE * 3, y, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE * 2, y, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y, i++);
		drawer.drawSlot(x, y + SLOT_SIZE, i++);

		Matrix4f baseTransform;

		matrices.push();
		matrices.translate(x + SLOT_SIZE * (normSize - 1) / 2, y + SLOT_SIZE * normSize / 2, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		matrices.translate(SLOT_SIZE * -1.5f, SLOT_SIZE * -1.5f, 0f);
		baseTransform = new Matrix4f(matrices.peek().getPositionMatrix());
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x + squareSize - SLOT_SIZE * 2, y + SLOT_SIZE, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + SLOT_SIZE, i++);

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		matrices.translate(0f, SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, -SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(-SLOT_SIZE, SLOT_SIZE * 2, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, -SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, -SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize / 2 - SLOT_SIZE / 2, i++);

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 2, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, -SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x, y + squareSize - SLOT_SIZE * 2, i++);

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		matrices.translate(SLOT_SIZE * 2, SLOT_SIZE * 2, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x + squareSize - SLOT_SIZE * 2, y + squareSize - SLOT_SIZE * 2, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE * 2, i++);
		drawer.drawSlot(x, y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + SLOT_SIZE, y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE * 3, y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE * 2, y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE, i);
	}

	public static void drawTwentySeven(Background bgDrawer, Slots drawer, int x, int y, MatrixStack matrices) {
		int i = 0;
		var fiveOffset = (int) Math.floor(SLOT_SIZE * 1.70710678f);
		int squareSize = getSquareSize(27);
		bgDrawer.drawBackground(squareSize + 2);
		x++;
		y++;

		drawer.drawSlot(x, y, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE * 4, y, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE * 3, y, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE * 2, y, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y, i++);

		matrices.push();
		matrices.translate(x + SLOT_SIZE, y + SLOT_SIZE, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		matrices.translate(0f, SLOT_SIZE * -0.5f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x + squareSize - SLOT_SIZE * 3, y + SLOT_SIZE, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE * 2, y + SLOT_SIZE, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + SLOT_SIZE, i++);
		drawer.drawSlot(x, y + fiveOffset, i++);
		drawer.drawSlot(x + fiveOffset, y + fiveOffset, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE * 2, y + SLOT_SIZE * 2, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + SLOT_SIZE * 2, i++);
		drawer.drawSlot(x, y + squareSize - SLOT_SIZE * 3, i++);
		drawer.drawSlot(x + SLOT_SIZE, y + squareSize - SLOT_SIZE * 3, i++);
		drawer.drawSlot(x + SLOT_SIZE * 2, y + squareSize - SLOT_SIZE * 3, i++);
		drawer.drawSlot(x + SLOT_SIZE * 3, y + SLOT_SIZE * 3, i++);
		drawer.drawSlot(x + SLOT_SIZE * 3 + fiveOffset, y + SLOT_SIZE * 3, i++);
		drawer.drawSlot(x, y + squareSize - SLOT_SIZE * 2, i++);
		drawer.drawSlot(x + SLOT_SIZE, y + squareSize - SLOT_SIZE * 2, i++);
		drawer.drawSlot(x + SLOT_SIZE * 2, y + squareSize - SLOT_SIZE * 2, i++);

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 4, y + SLOT_SIZE * 4, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		matrices.translate(0f, SLOT_SIZE * -0.5f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x, y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + SLOT_SIZE, y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + SLOT_SIZE * 2, y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + SLOT_SIZE * 3, y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + SLOT_SIZE * 3 + fiveOffset, y + squareSize - SLOT_SIZE, i);
	}

	public static void drawTwentyEight(Background bgDrawer, Slots drawer, int x, int y, MatrixStack matrices) {
		int i = 0;
		int squareSize = getSquareSize(28);
		int centerOffset = (squareSize - SLOT_SIZE) / 2;
		bgDrawer.drawBackground(squareSize + 2);
		x++;
		y++;
		
		drawer.drawSlot(x, y, i++);
		drawer.drawSlot(x + SLOT_SIZE, y, i++);
		drawer.drawSlot(x + centerOffset, y, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE * 2, y, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y, i++);
		drawer.drawSlot(x, y + SLOT_SIZE, i++);

		Matrix4f baseTransform;

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 1.5f, y + SLOT_SIZE * 1.5f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		baseTransform = new Matrix4f(matrices.peek().getPositionMatrix());
		matrices.translate(0f, -SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, -SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + SLOT_SIZE, i++);

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, -SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, -SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x, y + centerOffset, i++);

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		matrices.translate(SLOT_SIZE, 0f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, -SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + centerOffset, i++);

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		matrices.translate(SLOT_SIZE, SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, -SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, -SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x, y + squareSize - SLOT_SIZE * 2, i++);

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		matrices.translate(SLOT_SIZE * 2, SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, -SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE * 2, i++);
		drawer.drawSlot(x, y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + SLOT_SIZE, y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + centerOffset, y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE * 2, y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE, i);
	}

	public static void drawTwentyNine(Background bgDrawer, Slots drawer, int x, int y, MatrixStack matrices) {
		int i = 0;
		int squareSize = getSquareSize(29);
		bgDrawer.drawBackground(squareSize + 2);
		x++;
		y++;
		
		drawer.drawSlot(x, y, i++);
		drawer.drawSlot(x + SLOT_SIZE, y, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE * 3, y, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE * 2, y, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y, i++);

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 2, y + SLOT_SIZE, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(25.99204718f));
		matrices.translate(0f, SLOT_SIZE * -0.40947192f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 5.58822412f, y + SLOT_SIZE * 2.93434180, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(20.25003828f));
		matrices.translate(SLOT_SIZE * -3, SLOT_SIZE * -0.95501732f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * -0.04498268f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, 0f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x, y + SLOT_SIZE, i++);

		matrices.push();
		matrices.translate(x + SLOT_SIZE, y + SLOT_SIZE * 2, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(21.62093977f));
		matrices.translate(0f, SLOT_SIZE * -0.67231250f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 4.93434180f, y + SLOT_SIZE * 2.93434180, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(20.25540431f));
		matrices.translate(-SLOT_SIZE * 3, SLOT_SIZE * -0.11130742f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * -0.05050338f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, 0f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE * 3, i++);

		Matrix4f baseTransform;

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 2.93434180f, y + SLOT_SIZE * 4.93434180, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(18.17734536f));
		matrices.translate(-SLOT_SIZE, SLOT_SIZE * -0.70852340f, 0f);
		baseTransform = new Matrix4f(matrices.peek().getPositionMatrix());
		matrices.translate(SLOT_SIZE * -2.36186381f, SLOT_SIZE * -1.12411661f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.10208347f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.02203314f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, 0f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x + squareSize - SLOT_SIZE * 2, y + squareSize - SLOT_SIZE * 2, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE * 2, i++);

		matrices.push();
		matrices.multiplyPositionMatrix(baseTransform);
		matrices.translate(SLOT_SIZE * -2, SLOT_SIZE * -0.02203314f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.02203314f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, 0f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x, y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + SLOT_SIZE, y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE * 3, y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE * 2, y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE, i);
	}

	public static void drawThirtySeven(Background bgDrawer, Slots drawer, int x, int y, MatrixStack matrices) {
		int i = 0;
		float normSize = 6.59861960f;
		int squareSize = getSquareSize(37);
		bgDrawer.drawBackground(squareSize + 2);
		x++;
		y++;

		drawer.drawSlot(x, y, i++);
		drawer.drawSlot(x + SLOT_SIZE, y, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE * 3, y, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE * 2, y, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE * 2, y + SLOT_SIZE, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + SLOT_SIZE, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + SLOT_SIZE * 2, i++);

		double offset = 0.07187936f;

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 0.70223662f, y + SLOT_SIZE * 2.32969614f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(42.08660748f));
		matrices.translate(SLOT_SIZE * offset, SLOT_SIZE * -3, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * offset, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 2, y + SLOT_SIZE * (normSize - 2.70710678f), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		matrices.translate(0f, SLOT_SIZE * -3f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * (normSize - 2.32969614f), y + SLOT_SIZE * (normSize - 0.70223662f), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(47.91339252f));
		matrices.translate(SLOT_SIZE * -(2 + offset), SLOT_SIZE * -(3 - offset), 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * -offset, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x, y + SLOT_SIZE, i++);

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 0.70223662f, y + SLOT_SIZE * 2.32969614f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(42.08660748f));
		matrices.translate(SLOT_SIZE * offset, SLOT_SIZE * -2, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * -offset, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 2, y + SLOT_SIZE * (normSize - 2.70710678f), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		matrices.translate(0f, SLOT_SIZE * -2, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * (normSize - 2.32969614f), y + SLOT_SIZE * (normSize - 0.70223662f), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(47.91339252f));
		matrices.translate(SLOT_SIZE * -(2 + offset), SLOT_SIZE * -(2 - offset), 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * -offset, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE * 2, i++);

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 0.70223662f, y + SLOT_SIZE * 2.32969614f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(42.08660748f));
		matrices.translate(0f, -SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * offset, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 2, y + SLOT_SIZE * (normSize - 2.70710678f), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		matrices.translate(0f, -SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * (normSize - 2.32969614f), y + SLOT_SIZE * (normSize - 0.70223662f), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(47.91339252f));
		matrices.translate(SLOT_SIZE * -2, SLOT_SIZE * -(1 - offset), 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * -offset, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x, y + squareSize - SLOT_SIZE * 4, i++);
		drawer.drawSlot(x, y + squareSize - SLOT_SIZE * 3, i++);
		drawer.drawSlot(x + SLOT_SIZE, y + squareSize - SLOT_SIZE * 3, i++);
		drawer.drawSlot(x, y + squareSize - SLOT_SIZE * 2, i++);
		drawer.drawSlot(x + SLOT_SIZE, y + squareSize - SLOT_SIZE * 2, i++);
		drawer.drawSlot(x + SLOT_SIZE * 2, y + squareSize - SLOT_SIZE * 2, i++);
		drawer.drawSlot(x, y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + SLOT_SIZE, y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + SLOT_SIZE * 2, y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + SLOT_SIZE * 3, y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE * 2, y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE, i);
	}

	public static void drawThirtyEight(Background bgDrawer, Slots drawer, int x, int y, MatrixStack matrices) {
		int i = 0;
		var fiveOffset = (int) Math.floor(SLOT_SIZE * 1.70710678f);
		int squareSize = getSquareSize(38);
		bgDrawer.drawBackground(squareSize + 2);
		x++;
		y++;
		
		drawer.drawSlot(x, y, i++);
		for (int j = 5; j >= 1; j--) {
			drawer.drawSlot(x + squareSize - SLOT_SIZE * j, y, i++);
		}

		matrices.push();
		matrices.translate(x + SLOT_SIZE, y + SLOT_SIZE, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		matrices.translate(0f, SLOT_SIZE * -0.5f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x, y + squareSize - SLOT_SIZE * 5, i++);
		drawer.drawSlot(x + fiveOffset, y + squareSize - SLOT_SIZE * 5, i++);

		for (int j = 4; j >= 1; j--) {
			drawer.drawSlot(x + squareSize - SLOT_SIZE * j, y + SLOT_SIZE, i++);
		}

		drawer.drawSlot(x + squareSize - SLOT_SIZE * 2, y + SLOT_SIZE * 2, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + SLOT_SIZE * 2, i++);

		drawer.drawSlot(x, y + squareSize - SLOT_SIZE * 4, i++);
		drawer.drawSlot(x + SLOT_SIZE, y + squareSize - SLOT_SIZE * 4, i++);
		drawer.drawSlot(x + SLOT_SIZE * 2, y + squareSize - SLOT_SIZE * 4, i++);
		drawer.drawSlot(x + SLOT_SIZE * 3, y + squareSize - SLOT_SIZE * 4, i++);

		drawer.drawSlot(x + squareSize - SLOT_SIZE * 2, y + SLOT_SIZE * 3, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + SLOT_SIZE * 3, i++);

		drawer.drawSlot(x, y + squareSize - SLOT_SIZE * 3, i++);
		drawer.drawSlot(x + SLOT_SIZE, y + squareSize - SLOT_SIZE * 3, i++);
		drawer.drawSlot(x + SLOT_SIZE * 2, y + squareSize - SLOT_SIZE * 3, i++);
		drawer.drawSlot(x + SLOT_SIZE * 3, y + squareSize - SLOT_SIZE * 3, i++);

		drawer.drawSlot(x + SLOT_SIZE * 4, y + SLOT_SIZE * 4, i++);
		drawer.drawSlot(x + SLOT_SIZE * 4 + fiveOffset, y + SLOT_SIZE * 4, i++);

		drawer.drawSlot(x, y + squareSize - SLOT_SIZE * 2, i++);
		drawer.drawSlot(x + SLOT_SIZE, y + squareSize - SLOT_SIZE * 2, i++);
		drawer.drawSlot(x + SLOT_SIZE * 2, y + squareSize - SLOT_SIZE * 2, i++);
		drawer.drawSlot(x + SLOT_SIZE * 3, y + squareSize - SLOT_SIZE * 2, i++);

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 5, y + SLOT_SIZE * 5, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		matrices.translate(0f, SLOT_SIZE * -0.5f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x, y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + SLOT_SIZE, y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + SLOT_SIZE * 2, y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + SLOT_SIZE * 3, y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + SLOT_SIZE * 4, y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE, i);
	}

	public static void drawThirtyNine(Background bgDrawer, Slots drawer, int x, int y, MatrixStack matrices) {
		int i = 0;
		float normSize = 6.81880916f;
		int squareSize = getSquareSize(39);
		bgDrawer.drawBackground(squareSize + 2);
		x++;
		y++;

		drawer.drawSlot(x, y, i++);
		for (int k = 5; k >= 1; k--) {
			drawer.drawSlot(x + squareSize - SLOT_SIZE * k, y, i++);
		}
		for (int k = 3; k >= 1; k--) {
			drawer.drawSlot(x + squareSize - SLOT_SIZE * k, y + SLOT_SIZE, i++);
		}
		drawer.drawSlot(x, y + squareSize - SLOT_SIZE * 5, i++);

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 0.60617891f, y + SLOT_SIZE * 1.29752487f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-37.07040322f));
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE * 0.12360160f, SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.23145647f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE * -0.13060668f, SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x + squareSize - SLOT_SIZE * 2, y + SLOT_SIZE * 2, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + SLOT_SIZE * 2, i++);
		for (int k = 0; k <= 2; k++) {
			drawer.drawSlot(x + SLOT_SIZE * k, y + squareSize - SLOT_SIZE * 4, i++);
		}
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + SLOT_SIZE * 3, i++);
		for (int k = 0; k <= 3; k++) {
			drawer.drawSlot(x + SLOT_SIZE * k, y + squareSize - SLOT_SIZE * 3, i++);
		}
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + SLOT_SIZE * 4, i++);
		for (int k = 0; k <= 3; k++) {
			drawer.drawSlot(x + SLOT_SIZE * k, y + squareSize - SLOT_SIZE * 2, i++);
		}
		for (int k = 0; k <= 4; k++) {
			drawer.drawSlot(x + SLOT_SIZE * k, y + squareSize - SLOT_SIZE, i++);
		}

		matrices.push();
		matrices.translate(x + SLOT_SIZE * (normSize - 1.29752487f), y + SLOT_SIZE * (normSize - 0.60617891f), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(37.07040322f));
		matrices.translate(SLOT_SIZE * -3.12360160f, SLOT_SIZE * -1.99299492f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * -0.12360160f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE * 0.23145647f, SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.13060668f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE, i);
	}

	public static void drawForty(Background bgDrawer, Slots drawer, int x, int y, MatrixStack matrices) {
		int i = 0;
		int squareSize = getSquareSize(40);
		bgDrawer.drawBackground(squareSize + 2);
		x++;
		y++;

		for (int j = 0; j < 3; j++) {
			for (int k = 0; k < 3 - j; k++) {
				drawer.drawSlot(x + SLOT_SIZE * k, y + SLOT_SIZE * j, i++);
			}
		}
		for (int j = 0; j < 3; j++) {
			for (int k = 3 - j; k >= 1; k--) {
				drawer.drawSlot(x + squareSize - SLOT_SIZE * k, y + SLOT_SIZE * j, i++);
			}
		}

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 2, y + SLOT_SIZE * 2, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		matrices.translate(0f, SLOT_SIZE * -2, 0f);

		for (int j = 0; j < 4; j++) {
			for (int k = 0; k < 4; k++) {
				drawer.drawSlot(SLOT_SIZE * k, SLOT_SIZE * j, i++);
			}
		}

		matrices.pop();

		for (int j = 3; j >= 1; j--) {
			for (int k = 0; k < 4 - j; k++) {
				drawer.drawSlot(x + SLOT_SIZE * k, y + squareSize - SLOT_SIZE * j, i++);
			}
		}
		for (int j = 3; j >= 1; j--) {
			for (int k = 4 - j; k >= 1; k--) {
				drawer.drawSlot(x + squareSize - SLOT_SIZE * k, y + squareSize - SLOT_SIZE * j, i++);
			}
		}
	}

	public static void drawFortyOne(Background bgDrawer, Slots drawer, int x, int y, MatrixStack matrices) {
		int i = 0;
		int squareSize = getSquareSize(41);
		bgDrawer.drawBackground(squareSize + 2);
		x++;
		y++;

		for (int j = 0; j < 3; j++) {
			for (int k = 0; k < 3 - j; k++) {
				drawer.drawSlot(x + SLOT_SIZE * k, y + SLOT_SIZE * j, i++);
			}
		}

		for (int j = 3; j >= 1; j--) {
			drawer.drawSlot(x + squareSize - SLOT_SIZE * j, y, i++);
		}

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 3.07655182f, y + SLOT_SIZE * 0.74322162f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(16.60058754f));

		for (int j = 0; j < 4; j++) {
			drawer.drawSlot(SLOT_SIZE * j, 0, i++);
		}

		for (int k = 0; k < 3; k++) {
			matrices.translate(SLOT_SIZE * -0.67262145f, SLOT_SIZE, 0f);
			for (int j = 0; j < 5; j++) {
				drawer.drawSlot(SLOT_SIZE * j, 0, i++);
			}
		}

		matrices.translate(SLOT_SIZE * 0.32737854f, SLOT_SIZE, 0f);

		for (int j = 0; j < 4; j++) {
			drawer.drawSlot(SLOT_SIZE * j, 0, i++);
		}

		matrices.pop();

		for (int j = 0; j < 3; j++) {
			drawer.drawSlot(x + SLOT_SIZE * j, y + squareSize - SLOT_SIZE, i++);
		}

		for (int j = 3; j >= 1; j--) {
			for (int k = 4 - j; k >= 1; k--) {
				drawer.drawSlot(x + squareSize - SLOT_SIZE * k, y + squareSize - SLOT_SIZE * j, i++);
			}
		}
	}

	public static void drawFifty(Background bgDrawer, Slots drawer, int x, int y, MatrixStack matrices) {
		int i = 0;
		float normSize = 7.59861960f;
		int squareSize = getSquareSize(50);
		bgDrawer.drawBackground(squareSize + 2);
		x++;
		y++;

		drawer.drawSlot(x, y, i++);
		drawer.drawSlot(x + SLOT_SIZE, y, i++);
		drawer.drawSlot(x, y + SLOT_SIZE, i++);
		drawer.drawSlot(x + SLOT_SIZE, y + SLOT_SIZE, i++);
		drawer.drawSlot(x, y + SLOT_SIZE * 2, i++);


		for (int j = 0; j < 5; j++) {
			for (int k = 5 - j; k >= 1; k--) {
				drawer.drawSlot(x + squareSize - SLOT_SIZE * k, y + SLOT_SIZE * j, i++);
			}
		}

		double offset = 0.07187936f;

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 0.70223662f, y + SLOT_SIZE * 3.32969614f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(42.08660748f));
		matrices.translate(SLOT_SIZE * offset, SLOT_SIZE * -3, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * offset, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 2, y + SLOT_SIZE * (normSize - 2.70710678f), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		matrices.translate(0f, SLOT_SIZE * -3f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * (normSize - 3.32969614f), y + SLOT_SIZE * (normSize - 0.70223662f), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(47.91339252f));
		matrices.translate(SLOT_SIZE * -(2 + offset), SLOT_SIZE * -(3 - offset), 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * -offset, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 0.70223662f, y + SLOT_SIZE * 3.32969614f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(42.08660748f));
		matrices.translate(SLOT_SIZE * offset, SLOT_SIZE * -2, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * -offset, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 2, y + SLOT_SIZE * (normSize - 2.70710678f), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		matrices.translate(0f, SLOT_SIZE * -2, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * (normSize - 3.32969614f), y + SLOT_SIZE * (normSize - 0.70223662f), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(47.91339252f));
		matrices.translate(SLOT_SIZE * -(2 + offset), SLOT_SIZE * -(2 - offset), 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * -offset, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 0.70223662f, y + SLOT_SIZE * 3.32969614f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(42.08660748f));
		matrices.translate(0f, -SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * offset, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 2, y + SLOT_SIZE * (normSize - 2.70710678f), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		matrices.translate(0f, -SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * (normSize - 3.32969614f), y + SLOT_SIZE * (normSize - 0.70223662f), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(47.91339252f));
		matrices.translate(SLOT_SIZE * -2, SLOT_SIZE * -(1 - offset), 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * -offset, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		for (int j = 4; j >= 1; j--) {
			for (int k = 0; k <= 4 - j; k++) {
				drawer.drawSlot(x + SLOT_SIZE * k, y + squareSize - SLOT_SIZE * j, i++);
			}
		}

		drawer.drawSlot(x + squareSize - SLOT_SIZE * 2, y + squareSize - SLOT_SIZE * 2, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE * 2, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE * 3, y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE * 2, y + squareSize - SLOT_SIZE, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE, i);
	}

	public static void drawFiftyOne(Background bgDrawer, Slots drawer, int x, int y, MatrixStack matrices) {
		int i = 0;
		float normSize = 7.70435372f;
		int squareSize = getSquareSize(51);
		bgDrawer.drawBackground(squareSize + 2);
		x++;
		y++;
		
		drawer.drawSlot(x, y, i++);
		drawer.drawSlot(x + SLOT_SIZE, y, i++);
		drawer.drawSlot(x + SLOT_SIZE * 2, y, i++);

		matrices.push();
		matrices.translate(x + SLOT_SIZE * (normSize - 4.58444800f), y, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x, y + SLOT_SIZE, i++);
		drawer.drawSlot(x + SLOT_SIZE, y + SLOT_SIZE, i++);
		drawer.drawSlot(x, y + SLOT_SIZE * 2, i++);

		for (int j = 0; j < 4; j++) {
			for (int k = 3 - j/2; k >= 1; k--) {
				drawer.drawSlot(x + squareSize - SLOT_SIZE * k, y + SLOT_SIZE * j, i++);
			}
		}

		double offset = 0.08471829f;
		Matrix4f transform1, transform2, transform3, transform4;

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 0.69479500f, y + SLOT_SIZE * 3.17645419f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-30.03434357f));
		transform1 = new Matrix4f(matrices.peek().getPositionMatrix());
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * (normSize - 4), y + SLOT_SIZE * (normSize - 1), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-22.08119070f));
		matrices.translate(SLOT_SIZE * offset * -5, SLOT_SIZE * -5, 0f);
		transform2 = new Matrix4f(matrices.peek().getPositionMatrix());
		matrices.translate(SLOT_SIZE * 0.02639259f, 0f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 3, y + SLOT_SIZE, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-22.08119070f));
		matrices.translate(SLOT_SIZE * (offset - 1), 0f, 0f);
		transform3 = new Matrix4f(matrices.peek().getPositionMatrix());
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(transform1);
		matrices.translate(SLOT_SIZE * -0.077f, SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(transform2);
		matrices.translate(SLOT_SIZE * (offset + 0.00501247f), SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(transform3);
		matrices.translate(SLOT_SIZE * (offset - 0.00501247f), SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * (normSize - 1.69479500f), y + SLOT_SIZE * (normSize - 3.17645419f), 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-30.03434357f));
		matrices.translate(SLOT_SIZE * -0.804f, SLOT_SIZE * -4, 0f);
		transform4 = new Matrix4f(matrices.peek().getPositionMatrix());
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(transform1);
		matrices.translate(SLOT_SIZE * -0.13531248f, SLOT_SIZE * 2, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(transform2);
		matrices.translate(SLOT_SIZE * offset * 2, SLOT_SIZE * 2, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(transform3);
		matrices.translate(SLOT_SIZE * offset * 2, SLOT_SIZE * 2, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(transform4);
		matrices.translate(SLOT_SIZE * -0.06068752f, SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(transform1);
		matrices.translate(SLOT_SIZE * -0.196f, SLOT_SIZE * 3, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(transform2);
		matrices.translate(SLOT_SIZE * (offset * 3 - 0.00501247f), SLOT_SIZE * 3, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(transform3);
		matrices.translate(SLOT_SIZE * (offset * 3 + 0.00501247f), SLOT_SIZE * 3, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(transform4);
		matrices.translate(SLOT_SIZE * -0.119f, SLOT_SIZE * 2, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(transform2);
		matrices.translate(SLOT_SIZE * (offset * 4 - 0.02639259f), SLOT_SIZE * 4, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(transform3);
		matrices.translate(SLOT_SIZE * (offset * 4 + 0.02639259f), SLOT_SIZE * 4, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(transform4);
		matrices.translate(SLOT_SIZE * -0.196f, SLOT_SIZE * 3, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		for (int j = 0; j < 4; j++) {
			for (int k = 0; k <= j/2; k++) {
				drawer.drawSlot(x + SLOT_SIZE * k, y + squareSize - SLOT_SIZE * (4 - j), i++);
			}
		}

		drawer.drawSlot(x + squareSize - SLOT_SIZE * 2, y + squareSize - SLOT_SIZE * 3, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE * 3, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE * 3, y + squareSize - SLOT_SIZE * 2, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE * 2, y + squareSize - SLOT_SIZE * 2, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE * 2, i++);

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 2.58444800f, y + SLOT_SIZE * (normSize - 1), 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		for (int j = 4; j >= 1; j--) {
			drawer.drawSlot(x + squareSize - SLOT_SIZE * j, y + squareSize - SLOT_SIZE, i++);
		}
	}

	public static void drawFiftyTwo(Background bgDrawer, Slots drawer, int x, int y, MatrixStack matrices) {
		int i = 0;
		int squareSize = getSquareSize(52);
		bgDrawer.drawBackground(squareSize + 2);
		x++;
		y++;

		drawer.drawSlot(x, y, i++);

		for (int j = 6; j >= 1; j--) {
			drawer.drawSlot(x + squareSize - SLOT_SIZE * j, y, i++);
		}

		Matrix4f mat;

		matrices.push();
		matrices.translate(x + SLOT_SIZE, y + SLOT_SIZE, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		matrices.translate(0f, SLOT_SIZE * -0.5f, 0f);
		mat = new Matrix4f(matrices.peek().getPositionMatrix());
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		for (int j = 5; j >= 1; j--) {
			drawer.drawSlot(x + squareSize - SLOT_SIZE * j, y + SLOT_SIZE, i++);
		}

		drawer.drawSlot(x, y + squareSize - SLOT_SIZE * 6, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE * 6, y + squareSize - SLOT_SIZE * 6, i++);
		drawer.drawSlot(x + SLOT_SIZE * 3, y + SLOT_SIZE * 2, i++);

		for (int j = 3; j >= 1; j--) {
			drawer.drawSlot(x + squareSize - SLOT_SIZE * j, y + SLOT_SIZE * 2, i++);
		}
		for (int j = 0; j < 3; j++) {
			drawer.drawSlot(x + SLOT_SIZE * j, y + squareSize - SLOT_SIZE * 5, i++);
		}

		matrices.push();
		matrices.translate(SLOT_SIZE * 3, SLOT_SIZE * 2, 0f);
		matrices.multiplyPositionMatrix(mat);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		for (int j = 2; j >= 1; j--) {
			drawer.drawSlot(x + squareSize - SLOT_SIZE * j, y + SLOT_SIZE * 3, i++);
		}

		for (int j = 0; j < 4; j++) {
			drawer.drawSlot(x + SLOT_SIZE * j, y + squareSize - SLOT_SIZE * 4, i++);
		}

		drawer.drawSlot(x + squareSize - SLOT_SIZE * 3, y + squareSize - SLOT_SIZE * 4, i++);

		for (int j = 2; j >= 1; j--) {
			drawer.drawSlot(x + squareSize - SLOT_SIZE * j, y + SLOT_SIZE * 4, i++);
		}

		for (int j = 0; j < 5; j++) {
			drawer.drawSlot(x + SLOT_SIZE * j, y + squareSize - SLOT_SIZE * 3, i++);
		}

		drawer.drawSlot(x + SLOT_SIZE * 5, y + SLOT_SIZE * 5, i++);
		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + SLOT_SIZE * 5, i++);

		for (int j = 0; j < 5; j++) {
			drawer.drawSlot(x + SLOT_SIZE * j, y + squareSize - SLOT_SIZE * 2, i++);
		}

		matrices.push();
		matrices.translate(SLOT_SIZE * 5, SLOT_SIZE * 5, 0f);
		matrices.multiplyPositionMatrix(mat);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		for (int j = 0; j < 6; j++) {
			drawer.drawSlot(x + SLOT_SIZE * j, y + squareSize - SLOT_SIZE, i++);
		}

		drawer.drawSlot(x + squareSize - SLOT_SIZE, y + squareSize - SLOT_SIZE, i);
	}

	private static int rowFiftyThree(Slots drawer, int i, MatrixStack matrices) {
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * -0.48051357f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * -0.4805137f, 0f);
		drawer.drawSlot(0, 0, i++);
		drawer.drawSlot(SLOT_SIZE, 0, i++);
		matrices.translate(SLOT_SIZE * 2, SLOT_SIZE * -0.48051375f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * -0.4805137f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * -0.48051375f, 0f);
		drawer.drawSlot(0, 0, i++);
		return i;
	}

	public static void drawFiftyThree(Background bgDrawer, Slots drawer, int x, int y, MatrixStack matrices) {
		int i = 0;
		int squareSize = getSquareSize(53);
		bgDrawer.drawBackground(squareSize + 2);
		x++;
		y++;

		for (int j = 0; j < 5; j++) {
			for (int k = 0; k < 5 - j; k++) {
				drawer.drawSlot(x + SLOT_SIZE * k, y + SLOT_SIZE * j, i++);
			}
		}

		drawer.drawSlot(x, y + squareSize - SLOT_SIZE * 2, i++);

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 5.09470432f, y + SLOT_SIZE * 0.42478198f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-25.13686477f));
		matrices.translate(SLOT_SIZE * -6, SLOT_SIZE * 2.40256844f, 0f);
		i = rowFiftyThree(drawer, i, matrices);
		matrices.pop();

		drawer.drawSlot(x, y + squareSize - SLOT_SIZE, i++);

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 5.09470432f, y + SLOT_SIZE * 0.42478198f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-25.13686477f));
		matrices.translate(SLOT_SIZE * -6, SLOT_SIZE * 3.40256844f, 0f);
		i = rowFiftyThree(drawer, i, matrices);
		matrices.pop();

		drawer.drawSlot(x + squareSize - SLOT_SIZE, y, i++);

		for (int j = 6; j >= 1; j--) {
			for (int k = 7 - j; k >= 1; k--) {
				drawer.drawSlot(x + squareSize - SLOT_SIZE * k, y + squareSize - SLOT_SIZE * j, i++);
			}
		}
	}

	public static void drawFiftyFour(Background bgDrawer, Slots drawer, int x, int y, MatrixStack matrices) {
		int i = 0;
		float normSize = 7.84666719f;
		int squareSize = getSquareSize(54);
		bgDrawer.drawBackground(squareSize + 2);
		x++;
		y++;

		int[] lengths = {6,5,4,2,1};
		for (int j = 0; j < lengths.length; j++) {
			for (int k = 0; k < lengths[j]; k++) {
				drawer.drawSlot(x + SLOT_SIZE * k, y + SLOT_SIZE * j, i++);
			}
		}

		drawer.drawSlot(x + squareSize - SLOT_SIZE, y, i++);

		Quaternionf rot = RotationAxis.POSITIVE_Z.rotationDegrees(34.73490026f);

		matrices.push();
		matrices.translate(x + SLOT_SIZE * (normSize - 0.75f), y + SLOT_SIZE * (normSize - 6.67333359f), 0f);
		matrices.multiply(rot);
		matrices.translate(-SLOT_SIZE, 0f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * normSize, y + SLOT_SIZE * (normSize - 6), 0f);
		matrices.multiply(rot);
		matrices.translate(-SLOT_SIZE, 0f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * (normSize - 1.52688698f), y + SLOT_SIZE * (normSize - 5.99513072f), 0f);
		matrices.multiply(rot);
		matrices.translate(-SLOT_SIZE, 0f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * (normSize - 0.77688698f), y + SLOT_SIZE * (normSize - 5.32179712f), 0f);
		matrices.multiply(rot);
		matrices.translate(-SLOT_SIZE, 0f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * (normSize - 1.55377397f), y + SLOT_SIZE * (normSize - 4.64359425f), 0f);
		matrices.multiply(rot);
		matrices.translate(SLOT_SIZE * -2, SLOT_SIZE * -0.07728492f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.07728492f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(-SLOT_SIZE, SLOT_SIZE * 0.92271507f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.07728492f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 1.55377397f, y + SLOT_SIZE * 4.64359425f, 0f);
		matrices.multiply(rot);
		matrices.translate(0f, SLOT_SIZE * -2, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.07728492f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(-SLOT_SIZE, SLOT_SIZE * 0.92271507f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.07728492f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 0.77688698f, y + SLOT_SIZE * 5.32179712f, 0f);
		matrices.multiply(rot);
		matrices.translate(0f, -SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 1.52688698f, y + SLOT_SIZE * 5.99513072f, 0f);
		matrices.multiply(rot);
		matrices.translate(0f, -SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.translate(x, y + SLOT_SIZE * 6, 0f);
		matrices.multiply(rot);
		matrices.translate(0f, -SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 0.75f, y + SLOT_SIZE * 6.67333359f, 0f);
		matrices.multiply(rot);
		matrices.translate(0f, -SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		drawer.drawSlot(x, y + squareSize - SLOT_SIZE, i++);

		for (int j = lengths.length; j >= 1; j--) {
			for (int k = lengths[j - 1]; k >= 1; k--) {
				drawer.drawSlot(x + squareSize - SLOT_SIZE * k, y + squareSize - SLOT_SIZE * j, i++);
			}
		}
	}

	public static void drawFiftyFive(Background bgDrawer, Slots drawer, int x, int y, MatrixStack matrices) {
		int i = 0;
		int squareSize = getSquareSize(55);
		bgDrawer.drawBackground(squareSize + 2);
		x++;
		y++;

		for (int j = 0; j < 3; j++) {
			for (int k = 0; k < 3 - j; k++) {
				drawer.drawSlot(x + SLOT_SIZE * k, y + SLOT_SIZE * j, i++);
			}
		}

		for (int j = 4; j >= 1; j--) {
			drawer.drawSlot(x + squareSize - SLOT_SIZE * j, y, i++);
		}

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 3.13009105f, y + SLOT_SIZE * 0.67256345f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(21.66800178f));
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		Matrix4f mat1;

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 3.16066671f, y + SLOT_SIZE * 5.96465671f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(13.30405178f));
		mat1 = new Matrix4f(matrices.peek().getPositionMatrix());
		matrices.translate(SLOT_SIZE * -0.25819987f, SLOT_SIZE * -5.04051442, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.04051442f, 0f);
		drawer.drawSlot(0, 0, i++);
		drawer.drawSlot(SLOT_SIZE, 0, i++);
		matrices.translate(SLOT_SIZE * 2, SLOT_SIZE * -0.00696516f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 2, y + SLOT_SIZE * 2, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(15.87748802f));
		matrices.translate(0f, SLOT_SIZE * -0.55282875f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		Matrix4f mat2;

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 3.09209914f, y + SLOT_SIZE * 1.81565139f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(14.57170350f));
		mat2 = new Matrix4f(matrices.peek().getPositionMatrix());
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(mat1);
		matrices.translate(SLOT_SIZE * -0.02173524f, SLOT_SIZE * -4, 0f);
		for (int j = 0; j < 3; j++) {
			drawer.drawSlot(SLOT_SIZE * j, 0, i++);
		}
		matrices.translate(SLOT_SIZE * 3, SLOT_SIZE * -0.00696516f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(mat2);
		matrices.translate(SLOT_SIZE * -1.72683179f, SLOT_SIZE * 0.90693694f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.09306304f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(mat1);
		matrices.translate(SLOT_SIZE * -0.77086806f, SLOT_SIZE * -3, 0f);
		for (int j = 0; j < 4; j++) {
			drawer.drawSlot(SLOT_SIZE * j, 0, i++);
		}
		matrices.pop();

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 1.18415667f, y + SLOT_SIZE * 3.40017871f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(13.79356726f));
		matrices.translate(-SLOT_SIZE, SLOT_SIZE * -0.10665350f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.10665350f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.pop();

		matrices.push();
		matrices.multiplyPositionMatrix(mat1);
		matrices.translate(SLOT_SIZE * -1.5139121f, SLOT_SIZE * -2, 0f);
		for (int j = 0; j < 4; j++) {
			drawer.drawSlot(SLOT_SIZE * j, 0, i++);
		}
		matrices.translate(SLOT_SIZE * -1.74304404f, SLOT_SIZE * 0.96140677f, 0f);
		drawer.drawSlot(0, 0, i++);
		matrices.translate(SLOT_SIZE, SLOT_SIZE * 0.03859322f, 0f);
		for (int j = 0; j < 4; j++) {
			drawer.drawSlot(SLOT_SIZE * j, 0, i++);
		}
		matrices.translate(SLOT_SIZE * -0.74304404f, SLOT_SIZE, 0f);
		for (int j = 0; j < 4; j++) {
			drawer.drawSlot(SLOT_SIZE * j, 0, i++);
		}
		matrices.pop();

		for (int j = 0; j < 3; j++) {
			drawer.drawSlot(x + SLOT_SIZE * j, y + squareSize - SLOT_SIZE, i++);
		}

		for (int j = 4; j >= 1; j--) {
			for (int k = 5 - j; k >= 1; k--) {
				drawer.drawSlot(x + squareSize - SLOT_SIZE * k, y + squareSize - SLOT_SIZE * j, i++);
			}
		}
	}

	private static int tiltedRowSixtyFive(Slots drawer, int i, MatrixStack matrices, float middleOffset) {
		drawer.drawSlot(0, 0, i++);
		drawer.drawSlot(SLOT_SIZE, 0, i++);

		matrices.translate(0f, middleOffset, 0f);
		drawer.drawSlot(SLOT_SIZE * 2, 0, i++);
		matrices.translate(0f, -middleOffset, 0f);

		drawer.drawSlot(SLOT_SIZE * 3, 0, i++);
		drawer.drawSlot(SLOT_SIZE * 4, 0, i++);

		return i;
	}

	public static void drawSixtyFive(Background bgDrawer, Slots drawer, int x, int y, MatrixStack matrices) {
		int i = 0;
		int squareSize = getSquareSize(65);
		bgDrawer.drawBackground(squareSize + 2);
		x++;
		y++;

		for (int j = 0; j < 4; j++) {
			for (int k = 0; k < 4 - j; k++) {
				drawer.drawSlot(x + SLOT_SIZE * k, y + SLOT_SIZE * j, i++);
			}
		}

		for (int j = 0; j < 4; j++) {
			for (int k = 4 - j; k >= 1; k--) {
				drawer.drawSlot(x + squareSize - SLOT_SIZE * k, y + SLOT_SIZE * j, i++);
			}
		}

		drawer.drawSlot(x + squareSize / 2 - SLOT_SIZE / 2, y + squareSize / 2 - SLOT_SIZE / 2, i++);

		float middleOffset = SLOT_SIZE * 0.20710678f;

		matrices.push();
		matrices.translate(x + SLOT_SIZE * 2.5f, y + SLOT_SIZE * 2.5f, 0f);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
		matrices.translate(0f, SLOT_SIZE * -2.5f, 0f);
		i = tiltedRowSixtyFive(drawer, i, matrices, -middleOffset);
		matrices.translate(0f, SLOT_SIZE, 0f);
		i = tiltedRowSixtyFive(drawer, i, matrices, -middleOffset);

		matrices.translate(-middleOffset, SLOT_SIZE, 0f);
		drawer.drawSlot(0, 0, i++);
		drawer.drawSlot(SLOT_SIZE, 0, i++);
		matrices.translate(SLOT_SIZE * 3 + middleOffset * 2, 0f, 0f);
		drawer.drawSlot(0, 0, i++);
		drawer.drawSlot(SLOT_SIZE, 0, i++);

		matrices.translate(SLOT_SIZE * -3 - middleOffset, SLOT_SIZE, 0f);
		i = tiltedRowSixtyFive(drawer, i, matrices, middleOffset);
		matrices.translate(0f, SLOT_SIZE, 0f);
		i = tiltedRowSixtyFive(drawer, i, matrices, middleOffset);

		matrices.pop();

		for (int j = 4; j >= 1; j--) {
			for (int k = 0; k < 5 - j; k++) {
				drawer.drawSlot(x + SLOT_SIZE * k, y + squareSize - SLOT_SIZE * j, i++);
			}
		}

		for (int j = 4; j >= 1; j--) {
			for (int k = 5 - j; k >= 1; k--) {
				drawer.drawSlot(x + squareSize - SLOT_SIZE * k, y + squareSize - SLOT_SIZE * j, i++);
			}
		}
	}
}