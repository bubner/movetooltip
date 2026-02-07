package me.bubner.movetooltip.mixin;

import me.bubner.movetooltip.MoveTooltip;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.joml.Matrix3x2fStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class MixinGui {
    @Unique
    private boolean moveTooltip$translated = false;

    @Inject(method = "renderSelectedItemName", at = @At("HEAD"))
    private void moveTooltip$pre(GuiGraphics graphics, CallbackInfo ci) {
        int offset = MoveTooltip.getOffset();
        if (offset == 0) return;
        Matrix3x2fStack pose = graphics.pose();
        pose.pushMatrix();
        // Translates up the screen with +offset
        pose.translate(0.0f, (float) -offset);
        moveTooltip$translated = true;
    }

    @Inject(method = "renderSelectedItemName", at = @At("RETURN"))
    private void moveTooltip$post(GuiGraphics graphics, CallbackInfo ci) {
        if (moveTooltip$translated) {
            // Undo offsetting the tooltip to not impact other calculations of the render text
            graphics.pose().popMatrix();
            moveTooltip$translated = false;
        }
    }
}