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

import ichttt.mods.sounddeviceoptions.client.ClientHooks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fmllegacy.network.FMLNetworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(SoundDeviceOptions.MODID)
public class SoundDeviceOptions {
    public static final String MODID = "sounddeviceoptions";
    public static final String NAME = "Sound Device Options";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public SoundDeviceOptions() {
        //noinspection Convert2MethodRef - this is to avoid classloading
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientHooks.register());
        DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> LOGGER.debug("Running on dedicated server - Not doing anything"));
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, SDOConfig.clientSpec);

        //Make sure the mod being absent on the other network side does not cause the client to display the server as incompatible
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
    }
}