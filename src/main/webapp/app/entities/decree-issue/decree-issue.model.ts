import { IDecree } from 'app/entities/decree/decree.model';

export interface IDecreeIssue {
  id?: number;
  name?: string | null;
  decreees?: IDecree[] | null;
}

export class DecreeIssue implements IDecreeIssue {
  constructor(public id?: number, public name?: string | null, public decreees?: IDecree[] | null) {}
}

export function getDecreeIssueIdentifier(decreeIssue: IDecreeIssue): number | undefined {
  return decreeIssue.id;
}
