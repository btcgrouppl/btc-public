package pl.btcgrouppl.btc.backend.commons.integration.impl;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 01.06.15.
 * <p>
 *     Integration message. Wrapper for all sort of objects (commands, events), that can be transported
 *     via spring integration
 * </p>
 */
public class IntegrationMessage {

    private String messageClass;
    private Object body;

    public IntegrationMessage(String messageClass, Object body) {
        this.messageClass = messageClass;
        this.body = body;
    }

    public String getMessageClass() {
        return messageClass;
    }

    public Object getBody() {
        return body;
    }

}
