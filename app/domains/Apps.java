package domains;

import org.neo4j.graphdb.GraphDatabaseService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangjungis@126.com on 14-8-19.
 */
public class Apps {

    public enum Message {
        FIND
    }

    public static class FindMessage {
        private String id;
        public FindMessage(String id) {
            this.id = id;
        }
        public String getId() {
            return id;
        }
    }

    final GraphDatabaseService neo4j;

    private Apps(final GraphDatabaseService neo4j) {
        this.neo4j = neo4j;
    }

    public static Apps buildApps(final GraphDatabaseService neo4j) {
        return new Apps(neo4j);
    }


    public List<App> find() {
        List<App> result = new ArrayList<App>();
        for (int i =0 ; i<10; i++) {
            result.add(new App("测试1", "40012345" + i));
        }
        return result;
    }

    public App findBy(String id) {
       return new App("测试1", "40012345");
    }
}
