using System;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Collections.Generic;

namespace Partner.Service {

  [ServiceContract]
  public interface IAgendaEDTService
  {
    [OperationContract]
    [WebInvoke( Method = "GET", UriTemplate = "book",
                ResponseFormat = WebMessageFormat.Json)]
    boolean book(String roomName);

}

}