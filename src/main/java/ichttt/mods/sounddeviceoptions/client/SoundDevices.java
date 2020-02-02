/*
 * SoundDeviceOptions
 * Copyright (C) 2018-2019
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

import ichttt.mods.sounddeviceoptions.SDOConfig;
import ichttt.mods.sounddeviceoptions.SoundDeviceOptions;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALC11;
import org.lwjgl.openal.ALUtil;

import java.util.ArrayList;
import java.util.List;

public class SoundDevices {
    public static final List<String> VALID_DEVICES = new ArrayList<>();

    public static void reloadDeviceList() {
        synchronized (VALID_DEVICES) {
            boolean success = false;
            try {
                reloadDeviceList0();
                success = true;
            } catch (UnsatisfiedLinkError e) {
                SoundDeviceOptions.LOGGER.error("Failed to reload device list! Native lib not hooked!", e);
            } catch (RuntimeException e) {
                SoundDeviceOptions.LOGGER.error("Failed to reload device list! Unexpected error!", e);
            }
            if (!success)
                VALID_DEVICES.clear();
        }
    }

    private static void reloadDeviceList0() {
        VALID_DEVICES.clear();
        if (ALC10.alcIsExtensionPresent(0, "ALC_enumerate_all_EXT")) {
            SoundDeviceOptions.LOGGER.debug("Reading sound devices");
            List<String> devices = ALUtil.getStringList(0, ALC11.ALC_ALL_DEVICES_SPECIFIER);
            if (devices == null) {
                SoundDeviceOptions.LOGGER.error("Got null from devices list!");
                return;
            }
            for (String deviceName : devices) {
                String error = null;
                long device = ALC10.alcOpenDevice(deviceName);
                if (device == 0)
                    error = "null device";
                int code = ALC10.alcGetError(device);
                if (code != ALC10.ALC_NO_ERROR)
                    error = code + "";

                if (device != 0) {
                    boolean success = ALC10.alcCloseDevice(device);
                    if (!success)
                        error = "Could not close";
                }
                if (error != null) {
                    SoundDeviceOptions.LOGGER.error("Error testing device " + deviceName);
                    SoundDeviceOptions.LOGGER.error("Error code: " + error);
                } else {
                    SoundDeviceOptions.LOGGER.debug("Found valid device " + deviceName);
                    VALID_DEVICES.add(deviceName);
                }
            }
        } else {
            SoundDeviceOptions.LOGGER.warn("Could not list devices - operation not supported by sound driver!");
        }
    }

    public static boolean validateActiveOutput(String output) {
        return output == null || VALID_DEVICES.contains(output);
    }

    public static void updateOutput(String newValue) {
        if (newValue == null)
            newValue = "";
        SDOConfig.CLIENT.activeSoundDevice.set(newValue);
        SDOConfig.CLIENT.activeSoundDevice.save();
    }
}
