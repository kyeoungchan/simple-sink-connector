package com.example.simplesinkconnector;

import java.util.Map;
import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

public class SingleFileSinkConnectorConfig extends AbstractConfig {

    /* 토픽의 데이터를 저장할 파일 이름을 옵션값으로 받기 위해 선언한다.
    * 토픽이 옵션값에 없는 이류는 커넥트를 통해 커넥터를 생성할 때 기본값으로 받아야하기 때문이다.
    * ➡ 싱크 커넥터의 옵션값으로 토픽을 추가하지 않아도 된다. */
    public static final String DIR_FILE_NAME = "file";
    private static final String DIR_FILE_NAME_DEFAULT_VALUE = "/tmp/kafka/txt";
    private static final String DIR_FILE_NAME_DOC = "저장할 디렉토리와 파일 이름";

    // 커넥터에서 사용할 옵션값을에 대한 정의를 표현한다.
    public static ConfigDef CONFIG = new ConfigDef().define(
            DIR_FILE_NAME,
            ConfigDef.Type.STRING,
            DIR_FILE_NAME_DEFAULT_VALUE,
            ConfigDef.Importance.HIGH,
            DIR_FILE_NAME_DOC
    );

    public SingleFileSinkConnectorConfig(Map<String, String> props) {
        super(CONFIG, props);
    }
}
