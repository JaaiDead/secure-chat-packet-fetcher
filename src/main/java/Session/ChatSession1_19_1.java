
package Session;

//Read Readme.md

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.PlayerMessageSignature;
import com.viaversion.viaversion.api.minecraft.ProfileKey;
import multiconnectedimpl.DecoratableMessage;
import multiconnectedimpl.MessageMetadata;
import multiconnectedimpl.chain.v1_19_1.MessageBody;
import multiconnectedimpl.chain.v1_19_1.MessageHeader;

import java.security.PrivateKey;
import java.security.SignatureException;
import java.util.UUID;

public class ChatSession1_19_1 extends ChatSession {

    private byte[] precedingSignature;

    public ChatSession1_19_1(UserConnection user, UUID uuid, PrivateKey privateKey, ProfileKey profileKey) {
        super(user, uuid, privateKey, profileKey);
    }

    public byte[] signChatMessage(final MessageMetadata metadata, final DecoratableMessage content, final PlayerMessageSignature[] lastSeenMessages) throws SignatureException {
        final byte[] signature = this.sign(signer -> {
            final MessageHeader messageHeader = new MessageHeader(this.precedingSignature, metadata.getSender());
            final MessageBody messageBody = new MessageBody(content, metadata.getTimestamp(), metadata.getSalt(), lastSeenMessages);
            messageHeader.update(signer);
            messageBody.update(signer);
        });
        this.precedingSignature = signature;
        return signature;
    }

}