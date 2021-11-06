jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EmployeeService } from '../service/employee.service';
import { IEmployee, Employee } from '../employee.model';
import { ISector } from 'app/entities/sector/sector.model';
import { SectorService } from 'app/entities/sector/service/sector.service';
import { IDegree } from 'app/entities/degree/degree.model';
import { DegreeService } from 'app/entities/degree/service/degree.service';

import { EmployeeUpdateComponent } from './employee-update.component';

describe('Employee Management Update Component', () => {
  let comp: EmployeeUpdateComponent;
  let fixture: ComponentFixture<EmployeeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let employeeService: EmployeeService;
  let sectorService: SectorService;
  let degreeService: DegreeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [EmployeeUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(EmployeeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmployeeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    employeeService = TestBed.inject(EmployeeService);
    sectorService = TestBed.inject(SectorService);
    degreeService = TestBed.inject(DegreeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Sector query and add missing value', () => {
      const employee: IEmployee = { id: 456 };
      const sector: ISector = { id: 53597 };
      employee.sector = sector;

      const sectorCollection: ISector[] = [{ id: 33094 }];
      jest.spyOn(sectorService, 'query').mockReturnValue(of(new HttpResponse({ body: sectorCollection })));
      const additionalSectors = [sector];
      const expectedCollection: ISector[] = [...additionalSectors, ...sectorCollection];
      jest.spyOn(sectorService, 'addSectorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ employee });
      comp.ngOnInit();

      expect(sectorService.query).toHaveBeenCalled();
      expect(sectorService.addSectorToCollectionIfMissing).toHaveBeenCalledWith(sectorCollection, ...additionalSectors);
      expect(comp.sectorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Degree query and add missing value', () => {
      const employee: IEmployee = { id: 456 };
      const degree: IDegree = { id: 76842 };
      employee.degree = degree;

      const degreeCollection: IDegree[] = [{ id: 3692 }];
      jest.spyOn(degreeService, 'query').mockReturnValue(of(new HttpResponse({ body: degreeCollection })));
      const additionalDegrees = [degree];
      const expectedCollection: IDegree[] = [...additionalDegrees, ...degreeCollection];
      jest.spyOn(degreeService, 'addDegreeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ employee });
      comp.ngOnInit();

      expect(degreeService.query).toHaveBeenCalled();
      expect(degreeService.addDegreeToCollectionIfMissing).toHaveBeenCalledWith(degreeCollection, ...additionalDegrees);
      expect(comp.degreesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const employee: IEmployee = { id: 456 };
      const sector: ISector = { id: 22695 };
      employee.sector = sector;
      const degree: IDegree = { id: 66778 };
      employee.degree = degree;

      activatedRoute.data = of({ employee });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(employee));
      expect(comp.sectorsSharedCollection).toContain(sector);
      expect(comp.degreesSharedCollection).toContain(degree);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Employee>>();
      const employee = { id: 123 };
      jest.spyOn(employeeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employee });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: employee }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(employeeService.update).toHaveBeenCalledWith(employee);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Employee>>();
      const employee = new Employee();
      jest.spyOn(employeeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employee });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: employee }));
      saveSubject.complete();

      // THEN
      expect(employeeService.create).toHaveBeenCalledWith(employee);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Employee>>();
      const employee = { id: 123 };
      jest.spyOn(employeeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employee });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(employeeService.update).toHaveBeenCalledWith(employee);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSectorById', () => {
      it('Should return tracked Sector primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSectorById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackDegreeById', () => {
      it('Should return tracked Degree primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDegreeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
