function initializeCoreMod() {
    return {
        'SoundSystem Transformer': {
            'target': {
                'type': 'CLASS',
                'name': 'net.minecraft.client.audio.SoundSystem'
            },
            'transformer': function(classNode) {
                var methods = classNode.methods;
                var success = false;

                for (var i in methods) {
                    var method = methods[i];
                    var methodName = method.name;

                    var ASMAPI = Java.type('net.minecraftforge.coremod.api.ASMAPI');
                    var deobfNameEquals = ASMAPI.mapMethod("func_216406_f").equals(methodName);

                    if (!deobfNameEquals) {
                        continue;
                    }
                    ASMAPI.log("INFO", "Found method init()V, patching...");

                    var Opcodes = Java.type('org.objectweb.asm.Opcodes');
                    var arrayLength = method.instructions.size();
                    for (var i = 0; i < arrayLength; ++i) {
                        var instruction = method.instructions.get(i);
                        if (instruction.getOpcode() === Opcodes.INVOKESTATIC && instruction.owner === "org/lwjgl/openal/ALC10" && instruction.name === "alcOpenDevice") {
                            method.instructions.insertBefore(instruction, ASMAPI.buildMethodCall("ichttt/mods/sounddeviceoptions/client/ASMHooks", "setupSound", "(Ljava/nio/ByteBuffer;)J", ASMAPI.MethodType.STATIC))
                            method.instructions.remove(instruction);
                            ASMAPI.log("DEBUG", "Patched!");
                            success = true;
                            break;
                        }
                    }
                    break;
                }
                if (success === false) {
                    ASMAPI.log("ERROR", "Failed to transform!");
                    throw "Failed to transform!"
                }

                return classNode;
            }
        }
    }
}
