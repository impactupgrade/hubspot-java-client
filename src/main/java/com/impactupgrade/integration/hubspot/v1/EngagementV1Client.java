package com.impactupgrade.integration.hubspot.v1;

import com.impactupgrade.integration.hubspot.v1.builder.ContactBuilder;
import com.impactupgrade.integration.hubspot.v1.exception.DuplicateContactException;
import com.impactupgrade.integration.hubspot.v1.exception.HubSpotException;
import com.impactupgrade.integration.hubspot.v1.model.Contact;
import com.impactupgrade.integration.hubspot.v1.model.ContactArray;
import com.impactupgrade.integration.hubspot.v1.model.ContactRequest;
import com.impactupgrade.integration.hubspot.v1.model.EngagementRequest;
import com.impactupgrade.integration.hubspot.v1.model.internal.ErrorResponse;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * The Engagements API for V3 is currently under development. Until then, we'll need to use V1...
 */
public class EngagementV1Client extends AbstractV1Client {

  private final WebTarget engagementsTarget = target.path("engagements/v1/engagements");

  public EngagementV1Client(String apiKey) {
    super(apiKey);
  }

  public EngagementRequest insert(EngagementRequest engagementRequest) throws DuplicateContactException, HubSpotException {
    Response response = engagementsTarget
        .queryParam("hapikey", apiKey)
        .request(MediaType.APPLICATION_JSON)
        .post(Entity.entity(engagementRequest, MediaType.APPLICATION_JSON));

    if (response.getStatus() == 409) {
      ErrorResponse error = response.readEntity(ErrorResponse.class);
      throw new DuplicateContactException(error.getIdentityProfile().getVid());
    } else if (response.getStatus() != 200) {
      ErrorResponse error = response.readEntity(ErrorResponse.class);
      throw new HubSpotException(error.getMessage());
    } else {
      return response.readEntity(EngagementRequest.class);
    }
  }
}
