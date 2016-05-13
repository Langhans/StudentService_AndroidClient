package timlanghans.yrgo.se.studentsclient.clientCallback;

/**
 * Created by timlanghans on 16-04-26 / 17.
 */
public interface IClientCallbackBus {

    public void registerListener(IClientCallbackListener listener);

    public boolean unregisterListener(IClientCallbackListener listener);

    public void broadcastEvent(CallbackEvent event);







}
