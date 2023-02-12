package BootEXE;

import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ProfileKey;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import util.DataConsumer;

import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.UUID;
import java.util.function.Consumer;

      public class KickStarter implements ModInitializer {
        // This logger is used to write text to the console and the log file.
        // It is considered best practice to use your mod id as the logger's name.
        // That way, it's clear which mod wrote info, warnings, and errors.
        public static final Logger LOGGER = LoggerFactory.getLogger("scpf");

        @Override
        public void onInitialize() {
            class ChatSession extends StoredObject {

                private final UUID uuid;
                private final ProfileKey profileKey;
                private final Signature signer;


                public ChatSession(final UserConnection user, final UUID uuid, final PrivateKey privateKey, final ProfileKey profileKey) {
                    super(user);

                    this.uuid = uuid;
                    this.profileKey = profileKey;

                    try {
                        this.signer = Signature.getInstance("SHA256withRSA");
                        this.signer.initSign(privateKey);
                    } catch (Throwable e) {
                        throw new RuntimeException("Failed to initialize signature", e);
                    }
                }

                public UUID getUuid() {
                    return this.uuid;
                }

                public ProfileKey getProfileKey() {
                    return this.profileKey;
                }

                public byte[] sign(final Consumer<DataConsumer> dataConsumer) throws SignatureException {
                    dataConsumer.accept(bytes -> {
                        try {
                            this.signer.update(bytes);
                        } catch (SignatureException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    return this.signer.sign();
                }

            }

        }
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
    }


