import {ActivatedRouteSnapshot, CanActivate, CanActivateFn, Router, RouterStateSnapshot} from '@angular/router';
import {Injectable} from '@angular/core';
import {AuthService} from '../auth/auth.service';

@ Injectable()
export class AuthGuard  {

  constructor(private router: Router, private authService: AuthService) {}



  canActivate( router: ActivatedRouteSnapshot,
              state: RouterStateSnapshot,) {

    if(this.authService.isAuthenticated)
    {
      return true;
    }
    else {
      this.router.navigateByUrl('/login');
      return false;
    }

  }

}
