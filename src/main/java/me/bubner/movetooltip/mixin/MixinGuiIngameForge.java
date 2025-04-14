package me.bubner.movetooltip.mixin;

import me.bubner.movetooltip.MoveTooltip;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.common.config.Configuration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;

@Mixin(GuiIngameForge.class)
public class MixinGuiIngameForge {
    @Inject(method = "renderToolHightlight", at = @At("HEAD"), remap = false) // sic
    public void moveTooltip$renderToolHightlight(ScaledResolution scaledRes, CallbackInfo ci) throws IllegalAccessException, NoSuchFieldException {
        Configuration config = MoveTooltip.config;
        if (config == null)
            return;
        int offset = config.get("settings", "offset", 0).getInt();
        if (offset == 0)
            return;
        Field scaledHeight = ScaledResolution.class.getDeclaredField("scaledHeight");
        scaledHeight.setAccessible(true);
        // Add an arbitrary offset on scaledRes before it gets sent to the renderer, which moves it
        scaledHeight.setInt(scaledRes, scaledRes.getScaledHeight() - offset);
    }
}
