package io.github.phiysng.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class LettuceOn {
    public static void main(String[] args) {
        RedisClient client = RedisClient.create("redis://localhost");

        StatefulRedisConnection<String, String> connection = client.connect();


        RedisCommands<String, String> commands = connection.sync();

        for (int i = 0; i < 100; i++) {
            String value = commands.get("foo");
            if(Objects.isNull(value)){
                commands.set("foo","1");
            }else{
                commands.incr("foo");
            }
            System.out.println(value);
        }

        connection.close();

        client.shutdown();

    }
}
