import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IDecreeIssue, DecreeIssue } from '../decree-issue.model';
import { DecreeIssueService } from '../service/decree-issue.service';

@Component({
  selector: 'jhi-decree-issue-update',
  templateUrl: './decree-issue-update.component.html',
})
export class DecreeIssueUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
  });

  constructor(protected decreeIssueService: DecreeIssueService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ decreeIssue }) => {
      this.updateForm(decreeIssue);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const decreeIssue = this.createFromForm();
    if (decreeIssue.id !== undefined) {
      this.subscribeToSaveResponse(this.decreeIssueService.update(decreeIssue));
    } else {
      this.subscribeToSaveResponse(this.decreeIssueService.create(decreeIssue));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDecreeIssue>>): void {
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

  protected updateForm(decreeIssue: IDecreeIssue): void {
    this.editForm.patchValue({
      id: decreeIssue.id,
      name: decreeIssue.name,
    });
  }

  protected createFromForm(): IDecreeIssue {
    return {
      ...new DecreeIssue(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }
}
