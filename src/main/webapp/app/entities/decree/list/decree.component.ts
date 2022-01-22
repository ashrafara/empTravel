import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDecree } from '../decree.model';

import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/config/pagination.constants';
import { DecreeService } from '../service/decree.service';
import { DecreeDeleteDialogComponent } from '../delete/decree-delete-dialog.component';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-decree',
  templateUrl: './decree.component.html',
})
export class DecreeComponent implements OnInit {
  decrees?: IDecree[];
  isLoading = false;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page?: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected decreeService: DecreeService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: DataUtils,
    protected router: Router,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    this.isLoading = true;
    const pageToLoad: number = page ?? this.page ?? 1;

    this.decreeService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IDecree[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate);
        },
        () => {
          this.isLoading = false;
          this.onError();
        }
      );
  }
  ngOnInit(): void {
    this.handleNavigation();
  }

  trackId(index: number, item: IDecree): number {
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(fileUrl: any): void {
    window.open('/api/public/file/download/' + String(fileUrl), '_blank');
  }

  delete(decree: IDecree): void {
    const modalRef = this.modalService.open(DecreeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.decree = decree;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadPage();
      }
    });
  }

  printReport(): void {
    const url = '/api/public/decrees/xlsx';
    window.open(url, '_blank');
  }

  printDaysReport(): void {
    const url = '/api/public/decrees/count-decree/xlsx';
    window.open(url, '_blank');
  }

  printDecreeReport(): void {
    const url = '/api/public/decrees/count-days/xlsx';
    window.open(url, '_blank');
  }

  protected sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? ASC : DESC)];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected handleNavigation(): void {
    combineLatest([this.activatedRoute.data, this.activatedRoute.queryParamMap]).subscribe(([data, params]) => {
      const page = params.get('page');
      const pageNumber = +(page ?? 1);
      const sort = (params.get(SORT) ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === ASC;
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber, true);
      }
    });
  }

  protected onSuccess(data: IDecree[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/decree'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? ASC : DESC),
        },
      });
    }
    this.decrees = data ?? [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
