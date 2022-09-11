import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent implements OnInit {

  constructor(private router: Router, private authService: AuthService ) { }

  ngOnInit(): void {
    this.authService.isLoggedIn().subscribe(isLogged => {
      if (isLogged) {
        this.router.navigate(['dashboard']);
      } else {
        this.authService.login();  
      }
    })
  }

}
