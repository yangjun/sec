package actors;

import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yangjungis@126.com on 14-8-19.
 */
public abstract class ActorStack extends UntypedActor {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected abstract void wrappedReceive(Object message);

    public ActorStack() {
        logger.info("Starting actor " + getClass().getName());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        org.slf4j.MDC.put("akkaSource", this.getSelf().path().name());
        wrappedReceive(message);
    }
}
