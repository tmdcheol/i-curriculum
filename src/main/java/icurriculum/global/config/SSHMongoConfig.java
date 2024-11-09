package icurriculum.global.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SSHMongoConfig {
    @Value("${spring.data.mongodb.username}")
    private String mongoUsername;

    @Value("${spring.data.mongodb.password}")
    private String mongoPassword;

    @Value("${spring.data.mongodb.host}")
    private String mongoHost;


    private final SSHConfig sshConfig;  // SSHConfig 클래스 사용
    private final MongoProperties mongoProperties;  // MongoDB 프로퍼티

    @Bean
    @Primary
    public MongoClient mongoClient() {
        Integer forwardedPort = sshConfig.buildSshConnection();  // SSHConfig에서 로컬 포트 가져오기
        String connectionString = String.format(
            "mongodb://%s:%s@%s:%d/?readPreference=secondaryPreferred&retryWrites=false",
            mongoUsername,
            mongoPassword,
            mongoHost,
            forwardedPort);  // MongoDB URI 구성

        log.info("MongoDB Connection String: {}", connectionString);

        // MongoClientSettings 생성
        MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(connectionString))  // MongoDB 연결 문자열 적용
            .build();

        // MongoClient 생성
        MongoClient client = MongoClients.create(settings);


        return client;  // MongoClient 반환
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "demo");  // 여기서 demo 데이터베이스 지정
    }
}