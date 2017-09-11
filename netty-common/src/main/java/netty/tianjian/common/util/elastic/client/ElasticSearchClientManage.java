package netty.tianjian.common.util.elastic.client;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/7/10
 * @description
 */
public class ElasticSearchClientManage {
    private String hostName;
    private int port;
    private static TransportClient client;
    private static ElasticSearchClientManage elasticSearchClientManage;

    private ElasticSearchClientManage(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
    }

    private ElasticSearchClientManage() {
        new ElasticSearchClientManage("127.0.0.1", 9300);
    }

    public static ElasticSearchClientManage getInstanceEsClientManage() {
        if(elasticSearchClientManage == null) {
            elasticSearchClientManage = new ElasticSearchClientManage("127.0.0.1", 9300);
        }
        return elasticSearchClientManage;
    }

    public static ElasticSearchClientManage getInstanceEsClientManage(String hostName, int port) {
        if(elasticSearchClientManage == null) {
            elasticSearchClientManage = new ElasticSearchClientManage(hostName, port);
        }
        return elasticSearchClientManage;
    }


    public TransportClient getESClient() throws UnknownHostException {
        if(client != null) {
            return client;
        }
        client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostName), port));
        return client;
    }

    public static void closeClient() {
        if(client != null) {
            client.close();
            client = null;
        }

        if(elasticSearchClientManage != null) {
            elasticSearchClientManage = null;
        }
    }

}
