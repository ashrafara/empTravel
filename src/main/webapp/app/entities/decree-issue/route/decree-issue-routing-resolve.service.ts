import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDecreeIssue, DecreeIssue } from '../decree-issue.model';
import { DecreeIssueService } from '../service/decree-issue.service';

@Injectable({ providedIn: 'root' })
export class DecreeIssueRoutingResolveService implements Resolve<IDecreeIssue> {
  constructor(protected service: DecreeIssueService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDecreeIssue> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((decreeIssue: HttpResponse<DecreeIssue>) => {
          if (decreeIssue.body) {
            return of(decreeIssue.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DecreeIssue());
  }
}
