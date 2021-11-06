import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDecreeIssue } from '../decree-issue.model';
import { DecreeIssueService } from '../service/decree-issue.service';

@Component({
  templateUrl: './decree-issue-delete-dialog.component.html',
})
export class DecreeIssueDeleteDialogComponent {
  decreeIssue?: IDecreeIssue;

  constructor(protected decreeIssueService: DecreeIssueService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.decreeIssueService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
