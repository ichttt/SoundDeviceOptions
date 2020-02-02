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

import java.nio.ByteBuffer;

@SuppressWarnings("unused")
public class ASMHooks {

    //Accept a ByteBuffer as an arg, so the signature matches
    public static long setupSound(ByteBuffer deviceSpecifier) {
        SoundDevices.reloadDeviceList();
        String device = SDOConfig.getActiveSoundDevice();
        boolean valid = SoundDevices.validateActiveOutput(device);
        if (!valid) {
            SoundDeviceOptions.LOGGER.warn("Sound device " + SDOConfig.CLIENT.activeSoundDevice.get() + " no longer valid");
            device = null;
            SoundDevices.updateOutput(null);
        }
        SoundDeviceOptions.LOGGER.info("SoundManager loading on device {}", SDOConfig.getActiveSoundDevice());
        return ALC10.alcOpenDevice(device);
    }
}
