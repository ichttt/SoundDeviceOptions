function initializeCoreMod() {
    return {
        'coreModName': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.client.audio.SoundSystem',
                'methodName': 'func_216406_f',
                'methodDesc': '()J'
            },
            'transformer': function(method) {
                var success = false;

                var ASMAPI = Java.type('net.minecraftforge.coremod.api.ASMAPI');
                var newList = ASMAPI.listOf(ASMAPI.buildMethodCall("ichttt/mods/sounddeviceoptions/client/ASMHooks", "setupSound", "(Ljava/nio/ByteBuffer;)J", ASMAPI.MethodType.STATIC));
                if (!ASMAPI.insertInsnList(method, ASMAPI.MethodType.STATIC, "org/lwjgl/openal/ALC10", "alcOpenDevice", "(Ljava/nio/ByteBuffer;)J", newList, ASMAPI.InsertMode.REMOVE_ORIGINAL)) {
                    throw "MethodInsnNode not found!";
                }

                return method;
            }
        }
    }
}
