import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LocalStorageUserService {

  ACCESS_TOKEN_KEY = 'user_token'

  USER_EMAIL_KEY = 'user_email'

  constructor() { }

  public saveUserAccessToken(token: string) {
    if (!token && token.trim() !== '') return; 

    localStorage.setItem(this.ACCESS_TOKEN_KEY, token);
  }

  public getUserAccessToken(): string | null {
    return localStorage.getItem(this.ACCESS_TOKEN_KEY);
  }

  public saveUserEmail(email: string) {
    if (!email) return; 
    
    localStorage.setItem(this.USER_EMAIL_KEY, email);
  }

  public getUserEmail(): string | null {
    return localStorage.getItem(this.USER_EMAIL_KEY);
  }
}
