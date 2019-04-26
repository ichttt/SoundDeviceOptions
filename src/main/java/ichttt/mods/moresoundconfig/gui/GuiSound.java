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

package ichttt.mods.moresoundconfig.gui;

import ichttt.mods.moresoundconfig.SDOConfig;
import ichttt.mods.moresoundconfig.SoundDevices;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiErrorScreen;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenOptionsSounds;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.resources.I18n;

public class GuiSound extends GuiScreenOptionsSounds {
    public GuiSound(GuiScreen parentIn, GameSettings settingsIn) {
        super(parentIn, settingsIn);
    }

    @Override
    public void initGui() {
        super.initGui();
//        SoundDevices.reloadDeviceList();
        GuiButton fromBList = this.buttons.remove(this.buttons.size() - 1);
        IGuiEventListener fromCList = this.children.remove(this.children.size() - 1);
        if (fromBList != fromCList) {
            throw new RuntimeException("Removed wrong button? From button list= " + fromBList + " id " + fromBList.id + " from children list= " + fromCList);
        }
        addButton(new GuiButton(300, this.width / 2 - 100, this.height / 6 + 156, mc.fontRenderer.trimStringToWidth(I18n.format("sounddeviceoptions.output", SDOConfig.friendlyActiveSoundDevice()), 200)) {
            @Override
            public void onClick(double mouseX, double mouseY) {
                SoundDevices.reloadDeviceList();
                if (SoundDevices.validDevices.isEmpty()) {
                    mc.displayGuiScreen(new GuiErrorScreen("Failed to read sound devices", "Your audio driver might not support this feature."));
                } else {
                    mc.displayGuiScreen(new GuiChooseOutput(GuiSound.this));
                }
            }
        });
        addButton(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 180, I18n.format("gui.done")) {
            @Override
            public void onClick(double mouseX, double mouseY) {
                GuiSound.this.mc.gameSettings.saveOptions(); //Same as GuiScreenOptionsSound this
                GuiSound.this.mc.displayGuiScreen(GuiSound.this.parent);
            }
        });
    }
}
