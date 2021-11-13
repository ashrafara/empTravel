import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEmployee, Employee } from '../employee.model';
import { EmployeeService } from '../service/employee.service';
import { ISector } from 'app/entities/sector/sector.model';
import { SectorService } from 'app/entities/sector/service/sector.service';
import { IDegree } from 'app/entities/degree/degree.model';
import { DegreeService } from 'app/entities/degree/service/degree.service';

@Component({
  selector: 'jhi-employee-update',
  templateUrl: './employee-update.component.html',
})
export class EmployeeUpdateComponent implements OnInit {
  isSaving = false;

  sectorsSharedCollection: ISector[] = [];
  degreesSharedCollection: IDegree[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    jobposition: [],
    phone: [],
    departement: [],
    sector: [],
    degree: [],
  });

  constructor(
    protected employeeService: EmployeeService,
    protected sectorService: SectorService,
    protected degreeService: DegreeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employee }) => {
      this.updateForm(employee);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employee = this.createFromForm();
    if (employee.id !== undefined) {
      this.subscribeToSaveResponse(this.employeeService.update(employee));
    } else {
      this.subscribeToSaveResponse(this.employeeService.create(employee));
    }
  }

  trackSectorById(index: number, item: ISector): number {
    return item.id!;
  }

  trackDegreeById(index: number, item: IDegree): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployee>>): void {
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

  protected updateForm(employee: IEmployee): void {
    this.editForm.patchValue({
      id: employee.id,
      name: employee.name,
      jobposition: employee.jobposition,
      phone: employee.phone,
      departement: employee.departement,
      sector: employee.sector,
      degree: employee.degree,
    });

    this.sectorsSharedCollection = this.sectorService.addSectorToCollectionIfMissing(this.sectorsSharedCollection, employee.sector);
    this.degreesSharedCollection = this.degreeService.addDegreeToCollectionIfMissing(this.degreesSharedCollection, employee.degree);
  }

  protected loadRelationshipsOptions(): void {
    this.sectorService
      .query({ size: 5000 })
      .pipe(map((res: HttpResponse<ISector[]>) => res.body ?? []))
      .pipe(map((sectors: ISector[]) => this.sectorService.addSectorToCollectionIfMissing(sectors, this.editForm.get('sector')!.value)))
      .subscribe((sectors: ISector[]) => (this.sectorsSharedCollection = sectors));

    this.degreeService
      .query({ size: 5000 })
      .pipe(map((res: HttpResponse<IDegree[]>) => res.body ?? []))
      .pipe(map((degrees: IDegree[]) => this.degreeService.addDegreeToCollectionIfMissing(degrees, this.editForm.get('degree')!.value)))
      .subscribe((degrees: IDegree[]) => (this.degreesSharedCollection = degrees));
  }

  protected createFromForm(): IEmployee {
    return {
      ...new Employee(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      jobposition: this.editForm.get(['jobposition'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      departement: this.editForm.get(['departement'])!.value,
      sector: this.editForm.get(['sector'])!.value,
      degree: this.editForm.get(['degree'])!.value,
    };
  }
}
