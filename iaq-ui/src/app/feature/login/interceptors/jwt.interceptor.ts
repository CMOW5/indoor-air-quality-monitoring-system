import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../auth/auth.service';


@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  BEARER_HEADER = 'Bearer'

  constructor(private userInfoService: AuthService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    // add auth header with jwt if account is logged in and request is to the api url
    const token = this.userInfoService.getAccessToken();

    if (token) { // todo: is Logged in and is API url
      request = request.clone({
        setHeaders: { Authorization: `${this.BEARER_HEADER} ${token}`}
      });
    }

    return next.handle(request);
  }
}
