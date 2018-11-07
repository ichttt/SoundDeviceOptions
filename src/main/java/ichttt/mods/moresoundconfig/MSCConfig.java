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

import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.Config;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Config(modid = MoreSoundConfig.MODID)
public class MSCConfig {

    public static String activeSoundDevice = "";

    public static int contextFrequency = 44100;

    public static int contextRefresh = 60;

    @Nullable
    public static String getActiveSoundDevice() {
        if (activeSoundDevice == null)
            return null;
        if (activeSoundDevice.isEmpty())
            return null;
        return activeSoundDevice;
    }

    @Nonnull
    public static String friendlyActiveSoundDevice() {
        String device = getActiveSoundDevice();
        if (device == null)
            return I18n.format("msc.default");
        return device;
    }
}
