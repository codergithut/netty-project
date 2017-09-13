package test.client;

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
public class ElasticSearchClientTest {

    TransportClient client;

    private String addId;

    IndexResponse response;
    private DeleteByQueryRequestBuilder deleteByQueryRequestBuilder;

    @Before
    public void initParam() throws UnknownHostException {
        ElasticSearchClientManage elasticSearchClientManage = ElasticSearchClientManage.getInstanceEsClientManage();
        client = elasticSearchClientManage.getESClient();
    }

    /**
     * 插入数据
     * @throws IOException
     */
    @Test
    @Ignore
    public void insertIndexAndDataTest() throws IOException {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("user","kimchy");
        json.put("postDate",new Date());
        json.put("message","trying out Elasticsearch");

        /**
         * 测试插入数据
         */
        response = client.prepareIndex("twitter", "tweet")
                .setSource(json)
                .get();


        String _index = response.getIndex();
        String _type = response.getType();
        addId = response.getId();
        long _version = response.getVersion();
        RestStatus status = response.status();
        /**
         * 验证结果
         */
        assertEquals("twitter", _index);
        assertEquals("tweet", _type);
        assertEquals("created", response.getResult().getLowercase());
    }

    /**
     * 根据id获取数据
     */
    @Test
    @Ignore
    public void searchDataTest() {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("user","search");
        json.put("postDate",new Date());
        json.put("message","trying search");
        /**
         * 插入数据
         */
        response = client.prepareIndex("twitter", "tweet")
                .setSource(json)
                .get();
        String id = response.getId();
        /**
         * 测试获取数据
         */
        GetResponse getResponse = client.prepareGet("twitter", "tweet", id).get();

        /**
         * 验证数据
         */
        assertEquals("twitter", getResponse.getIndex());
        assertEquals("tweet", getResponse.getType());
        assertNotSame(json, getResponse.getSource());

    }

    /**
     * 删除数据
     */
    @Test
    @Ignore
    public void deleteDataTest() {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("user","delete");
        json.put("postDate",new Date());
        json.put("message","delete test");

        /**
         * 测试插入数据
         */
        response = client.prepareIndex("twitter", "tweet")
                .setSource(json)
                .get();
        DeleteResponse deleteResponse = client.prepareDelete("twitter", "tweet", response.getId()).get();

        /**
         * 验证结果
         */
        assertEquals("twitter", deleteResponse.getIndex());
        assertEquals("tweet", deleteResponse.getType());
        assertEquals("deleted", deleteResponse.getResult().getLowercase());

    }

    /**
     * 查询删除操作，有点问题，assert断言有出入需要查看
     */
    @Test
    @Ignore
    public void deleteDataByQueryTest() {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("user","delete");
        json.put("postDate",new Date());
        json.put("message","delete test");

        /**
         * 测试插入数据 user没有分析插入
         */
        response = client.prepareIndex("twitter", "tweet")
                .setSource(json)
                .get();


        BulkByScrollResponse response =
                DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
                        .filter(QueryBuilders.matchQuery("gender", "male"))
                        .source("persons")
                        .get();

        long deleted = response.getDeleted();
        //assertTrue(deleted == 1);
    }

    /**
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     * 跟新文件操作
     */
    @Test
    @Ignore
    public void updateTest() throws IOException, ExecutionException, InterruptedException {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("index");
        updateRequest.type("type");
        updateRequest.id("1");
        updateRequest.doc(jsonBuilder()
                .startObject()
                .field("gender", "male")
                .endObject());
        client.update(updateRequest).get();

    }

    /**
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     * 跟新或创建文件如果文件存在执行跟新操作，如果不存在则执行插入操作
     */
    @Test
    @Ignore
    public void upSertTest() throws IOException, ExecutionException, InterruptedException {
        IndexRequest indexRequest = new IndexRequest("index", "type", "1")
                .source(jsonBuilder()
                        .startObject()
                        .field("name", "Joe Smith")
                        .field("gender", "male")
                        .endObject());
        UpdateRequest updateRequest = new UpdateRequest("index", "type", "1")
                .doc(jsonBuilder()
                        .startObject()
                        .field("gender", "male")
                        .endObject())
                .upsert(indexRequest);
        client.update(updateRequest).get();
    }


    /**
     * 测试多条查询结果，暂时都是空未放入测试数据需要修正
     */
    @Test
    @Ignore
    public void multiQueryTest() {
        MultiGetResponse multiGetItemResponses = client.prepareMultiGet()
                .add("twitter", "tweet", "1")
                .add("twitter", "tweet", "2", "3", "4")
                .add("another", "type", "foo")
                .get();

        for (MultiGetItemResponse itemResponse : multiGetItemResponses) {
            GetResponse response = itemResponse.getResponse();
            if (response != null && response.isExists()) {
                String json = response.getSourceAsString();
                System.out.println(json);
            }
        }

    }

    @Test
    @Ignore
    public void bulkInsert() throws IOException {
        BulkRequestBuilder bulkRequest = client.prepareBulk();

// either use client#prepare, or use Requests# to directly build index/delete requests
        bulkRequest.add(client.prepareIndex("twitter", "tweet", "1")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "trying out Elasticsearch")
                        .endObject()
                )
        );

        bulkRequest.add(client.prepareIndex("twitter", "tweet", "2")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "another post")
                        .endObject()
                )
        );

        BulkResponse bulkResponse = bulkRequest.get();
        if (bulkResponse.hasFailures()) {
            // process failures by iterating through each bulk response item
        }
    }


    @Test
    @Ignore
    public void bulkProcessorTest() throws IOException {
        BulkProcessor bulkProcessor = BulkProcessor.builder(
                client,
                new BulkProcessor.Listener() {
                    public void beforeBulk(long executionId,
                                           BulkRequest request) {
                        System.out.println("all works has begin");
                    }

                    public void afterBulk(long executionId,
                                          BulkRequest request,
                                          BulkResponse response) {
                        System.out.println("all works has done");
                    }

                    public void afterBulk(long executionId,
                                          BulkRequest request,
                                          Throwable failure) {
                        System.out.println("something is wrong");
                    }
                })
                .setBulkActions(10000)
                .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))
                .setFlushInterval(TimeValue.timeValueSeconds(5))
                .setConcurrentRequests(1)
                .setBackoffPolicy(
                        BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
                .build();


        Map<String, Object> json = new HashMap<String, Object>();
        json.put("user","delete");
        json.put("postDate",new Date());
        json.put("message","delete test");

        bulkProcessor.add(new DeleteRequest("twitter", "tweet", "2"));

        bulkProcessor.add((DocWriteRequest) new IndexRequest("twitter", "tweet", "1").source(jsonBuilder()
                .startObject()
                .field("user", "bulkProcessor")
                .field("postDate", new Date())
                .field("message", "trying out Elasticsearch")
                .endObject()));

        bulkProcessor.flush();

        bulkProcessor.close();

        RefreshResponse response = client.admin().indices().prepareRefresh().get();

        SearchResponse response1 = client.prepareSearch().get();

        System.out.println("this is test");
    }

    @Test
    @Ignore
    public void querySearchTest() {
        SearchResponse response = client.prepareSearch("twitter", "twitter")
                .setTypes("tweet", "tweet")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.termQuery("multi", "test"))                 // Query
                .setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     // Filter
                .setFrom(0).setSize(60).setExplain(true)
                .get();
    }

    @Test
    @Ignore
    public void queryScrollSearchTest() {
        QueryBuilder qb = termQuery("multi", "test");

        SearchResponse scrollResp = client.prepareSearch("twitter")
                .addSort(FieldSortBuilder.DOC_FIELD_NAME, SortOrder.ASC)
                .setScroll(new TimeValue(60000))
                .setQuery(qb)
                .setSize(100).get(); //max of 100 hits will be returned for each scroll
        //Scroll until no hits are returned
        do {
            for (SearchHit hit : scrollResp.getHits().getHits()) {

            }

            scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
        } while(scrollResp.getHits().getHits().length != 0);
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

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @param
     * @return
     * @description 限制每个片上最大的查询条数
     */
    @Test
    @Ignore
    public void TerminateAfterTest() {
        SearchResponse sr = client.prepareSearch()
                .setTerminateAfter(2)
                .get();

        if (sr.isTerminatedEarly()) {
            System.out.println("We finished early");
        }
    }

    @Test
    @Ignore
    public void SearchTemplateTest() {

        /**
         * 模板参数设置
         */
        Map<String, Object> template_params = new HashMap<>();
        template_params.put("param_gender", "male");
//
//        SearchResponse sr = new SearchTemplateRequestBuilder(client)
//                .setScript("template_gender")
//                .setScriptType(ScriptService.ScriptType.FILE)
//                .setScriptParams(template_params)
//                .setRequest(new SearchRequest())
//                .get()
//                .getResponse();
//
//        /**
//         * 模板设置
//         */
//        client.admin().cluster().preparePutStoredScript()
//                .setScriptLang("mustache")
//                .setId("template_gender")
//                .setSource(new BytesArray(
//                        "{\n" +
//                                "    \"query\" : {\n" +
//                                "        \"match\" : {\n" +
//                                "            \"gender\" : \"{{param_gender}}\"\n" +
//                                "        }\n" +
//                                "    }\n" +
//                                "}")).get();
//        /**
//         * 按照模板查找
//         */
//        SearchResponse sr1 = new SearchTemplateRequestBuilder(client)
//                .setScript("template_gender")
//                .setScriptType(ScriptType.STORED)
//                .setScriptParams(template_params)
//                .setRequest(new SearchRequest())
//                .get()
//                .getResponse();

        /**
         * 组合拳，一次搞定
         */
        SearchResponse sr2 = new SearchTemplateRequestBuilder(client)
                .setScript("{\n" +
                        "        \"query\" : {\n" +
                        "            \"match\" : {\n" +
                        "                \"gender\" : \"{{param_gender}}\"\n" +
                        "            }\n" +
                        "        }\n" +
                        "}")
                .setScriptType(ScriptType.INLINE)
                .setScriptParams(template_params)
                .setRequest(new SearchRequest())
                .get()
                .getResponse();

    }

    @Test
    public void StructuringAggregation() {

        /**
         * grunt 安装失败解决方案 http://www.imooc.com/qadetail/120294
         * es套餐window环境处理方法 http://www.cnblogs.com/binshen/p/7419066.html
         * es java api https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/java-span-queries.html
         */

        SearchResponse sr = client.prepareSearch()
                .addAggregation(
                        AggregationBuilders.terms("by_country").field("country")
                                .subAggregation(AggregationBuilders.dateHistogram("by_year")
                                        .field("dateOfBirth")
                                        .dateHistogramInterval(DateHistogramInterval.YEAR)
                                        .subAggregation(AggregationBuilders.avg("avg_children").field("children"))
                                )
                )
                .execute().actionGet();
        System.out.println(sr);
    }


    @After
    public void clearResource() {
//        if(response != null) {
//            client.prepareDelete("twitter", "tweet", response.getId()).get();
//        }
        ElasticSearchClientManage.closeClient();
    }
}
