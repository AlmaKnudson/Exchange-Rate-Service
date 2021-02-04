package com.example.demo.xml;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * XmlFileDownloader used to download the daily pricing once every hour.
 * If the daily pricing does not change throughout the day then we can change
 * this to run each day after the pricing is scheduled to be updated on ECB.
 *
 * Another thing to consider is to replace this by subscribing to the ECB rss feed if that publishes currency conversions.
 */
public class XmlFileDownloader implements Runnable {
    //TODO: Consider more meaningful timeout values
    private static final int CONNECTION_TIMEOUT_MS = 3000;
    private static final int READ_TIMEOUT_MS = 3000;
    private static final String FILE_PATH = "exchange-rates.xml";
    private static final String ECB_DAILY_EXCHANGE_RATE_URL = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";

    private static final Logger logger = LoggerFactory.getLogger(XmlFileDownloader.class);

    private final List<DownloaderChannel> channels = new ArrayList<>();

    @Override
    public void run() {
        downloadFile();
    }

    private void downloadFile() {
        File file = new File(FILE_PATH);
        try {
            FileUtils.copyURLToFile(
                    new URL(ECB_DAILY_EXCHANGE_RATE_URL),
                    file,
                    CONNECTION_TIMEOUT_MS,
                    READ_TIMEOUT_MS);
        } catch (IOException exception) {
            logger.error("An error occurred while downloading the xml file.", exception);
        } finally {
            setDownloadFinished(file);
        }
    }

    public void addObserver(DownloaderChannel channel) {
        this.channels.add(channel);
    }

    public void setDownloadFinished(File file) {
        logger.info("Finished downloading exchange rate file.");
        for (DownloaderChannel channel : this.channels) {
            channel.downloadFinished(file);
        }
    }
}
