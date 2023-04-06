package com.impactupgrade.integration.hubspot.v1;

import com.impactupgrade.integration.hubspot.v1.exception.DuplicateContactException;
import com.impactupgrade.integration.hubspot.v1.exception.HubSpotException;
import com.impactupgrade.integration.hubspot.v1.model.EngagementRequest;
import com.impactupgrade.integration.hubspot.v1.model.internal.ErrorResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * The Engagements API for V3 is currently under development. Until then, we'll need to use V1...
 */
public class EngagementV1Client extends AbstractV1Client {

  private final Logger log = LogManager.getLogger(EngagementV1Client.class);

  private final WebTarget engagementsTarget = target.path("engagements/v1/engagements");

  public EngagementV1Client(String apiKey) {
    super(apiKey);
  }

  public EngagementRequest insert(EngagementRequest engagementRequest) throws DuplicateContactException, HubSpotException, InterruptedException {
    return _insert(0, engagementRequest);
  }

  private EngagementRequest _insert(int count, EngagementRequest engagementRequest) throws DuplicateContactException, HubSpotException, InterruptedException {
    if (count == 6) {
      log.error("unable to complete insert by attempt {}", count);
      return new EngagementRequest();
    }
    Response response = engagementsTarget
        .request(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer " + apiKey)
        .post(Entity.entity(engagementRequest, MediaType.APPLICATION_JSON));

    if (response.getStatus() == 409) {
      ErrorResponse error = response.readEntity(ErrorResponse.class);
      throw new DuplicateContactException(error.getIdentityProfile().getVid());
    } else if (response.getStatus() != 200) {
      ErrorResponse error = response.readEntity(ErrorResponse.class);
      throw new HubSpotException(error.getMessage());
    } else if (response.getStatus() == 429) {
      log.warn("insert attempt {} failed due to API rate limit; retrying in 5s", count);
      Thread.sleep(5000);
      return _insert(count + 1, engagementRequest);
    } else {
      return response.readEntity(EngagementRequest.class);
    }
  }
}
