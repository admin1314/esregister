package com.zc.configuration;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zc.dao.ClusterMapper;
import com.zc.dao.ClusterUtils;
import com.zc.model.ClusterModel;

@Configuration
public class EsConfiguration {

	@Autowired
	ClusterMapper clusterMapper;

	/**
	 * 初始化elasticsearch客户端
	 * 
	 * @return
	 */
	@Bean
	@Qualifier("esClients")
	public Map<String, Client> initEsClients() {
		Map<String, Client> map = new ConcurrentHashMap<String, Client>();
		List<ClusterModel> clusterInfoList = clusterMapper.findAll();
		if (clusterInfoList != null) {
			for (ClusterModel clusterInfo : clusterInfoList) {
				try {
					Client client = ClusterUtils.getClient(clusterInfo.getIp(), clusterInfo.getClusterName(), clusterInfo.getPort());	
					//TODO 检查集群连通性
					map.put(clusterInfo.getId(), client);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}

}
