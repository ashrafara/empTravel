jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDecree, Decree } from '../decree.model';
import { DecreeService } from '../service/decree.service';

import { DecreeRoutingResolveService } from './decree-routing-resolve.service';

describe('Decree routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: DecreeRoutingResolveService;
  let service: DecreeService;
  let resultDecree: IDecree | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(DecreeRoutingResolveService);
    service = TestBed.inject(DecreeService);
    resultDecree = undefined;
  });

  describe('resolve', () => {
    it('should return IDecree returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDecree = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDecree).toEqual({ id: 123 });
    });

    it('should return new IDecree if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDecree = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDecree).toEqual(new Decree());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Decree })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDecree = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDecree).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
