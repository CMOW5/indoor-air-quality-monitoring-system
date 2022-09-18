import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { LocalStorageUserService } from '../user/local-storage-user-info.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  
  constructor(private httpClient: HttpClient, private localStorageUserService: LocalStorageUserService) { }

  public login() {
    window.location.href = environment.COGNITO_LOGIN_URL;
  }

  public logout() {
    // todo: revoke the token in cognito as well
    this.localStorageUserService.deleteUserData();
    this.login();
  }

  public isLoggedIn(): Observable<boolean> {
    return new Observable((observer) => {

       // get token from url
      let token = this.getTokenFromRedirectionUrl();

      if (token) {
        this.localStorageUserService.saveUserAccessToken(token);
      }
    
      this.isTokenValid().subscribe(isValid => {
        observer.next(isValid);
      })
      
  
    });     
  }

  public getTokenFromRedirectionUrl(): string | null {
    const urlParams = new URLSearchParams(window.location.hash.replace("#","?"));
    const token = urlParams.get('access_token');
    return token;
  }

  public isTokenValid(): Observable<boolean> {
  
    return new Observable((observer) => {
    
      if (this.isTokenExpired()) {
        observer.next(false);
      }
      
      this.getUserData().subscribe({ 
        next: (userData) => {
          this.localStorageUserService.saveUserEmail(userData.email);
          observer.next(true);
        }, 
        error: (e) => {
          observer.next(false);
        }});      
    });     
  }

  public isTokenExpired() {
    const token = this.localStorageUserService.getUserAccessToken();

    // validate token expiry
    return false;
  }

  public getUserData(): Observable<UserData> {
    return new Observable((observer) => {
      this.httpClient.get<UserData>(environment.COGNITO_USER_INFO_URL).subscribe((response) => {
        observer.next(response);
      }, error => {
        // todo: we shouldn't call login here. have the requester decide what to do if there's any error
        this.login();
      });     
    });     
  }

  public getAccessToken(): string | null {
    return this.localStorageUserService.getUserAccessToken();
  }

  public getUserEmail(): string | null {
    return this.localStorageUserService.getUserEmail();
  }
}

interface UserData {
  email: string
  username: string;
}
