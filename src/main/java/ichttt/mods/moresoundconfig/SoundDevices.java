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

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.Loader;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALC11;
import org.lwjgl.openal.ALCdevice;

import java.util.ArrayList;
import java.util.List;

public class SoundDevices {
    public static List<String> validDevices = new ArrayList<>();

    public static void reloadDeviceList() {
        boolean success = false;
        try {
            success = !reloadDeviceList0();
        } catch (UnsatisfiedLinkError e) {
            MoreSoundConfig.LOGGER.error("Failed to reload device list! Native lib not hooked!", e);
        } catch (RuntimeException e) {
            MoreSoundConfig.LOGGER.error("Failed to reload device list! Unexpected error!", e);
        }
        if (!success)
            validDevices.clear();
    }

    public static boolean reloadDeviceList0() {
        validDevices.clear();
        boolean errorOccurred = false;
        if (ALC10.alcIsExtensionPresent(null, "ALC_enumerate_all_EXT")) {
            MoreSoundConfig.LOGGER.info("Reading sound devices");
            String s = ALC10.alcGetString(null, ALC11.ALC_ALL_DEVICES_SPECIFIER);
            String[] devices = s.split("\u0000");
            for (String deviceName : devices) {
                String error = null;
                ALCdevice device = ALC10.alcOpenDevice(deviceName);
                if (device == null) {
                    error = "null device";
                } else if (!device.isValid()) {
                    error = "invalid device";
                }
                int code = ALC10.alcGetError(device);
                if (code != ALC10.ALC_NO_ERROR)
                    error = code + "";

                if (device != null) {
                    boolean success = ALC10.alcCloseDevice(device);
                    if (!success)
                        error = "Could not close";
                }
                if (error != null) {
                    errorOccurred = true;
                    MoreSoundConfig.LOGGER.error("Error testing device " + deviceName);
                    MoreSoundConfig.LOGGER.error("Error code: " + error);
                } else {
                    MoreSoundConfig.LOGGER.debug("Found valid device " + deviceName);
                    validDevices.add(deviceName);
                }
            }
        } else {
            MoreSoundConfig.LOGGER.warn("Could not list devices - operation not supported by sound driver!");
            return true;
        }
        return errorOccurred;
    }

    public static boolean validateActiveOutput(String output) {
        return output == null || validDevices.contains(output);
    }

    public static void updateOutput(String newValue) {
        if (newValue == null)
            newValue = "";
        MSCConfig.activeSoundDevice = newValue;
        Minecraft.getMinecraft().addScheduledTask(() -> ConfigManager.sync(MoreSoundConfig.MODID, Config.Type.INSTANCE));
    }
}
