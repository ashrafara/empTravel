import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDecreeIssue } from '../decree-issue.model';

@Component({
  selector: 'jhi-decree-issue-detail',
  templateUrl: './decree-issue-detail.component.html',
})
export class DecreeIssueDetailComponent implements OnInit {
  decreeIssue: IDecreeIssue | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ decreeIssue }) => {
      this.decreeIssue = decreeIssue;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
