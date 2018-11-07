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

import ichttt.mods.moresoundconfig.MSCConfig;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenOptionsSounds;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

import java.io.IOException;

public class GuiSound extends GuiScreenOptionsSounds {
    public GuiSound(GuiScreen parentIn, GameSettings settingsIn) {
        super(parentIn, settingsIn);
    }

    @Override
    public void initGui() {
        super.initGui();
//        SoundDevices.reloadDeviceList();
        this.buttonList.remove(this.buttonList.size() - 1);
        this.buttonList.add(new GuiButton(300, this.width / 2 - 100, this.height / 6 + 156, mc.fontRenderer.trimStringToWidth(I18n.format("msc.output", MSCConfig.friendlyActiveSoundDevice()), 200)));
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 180, I18n.format("gui.done")));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 300) {
            mc.displayGuiScreen(new GuiChooseOutput(this));
        } else {
            super.actionPerformed(button);
        }
    }
}
