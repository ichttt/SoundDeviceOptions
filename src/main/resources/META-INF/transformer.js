function initializeCoreMod() {
    return {
        'coreModName': {
            'target': {
                'type': 'CLASS',
                'name': 'net.minecraft.client.audio.LibraryLWJGL3'
            },
            'transformer': function(classNode) {
                var methods = classNode.methods;

                for (var i in methods) {
                    var method = methods[i];
                    var methodName = method.name;

                    var deobfNameEquals = "init".equals(methodName);

                    if (!deobfNameEquals) {
                        continue;
                    }
                    print("Found method init()V, patching...");

                    var Opcodes = Java.type('org.objectweb.asm.Opcodes');
                    var ASMAPI = Java.type('net.minecraftforge.coremod.api.ASMAPI');
                    var arrayLength = method.instructions.size();
                    for (var i = 0; i < arrayLength; ++i) {
                        var instruction = method.instructions.get(i);
                        if (instruction.getOpcode() === Opcodes.INVOKESTATIC && instruction.owner === "org/lwjgl/openal/ALC10" && instruction.name === "alcOpenDevice") {
                            method.instructions.insertBefore(instruction, ASMAPI.buildMethodCall("ichttt/mods/moresoundconfig/asm/ASMHooks", "setupSound", "(Ljava/nio/ByteBuffer;)J", ASMAPI.MethodType.STATIC))
                            method.instructions.remove(instruction);
                            print("Patched!");
                            break;
                        }
                    }
                    break;
                }

                return classNode;
            }
        }
    }
}
