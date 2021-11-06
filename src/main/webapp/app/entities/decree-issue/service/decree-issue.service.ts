import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDecreeIssue, getDecreeIssueIdentifier } from '../decree-issue.model';

export type EntityResponseType = HttpResponse<IDecreeIssue>;
export type EntityArrayResponseType = HttpResponse<IDecreeIssue[]>;

@Injectable({ providedIn: 'root' })
export class DecreeIssueService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/decree-issues');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(decreeIssue: IDecreeIssue): Observable<EntityResponseType> {
    return this.http.post<IDecreeIssue>(this.resourceUrl, decreeIssue, { observe: 'response' });
  }

  update(decreeIssue: IDecreeIssue): Observable<EntityResponseType> {
    return this.http.put<IDecreeIssue>(`${this.resourceUrl}/${getDecreeIssueIdentifier(decreeIssue) as number}`, decreeIssue, {
      observe: 'response',
    });
  }

  partialUpdate(decreeIssue: IDecreeIssue): Observable<EntityResponseType> {
    return this.http.patch<IDecreeIssue>(`${this.resourceUrl}/${getDecreeIssueIdentifier(decreeIssue) as number}`, decreeIssue, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDecreeIssue>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDecreeIssue[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDecreeIssueToCollectionIfMissing(
    decreeIssueCollection: IDecreeIssue[],
    ...decreeIssuesToCheck: (IDecreeIssue | null | undefined)[]
  ): IDecreeIssue[] {
    const decreeIssues: IDecreeIssue[] = decreeIssuesToCheck.filter(isPresent);
    if (decreeIssues.length > 0) {
      const decreeIssueCollectionIdentifiers = decreeIssueCollection.map(decreeIssueItem => getDecreeIssueIdentifier(decreeIssueItem)!);
      const decreeIssuesToAdd = decreeIssues.filter(decreeIssueItem => {
        const decreeIssueIdentifier = getDecreeIssueIdentifier(decreeIssueItem);
        if (decreeIssueIdentifier == null || decreeIssueCollectionIdentifiers.includes(decreeIssueIdentifier)) {
          return false;
        }
        decreeIssueCollectionIdentifiers.push(decreeIssueIdentifier);
        return true;
      });
      return [...decreeIssuesToAdd, ...decreeIssueCollection];
    }
    return decreeIssueCollection;
  }
}
