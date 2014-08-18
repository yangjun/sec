package utils;

import com.typesafe.config.Config;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.HighlyAvailableGraphDatabaseFactory;
import play.Configuration;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by yangjungis@126.com on 14-8-18.
 */
public class Neo4jUtils {

    private static GraphDatabaseService neo4j = null;
    private static AtomicBoolean isInit = new AtomicBoolean(false);
    private static ExecutionEngine engine;
    //private static List<TransactionEventHandler> transactionEventHandlers = new ArrayList<TransactionEventHandler>();

    /**
     * 初始化
     *
     * @param conf
     */
    public static void init(Config conf) {
        if (isInit.getAndSet(true)) {
            return;
        }
        Configuration configuration = new Configuration(conf);
        String neoPath = configuration.getString("neo4j.path", "/ivr-sec/db");
        String fileName = configuration.getString("neo4j.configfile", "/ivr-sec/config/neo4j.properites");
        boolean ha = configuration.getBoolean("neo4j.ha", false);
        if (!ha) {
            GraphDatabaseFactory factory = new GraphDatabaseFactory();
            neo4j = factory.newEmbeddedDatabaseBuilder(neoPath).loadPropertiesFromFile(fileName).newGraphDatabase();
        } else {
            HighlyAvailableGraphDatabaseFactory factory = new HighlyAvailableGraphDatabaseFactory();
            neo4j = factory.newHighlyAvailableDatabaseBuilder(neoPath).loadPropertiesFromFile(fileName).newGraphDatabase();
        }

        //transactionEventHandlers.clear();
        //transactionEventHandlers.add(UUIDTransactionEventHandler.build());

        //neo4j.registerTransactionEventHandler(UUIDTransactionEventHandler.build());
        //ActorRef actorRef = Akka.system().actorOf(Neo4jTransactionEventHandlerActor.props(), "neo4jTransactionEventHandlerActor");
        //neo4j.registerTransactionEventHandler(new ActorTransactionEventHandler(actorRef));
        //transactionEventHandlers.add(new ActorTransactionEventHandler(actorRef));

        //for (TransactionEventHandler handler : transactionEventHandlers) {
         //   neo4j.registerTransactionEventHandler(handler);
        //}

        engine = new ExecutionEngine(neo4j);
    }

    /**
     * 取得 GraphDatabaseService 单例对象
     *
     * @return
     */
    public static GraphDatabaseService neo4j() {
        return neo4j;
    }

    /**
     * 取得执行引擎（Cypher）
     *
     * @return
     */
    public static ExecutionEngine engine() {
        return engine;
    }

    /**
     * 关闭数据库
     */
    public static void shutdown() {
        if (null != neo4j) {
//            for (TransactionEventHandler handler : transactionEventHandlers) {
//                neo4j.unregisterTransactionEventHandler(handler);
//            }
//            transactionEventHandlers.clear();
            neo4j.shutdown();
            isInit.getAndSet(true);
        }
    }

    public static void addShutdownHook() {
        // add shutdown hook
        Runtime runtime = Runtime.getRuntime();
        runtime.addShutdownHook(new Thread(new Runnable() {
            public void run() {
                shutdown();
            }
        }));
    }
}
