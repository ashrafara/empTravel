import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DecreeIssueComponent } from '../list/decree-issue.component';
import { DecreeIssueDetailComponent } from '../detail/decree-issue-detail.component';
import { DecreeIssueUpdateComponent } from '../update/decree-issue-update.component';
import { DecreeIssueRoutingResolveService } from './decree-issue-routing-resolve.service';

const decreeIssueRoute: Routes = [
  {
    path: '',
    component: DecreeIssueComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DecreeIssueDetailComponent,
    resolve: {
      decreeIssue: DecreeIssueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DecreeIssueUpdateComponent,
    resolve: {
      decreeIssue: DecreeIssueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DecreeIssueUpdateComponent,
    resolve: {
      decreeIssue: DecreeIssueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(decreeIssueRoute)],
  exports: [RouterModule],
})
export class DecreeIssueRoutingModule {}
