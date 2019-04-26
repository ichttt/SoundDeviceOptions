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
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public class GuiChooseOutput extends GuiScreen {
    private final GuiScreen parent;
    private final String initalDevice;
    private DeviceList list;
    private int startIndex;

    public GuiChooseOutput(GuiScreen parent) {
        this.parent = parent;
        this.initalDevice = SDOConfig.friendlyActiveSoundDevice();
    }

    @Override
    public void initGui() {
        this.list = new DeviceList(SoundDevices.validDevices, SDOConfig.getActiveSoundDevice());
        this.list.setSlotXBoundsFromLeft(5);
        this.children.add(this.list);
        this.startIndex = this.list.selectedIndex;
        addButton(new GuiButton(1, width / 2 - 100, height / 6 + 168, I18n.format("gui.done")) {
            @Override
            public void onClick(double mouseX, double mouseY) {
                GuiChooseOutput.this.mc.displayGuiScreen(GuiChooseOutput.this.parent);
            }
        });
        super.initGui();
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.list.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(mc.fontRenderer, I18n.format("sounddeviceoptions.newdevice"), this.width / 2, 6, 0xFFFFFF);
        this.drawCenteredString(mc.fontRenderer, I18n.format("sounddeviceoptions.activedevice", TextFormatting.UNDERLINE + this.initalDevice + TextFormatting.RESET), this.width / 2, 18, 0xFFFFFF);
        super.render(mouseX, mouseY, partialTicks);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        if (this.list.selectedIndex != startIndex) {
            String newValue = this.list.devices.get(this.list.selectedIndex);
            if (SoundDevices.validateActiveOutput(newValue)) {
                SoundDevices.updateOutput(newValue);
            }
            mc.getSoundHandler().sndManager.reload();
        }
    }

    private class DeviceList extends GuiListExtended<DeviceList.Entry> {
        private final List<String> devices;
        int selectedIndex;

        public DeviceList(List<String> devices, String current) {
            super(GuiChooseOutput.this.mc,
                    GuiChooseOutput.this.width-10,
                    GuiChooseOutput.this.height - 30,
                    30, GuiChooseOutput.this.height-50,
                    12);
            for (String s : devices)
                this.addEntry(new Entry(s));
            this.devices = devices;
            this.selectedIndex = current == null ? 0 : devices.indexOf(current);
        }

        @Override
        protected boolean isSelected(int index) {
            return this.selectedIndex == index;
        }

        @Override
        protected int getScrollBarX() {
            return this.width;
        }


        @Override
        public int getListWidth() {
            return width;
        }

        @Override
        protected boolean mouseClicked(int index, int button, double mouseX, double mouseY) {
            return super.mouseClicked(index, button, mouseX, mouseY);
        }

        @Override
        protected int getContentHeight() {
            return this.getSize() * 10;
        }

        private class Entry extends GuiListExtended.IGuiListEntry<Entry> {
            private final String device;

            public Entry(String device) {
                this.device = SDOConfig.formatDevice(device);
            }

            @Override
            public void drawEntry(int entryWidth, int entryHeight, int mouseX, int mouseY, boolean p_194999_5_, float partialTicks) {
                GuiChooseOutput.this.mc.fontRenderer.drawString(device, this.getX() + 1, this.getY(), 0xFFFFFF);
            }

            @Override
            public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
                DeviceList.this.selectedIndex = index;
                return true;
            }


        }
    }
}
