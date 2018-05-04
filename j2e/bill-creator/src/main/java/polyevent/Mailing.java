package polyevent;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import polyevent.entities.Coordinator;
import polyevent.entities.Event;



public class Mailing {

    private Event event;
    private Coordinator coordinator;

    public Mailing(Event event){
        this.event = event;
        this.coordinator = event.getCoordinator();
    }

    public JsonNode sendSimpleMessage() throws UnirestException {

        String subject = coordinator.getFirstName() + " " + coordinator.getLastName() + ", voici votre facture";
        String content = "Votre événement " + event.getName() + " a bien été créé. Votre salle va vous être communiquer prochainement." +
                "Vous pourrez y accéder du " + event.getStartDate() + " au " + event.getEndDate() +
                ". La facture pour cet événement a donc bien été établie.";

        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/sandboxd67ad01d73c64b15915a5bac7c743c79.mailgun.org/messages")
                .basicAuth("api", "key-0c2fcad4200b9f1143cd1cce37e01c58")
                .queryString("from", "Mailgun Sandbox <postmaster@sandboxd67ad01d73c64b15915a5bac7c743c79.mailgun.org>")
                .queryString("to", coordinator.getFirstName() + " " + coordinator.getLastName() + " <jehanmilleret@gmail.com>")
                .queryString("subject", subject)
                .queryString("text", content)
                .asJson();

        return request.getBody();
    }
}
