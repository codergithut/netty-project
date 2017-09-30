package test.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import netty.tianjian.common.util.elastic.client.ElasticSearchClientManage;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.admin.indices.refresh.RefreshResponse;
import org.elasticsearch.action.bulk.*;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequestBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.annotation.Scope;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotSame;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/7/10
 * @description
 */
public class ElasticSearchLogTest {

    TransportClient client;

    IndexResponse response;
    private DeleteByQueryRequestBuilder deleteByQueryRequestBuilder;

    @Before
    public void initParam() throws UnknownHostException {
        ElasticSearchClientManage elasticSearchClientManage = ElasticSearchClientManage.getInstanceEsClientManage();
        client = elasticSearchClientManage.getESClient();
    }

    @Test
    @Ignore
    public void mulitSearchTest() {
        SearchRequestBuilder srb1 = client
                .prepareSearch().setQuery(QueryBuilders.queryStringQuery("elasticsearch")).setSize(1);
        SearchRequestBuilder srb2 = client
                .prepareSearch().setQuery(QueryBuilders.matchQuery("name", "kimchy")).setSize(1);

        MultiSearchResponse sr = client.prepareMultiSearch()
                .add(srb1)
                .add(srb2)
                .get();

        long nbHits = 0;
        for (MultiSearchResponse.Item item : sr.getResponses()) {
            SearchResponse response = item.getResponse();
            nbHits += response.getHits().getTotalHits();
        }
    }

    @Test
    @Ignore
    public void UsingAggregationsTest() {
        SearchResponse sr = client.prepareSearch()
                .setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(
                        AggregationBuilders.terms("agg1").field("field")
                )
                .addAggregation(
                        AggregationBuilders.dateHistogram("agg2")
                                .field("birth")
                                .dateHistogramInterval(DateHistogramInterval.YEAR)
                )
                .get();

        // Get your facet results
        Terms agg1 = sr.getAggregations().get("agg1");
        Histogram agg2 = sr.getAggregations().get("agg2");
    }

    private Map<String,String> StructuringAggregation(String statistical) {

        /**
         * grunt 安装失败解决方案 http://www.imooc.com/qadetail/120294
         * es套餐window环境处理方法 http://www.cnblogs.com/binshen/p/7419066.html
         * es java api https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/java-span-queries.html
         */

        SearchResponse sr = client.prepareSearch("logger-rzjl*")
                .addAggregation(
                        AggregationBuilders.terms("count").field(statistical)
                )
                .execute().actionGet();
        Map<String,String> data = analysisJsonData(sr.toString());

        for(Map.Entry<String,String> detail : data.entrySet()) {
            System.out.println("key ：" + detail.getKey() + " value ：" + detail.getValue());
        }

        return data;
    }

    @Test
    public void StructuringAggregationTest() {
        StructuringAggregation("jrxzqmc.keyword");
        StructuringAggregation("bwjcjg.keyword");
        StructuringAggregation("rkjg.keyword");
        StructuringAggregation("ywbm.keyword");
    }


    private Map<String,String> analysisJsonData(String data) {
        Map<String,String> statisticalData = new HashMap<String,String>();
        JSONObject jsonObject = JSON.parseObject(data);
        JSONObject t = (JSONObject) jsonObject.get("aggregations");
        JSONObject tt = (JSONObject)t.get("count");
        JSONArray ttt = (JSONArray) tt.get("buckets");
        for(JSONObject json : ttt.toJavaList(JSONObject.class)) {
            statisticalData.put(json.get("key").toString(), json.get("doc_count").toString());
        }
        return statisticalData;
    }


    @After
    public void clearResource() {
        ElasticSearchClientManage.closeClient();
    }
}
