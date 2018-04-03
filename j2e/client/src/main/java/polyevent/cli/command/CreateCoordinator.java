package polyevent.cli.command;

import api.EventApi;

import java.util.List;

public class CreateCoordinator extends AbstractCommand<EventApi>{
    @Override
    public String command() {
        return "createCoordinator";
    }

    @Override
    public void execute() throws Exception {

    }

    @Override
    public String helper() {
        return null;
    }

    @Override
    public void load(List args) {

    }
}
