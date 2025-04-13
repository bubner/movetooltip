package me.bubner.movetooltip.mixin;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.GuiIngameForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;

@Mixin(GuiIngameForge.class)
public class MixinGuiIngameForge {
    @Inject(method = "renderToolHightlight", at = @At("HEAD"), remap = false) // sic
    public void moveTooltip$renderToolHightlight(ScaledResolution scaledRes, CallbackInfo ci) throws IllegalAccessException, NoSuchFieldException {
        Field scaledHeight = ScaledResolution.class.getDeclaredField("scaledHeight");
        scaledHeight.setAccessible(true);
        scaledHeight.setInt(scaledRes, 59); // TODO: make a config thru command for this offset (/tooltip)
    }
}
