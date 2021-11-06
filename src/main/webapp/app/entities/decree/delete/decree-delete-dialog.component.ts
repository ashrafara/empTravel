import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDecree } from '../decree.model';
import { DecreeService } from '../service/decree.service';

@Component({
  templateUrl: './decree-delete-dialog.component.html',
})
export class DecreeDeleteDialogComponent {
  decree?: IDecree;

  constructor(protected decreeService: DecreeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.decreeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
