import { MatPaginatorIntl } from '@angular/material/paginator';

export class CMatPaginatorIntl extends MatPaginatorIntl {
  itemsPerPageLabel = 'حجم الصفحة';
  nextPageLabel = 'التالي';
  lastPageLabel = 'آخر صفحة';
  previousPageLabel = 'السابق';
  firstPageLabel = 'أول صفحة';

  getRangeLabel = function (page: number, pageSize: number, length: number): any {
    if (length === 0 || pageSize === 0) {
      return ` 0  من  ${String(length)}`;
    }
    length = Math.max(length, 0);
    const startIndex = page * pageSize;
    // If the start index exceeds the list length, do not try and fix the end index to the end.
    const endIndex = startIndex < length ? Math.min(startIndex + pageSize, length) : startIndex + pageSize;
    return `${String(startIndex + 1)} - ${String(endIndex)} من ${String(length)}`;
  };
}
