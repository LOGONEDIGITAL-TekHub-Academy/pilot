package com.logonedigital.pilot.project.domain.vo;

import com.logonedigital.pilot.shared.error.domain.Assert;

public record ProjectDescription(String value) {

  public ProjectDescription {
    Assert.notNull("value", value);
    Assert.field("value", value).minLength(3).maxLength(256);
  }
}
