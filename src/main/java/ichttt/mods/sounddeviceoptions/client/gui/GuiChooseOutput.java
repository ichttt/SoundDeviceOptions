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

import com.mojang.blaze3d.vertex.PoseStack;
import ichttt.mods.sounddeviceoptions.SDOConfig;
import ichttt.mods.sounddeviceoptions.client.SoundDevices;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.List;

public class GuiChooseOutput extends Screen {
    private final Screen parent;
    private final String initalDevice;
    private DeviceList list;
    private int startIndex;

    public GuiChooseOutput(Screen parent) {
        super(new TranslatableComponent("sounddeviceoptions.chooseoutput"));
        this.parent = parent;
        this.initalDevice = SDOConfig.friendlyActiveSoundDevice();
    }

    @Override
    public void init() {
        this.list = new DeviceList(SoundDevices.VALID_DEVICES, SDOConfig.getActiveSoundDevice());
        this.list.setLeftPos(5);
        this.addWidget(this.list);
        this.startIndex = this.list.selectedIndex;
        this.addRenderableWidget(new Button(width / 2 - 100, height - 30, 200, 20, new TranslatableComponent("gui.done"), button -> GuiChooseOutput.this.minecraft.setScreen(GuiChooseOutput.this.parent)));
        super.init();
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        this.renderDirtBackground(0);
        this.list.render(stack, mouseX, mouseY, partialTicks);
        GuiComponent.drawCenteredString(stack, minecraft.font, new TranslatableComponent("sounddeviceoptions.newdevice"), this.width / 2, 6, 0xFFFFFF);
        GuiComponent.drawCenteredString(stack, minecraft.font, new TranslatableComponent("sounddeviceoptions.activedevice", ChatFormatting.UNDERLINE + this.initalDevice + ChatFormatting.RESET), this.width / 2, 18, 0xFFFFFF);
        super.render(stack, mouseX, mouseY, partialTicks);
    }

    @Override
    public void removed() {
        if (this.list.selectedIndex != startIndex) {
            String newValue = this.list.selectedIndex == 0 ? null : this.list.devices.get(this.list.selectedIndex - 1);
            if (SoundDevices.validateActiveOutput(newValue)) {
                SoundDevices.updateOutput(newValue);
            }
            minecraft.getSoundManager().soundEngine.reload();
        }
        super.removed();
    }

    private class DeviceList extends ObjectSelectionList<DeviceList.Entry> {
        private final List<String> devices;
        int selectedIndex;

        public DeviceList(List<String> devices, String current) {
            super(GuiChooseOutput.this.minecraft, GuiChooseOutput.this.width - 10, GuiChooseOutput.this.height - 30, 30, GuiChooseOutput.this.height - 50, 12);
            int index = 0;
            Entry defaultOption = new Entry("<" + I18n.get("sounddeviceoptions.default") + ">", index);
            this.addEntry(defaultOption);
            this.setSelected(defaultOption);
            index++;
            this.selectedIndex = current == null ? 0 : (devices.indexOf(current) + 1);
            for (String s : devices) {
                Entry entry = new Entry(s, index);
                this.addEntry(entry);
                if (index == selectedIndex) setSelected(entry);
                index++;
            }
            this.devices = devices;
        }

        @Override
        protected int getScrollbarPosition() {
            return this.width - 6;
        }

        @Override
        public int getRowWidth() {
            return width;
        }

        private class Entry extends ObjectSelectionList.Entry<Entry> {
            private final String device;
            private final int index;

            public Entry(String device, int index) {
                this.device = SDOConfig.formatDevice(device);
                this.index = index;
            }

            @Override
            public void render(PoseStack stack, int entryIdx, int top, int left, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean p_194999_5_, float partialTicks) {
                GuiChooseOutput.this.minecraft.font.draw(stack, device, left + 1, top, 0xFFFFFF);
            }

            @Override
            public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
                DeviceList.this.selectedIndex = this.index;
                DeviceList.this.setSelected(this);
                return true;
            }


            @Override
            public Component getNarration() {
                return TextComponent.EMPTY; //TODO
            }
        }
    }
}