package Session;

import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.UUID;
import java.util.function.Consumer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;

import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ProfileKey;
import util.DataConsumer;


public class ChatSession extends StoredObject {

    private final UUID uuid;
    private final PrivateKey privateKey;
    private final ProfileKey profileKey;
    private final Signature signer;

    public ChatSession(final UserConnection user, final UUID uuid, final PrivateKey privateKey, final ProfileKey profileKey) {
        super(user);

        this.uuid = uuid;
        this.privateKey = privateKey;
        this.profileKey = profileKey;

        try {
            this.signer = Signature.getInstance("SHA256withRSA");
            this.signer.initSign(this.privateKey);
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