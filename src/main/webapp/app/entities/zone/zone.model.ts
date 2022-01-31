export interface IZone {
  id?: number;
  name?: string | null;
  amount1?: number | null;
  amount2?: number | null;
  amount3?: number | null;
}

export class Zone implements IZone {
  constructor(
    public id?: number,
    public name?: string | null,
    public amount1?: number | null,
    public amount2?: number | null,
    public amount3?: number | null
  ) {}
}

export function getZoneIdentifier(zone: IZone): number | undefined {
  return zone.id;
}
