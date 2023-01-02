package com.quizroulette.LegacyServer.user_adapter;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
  private String username;
  private String createDateTime;
  private String updateDateTime;
  private String password;
  private String avatarUrl;
}
