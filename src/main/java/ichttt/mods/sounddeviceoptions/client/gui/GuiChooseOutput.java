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

package ichttt.mods.sounddeviceoptions.client.gui;

import ichttt.mods.sounddeviceoptions.SDOConfig;
import ichttt.mods.sounddeviceoptions.client.SoundDevices;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

public class GuiChooseOutput extends Screen {
    private final Screen parent;
    private final String initalDevice;
    private DeviceList list;
    private int startIndex;

    public GuiChooseOutput(Screen parent) {
        super(new TranslationTextComponent("sounddeviceoptions.chooseoutput"));
        this.parent = parent;
        this.initalDevice = SDOConfig.friendlyActiveSoundDevice();
    }

    @Override
    public void init() {
        this.list = new DeviceList(SoundDevices.VALID_DEVICES, SDOConfig.getActiveSoundDevice());
        this.list.setLeftPos(5);
        this.children.add(this.list);
        this.startIndex = this.list.selectedIndex;
        addButton(new Button(width / 2 - 100, height / 6 + 168, 200, 20, I18n.format("gui.done"), button -> GuiChooseOutput.this.minecraft.displayGuiScreen(GuiChooseOutput.this.parent)));
        super.init();
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderDirtBackground(0);
        this.list.render(mouseX, mouseY, partialTicks);
        this.drawCenteredString(minecraft.fontRenderer, I18n.format("sounddeviceoptions.newdevice"), this.width / 2, 6, 0xFFFFFF);
        this.drawCenteredString(minecraft.fontRenderer, I18n.format("sounddeviceoptions.activedevice", TextFormatting.UNDERLINE + this.initalDevice + TextFormatting.RESET), this.width / 2, 18, 0xFFFFFF);
        super.render(mouseX, mouseY, partialTicks);
    }

    @Override
    public void removed() {
        if (this.list.selectedIndex != startIndex) {
            String newValue = this.list.selectedIndex == 0 ? null : this.list.devices.get(this.list.selectedIndex - 1);
            if (SoundDevices.validateActiveOutput(newValue)) {
                SoundDevices.updateOutput(newValue);
            }
            minecraft.getSoundHandler().sndManager.reload();
        }
        super.removed();
    }

    private class DeviceList extends ExtendedList<DeviceList.Entry> {
        private final List<String> devices;
        int selectedIndex;

        public DeviceList(List<String> devices, String current) {
            super(GuiChooseOutput.this.minecraft,
                    GuiChooseOutput.this.width-10,
                    GuiChooseOutput.this.height - 30,
                    30, GuiChooseOutput.this.height-50,
                    12);
            int index = 0;
            Entry defaultOption = new Entry("<" + I18n.format("sounddeviceoptions.default") + ">", index);
            this.addEntry(defaultOption);
            this.setSelected(defaultOption);
            index++;
            this.selectedIndex = current == null ? 0 : (devices.indexOf(current) + 1);
            for (String s : devices) {
                Entry entry = new Entry(s, index);
                this.addEntry(entry);
                if (index == selectedIndex)
                    setSelected(entry);
                index++;
            }
            this.devices = devices;
        }

        @Override
        public int getRowWidth() {
            return width;
        }

        private class Entry extends ExtendedList.AbstractListEntry<Entry> {
            private final String device;
            private final int index;

            public Entry(String device, int index) {
                this.device = SDOConfig.formatDevice(device);
                this.index = index;
            }

            @Override
            public void render(int entryIdx, int top, int left, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean p_194999_5_, float partialTicks) {
                GuiChooseOutput.this.minecraft.fontRenderer.drawString(device, left + 1, top, 0xFFFFFF);
            }

            @Override
            public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_)
            {
                DeviceList.this.selectedIndex = this.index;
                DeviceList.this.setSelected(this);
                return true;
            }


        }
    }
}
