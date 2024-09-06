package io.github.syst3ms.perfectlypacked.client.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.syst3ms.perfectlypacked.client.SquarePackingRendering;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
	@WrapOperation(
			method = "renderGuiItemModel",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/util/math/MatrixStack;push()V"
			)
	)
	private void useTempMatrixStackForBundle(MatrixStack instance, Operation<Void> original) {
		original.call(instance);
		if (SquarePackingRendering.TEMP_MATRICES != null) {
			instance.multiplyPositionMatrix(SquarePackingRendering.TEMP_MATRICES.peek().getPositionMatrix());
		}
	}
}
