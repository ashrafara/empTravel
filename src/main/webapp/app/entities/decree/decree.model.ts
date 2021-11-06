import * as dayjs from 'dayjs';
import { IEmployee } from 'app/entities/employee/employee.model';
import { IDecreeIssue } from 'app/entities/decree-issue/decree-issue.model';
import { DecType } from 'app/entities/enumerations/dec-type.model';

export interface IDecree {
  id?: number;
  decreenum?: number;
  decreeyear?: number;
  purpose?: string | null;
  dectype?: DecType | null;
  daynum?: number;
  city?: string | null;
  countrty?: string;
  sponsor?: string;
  proponent?: string;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  imageContentType?: string | null;
  image?: string | null;
  employees?: IEmployee[] | null;
  decreeissue?: IDecreeIssue | null;
}

export class Decree implements IDecree {
  constructor(
    public id?: number,
    public decreenum?: number,
    public decreeyear?: number,
    public purpose?: string | null,
    public dectype?: DecType | null,
    public daynum?: number,
    public city?: string | null,
    public countrty?: string,
    public sponsor?: string,
    public proponent?: string,
    public startDate?: dayjs.Dayjs | null,
    public endDate?: dayjs.Dayjs | null,
    public imageContentType?: string | null,
    public image?: string | null,
    public employees?: IEmployee[] | null,
    public decreeissue?: IDecreeIssue | null
  ) {}
}

export function getDecreeIdentifier(decree: IDecree): number | undefined {
  return decree.id;
}
