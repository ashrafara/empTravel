import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISector, Sector } from '../sector.model';
import { SectorService } from '../service/sector.service';

@Component({
  selector: 'jhi-sector-update',
  templateUrl: './sector-update.component.html',
})
export class SectorUpdateComponent implements OnInit {
  isSaving = false;

  sectorsSharedCollection: ISector[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    phone: [],
    address: [],
    sector: [],
  });

  constructor(protected sectorService: SectorService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sector }) => {
      this.updateForm(sector);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sector = this.createFromForm();
    if (sector.id !== undefined) {
      this.subscribeToSaveResponse(this.sectorService.update(sector));
    } else {
      this.subscribeToSaveResponse(this.sectorService.create(sector));
    }
  }

  trackSectorById(index: number, item: ISector): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISector>>): void {
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

  protected updateForm(sector: ISector): void {
    this.editForm.patchValue({
      id: sector.id,
      name: sector.name,
      phone: sector.phone,
      address: sector.address,
      sector: sector.sector,
    });

    this.sectorsSharedCollection = this.sectorService.addSectorToCollectionIfMissing(this.sectorsSharedCollection, sector.sector);
  }

  protected loadRelationshipsOptions(): void {
    this.sectorService
      .query()
      .pipe(map((res: HttpResponse<ISector[]>) => res.body ?? []))
      .pipe(map((sectors: ISector[]) => this.sectorService.addSectorToCollectionIfMissing(sectors, this.editForm.get('sector')!.value)))
      .subscribe((sectors: ISector[]) => (this.sectorsSharedCollection = sectors));
  }

  protected createFromForm(): ISector {
    return {
      ...new Sector(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      address: this.editForm.get(['address'])!.value,
      sector: this.editForm.get(['sector'])!.value,
    };
  }
}
