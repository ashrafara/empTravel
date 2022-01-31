import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { Country, ICountry } from '../country.model';
import { CountryService } from '../service/country.service';

@Injectable({ providedIn: 'root' })
export class CountryRoutingResolveService implements Resolve<ICountry> {
  constructor(protected service: CountryService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICountry> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((country: HttpResponse<Country>) => {
          if (country.body) {
            return of(country.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Country());
  }
}
