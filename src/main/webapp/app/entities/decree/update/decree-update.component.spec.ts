jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DecreeService } from '../service/decree.service';
import { IDecree, Decree } from '../decree.model';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { IDecreeIssue } from 'app/entities/decree-issue/decree-issue.model';
import { DecreeIssueService } from 'app/entities/decree-issue/service/decree-issue.service';

import { DecreeUpdateComponent } from './decree-update.component';

describe('Decree Management Update Component', () => {
  let comp: DecreeUpdateComponent;
  let fixture: ComponentFixture<DecreeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let decreeService: DecreeService;
  let employeeService: EmployeeService;
  let decreeIssueService: DecreeIssueService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DecreeUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(DecreeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DecreeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    decreeService = TestBed.inject(DecreeService);
    employeeService = TestBed.inject(EmployeeService);
    decreeIssueService = TestBed.inject(DecreeIssueService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Employee query and add missing value', () => {
      const decree: IDecree = { id: 456 };
      const employees: IEmployee[] = [{ id: 53169 }];
      decree.employees = employees;

      const employeeCollection: IEmployee[] = [{ id: 96305 }];
      jest.spyOn(employeeService, 'query').mockReturnValue(of(new HttpResponse({ body: employeeCollection })));
      const additionalEmployees = [...employees];
      const expectedCollection: IEmployee[] = [...additionalEmployees, ...employeeCollection];
      jest.spyOn(employeeService, 'addEmployeeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ decree });
      comp.ngOnInit();

      expect(employeeService.query).toHaveBeenCalled();
      expect(employeeService.addEmployeeToCollectionIfMissing).toHaveBeenCalledWith(employeeCollection, ...additionalEmployees);
      expect(comp.employeesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DecreeIssue query and add missing value', () => {
      const decree: IDecree = { id: 456 };
      const decreeissue: IDecreeIssue = { id: 19851 };
      decree.decreeissue = decreeissue;
      const sponsor: IDecreeIssue = { id: 89023 };
      decree.sponsor = sponsor;
      const proponent: IDecreeIssue = { id: 6754 };
      decree.proponent = proponent;

      const decreeIssueCollection: IDecreeIssue[] = [{ id: 22545 }];
      jest.spyOn(decreeIssueService, 'query').mockReturnValue(of(new HttpResponse({ body: decreeIssueCollection })));
      const additionalDecreeIssues = [decreeissue, sponsor, proponent];
      const expectedCollection: IDecreeIssue[] = [...additionalDecreeIssues, ...decreeIssueCollection];
      jest.spyOn(decreeIssueService, 'addDecreeIssueToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ decree });
      comp.ngOnInit();

      expect(decreeIssueService.query).toHaveBeenCalled();
      expect(decreeIssueService.addDecreeIssueToCollectionIfMissing).toHaveBeenCalledWith(decreeIssueCollection, ...additionalDecreeIssues);
      expect(comp.decreeIssuesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const decree: IDecree = { id: 456 };
      const employees: IEmployee = { id: 39020 };
      decree.employees = [employees];
      const decreeissue: IDecreeIssue = { id: 48950 };
      decree.decreeissue = decreeissue;
      const sponsor: IDecreeIssue = { id: 84029 };
      decree.sponsor = sponsor;
      const proponent: IDecreeIssue = { id: 6925 };
      decree.proponent = proponent;

      activatedRoute.data = of({ decree });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(decree));
      expect(comp.employeesSharedCollection).toContain(employees);
      expect(comp.decreeIssuesSharedCollection).toContain(decreeissue);
      expect(comp.decreeIssuesSharedCollection).toContain(sponsor);
      expect(comp.decreeIssuesSharedCollection).toContain(proponent);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Decree>>();
      const decree = { id: 123 };
      jest.spyOn(decreeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ decree });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: decree }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(decreeService.update).toHaveBeenCalledWith(decree);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Decree>>();
      const decree = new Decree();
      jest.spyOn(decreeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ decree });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: decree }));
      saveSubject.complete();

      // THEN
      expect(decreeService.create).toHaveBeenCalledWith(decree);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Decree>>();
      const decree = { id: 123 };
      jest.spyOn(decreeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ decree });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(decreeService.update).toHaveBeenCalledWith(decree);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackEmployeeById', () => {
      it('Should return tracked Employee primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEmployeeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackDecreeIssueById', () => {
      it('Should return tracked DecreeIssue primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDecreeIssueById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });

  describe('Getting selected relationships', () => {
    describe('getSelectedEmployee', () => {
      it('Should return option if no Employee is selected', () => {
        const option = { id: 123 };
        const result = comp.getSelectedEmployee(option);
        expect(result === option).toEqual(true);
      });

      it('Should return selected Employee for according option', () => {
        const option = { id: 123 };
        const selected = { id: 123 };
        const selected2 = { id: 456 };
        const result = comp.getSelectedEmployee(option, [selected2, selected]);
        expect(result === selected).toEqual(true);
        expect(result === selected2).toEqual(false);
        expect(result === option).toEqual(false);
      });

      it('Should return option if this Employee is not selected', () => {
        const option = { id: 123 };
        const selected = { id: 456 };
        const result = comp.getSelectedEmployee(option, [selected]);
        expect(result === option).toEqual(true);
        expect(result === selected).toEqual(false);
      });
    });
  });
});
