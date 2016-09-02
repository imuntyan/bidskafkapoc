package com.openlane.bids.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created by Igor.Muntyan on 8/25/2016.
 */
@Configuration
public class MongodbConfiguration {

//    private static final String ENV_MONGODB = "mongodb.";
//    private static final String PROP_REPLICA_SETS = "replicaSets";
//
//
//    private RelaxedPropertyResolver propertyResolver;
//    @Override
//    public void setEnvironment(Environment environment) {
//        this.propertyResolver = new RelaxedPropertyResolver(environment, ENV_MONGODB);
//    }
//
//
//    public @Bean MongoClientFactoryBean mongo() {
//        MongoClientFactoryBean mongo = new MongoClientFactoryBean();
//        propertyResolver.getProperty(PROP_REPLICA_SETS)
//        //mongo.setHost("localhost");
//        //mongo.setReplicaSetSeeds();
//        return mongo;
//    }

    @Autowired
    private MongoDbFactory mongo;

    public @Bean MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo);
    }
}
