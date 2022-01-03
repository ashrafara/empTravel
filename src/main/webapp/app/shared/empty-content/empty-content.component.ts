import { Component, Input } from '@angular/core';

@Component({
  selector: 'jhi-empty-content',
  templateUrl: './empty-content.component.html',
})
export class EmptyContentComponent {
  @Input() isLoading = false;
}
