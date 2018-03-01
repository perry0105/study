package org.perryw.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.*;
import com.hazelcast.core.HazelcastInstance;

import java.util.Map;

public class TestClient {
    public static void main(String[] a) {
        client();
    }

    private static void client() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.getNetworkConfig().setRedoOperation(true);
        clientConfig.getNetworkConfig().setConnectionAttemptLimit(Integer.MAX_VALUE);
        clientConfig.getNetworkConfig().setConnectionAttemptPeriod(10000);
        clientConfig.setProperty("hazelcast.client.statistics.enabled", "true");
        clientConfig.getNetworkConfig().addAddress("localhost:8683");

        EvictionConfig evictionConfig = new EvictionConfig()
                .setEvictionPolicy(EvictionPolicy.LRU)
                .setMaximumSizePolicy(EvictionConfig.MaxSizePolicy.ENTRY_COUNT)
                .setSize(50000);

        NearCacheConfig nearCacheConfig = new NearCacheConfig()
                .setName("dota")
                .setInMemoryFormat(InMemoryFormat.OBJECT)
                .setInvalidateOnChange(true)
                .setEvictionConfig(evictionConfig);

        clientConfig.addNearCacheConfig(nearCacheConfig);

        HazelcastInstance hazelcastInstance = HazelcastClient.newHazelcastClient(clientConfig);
        Map<Long, String> map = hazelcastInstance.getMap("dota");
        map.put(1L, "val1");
        map.put(2L, "val2");
        map.put(3L, "val3");
        int count = 1;
        while(true) {
            try {
                Thread.sleep(3000L);
                System.out.println("Value with key 1 is : " + map.get(1L));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}