package Session;

//Read Readme.md

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ProfileKey;
import net.lenni0451.mcstructs.text.serializer.TextComponentSerializer;
import net.lenni0451.mcstructs.text.utils.JsonUtils;
import multiconnectedimpl.DecoratableMessage;
import multiconnectedimpl.MessageMetadata;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.util.UUID;

public class ChatSession1_19_0 extends ChatSession {

    public ChatSession1_19_0(UserConnection user, UUID uuid, PrivateKey privateKey, ProfileKey profileKey) {
        super(user, uuid, privateKey, profileKey);
    }

    public byte[] signChatMessage(final MessageMetadata metadata, final DecoratableMessage content) throws SignatureException {
        return this.sign(signer -> {
            final byte[] data = new byte[32];
            final ByteBuffer buffer = ByteBuffer.wrap(data).order(ByteOrder.BIG_ENDIAN);
            buffer.putLong(metadata.getSalt());
            buffer.putLong(metadata.getSender().getMostSignificantBits()).putLong(metadata.getSender().getLeastSignificantBits());
            buffer.putLong(metadata.getTimestamp().getEpochSecond());
            signer.accept(data);
            signer.accept(JsonUtils.toSortedString(TextComponentSerializer.V1_18.serializeJson(content.getDecorated()), null).getBytes(StandardCharsets.UTF_8));
        });
    }

}