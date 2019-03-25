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
import ichttt.mods.moresoundconfig.SoundDevices;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.GuiScrollingList;

import java.io.IOException;
import java.util.List;

public class GuiChooseOutput extends GuiScreen {
    private final GuiScreen parent;
    private final String initalDevice;
    private DeviceList list;
    private int startIndex;

    public GuiChooseOutput(GuiScreen parent) {
        this.parent = parent;
        this.initalDevice = MSCConfig.friendlyActiveSoundDevice();
    }

    @Override
    public void initGui() {
        this.list = new DeviceList(SoundDevices.validDevices, MSCConfig.getActiveSoundDevice());
        this.startIndex = this.list.selectedIndex;
        this.buttonList.add(new GuiButton(1, width / 2 - 100, height / 6 + 168, I18n.format("gui.done")));
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.list.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(mc.fontRenderer, I18n.format("msc.newdevice"), this.width / 2, 6, 0xFFFFFF);
        this.drawCenteredString(mc.fontRenderer, I18n.format("msc.activedevice", TextFormatting.UNDERLINE + this.initalDevice + TextFormatting.RESET), this.width / 2, 18, 0xFFFFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 1) {
            this.mc.displayGuiScreen(this.parent);
        }
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        if (this.list.selectedIndex != startIndex) {
            String newValue = this.list.devices.get(this.list.selectedIndex);
            if (SoundDevices.validateActiveOutput(newValue)) {
                SoundDevices.updateOutput(newValue);
            }
            mc.getSoundHandler().sndManager.reloadSoundSystem();
        }
    }

    private class DeviceList extends GuiScrollingList {
        private final List<String> devices;
        int selectedIndex;

        public DeviceList(List<String> devices, String current) {
            super(GuiChooseOutput.this.mc,
                    GuiChooseOutput.this.width-10,
                    GuiChooseOutput.this.height - 30,
                    30, GuiChooseOutput.this.height-50,
                    5,
                    12,
                    GuiChooseOutput.this.width,
                    GuiChooseOutput.this.height);
            this.devices = devices;
            this.selectedIndex = current == null ? 0 : devices.indexOf(current);
        }

        @Override
        protected int getSize() {
            return this.devices.size();
        }

        @Override
        protected void elementClicked(int index, boolean doubleClick) {
            this.selectedIndex = index;
            if (doubleClick) {
                GuiChooseOutput.this.mc.displayGuiScreen(GuiChooseOutput.this.parent);
            }
        }

        @Override
        protected boolean isSelected(int index) {
            return this.selectedIndex == index;
        }

        @Override
        protected void drawBackground() {
            drawDefaultBackground();
        }

        @Override
        protected void drawSlot(int slotIdx, int entryRight, int slotTop, int slotBuffer, Tessellator tess) {
            String device = devices.get(slotIdx);
            mc.fontRenderer.drawString(device, left + 1, slotTop, 0xFFFFFF);
        }

        @Override
        protected int getContentHeight() {
            return this.getSize() * 10;
        }


    }
}
