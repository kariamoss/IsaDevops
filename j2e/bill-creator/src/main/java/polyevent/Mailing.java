package polyevent;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import polyevent.entities.Coordinator;
import polyevent.entities.Event;

import java.io.File;



public class Mailing {

    private Event event;
    private Coordinator coordinator;

    public Mailing(Event event){
        this.event = event;
        this.coordinator = event.getCoordinator();
    }

    public JsonNode sendSimpleMessage() throws UnirestException {

        String subject = coordinator.getFirstName() + " " + coordinator.getLastName() + ", voici votre facture";
        String content = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" style=\"font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\">\n" +
                "<head>\n" +
                "<meta name=\"viewport\" content=\"width=device-width\" />\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "<title>Votre facture arrive</title>\n" +
                "\n" +
                "\n" +
                "<style type=\"text/css\">\n" +
                "img {\n" +
                "max-width: 100%;\n" +
                "}\n" +
                "body {\n" +
                "-webkit-font-smoothing: antialiased; -webkit-text-size-adjust: none; width: 100% !important; height: 100%; line-height: 1.6em;\n" +
                "}\n" +
                "body {\n" +
                "background-color: #f6f6f6;\n" +
                "}\n" +
                "@media only screen and (max-width: 640px) {\n" +
                "  body {\n" +
                "    padding: 0 !important;\n" +
                "  }\n" +
                "  h1 {\n" +
                "    font-weight: 800 !important; margin: 20px 0 5px !important;\n" +
                "  }\n" +
                "  h2 {\n" +
                "    font-weight: 800 !important; margin: 20px 0 5px !important;\n" +
                "  }\n" +
                "  h3 {\n" +
                "    font-weight: 800 !important; margin: 20px 0 5px !important;\n" +
                "  }\n" +
                "  h4 {\n" +
                "    font-weight: 800 !important; margin: 20px 0 5px !important;\n" +
                "  }\n" +
                "  h1 {\n" +
                "    font-size: 22px !important;\n" +
                "  }\n" +
                "  h2 {\n" +
                "    font-size: 18px !important;\n" +
                "  }\n" +
                "  h3 {\n" +
                "    font-size: 16px !important;\n" +
                "  }\n" +
                "  .container {\n" +
                "    padding: 0 !important; width: 100% !important;\n" +
                "  }\n" +
                "  .content {\n" +
                "    padding: 0 !important;\n" +
                "  }\n" +
                "  .content-wrap {\n" +
                "    padding: 10px !important;\n" +
                "  }\n" +
                "  .invoice {\n" +
                "    width: 100% !important;\n" +
                "  }\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "\n" +
                "<body itemscope itemtype=\"http://schema.org/EmailMessage\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; -webkit-font-smoothing: antialiased; -webkit-text-size-adjust: none; width: 100% !important; height: 100%; line-height: 1.6em; background-color: #f6f6f6; margin: 0;\" bgcolor=\"#f6f6f6\">\n" +
                "\n" +
                "<table class=\"body-wrap\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; width: 100%; background-color: #f6f6f6; margin: 0;\" bgcolor=\"#f6f6f6\"><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0;\" valign=\"top\"></td>\n" +
                "<td class=\"container\" width=\"600\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; display: block !important; max-width: 600px !important; clear: both !important; margin: 0 auto;\" valign=\"top\">\n" +
                "<div class=\"content\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; max-width: 600px; display: block; margin: 0 auto; padding: 20px;\">\n" +
                "<table class=\"main\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" itemprop=\"action\" itemscope itemtype=\"http://schema.org/ConfirmAction\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; border-radius: 3px; background-color: #fff; margin: 0; border: 1px solid #e9e9e9;\" bgcolor=\"#fff\"><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-wrap\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 20px;\" valign=\"top\">\n" +
                "<meta itemprop=\"name\" content=\"Evenement cree\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\" /><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\">\n" +
                "Votre événement " + event.getName() + " a bien été créé."+
                "</td>\n" +
                "</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\">\n" +
                "La facture pour cet événement a donc bien été établie. Votre salle va vous être communiquer prochainement.\n" +
                "Vous pourrez y accéder du " + event.getStartDate().toLocaleString() + " au " + event.getEndDate().toLocaleString() + "\n" +
                "</td>\n" +
                "</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" itemprop=\"handler\" itemscope itemtype=\"http://schema.org/HttpActionHandler\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\">\n" +
                "<a href=\"http://localhost:8080/j2e-interface-1.0-SNAPSHOT/listEvents.jsf\" class=\"btn-primary\" itemprop=\"url\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; color: #FFF; text-decoration: none; line-height: 2em; font-weight: bold; text-align: center; cursor: pointer; display: inline-block; border-radius: 5px; text-transform: capitalize; background-color: #348eda; margin: 0; border-color: #348eda; border-style: solid; border-width: 10px 20px;\">Vérifier les événements</a>\n" +
                "</td>\n" +
                "</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\">\n" +
                "&mdash; The Mailgunners\n" +
                "</td>\n" +
                "</tr></table></td>\n" +
                "</tr></table><div class=\"footer\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; width: 100%; clear: both; color: #999; margin: 0; padding: 20px;\">\n" +
                "<table width=\"100%\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"aligncenter content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 12px; vertical-align: top; color: #999; text-align: center; margin: 0; padding: 0 0 20px;\" align=\"center\" valign=\"top\">Follow <a href=\"http://twitter.com/mail_gun\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 12px; color: #999; text-decoration: underline; margin: 0;\">@Mail_Gun</a> on Twitter.</td>\n"+
                "</tr></table></div></div>\n" +
                "</td>\n" +
                "<td style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0;\" valign=\"top\"></td>\n" +
                "</tr></table></body>\n" +
                "</html>\n";
                

        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/sandboxd67ad01d73c64b15915a5bac7c743c79.mailgun.org/messages")
                .basicAuth("api", "key-0c2fcad4200b9f1143cd1cce37e01c58")
                .queryString("from", "Mailgun Sandbox <postmaster@sandboxd67ad01d73c64b15915a5bac7c743c79.mailgun.org>")
                .queryString("to", "Jehan Milleret <jehanportable@gmail.com>")
                .queryString("subject", subject)
                .queryString("text", content)
                .asJson();

        return request.getBody();
    }
}
