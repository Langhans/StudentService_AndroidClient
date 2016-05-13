package timlanghans.yrgo.se.studentsclient.contentReaders;

/**
 * Created by timlanghans on 16-04-25 / 17.
 */
public class ContentReaderFactory implements IContentReaderFactory{

    // TODO fetch requested format from Context preferences
    // as for now: JSON only!

    private static final IContentReaderFactory factory = new ContentReaderFactory();

    private ContentReaderFactory(){}

    public static IContentReaderFactory getFactory(){
        return factory;
    }


    @Override
    public IContentReader getReader() {
        return new JsonContentReader();
    }
}
