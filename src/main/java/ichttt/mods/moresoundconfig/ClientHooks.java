package ichttt.mods.moresoundconfig;

import ichttt.mods.moresoundconfig.gui.GuiSound;
import net.minecraft.client.gui.GuiScreenOptionsSounds;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientHooks {


    public static void register() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientHooks::setup);
        MinecraftForge.EVENT_BUS.addListener(ClientHooks::onGuiOpen);
    }

    public static void setup(FMLCommonSetupEvent event) {
        SoundDeviceOptions.LOGGER.info("{} is starting", SoundDeviceOptions.NAME);
    }

    public static void onGuiOpen(GuiOpenEvent event) {
        if (event.getGui() instanceof GuiScreenOptionsSounds && !(event.getGui() instanceof GuiSound)) {
            GuiScreenOptionsSounds sounds = (GuiScreenOptionsSounds) event.getGui();
            event.setGui(new GuiSound(sounds.parent, sounds.game_settings_4));
        }
    }
}
