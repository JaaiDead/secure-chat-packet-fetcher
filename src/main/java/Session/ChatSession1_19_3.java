package Session;

//Read readme.md
import com.google.common.primitives.Ints;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.PlayerMessageSignature;
import com.viaversion.viaversion.api.minecraft.ProfileKey;
import multiconnectedimpl.MessageMetadata;
import multiconnectedimpl.chain.v1_19_3.MessageBody;
import multiconnectedimpl.chain.v1_19_3.MessageLink;

import java.security.PrivateKey;
import java.security.SignatureException;
import java.util.UUID;

public class ChatSession1_19_3 extends ChatSession {

    private final UUID sessionId = UUID.randomUUID();
    private MessageLink link;

    public ChatSession1_19_3(UserConnection user, UUID uuid, PrivateKey privateKey, ProfileKey profileKey) {
        super(user, uuid, privateKey, profileKey);

        this.link = new MessageLink(uuid, this.sessionId);
    }

    public byte[] signChatMessage(final MessageMetadata metadata, final String content, final PlayerMessageSignature[] lastSeenMessages) throws SignatureException {
        return this.sign(signer -> {
            final MessageLink messageLink = this.nextLink();
            final MessageBody messageBody = new MessageBody(content, metadata.getTimestamp(), metadata.getSalt(), lastSeenMessages);
            signer.accept(Ints.toByteArray(1));
            messageLink.update(signer);
            messageBody.update(signer);
        });
    }

    private MessageLink nextLink() {
        final MessageLink messageLink = this.link;
        if (messageLink != null) {
            this.link = messageLink.next();
        }

        return messageLink;
    }

    public UUID getSessionId() {
        return this.sessionId;
    }

}
