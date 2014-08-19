package controllers;

import actors.MyActor;
import akka.actor.ActorRef;
import akka.util.Timeout;
import domains.App;
import domains.Apps;
import play.libs.Akka;
import play.libs.F;
import play.libs.Json;
import play.mvc.*;

import scala.concurrent.Future;
import views.html.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static akka.pattern.Patterns.ask;

public class Application extends Controller {

    static ActorRef actorRef = Akka.system().actorOf(MyActor.props, "myActor");

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static F.Promise<Result> apps() {
        Timeout timeout = Timeout.apply(10, TimeUnit.SECONDS);
        Future f = ask(actorRef, Apps.Message.FIND, timeout);
        return F.Promise.wrap(f).map(new F.Function() {
            @Override
            public Object apply(Object o) throws Throwable {
                return ok(Json.toJson(o));
            }
        });
    }

    public static F.Promise<Result> app(String id) {
        Timeout timeout = Timeout.apply(5, TimeUnit.SECONDS);
        Future f = ask(actorRef, new Apps.FindMessage(id), timeout);
        return F.Promise.wrap(f).map(new F.Function() {
            @Override
            public Object apply(Object o) throws Throwable {
                return ok(Json.toJson(o));
            }
        });
    }
}
