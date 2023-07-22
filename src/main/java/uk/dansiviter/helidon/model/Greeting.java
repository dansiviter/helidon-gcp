package uk.dansiviter.helidon.model;

import jakarta.validation.constraints.NotBlank;

public record Greeting(@NotBlank String greeting) { }
