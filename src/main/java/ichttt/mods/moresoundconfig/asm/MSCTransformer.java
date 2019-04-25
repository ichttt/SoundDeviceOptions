///*
// * MoreSoundConfig
// * Copyright (C) 2018
// *
// * This program is free software: you can redistribute it and/or modify
// * it under the terms of the GNU General Public License as published by
// * the Free Software Foundation, either version 3 of the License, or
// * (at your option) any later version.
// *
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU General Public License for more details.
// *
// * You should have received a copy of the GNU General Public License
// * along with this program.  If not, see <http://www.gnu.org/licenses/>.
// */
//
//package ichttt.mods.moresoundconfig.asm;
//
//import ichttt.mods.moresoundconfig.asm.framework.ASMUtils;
//import ichttt.mods.moresoundconfig.asm.framework.AbstractMethodTransformer;
//import ichttt.mods.moresoundconfig.asm.framework.PatchFailedException;
//import org.objectweb.asm.ClassWriter;
//import org.objectweb.asm.tree.AbstractInsnNode;
//import org.objectweb.asm.tree.InsnList;
//import org.objectweb.asm.tree.MethodInsnNode;
//import org.objectweb.asm.tree.MethodNode;
//import org.objectweb.asm.tree.VarInsnNode;
//
//import java.util.ListIterator;
//
//public class MSCTransformer extends AbstractMethodTransformer {
//    public MSCTransformer() {
//        super("paulscode.sound.libraries.LibraryLWJGLOpenAL", "init", "", "()V", ClassWriter.COMPUTE_MAXS);
//    }
//
//    @Override
//    protected boolean patchMethod(MethodNode methodNode) throws PatchFailedException {
//        ListIterator<AbstractInsnNode> nodeIterator = methodNode.instructions.iterator();
//        while (nodeIterator.hasNext()) {
//            AbstractInsnNode node = nodeIterator.next();
//            if (ASMUtils.matchMethodNode(INVOKESTATIC, "org/lwjgl/openal/AL", "create", "", "()V", node)) {
//                MSCCoreMod.LOGGER.debug("Found needle INVOKESTATIC: " + ASMUtils.nodeToString(node) + ", patching");
//                methodNode.instructions.insertBefore(node, new MethodInsnNode(INVOKESTATIC, "ichttt/mods/moresoundconfig/asm/ASMHooks", "setupSound", "()V", false));
//                nodeIterator.remove();
//                return true;
//            }
//        }
//        MSCCoreMod.LOGGER.fatal("Could not find needle in haystack!");
//        return false;
//    }
//}
