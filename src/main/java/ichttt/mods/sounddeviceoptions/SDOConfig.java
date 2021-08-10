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

package ichttt.mods.sounddeviceoptions;

import net.minecraft.client.resources.language.I18n;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SDOConfig {
    static final ForgeConfigSpec clientSpec;
    public static final SDOConfig CLIENT;

    static {
        final Pair<SDOConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(SDOConfig::new);
        clientSpec = specPair.getRight();
        CLIENT = specPair.getLeft();
    }

    private SDOConfig(ForgeConfigSpec.Builder builder) {
        this.activeSoundDevice = builder.comment("The active sound device").define("activeSoundDevice", "");
    }

    public final ForgeConfigSpec.ConfigValue<String> activeSoundDevice;

    @Nullable
    public static String getActiveSoundDevice() {
        String device = CLIENT.activeSoundDevice.get();
        if (device == null)
            return null;
        if (device.isEmpty())
            return null;
        return device;
    }

    @Nonnull
    public static String friendlyActiveSoundDevice() {
        return formatDevice(getActiveSoundDevice());
    }

    public static String formatDevice(String device) {
        if (device == null)
            return I18n.get("sounddeviceoptions.default");
        else if (device.startsWith("OpenAL Soft on "))
            return device.substring("OpenAL Soft on ".length());
        return device;
    }
}