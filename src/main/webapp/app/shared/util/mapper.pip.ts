import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'translateMe',
})
export class TranslateMePipe implements PipeTransform {
  translations: any;

  constructor() {
    this.translations = new Map<string, string>();
    this.translations.set('ROLE_ADMIN', 'مدير نظام');
    this.translations.set('ROLE_USER', 'مستخدم');

    this.translations.set('TRAINING', 'تدريبية');
    this.translations.set('WORK', 'مهمة عمل');
    this.translations.set('MISSION', 'مهمة رسمية');
  }

  transform(value: any): unknown {
    return this.translations.get(value) ?? value;
  }
}
