import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDecreeIssue, DecreeIssue } from '../decree-issue.model';

import { DecreeIssueService } from './decree-issue.service';

describe('DecreeIssue Service', () => {
  let service: DecreeIssueService;
  let httpMock: HttpTestingController;
  let elemDefault: IDecreeIssue;
  let expectedResult: IDecreeIssue | IDecreeIssue[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DecreeIssueService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a DecreeIssue', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new DecreeIssue()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DecreeIssue', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DecreeIssue', () => {
      const patchObject = Object.assign({}, new DecreeIssue());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DecreeIssue', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a DecreeIssue', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDecreeIssueToCollectionIfMissing', () => {
      it('should add a DecreeIssue to an empty array', () => {
        const decreeIssue: IDecreeIssue = { id: 123 };
        expectedResult = service.addDecreeIssueToCollectionIfMissing([], decreeIssue);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(decreeIssue);
      });

      it('should not add a DecreeIssue to an array that contains it', () => {
        const decreeIssue: IDecreeIssue = { id: 123 };
        const decreeIssueCollection: IDecreeIssue[] = [
          {
            ...decreeIssue,
          },
          { id: 456 },
        ];
        expectedResult = service.addDecreeIssueToCollectionIfMissing(decreeIssueCollection, decreeIssue);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DecreeIssue to an array that doesn't contain it", () => {
        const decreeIssue: IDecreeIssue = { id: 123 };
        const decreeIssueCollection: IDecreeIssue[] = [{ id: 456 }];
        expectedResult = service.addDecreeIssueToCollectionIfMissing(decreeIssueCollection, decreeIssue);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(decreeIssue);
      });

      it('should add only unique DecreeIssue to an array', () => {
        const decreeIssueArray: IDecreeIssue[] = [{ id: 123 }, { id: 456 }, { id: 60082 }];
        const decreeIssueCollection: IDecreeIssue[] = [{ id: 123 }];
        expectedResult = service.addDecreeIssueToCollectionIfMissing(decreeIssueCollection, ...decreeIssueArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const decreeIssue: IDecreeIssue = { id: 123 };
        const decreeIssue2: IDecreeIssue = { id: 456 };
        expectedResult = service.addDecreeIssueToCollectionIfMissing([], decreeIssue, decreeIssue2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(decreeIssue);
        expect(expectedResult).toContain(decreeIssue2);
      });

      it('should accept null and undefined values', () => {
        const decreeIssue: IDecreeIssue = { id: 123 };
        expectedResult = service.addDecreeIssueToCollectionIfMissing([], null, decreeIssue, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(decreeIssue);
      });

      it('should return initial array if no DecreeIssue is added', () => {
        const decreeIssueCollection: IDecreeIssue[] = [{ id: 123 }];
        expectedResult = service.addDecreeIssueToCollectionIfMissing(decreeIssueCollection, undefined, null);
        expect(expectedResult).toEqual(decreeIssueCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
