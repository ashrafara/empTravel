import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DecreeIssueDetailComponent } from './decree-issue-detail.component';

describe('DecreeIssue Management Detail Component', () => {
  let comp: DecreeIssueDetailComponent;
  let fixture: ComponentFixture<DecreeIssueDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DecreeIssueDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ decreeIssue: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DecreeIssueDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DecreeIssueDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load decreeIssue on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.decreeIssue).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
