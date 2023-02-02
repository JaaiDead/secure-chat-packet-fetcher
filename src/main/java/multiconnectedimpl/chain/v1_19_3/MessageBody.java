package multiconnectedimpl.chain.v1_19_3;

//Read Readme.md

import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import com.viaversion.viaversion.api.minecraft.PlayerMessageSignature;
import util.DataConsumer;

import java.nio.charset.StandardCharsets;
import java.time.Instant;

public class MessageBody {

    private final String content;
    private final Instant timestamp;
    private final long salt;
    private final PlayerMessageSignature[] lastSeenMessages;

    public MessageBody(final String content, final Instant timestamp, final long salt, final PlayerMessageSignature[] lastSeenMessages) {
        this.content = content;
        this.timestamp = timestamp;
        this.salt = salt;
        this.lastSeenMessages = lastSeenMessages;
    }

    public void update(final DataConsumer dataConsumer) {
        dataConsumer.accept(Longs.toByteArray(this.salt));
        dataConsumer.accept(Longs.toByteArray(this.timestamp.getEpochSecond()));
        final byte[] contentData = this.content.getBytes(StandardCharsets.UTF_8);
        dataConsumer.accept(Ints.toByteArray(contentData.length));
        dataConsumer.accept(contentData);

        dataConsumer.accept(Ints.toByteArray(this.lastSeenMessages.length));
        for (PlayerMessageSignature messageSignatureData : this.lastSeenMessages) {
            dataConsumer.accept(messageSignatureData.signatureBytes());
        }
    }

}