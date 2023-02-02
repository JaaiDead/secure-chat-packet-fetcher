package multiconnectedimpl;

import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.components.StringComponent;

public class DecoratableMessage {

    private final String plain;
    private final ATextComponent decorated;

    public DecoratableMessage(final String plain) {
        this(plain, new StringComponent(plain));
    }

    public DecoratableMessage(final String plain, final ATextComponent decorated) {
        this.plain = plain;
        this.decorated = decorated;
    }

    public boolean isDecorated() {
        return !this.decorated.equals(new StringComponent(this.plain));
    }

    public String getPlain() {
        return this.plain;
    }

    public ATextComponent getDecorated() {
        return this.decorated;
    }

}
