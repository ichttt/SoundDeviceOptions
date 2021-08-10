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

package ichttt.mods.sounddeviceoptions.client.gui;

import ichttt.mods.sounddeviceoptions.SDOConfig;
import ichttt.mods.sounddeviceoptions.client.SoundDevices;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.ErrorScreen;
import net.minecraft.client.gui.screens.SoundOptionsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.TranslatableComponent;

public class GuiSound extends SoundOptionsScreen {
    public GuiSound(Screen parentIn, Options settingsIn) {
        super(parentIn, settingsIn);
    }

    @Override
    public void init() {
        super.init();
//        SoundDevices.reloadDeviceList();
        GuiEventListener fromCList = this.children().get(this.children().size() - 1);
        if (!(fromCList instanceof Button)) {
            throw new RuntimeException("Uh oh, about to remove wrong thing! " + fromCList);
        }
        this.removeWidget(fromCList);
        addRenderableWidget(new Button(this.width / 2 - 100, this.height / 6 + 156, 200, 20, new TranslatableComponent("sounddeviceoptions.output", SDOConfig.friendlyActiveSoundDevice()), new Button.OnPress() {
            @Override
            public void onPress(Button b) {
                SoundDevices.reloadDeviceList();
                if (SoundDevices.VALID_DEVICES.isEmpty()) {
                    minecraft.setScreen(new ErrorScreen(new TranslatableComponent("sounddeviceoptions.readFailed"), new TranslatableComponent("sounddeviceoptions.readFailedHint")));
                } else {
                    minecraft.setScreen(new GuiChooseOutput(GuiSound.this));
                }
            }
        }));
        addRenderableWidget(new Button(this.width / 2 - 100, this.height / 6 + 180, 200, 20, new TranslatableComponent("gui.done"), new Button.OnPress() {
            @Override
            public void onPress(Button button) {
                GuiSound.this.minecraft.options.save(); //Same as GuiScreenOptionsSound this
                GuiSound.this.minecraft.setScreen(GuiSound.this.lastScreen);
            }
        }));
    }
}