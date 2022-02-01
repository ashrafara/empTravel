import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { Decree, IDecree } from '../decree.model';
import { DecreeService } from '../service/decree.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { Employee, IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { IDecreeIssue } from 'app/entities/decree-issue/decree-issue.model';
import { DecreeIssueService } from 'app/entities/decree-issue/service/decree-issue.service';
import { ICountry } from 'app/entities/country/country.model';
import { CountryService } from 'app/entities/country/service/country.service';
import { DecType } from 'app/entities/enumerations/dec-type.model';
import { INgxSelectOption } from 'ngx-select-ex';

@Component({
  selector: 'jhi-decree-update',
  templateUrl: './decree-update.component.html',
})
export class DecreeUpdateComponent implements OnInit {
  isSaving = false;
  decTypeValues = Object.keys(DecType);
  selectedEmployees: IEmployee[] = [];
  selectControl = new FormControl();

  employeesSharedCollection: IEmployee[] = [];
  decreeIssuesSharedCollection: IDecreeIssue[] = [];
  countriesSharedCollection: ICountry[] = [];

  editForm = this.fb.group({
    id: [],
    decreenum: [null, [Validators.required]],
    decreeyear: [null, [Validators.required]],
    purpose: [],
    dectype: [],
    daynum: [null, [Validators.required]],
    city: [],
    countrty: [null, [Validators.required]],
    startDate: [],
    endDate: [],
    area: [],
    cost: [],
    decreecost: [],
    imageUrl: [],
    image: [],
    imageContentType: [],
    employees: [],
    decreeissue: [],
    sponsor: [],
    proponent: [],
    country: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected decreeService: DecreeService,
    protected employeeService: EmployeeService,
    protected decreeIssueService: DecreeIssueService,
    protected countryService: CountryService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ decree }) => {
      this.updateForm(decree);

      this.selectedEmployees = decree.employees;

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('empTravelApp.error', { message: err.message })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const decree = this.createFromForm();
    decree.employees = this.selectedEmployees;
    if (decree.id !== undefined) {
      this.subscribeToSaveResponse(this.decreeService.update(decree));
    } else {
      this.subscribeToSaveResponse(this.decreeService.create(decree));
    }
  }

  trackEmployeeById(index: number, item: IEmployee): number {
    return item.id!;
  }

  trackDecreeIssueById(index: number, item: IDecreeIssue): number {
    return item.id!;
  }

  trackCountryById(index: number, item: ICountry): number {
    return item.id!;
  }

  getSelectedEmployee(option: IEmployee, selectedVals?: IEmployee[]): IEmployee {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  doSelectionChanges(ngxSelectOptions: INgxSelectOption[]): void {
    this.selectedEmployees = [];
    ngxSelectOptions.forEach(ngxSelectOption => {
      const employee = new Employee();
      employee.id = Number(ngxSelectOption.value);
      // eslint-disable-next-line no-console
      console.log(ngxSelectOption.value);
      this.selectedEmployees.push(employee);
    });

    // eslint-disable-next-line no-console
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDecree>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(decree: IDecree): void {
    this.editForm.patchValue({
      id: decree.id,
      decreenum: decree.decreenum,
      decreeyear: decree.decreeyear,
      purpose: decree.purpose,
      dectype: decree.dectype,
      daynum: decree.daynum,
      city: decree.city,
      countrty: decree.countrty,
      startDate: decree.startDate,
      endDate: decree.endDate,
      area: decree.area,
      cost: decree.cost,
      decreecost: decree.decreecost,
      imageUrl: decree.imageUrl,
      image: decree.image,
      imageContentType: decree.imageContentType,
      employees: decree.employees,
      decreeissue: decree.decreeissue,
      sponsor: decree.sponsor,
      proponent: decree.proponent,
      country: decree.country,
    });

    this.employeesSharedCollection = this.employeeService.addEmployeeToCollectionIfMissing(
      this.employeesSharedCollection,
      ...(decree.employees ?? [])
    );
    this.decreeIssuesSharedCollection = this.decreeIssueService.addDecreeIssueToCollectionIfMissing(
      this.decreeIssuesSharedCollection,
      decree.decreeissue,
      decree.sponsor,
      decree.proponent
    );
    this.countriesSharedCollection = this.countryService.addCountryToCollectionIfMissing(this.countriesSharedCollection, decree.country);
  }

  protected loadRelationshipsOptions(): void {
    this.employeeService
      .query({ size: 5000 })
      .pipe(map((res: HttpResponse<IEmployee[]>) => res.body ?? []))
      .pipe(
        map((employees: IEmployee[]) =>
          this.employeeService.addEmployeeToCollectionIfMissing(employees, ...(this.editForm.get('employees')!.value ?? []))
        )
      )
      .subscribe((employees: IEmployee[]) => (this.employeesSharedCollection = employees));

    this.decreeIssueService
      .query({ size: 5000 })
      .pipe(map((res: HttpResponse<IDecreeIssue[]>) => res.body ?? []))
      .pipe(
        map((decreeIssues: IDecreeIssue[]) =>
          this.decreeIssueService.addDecreeIssueToCollectionIfMissing(
            decreeIssues,
            this.editForm.get('decreeissue')!.value,
            this.editForm.get('sponsor')!.value,
            this.editForm.get('proponent')!.value
          )
        )
      )
      .subscribe((decreeIssues: IDecreeIssue[]) => (this.decreeIssuesSharedCollection = decreeIssues));

    this.countryService
      .query()
      .pipe(map((res: HttpResponse<ICountry[]>) => res.body ?? []))
      .pipe(
        map((countries: ICountry[]) => this.countryService.addCountryToCollectionIfMissing(countries, this.editForm.get('country')!.value))
      )
      .subscribe((countries: ICountry[]) => (this.countriesSharedCollection = countries));
  }

  protected createFromForm(): IDecree {
    return {
      ...new Decree(),
      id: this.editForm.get(['id'])!.value,
      decreenum: this.editForm.get(['decreenum'])!.value,
      decreeyear: this.editForm.get(['decreeyear'])!.value,
      purpose: this.editForm.get(['purpose'])!.value,
      dectype: this.editForm.get(['dectype'])!.value,
      daynum: this.editForm.get(['daynum'])!.value,
      city: this.editForm.get(['city'])!.value,
      countrty: this.editForm.get(['countrty'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      endDate: this.editForm.get(['endDate'])!.value,
      area: this.editForm.get(['area'])!.value,
      cost: this.editForm.get(['cost'])!.value,
      decreecost: this.editForm.get(['decreecost'])!.value,
      imageUrl: this.editForm.get(['imageUrl'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      employees: this.editForm.get(['employees'])!.value,
      decreeissue: this.editForm.get(['decreeissue'])!.value,
      sponsor: this.editForm.get(['sponsor'])!.value,
      proponent: this.editForm.get(['proponent'])!.value,
      country: this.editForm.get(['country'])!.value,
    };
  }
}
