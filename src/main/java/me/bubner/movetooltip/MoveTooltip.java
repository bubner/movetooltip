package me.bubner.movetooltip;

import me.bubner.movetooltip.commands.Settings;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod(modid = MoveTooltip.MOD_ID)
public class MoveTooltip {
    public static final String MOD_ID = "movetooltip";
    
    public static Configuration config;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        File configFile = new File(Loader.instance().getConfigDir(), "movetooltip.cfg");
        Configuration config = new Configuration(configFile);
        config.load();
        config.get("settings", "offset", 0);
        if (config.hasChanged())
            config.save();
        MoveTooltip.config = config;
        ClientCommandHandler.instance.registerCommand(new Settings());
    }
}
