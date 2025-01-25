import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {AppComponent} from '../app.component';
import {AppRoutingModule} from '../app-routing.module';
import {AuthGuard} from './auth.guard';
import {AuthService} from '../auth/auth.service';



@NgModule({
  bootstrap: [AppComponent],
  declarations: [],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AppComponent
  ],
  providers: [
    AuthGuard, // Ajouter AuthGuard ici
    AuthService // Ajouter AuthService si ce n'est pas déjà fait
  ]
})
export class AppModule { }
