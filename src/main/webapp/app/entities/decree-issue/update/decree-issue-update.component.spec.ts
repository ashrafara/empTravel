jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DecreeIssueService } from '../service/decree-issue.service';
import { IDecreeIssue, DecreeIssue } from '../decree-issue.model';

import { DecreeIssueUpdateComponent } from './decree-issue-update.component';

describe('DecreeIssue Management Update Component', () => {
  let comp: DecreeIssueUpdateComponent;
  let fixture: ComponentFixture<DecreeIssueUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let decreeIssueService: DecreeIssueService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DecreeIssueUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(DecreeIssueUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DecreeIssueUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    decreeIssueService = TestBed.inject(DecreeIssueService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const decreeIssue: IDecreeIssue = { id: 456 };

      activatedRoute.data = of({ decreeIssue });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(decreeIssue));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DecreeIssue>>();
      const decreeIssue = { id: 123 };
      jest.spyOn(decreeIssueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ decreeIssue });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: decreeIssue }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(decreeIssueService.update).toHaveBeenCalledWith(decreeIssue);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DecreeIssue>>();
      const decreeIssue = new DecreeIssue();
      jest.spyOn(decreeIssueService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ decreeIssue });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: decreeIssue }));
      saveSubject.complete();

      // THEN
      expect(decreeIssueService.create).toHaveBeenCalledWith(decreeIssue);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DecreeIssue>>();
      const decreeIssue = { id: 123 };
      jest.spyOn(decreeIssueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ decreeIssue });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(decreeIssueService.update).toHaveBeenCalledWith(decreeIssue);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
