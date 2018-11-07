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
import ichttt.mods.moresoundconfig.MoreSoundConfig;
import ichttt.mods.moresoundconfig.SoundDevices;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;

public class ASMHooks {

    public static void setupSound() throws LWJGLException {
        AL.create(null, 44100, 60, false, false);
        SoundDevices.reloadDeviceList();
        String device = MSCConfig.getActiveSoundDevice();
        boolean valid = SoundDevices.validateActiveOutput(device);
        if (!valid) {
            MoreSoundConfig.LOGGER.warn("Sound device " + MSCConfig.activeSoundDevice + " no longer valid");
            device = null;
            SoundDevices.updateOutput(null);
        }
        MoreSoundConfig.LOGGER.info("SoundManager loading on device " + MSCConfig.friendlyActiveSoundDevice());
        AL.destroy();
        AL.create(device, MSCConfig.contextFrequency, MSCConfig.contextRefresh, false);
    }
}
