import { AfterViewChecked, AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { NavigationError, Router } from '@angular/router';

import { AccountService } from 'app/core/auth/account.service';

import { MatSidenav } from '@angular/material/sidenav';
import { BreakpointObserver } from '@angular/cdk/layout';
import { LoginService } from '../../login/login.service';

@Component({
  selector: 'jhi-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.scss'],
})
export class MainComponent implements OnInit, AfterViewInit, AfterViewChecked {
  @ViewChild(MatSidenav, { static: true })
  sidenav!: MatSidenav;

  constructor(
    private accountService: AccountService,
    private titleService: Title,
    private router: Router,
    private loginService: LoginService,
    private observer: BreakpointObserver
  ) {}

  ngOnInit(): void {
    // try to log in automatically
    this.accountService.identity().subscribe();

    this.router.events.subscribe(event => {
      if (event instanceof NavigationError && event.error.status === 404) {
        this.router.navigate(['/404']);
      }
    });
  }

  ngAfterViewInit(): void {
    this.observer.observe(['(max-width: 800px)']).subscribe(res => {
      if (res.matches) {
        this.sidenav.mode = 'over';
        this.sidenav.close();
      } else {
        this.sidenav.mode = 'side';
        this.sidenav.open();
      }
    });

    if (!this.accountService.isAuthenticated()) {
      this.sidenav.close();
    }
  }

  ngAfterViewChecked(): void {
    if (!this.accountService.isAuthenticated()) {
      this.sidenav.close();
    }
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  getFirstName(): any {
    return this.isAuthenticated() ? this.accountService.getFirstName() : '';
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  logout(): void {
    this.loginService.logout();
    this.router.navigate(['']);
  }
}
