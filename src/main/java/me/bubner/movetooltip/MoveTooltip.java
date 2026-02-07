package me.bubner.movetooltip;

import me.bubner.movetooltip.commands.Settings;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class MoveTooltip implements ClientModInitializer {
    public static final String MOD_ID = "movetooltip";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("movetooltip.properties");
    private static final Properties config = new Properties();

    public static int getOffset() {
        return Integer.parseInt(config.getProperty("offset", "0"));
    }

    public static void setOffset(int offset) {
        config.setProperty("offset", String.valueOf(offset));
        saveConfig();
    }

    private static void loadConfig() {
        if (Files.exists(CONFIG_PATH)) {
            try (var reader = Files.newBufferedReader(CONFIG_PATH)) {
                config.load(reader);
            } catch (IOException e) {
                LOGGER.error("Failed to load config", e);
            }
        } else {
            config.setProperty("offset", "0");
            saveConfig();
        }
    }

    private static void saveConfig() {
        try (var writer = Files.newBufferedWriter(CONFIG_PATH)) {
            config.store(writer, "MoveTooltip configuration");
        } catch (IOException e) {
            LOGGER.error("Failed to save config", e);
        }
    }

    @Override
    public void onInitializeClient() {
        loadConfig();
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                Settings.register(dispatcher));
    }
}