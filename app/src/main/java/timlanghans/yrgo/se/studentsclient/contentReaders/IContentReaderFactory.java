package timlanghans.yrgo.se.studentsclient.contentReaders;

import timlanghans.yrgo.se.studentsclient.commons.Format;

/**
 * Created by timlanghans on 16-04-22 / 16.
 */
public interface IContentReaderFactory {

    /**
     * Uses the Preference setting for the content format to return a matching
     * IContentReader object
     * @return new IContentReader object
     */
    public IContentReader getReader();




}
