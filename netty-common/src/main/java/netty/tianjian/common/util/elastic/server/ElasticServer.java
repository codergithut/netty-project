package netty.tianjian.common.util.elastic.server;

import netty.tianjian.common.util.elastic.client.ElasticSearchClientManage;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.rest.RestStatus;
import org.junit.Before;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/11
 * @description
 */
public class ElasticServer {

    private static TransportClient client;

    private static IndexResponse response;

    private static void initClient() throws UnknownHostException {
        ElasticSearchClientManage elasticSearchClientManage = ElasticSearchClientManage.getInstanceEsClientManage();
        client = elasticSearchClientManage.getESClient();
    }

    public static String saveDataToEs(Map<String,Object> json, String index, String type) throws UnknownHostException {
        initClient();
        response = client.prepareIndex(index, type)
                .setSource(json)
                .get();

        return response.getResult().getLowercase();

    }

    public static DeleteResponse deleteDataToEs(String index, String type, String id) {
        DeleteResponse response = client.prepareDelete(index, type, id).get();
        return response;

    }

}
