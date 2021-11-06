import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DecreeIssueComponent } from './list/decree-issue.component';
import { DecreeIssueDetailComponent } from './detail/decree-issue-detail.component';
import { DecreeIssueUpdateComponent } from './update/decree-issue-update.component';
import { DecreeIssueDeleteDialogComponent } from './delete/decree-issue-delete-dialog.component';
import { DecreeIssueRoutingModule } from './route/decree-issue-routing.module';

@NgModule({
  imports: [SharedModule, DecreeIssueRoutingModule],
  declarations: [DecreeIssueComponent, DecreeIssueDetailComponent, DecreeIssueUpdateComponent, DecreeIssueDeleteDialogComponent],
  entryComponents: [DecreeIssueDeleteDialogComponent],
})
export class DecreeIssueModule {}
