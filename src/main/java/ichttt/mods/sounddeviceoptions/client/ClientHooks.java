/*
 * SoundDeviceOptions
 * Copyright (C) 2018-2020
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

package ichttt.mods.sounddeviceoptions.client;

import ichttt.mods.sounddeviceoptions.SoundDeviceOptions;
import ichttt.mods.sounddeviceoptions.client.gui.GuiChooseOutput;
import ichttt.mods.sounddeviceoptions.client.gui.GuiSound;
import net.minecraft.client.gui.screen.OptionsSoundsScreen;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientHooks {

    public static void register() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientHooks::setup);
        MinecraftForge.EVENT_BUS.addListener(ClientHooks::onGuiOpen);
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY, () -> (minecraft, screen) -> new GuiChooseOutput(screen));
    }

    private static void setup(FMLCommonSetupEvent event) {
        SoundDeviceOptions.LOGGER.info("{} is starting", SoundDeviceOptions.NAME);
    }

    private static void onGuiOpen(GuiOpenEvent event) {
        if (event.getGui() instanceof OptionsSoundsScreen && !(event.getGui() instanceof GuiSound)) {
            OptionsSoundsScreen sounds = (OptionsSoundsScreen) event.getGui();
            event.setGui(new GuiSound(sounds.lastScreen, sounds.options));
        }
    }
}