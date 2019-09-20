package com.liyz.service.third.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/20 9:48
 */
@Configuration
@Slf4j
public class ElasticSearchConfig {

    @Value("${elasticsearch.ip.address}")
    private String address;

    private static RestHighLevelClient client;

    @PostConstruct
    public void restHighLevelClient() {
        String[] hosts = address.split(",");
        List<Node> nodes = new ArrayList<>();
        for (String host:hosts){
            nodes.add(new Node(new HttpHost(host, 9200, "http")));
        }
        Node[] nodesArr = nodes.toArray(new Node[nodes.size()]);
        client = new RestHighLevelClient(RestClient.builder(nodesArr));
        log.info("ElasticSearch client init success ....");
    }

    public static void saveEsDataWithBulk(Map<String,Map<String, Object>> esBulkData, String index) {
        BulkRequest bulkRequest = new BulkRequest();
        for(Map.Entry<String, Map<String, Object>> oneEsData:esBulkData.entrySet()){
            String id = oneEsData.getKey();
            Map<String, Object> esData = oneEsData.getValue();
            UpdateRequest request = new UpdateRequest(index, id);
            request.doc(esData);
            request.docAsUpsert(true);
            bulkRequest.add(request);
        }
        try {
            client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("save esData error:", e);
        }
    }
}
