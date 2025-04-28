package com.logonedigital.pilot.project.domain.vo;

import com.logonedigital.pilot.shared.error.domain.Assert;
import java.util.UUID;

public record PublicId(UUID value) {

  public PublicId {
    Assert.notNull("value", value);
  }
}
