package actors;

import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.japi.Function;
import domains.Apps;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by yangjungis@126.com on 14-8-19.
 */
public class MyActor extends InstrumentedActor {

    public static Props props = Props.create(MyActor.class);

    private static SupervisorStrategy strategy = new OneForOneStrategy(5,
            Duration.apply(5, TimeUnit.SECONDS),
            new Function<Throwable, SupervisorStrategy.Directive>() {
                @Override
                public SupervisorStrategy.Directive apply(Throwable t) throws Exception {
                    if (t instanceof ArithmeticException) {
                        return SupervisorStrategy.resume();
                    } else if (t instanceof NullPointerException) {
                        return SupervisorStrategy.restart();
                    } else if (t instanceof IllegalArgumentException) {
                        return SupervisorStrategy.stop();
                    } else {
                        return SupervisorStrategy.escalate();
                    }
                }
            });


    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    public void preStart() {
        logger.info("preStart...");
    }

    @Override
    protected void wrappedReceive(Object message) {
        if (Apps.Message.FIND.equals(message)) {
           this.getSender().tell(Apps.buildApps(neo4j).find(), this.getSelf());
        } else if (message instanceof Apps.FindMessage) {
            Apps.FindMessage m = (Apps.FindMessage)  message;
            this.getSender().tell(Apps.buildApps(neo4j).findBy(m.getId()), this.getSelf());
        } else {
            this.unhandled(message);
        }
    }
}
