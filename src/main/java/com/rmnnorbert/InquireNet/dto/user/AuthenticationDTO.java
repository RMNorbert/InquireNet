package com.rmnnorbert.InquireNet.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record AuthenticationDTO(@NotBlank String username,
                                @NotBlank @Size(min = 8) String password)
{
}
