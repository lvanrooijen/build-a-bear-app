package com.lvr.Build.a.bear.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class registerDto {
  private String firstName;
  private String lastName;
  private String email;
  private String password;
}
