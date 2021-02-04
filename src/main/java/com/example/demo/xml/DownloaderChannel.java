package com.example.demo.xml;

import java.io.File;

/**
 * Channel for the observer pattern.
 */
public interface DownloaderChannel {
    void downloadFinished(File file);
}
