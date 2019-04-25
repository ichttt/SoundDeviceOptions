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

package ichttt.mods.moresoundconfig.asm;

import ichttt.mods.moresoundconfig.MSCConfig;
import ichttt.mods.moresoundconfig.SoundDeviceOptions;
import ichttt.mods.moresoundconfig.SoundDevices;
import net.minecraft.client.audio.LibraryLWJGL3;
import org.lwjgl.openal.ALC10;

public class ASMHooks {

    public static long setupSound() throws LibraryLWJGL3.LWJGL3SoundSystemException {
        //Dummy create to link natives
//        AL.create(null, 44100, 60, false, false);
        //AL query devices
        SoundDevices.reloadDeviceList();
        String device = MSCConfig.getActiveSoundDevice();
        boolean valid = SoundDevices.validateActiveOutput(device);
        if (!valid) {
            SoundDeviceOptions.LOGGER.warn("Sound device " + MSCConfig.activeSoundDevice + " no longer valid");
            device = null;
            SoundDevices.updateOutput(null);
        }
        SoundDeviceOptions.LOGGER.info("SoundManager loading on device " + MSCConfig.friendlyActiveSoundDevice());
        //AL shutdown and startup with actual parameters
//        AL.destroy();
        return ALC10.alcOpenDevice(device);
    }
}
