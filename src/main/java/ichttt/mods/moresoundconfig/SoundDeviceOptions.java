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

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(SoundDeviceOptions.MODID)
@Mod.EventBusSubscriber(modid = SoundDeviceOptions.MODID)
public class SoundDeviceOptions {
    public static final String MODID = "sounddeviceoptions";
    public static final String NAME = "Sound Device Options";
    public static final String FINGERPRINT = "7904c4e13947c8a616c5f39b26bdeba796500722";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public SoundDeviceOptions() {
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> ClientHooks.register());
        DistExecutor.runWhenOn(Dist.DEDICATED_SERVER, () -> () -> LOGGER.info("Running on dedicated server - Not doing anything"));
    }
}
