using System;
using System.Net;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Collections.Generic;
using System.Linq;

namespace Partner.Service {

  // The service is stateful, as it is only a Proof of Concept.
  // Services should be stateless, this is for demonstration purpose only.
  [ServiceBehavior(InstanceContextMode = InstanceContextMode.Single)]
  public class AgendaEDTService : IAgendaEDTService
  {
    private const string MagicKey = "896983"; // ASCII code for "YES"

    public bool book(String name)
    {
      return !name.Contains("3");
    }
  }
}