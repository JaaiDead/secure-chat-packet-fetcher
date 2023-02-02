package multiconnectedimpl.chain.v1_19_1;

//Read Readme.md

import util.DataConsumer;

import java.util.UUID;

public class MessageHeader {

    private final byte[] precedingSignature;
    private final UUID sender;

    public MessageHeader(final byte[] precedingSignature, final UUID sender) {
        this.precedingSignature = precedingSignature;
        this.sender = sender;
    }

    public void update(final DataConsumer dataConsumer) {
        if (this.precedingSignature != null) {
            dataConsumer.accept(this.precedingSignature);
        }

        dataConsumer.accept(this.sender);
    }

}
