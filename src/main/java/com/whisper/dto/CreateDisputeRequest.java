package com.whisper.dto;

public record CreateDisputeRequest(
  String description,
  Long whisperId
){}
