import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DecreeComponent } from './list/decree.component';
import { DecreeDetailComponent } from './detail/decree-detail.component';
import { DecreeUpdateComponent } from './update/decree-update.component';
import { DecreeDeleteDialogComponent } from './delete/decree-delete-dialog.component';
import { DecreeRoutingModule } from './route/decree-routing.module';

@NgModule({
  imports: [SharedModule, DecreeRoutingModule],
  declarations: [DecreeComponent, DecreeDetailComponent, DecreeUpdateComponent, DecreeDeleteDialogComponent],
  entryComponents: [DecreeDeleteDialogComponent],
})
export class DecreeModule {}
