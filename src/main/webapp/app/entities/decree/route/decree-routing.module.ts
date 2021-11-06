import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DecreeComponent } from '../list/decree.component';
import { DecreeDetailComponent } from '../detail/decree-detail.component';
import { DecreeUpdateComponent } from '../update/decree-update.component';
import { DecreeRoutingResolveService } from './decree-routing-resolve.service';

const decreeRoute: Routes = [
  {
    path: '',
    component: DecreeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DecreeDetailComponent,
    resolve: {
      decree: DecreeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DecreeUpdateComponent,
    resolve: {
      decree: DecreeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DecreeUpdateComponent,
    resolve: {
      decree: DecreeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(decreeRoute)],
  exports: [RouterModule],
})
export class DecreeRoutingModule {}
