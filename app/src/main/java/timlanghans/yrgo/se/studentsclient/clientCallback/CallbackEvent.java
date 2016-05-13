package timlanghans.yrgo.se.studentsclient.clientCallback;

/**
 * Created by timlanghans on 16-04-26 / 17.
 */
public class CallbackEvent {

    private final EventTag tag;
    private final Object value;


    public CallbackEvent(EventTag tag , Object value){
        this.tag = tag;
        this.value = value;
    }
    public Object getValue() {
        return value;
    }

    public EventTag getTag() {
        return tag;
    }

}
