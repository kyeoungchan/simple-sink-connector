package com.example.simplesinkconnector;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.connect.errors.ConnectException;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;

public class SingleFileSinkTask extends SinkTask {

    private SingleFileSinkConnectorConfig config;
    private File file;
    private FileWriter fileWriter;

    @Override
    public String version() {
        return "1.0";
    }

    // 여기서는 파일에 데이터를 저장하므로 1개의 파일 인스턴스를 생성한다.
    @Override
    public void start(Map<String, String> props) {
        try {
            config = new SingleFileSinkConnectorConfig(props);
            file = new File(config.getString(SingleFileSinkConnectorConfig.DIR_FILE_NAME));
            fileWriter = new FileWriter(file, true);
        } catch (Exception e) {
            throw new ConnectException(e.getMessage(), e);
        }
    }

    @Override
    public void put(Collection<SinkRecord> records) {
        try {
            for (SinkRecord record : records) {
                fileWriter.write(record.value().toString() + "\n");
            }
        } catch (IOException e) {
            throw new ConnectException(e.getMessage(), e);
        }
    }

    /**
     * put() 메서드에서 파일에 데이터를 저장하는 것은 버퍼에 데이터를 저장하는 것이다.
     * 실질적으로 파일 시스템에 데이터를 저장하려면 FileWriter 클래스의 flush() 메서드를 호출해야 한다.
     */
    @Override
    public void flush(Map<TopicPartition, OffsetAndMetadata> offsets) {
        try {
            fileWriter.flush();
        } catch (IOException e) {
            throw new ConnectException(e.getMessage(), e);
        }
    }

    @Override
    public void stop() {
        try {
            fileWriter.close();
        } catch (IOException e) {
            throw new ConnectException(e.getMessage(), e);
        }
    }
}
