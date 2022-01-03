import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'dashboard',
        data: { pageTitle: 'dashboard' },
        loadChildren: () => import('./dashboard/dashboard.module').then(m => m.DashboardModule),
      },
      {
        path: 'employee',
        data: { pageTitle: 'Employees' },
        loadChildren: () => import('./employee/employee.module').then(m => m.EmployeeModule),
      },
      {
        path: 'sector',
        data: { pageTitle: 'Sectors' },
        loadChildren: () => import('./sector/sector.module').then(m => m.SectorModule),
      },
      {
        path: 'decree',
        data: { pageTitle: 'Decrees' },
        loadChildren: () => import('./decree/decree.module').then(m => m.DecreeModule),
      },
      {
        path: 'decree-issue',
        data: { pageTitle: 'DecreeIssues' },
        loadChildren: () => import('./decree-issue/decree-issue.module').then(m => m.DecreeIssueModule),
      },
      {
        path: 'degree',
        data: { pageTitle: 'Degrees' },
        loadChildren: () => import('./degree/degree.module').then(m => m.DegreeModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
