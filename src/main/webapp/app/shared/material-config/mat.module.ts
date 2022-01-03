import { OverlayModule } from '@angular/cdk/overlay';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatBadgeModule } from '@angular/material/badge';
import { MatButtonModule } from '@angular/material/button';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatChipsModule } from '@angular/material/chips';
import { MAT_DATE_FORMATS, MAT_DATE_LOCALE, MatDateFormats, MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatDividerModule } from '@angular/material/divider';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule, MatIconRegistry } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatPaginatorIntl, MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSelectModule } from '@angular/material/select';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatSortModule } from '@angular/material/sort';
import { MatStepperModule } from '@angular/material/stepper';
import { MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatTreeModule } from '@angular/material/tree';
import { DomSanitizer } from '@angular/platform-browser';
import { EmptyContentComponent } from 'app/shared/empty-content/empty-content.component';
import { CMatPaginatorIntl } from 'app/shared/material-config/cmat.paginator';
import { MatRadioModule } from '@angular/material/radio';

export const MOMENT_DATE_FORMATS: MatDateFormats = {
  parse: {
    dateInput: 'YYYY-MM-DD',
  },
  display: {
    dateInput: 'YYYY-MM-DD',
    monthYearLabel: 'MMMM Y',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM Y',
  },
};

@NgModule({
  imports: [
    CommonModule,
    MatSidenavModule,
    MatListModule,
    MatInputModule,
    MatIconModule,
    MatCardModule,
    MatMenuModule,
    MatToolbarModule,
    MatFormFieldModule,
    MatButtonModule,
    MatTreeModule,
    MatExpansionModule,
    MatDatepickerModule,
    MatDividerModule,
    MatTableModule,
    MatDialogModule,
    MatSortModule,
    MatPaginatorModule,
    MatMenuModule,
    MatSelectModule,
    MatProgressBarModule,
    MatExpansionModule,
    MatTabsModule,
    MatCheckboxModule,
    MatTooltipModule,
    MatBadgeModule,
    OverlayModule,
    MatProgressSpinnerModule,
    MatButtonToggleModule,
    MatAutocompleteModule,
    ReactiveFormsModule,
    FormsModule,
    MatChipsModule,
    MatStepperModule,
    MatNativeDateModule,
    MatRadioModule,
  ],
  exports: [
    CommonModule,
    MatSidenavModule,
    MatListModule,
    MatInputModule,
    MatIconModule,
    MatCardModule,
    MatMenuModule,
    MatToolbarModule,
    MatFormFieldModule,
    MatButtonModule,
    MatTreeModule,
    MatExpansionModule,
    MatDatepickerModule,
    MatDividerModule,
    MatTableModule,
    MatDialogModule,
    MatSortModule,
    MatPaginatorModule,
    MatMenuModule,
    MatSelectModule,
    MatProgressBarModule,
    MatExpansionModule,
    MatTabsModule,
    MatCheckboxModule,
    MatTooltipModule,
    MatBadgeModule,
    OverlayModule,
    MatProgressSpinnerModule,
    MatButtonToggleModule,
    MatAutocompleteModule,
    ReactiveFormsModule,
    FormsModule,
    MatChipsModule,
    MatStepperModule,
    EmptyContentComponent,
    MatNativeDateModule,
    MatRadioModule,
  ],
  declarations: [EmptyContentComponent],
  providers: [
    MatDatepickerModule,
    { provide: MAT_DIALOG_DATA, useValue: {} },
    { provide: MatPaginatorIntl, useValue: new CMatPaginatorIntl() },
    { provide: MAT_DATE_LOCALE, useValue: 'ar-ly' },
    { provide: MAT_DATE_FORMATS, useValue: MOMENT_DATE_FORMATS },
  ],
})
export class MatModule {
  constructor(private iconRegistry: MatIconRegistry, private sanitizer: DomSanitizer) {
    this.iconRegistry.addSvgIcon('search', sanitizer.bypassSecurityTrustResourceUrl('content/images/search.svg'));
    this.iconRegistry.addSvgIcon('sign', sanitizer.bypassSecurityTrustResourceUrl('content/images/sign.svg'));
    this.iconRegistry.addSvgIcon('files', sanitizer.bypassSecurityTrustResourceUrl('content/images/files.svg'));
    this.iconRegistry.addSvgIcon('business', sanitizer.bypassSecurityTrustResourceUrl('content/images/business.svg'));
    this.iconRegistry.addSvgIcon('person_search', sanitizer.bypassSecurityTrustResourceUrl('content/images/person_search.svg'));
    this.iconRegistry.addSvgIcon('person_plus', sanitizer.bypassSecurityTrustResourceUrl('content/images/person_plus.svg'));
    this.iconRegistry.addSvgIcon('rule', sanitizer.bypassSecurityTrustResourceUrl('content/images/rule.svg'));

    this.iconRegistry.addSvgIcon('done', sanitizer.bypassSecurityTrustResourceUrl('content/images/done.svg'));
    this.iconRegistry.addSvgIcon('cancel', sanitizer.bypassSecurityTrustResourceUrl('content/images/cancel.svg'));
    this.iconRegistry.addSvgIcon('info', sanitizer.bypassSecurityTrustResourceUrl('content/images/info.svg'));
  }
}
