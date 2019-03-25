/*
 * MoreSoundConfig
 * Copyright (C) 2018
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ichttt.mods.moresoundconfig;

import ichttt.mods.moresoundconfig.gui.GuiSound;
import net.minecraft.client.gui.GuiScreenOptionsSounds;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = MoreSoundConfig.MODID,
        name = MoreSoundConfig.NAME,
        version = MoreSoundConfig.VERSION,
        acceptedMinecraftVersions = "[1.12.2,1.13)",
        clientSideOnly = true,
        certificateFingerprint = MoreSoundConfig.FINGERPRINT)
@Mod.EventBusSubscriber
public class MoreSoundConfig {
    public static final String MODID = "moresoundconfig";
    public static final String NAME = "More Sound Config";
    public static final String VERSION = "1.0.1";
    public static final String FINGERPRINT = "7904c4e13947c8a616c5f39b26bdeba796500722";
    public static final Logger LOGGER = LogManager.getLogger(MODID);


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("{} version {} starting", NAME, VERSION);
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent event) {
        if (event.getModID().equals(MODID)) {
            ConfigManager.sync(MODID, Config.Type.INSTANCE);
        }
    }

    @SubscribeEvent
    public static void onGuiOpen(GuiOpenEvent event) {
        if (event.getGui() instanceof GuiScreenOptionsSounds && !(event.getGui() instanceof GuiSound)) {
            GuiScreenOptionsSounds sounds = (GuiScreenOptionsSounds) event.getGui();
            event.setGui(new GuiSound(sounds.parent, sounds.game_settings_4));
        }
    }
}
