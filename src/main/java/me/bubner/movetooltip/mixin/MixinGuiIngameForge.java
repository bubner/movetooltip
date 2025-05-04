package me.bubner.movetooltip.mixin;

import me.bubner.movetooltip.MoveTooltip;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.common.config.Configuration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;

@Mixin(GuiIngameForge.class)
public class MixinGuiIngameForge {
    @Unique
    private static final Field moveTooltip$scaledHeight;
    @Unique
    private int moveTooltip$previousHeight;
    
    static {
        try {
            boolean isDev = (boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
            // Use MCP mapping if we're in an obfuscated environment
            moveTooltip$scaledHeight = ScaledResolution.class.getDeclaredField(isDev ? "scaledHeight" : "field_78331_b");
            moveTooltip$scaledHeight.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Inject(method = "renderToolHightlight", at = @At("HEAD"), remap = false) // sic
    public void moveTooltipPre$renderToolHightlight(ScaledResolution scaledRes, CallbackInfo ci) throws IllegalAccessException {
        moveTooltip$previousHeight = scaledRes.getScaledHeight();
        Configuration config = MoveTooltip.config;
        if (config == null)
            return;
        int offset = config.get("settings", "offset", 0).getInt();
        if (offset == 0)
            return;
        // Add an arbitrary offset on scaledRes before it gets sent to the renderer, which moves it
        moveTooltip$scaledHeight.setInt(scaledRes, scaledRes.getScaledHeight() - offset);
    }
    
    @Inject(method = "renderToolHightlight", at = @At("TAIL"), remap = false) // sic
    public void moveTooltipPost$renderToolHightlight(ScaledResolution scaledResolution, CallbackInfo ci) throws IllegalAccessException {
        // Undo offsetting the tooltip as we've used the value, but we don't want to impact other mods
        moveTooltip$scaledHeight.setInt(scaledResolution, moveTooltip$previousHeight);
    }
}
