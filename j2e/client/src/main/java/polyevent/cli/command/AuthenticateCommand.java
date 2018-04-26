package polyevent.cli.command;

import api.EventApi;
import polyevent.Coordinator;

import java.util.List;

public class AuthenticateCommand extends AbstractCommand<EventApi> {
    private String email;
    private String password;


    @Override
    public String command() {
        return "Authenticate";
    }

    @Override
    public void execute() throws Exception {
        Coordinator c = shell.api.coordinatorService.authenticate(email, password);
    }

    @Override
    public void load(List<String> args) {
        email = args.get(0);
        password = args.get(1);
    }

    @Override
    public String helper() {
        return "Authenticate yourself.\nUsage: " + command() + " email password ";
    }
}
