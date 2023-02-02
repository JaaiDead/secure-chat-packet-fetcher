package multiconnectedimpl;

//Read Readme.md
import java.time.Instant;
import java.util.UUID;

public class MessageMetadata {

    private final UUID sender;
    private final Instant timestamp;
    private final long salt;

    public MessageMetadata(final UUID sender, final Instant timestamp, final long salt) {
        this.sender = sender;
        this.timestamp = timestamp;
        this.salt = salt;
    }

    public MessageMetadata(final UUID sender, final long timestamp, final long salt) {
        this(sender, Instant.ofEpochMilli(timestamp), salt);
    }

    public UUID getSender() {
        return this.sender;
    }

    public Instant getTimestamp() {
        return this.timestamp;
    }

    public long getSalt() {
        return this.salt;
    }

}