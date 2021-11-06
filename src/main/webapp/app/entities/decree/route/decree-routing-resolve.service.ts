import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDecree, Decree } from '../decree.model';
import { DecreeService } from '../service/decree.service';

@Injectable({ providedIn: 'root' })
export class DecreeRoutingResolveService implements Resolve<IDecree> {
  constructor(protected service: DecreeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDecree> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((decree: HttpResponse<Decree>) => {
          if (decree.body) {
            return of(decree.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Decree());
  }
}
