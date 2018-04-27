package polyevent.cli.command;

import api.EventApi;
import polyevent.Coordinator;

import java.util.List;

public class AuthentificationCommand extends AbstractCommand<EventApi> {
    private String email;
    private String password;
    public static Coordinator cache;


    @Override
    public String command() {
        return "auth";
    }

    @Override
    public void execute() throws Exception {
        cache = shell.api.coordinatorService.authentificate(email,password);
    }

    @Override
    public void load(List<String> args) {
        email = args.get(0);
        password = args.get(1);
    }

    @Override
    public String helper() {
        return "Authentificate yourself.\nUsage: " + command() + " email password ";
    }
}
