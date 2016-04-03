package com.rogeri.schoolknowledge;

import android.util.Log;
import android.app.Application;

import com.rogeri.schoolknowledge.R;
import com.rogeri.schoolknowledge.model.User;
/**
* Created by rogeri on 02/04/16.
*/
public class SchoolKnowledge extends Application {
  public static final String LOG_TAG="SchoolKnowledge";
  private boolean anonymous;
  private User player;

  public void setPlayer(User p) {
    player = p;
    anonymous=false;
  }

  public void setAnonymous() {
    player = null;
    anonymous=true;
  }

  public boolean isAnonymous() {
    return anonymous;
  }

  public User getPlayer() {
    return player;
  }

  public void logout() {
    player = null;
    anonymous=false;
  }

  public boolean isLoggedIn() {
    return player!=null || anonymous==true;
  }

}
