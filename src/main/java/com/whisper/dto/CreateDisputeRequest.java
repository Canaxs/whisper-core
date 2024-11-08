package com.whisper.dto;

import java.util.Set;

public record CreateDisputeRequest(
  String description,
  Long whisperId,
  Set<String> tags
){}
