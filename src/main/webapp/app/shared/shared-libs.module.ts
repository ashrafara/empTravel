import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { MatModule } from './material-config/mat.module';

@NgModule({
  imports: [NgbModule, FontAwesomeModule, MatModule],
  exports: [FormsModule, CommonModule, NgbModule, FontAwesomeModule, ReactiveFormsModule, MatModule],
})
export class SharedLibsModule {}
